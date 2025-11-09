package br.ufrn.imd.libralibre.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.ufrn.imd.libralibre.model.Emprestimo;
import br.ufrn.imd.libralibre.model.Livro;
import br.ufrn.imd.libralibre.model.LivroFisico;
import br.ufrn.imd.libralibre.model.Usuario;

public class BibliotecaService {
    
    private final PersistenciaService persistenciaService;

    // por enquanto, os dados que ficam na memória
    // no futuro, esta classe usará um "PersistenciaService" para ler/salvar em JSON.
    private List<Livro> acervo = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public BibliotecaService() {
        this.persistenciaService = new PersistenciaService();
        carregarDadosDoDisco();
    }

    // métodos auxiliares

    private void carregarDadosDoDisco() {
        PersistenciaService.DataHolder dados = persistenciaService.carregarDados();
        this.acervo = dados.acervo;
        this.usuarios = dados.usuarios;
        this.emprestimos = dados.emprestimos;
    }


    private void salvarDadosNoDisco() {
        persistenciaService.salvarDados(acervo, usuarios, emprestimos);
    }

    // métodos de busca

    /**
     * busca um usuário pelo seu ID.
     * se encontrar, retorna um Optional contendo o usuário
     */
    public Optional<Usuario> buscarUsuarioPorId(String id) {
        // (Boas Práticas) Usando Java Streams para uma busca limpa e funcional.
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public Optional<Livro> buscarLivroPorIsbn(String isbn) {
        return acervo.stream()
                .filter(l -> l.getIsbn().equals(isbn))
                .findFirst();
    }
   

    // busca um emprestino ativo para um usuário e livro específicos
    public Optional<Emprestimo> buscarEmprestimoAtivo(String isbn, String idUsuario) {
        return emprestimos.stream()
                .filter(e -> e.isAtivo() && 
                             e.getIsbnLivro().equals(isbn) &&
                             e.getIdUsuario().equals(idUsuario))
                .findFirst();
    }

    // métodos de cadastro

    public void adicionarUsuario(Usuario usuario) {
        if (buscarUsuarioPorId(usuario.getId()).isPresent()) {
            throw new IllegalStateException("Já existe um usuário com o ID: " + usuario.getId());
        }
        usuarios.add(usuario);
        salvarDadosNoDisco();
    }

    public void adicionarLivro(Livro livro) {
        if (buscarLivroPorIsbn(livro.getIsbn()).isPresent()) {
            throw new IllegalStateException("Já existe um livro com o ISBN: " + livro.getIsbn());
        }
        acervo.add(livro);
        salvarDadosNoDisco();
    }

    // métodos de lógica do negocio

    public void realizarEmprestimo(String isbn, String idUsuario) {
        // programação defensiva!! valida se os objetos existem
        Usuario usuario = buscarUsuarioPorId(idUsuario)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado: " + idUsuario));
        
        Livro livro = buscarLivroPorIsbn(isbn)
                .orElseThrow(() -> new IllegalStateException("Livro não encontrado: " + isbn));

        // valida se já não há um empréstimo ativo
        if(buscarEmprestimoAtivo(isbn, idUsuario).isPresent()){
            throw new IllegalStateException("Este usuário já está com este livro emprestado.");
        }

        // (polimorfismo)
        if (!livro.estaDisponivel()) {
            throw new IllegalStateException("O livro '" + livro.getTitulo() + "' não está disponível.");
        }

        // se for fisico, gerencia as cópias
        if (livro instanceof LivroFisico) {
            ((LivroFisico) livro).emprestarCopia();
        }

        Emprestimo novoEmprestimo = new Emprestimo(isbn, idUsuario);
        emprestimos.add(novoEmprestimo);
        salvarDadosNoDisco();
    }


    public void realizarDevolucao(String isbn, String idUsuario) {
        Livro livro = buscarLivroPorIsbn(isbn)
                .orElseThrow(() -> new IllegalStateException("Livro não encontrado: " + isbn));
        
        // encontra o empréstimo *ativo*
        Emprestimo emprestimo = buscarEmprestimoAtivo(isbn, idUsuario)
                .orElseThrow(() -> new IllegalStateException("Não foi encontrado um empréstimo ativo para este livro e usuário."));

        // se o livro for físico, devolve a cópia
        if (livro instanceof LivroFisico) {
            ((LivroFisico) livro).devolverCopia();
        }

        emprestimo.registrarDevolucao();
        salvarDadosNoDisco();
    }


    // métodos de consulta (para UI)

    public List<Livro> getAcervo() {
        return acervo;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Emprestimo> getEmprestimosAtivos() {
        return emprestimos.stream()
                .filter(Emprestimo::isAtivo)
                .collect(Collectors.toList());
    }
}