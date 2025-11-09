package br.ufrn.imd.libralibre;

import java.util.Scanner;

import br.ufrn.imd.libralibre.model.LivroDigital;
import br.ufrn.imd.libralibre.model.LivroFisico;
import br.ufrn.imd.libralibre.model.Usuario;
import br.ufrn.imd.libralibre.service.BibliotecaService;

public class MainApp {
    private static final BibliotecaService service = new BibliotecaService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Libralibre - Gestor de Biblioteca");

        // boas praticas: 'main' apenas gerencia o loop do menu
        // a lógica de cada opção é separada em métodos privados (Clean Code)
        while (true) {
            exibirMenu();
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    adicionarLivroFisico();
                    break;
                case "2":
                    adicionarLivroDigital();
                    break;
                case "3":
                    adicionarUsuario();
                    break;
                case "4":
                    realizarEmprestimo();
                    break;
                case "5":
                    realizarDevolucao();
                    break;
                case "6":
                    listarAcervo();
                    break;
                case "7":
                    listarUsuarios();
                    break;
                case "8":
                    exibirRelatorio();
                    break;
                case "0":
                    System.out.println("Obrigado por usar o Libralibre. Até logo!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n-=-=- LibraLibre -=-=-");
        System.out.println("1. Adicionar Livro Físico");
        System.out.println("2. Adicionar Livro Digital");
        System.out.println("3. Adicionar Usuário");
        System.out.println("4. Realizar Empréstimo");
        System.out.println("5. Realizar Devolução");
        System.out.println("6. Listar Acervo de Livros");
        System.out.println("7. Listar Usuários");
        System.out.println("8. Exibir Relatório de Empréstimos");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // métodos do menu

    private static void adicionarLivroFisico() {
        try {
            System.out.println("\n-=- Adicionar Livro Físico -=-");
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Autor: ");
            String autor = scanner.nextLine();
            System.out.print("Quantidade de Cópias: ");
            int copias = Integer.parseInt(scanner.nextLine()); // string p int insta

            LivroFisico livro = new LivroFisico(isbn, titulo, autor, copias);
            service.adicionarLivro(livro);

            System.out.println("Sucesso: Livro físico '" + titulo + "' adicionado ao acervo.");

        } catch (NumberFormatException e) {
            // pd erro se o usuário digitar "abc" na quantidade
            System.out.println("Erro: A quantidade de cópias deve ser um número.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            // captura o erro vindo do service
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void adicionarLivroDigital() {
        try {
            System.out.println("\n-=- Adicionar Livro Digital -=-");
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Autor: ");
            String autor = scanner.nextLine();

            LivroDigital livro = new LivroDigital(isbn, titulo, autor);
            service.adicionarLivro(livro);

            System.out.println("Sucesso: Livro digital '" + titulo + "' adicionado ao acervo.");

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void adicionarUsuario() {
        try {
            System.out.println("\n-=- Adicionar Usuário -=-");
            System.out.print("ID (Matrícula ou CPF): ");
            String id = scanner.nextLine();
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            Usuario usuario = new Usuario(id, nome);
            service.adicionarUsuario(usuario);

            System.out.println("Sucesso: Usuário '" + nome + "' adicionado.");

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void realizarEmprestimo() {
        try {
            System.out.println("\n-=- Realizar Empréstimo -=-");
            System.out.print("ISBN do Livro: ");
            String isbn = scanner.nextLine();
            System.out.print("ID do Usuário: ");
            String idUsuario = scanner.nextLine();

            service.realizarEmprestimo(isbn, idUsuario);
            
            System.out.println("Sucesso: Empréstimo realizado!");

        } catch (IllegalStateException e) {
            // boas práticas
            System.out.println("Erro ao realizar empréstimo: " + e.getMessage());
        }
    }

    private static void realizarDevolucao() {
        try {
            System.out.println("\n-=- Realizar Devolução -=-");
            System.out.print("ISBN do Livro: ");
            String isbn = scanner.nextLine();
            System.out.print("ID do Usuário: ");
            String idUsuario = scanner.nextLine();

            service.realizarDevolucao(isbn, idUsuario);
            
            System.out.println("Sucesso: Devolução realizada!");

        } catch (IllegalStateException e) {
            System.out.println("Erro ao realizar devolução: " + e.getLocalizedMessage());
        }
    }

    private static void listarAcervo() {
        System.out.println("\n-- Acervo da Biblioteca --");
        if (service.getAcervo().isEmpty()) {
            System.out.println("O acervo está vazio.");
            return;
        }

        // polimorfismo! o `toString()` de cada tipo de livro será mostrado.
        service.getAcervo().forEach(livro -> System.out.println(livro.toString()));
    }

    private static void listarUsuarios() {
        System.out.println("\n-- Usuários Cadastrados --");
        if (service.getUsuarios().isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        
        service.getUsuarios().forEach(usuario -> System.out.println(usuario.toString()));
    }

    private static void exibirRelatorio() {
    System.out.println("\n-=- Relatório Consolidado de Empréstimos -=-");
    java.util.Map<String, Long> relatorio = service.getRelatorioConsolidado();

    if (relatorio.isEmpty()) {
        System.out.println("Nenhum emprestimo foi realizado ainda.");
        return;
    }

    System.out.println("Ranking de Livros Mais Populares:");
    int rank = 1;
    
    for (java.util.Map.Entry<String, Long> entrada : relatorio.entrySet()) {
        String isbn = entrada.getKey();
        Long contagem = entrada.getValue();

        String titulo = service.buscarLivroPorIsbn(isbn)
                               .map(livro -> livro.getTitulo())
                               .orElse("Título Desconhecido (ISBN: " + isbn + ")");

        System.out.printf("  %d. %s: %d empréstimo(s)\n", rank, titulo, contagem);
        rank++;
    }

    System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    System.out.println("Total de Empréstimos Registrados: " + service.getTotalEmprestimosRegistrados());
}
}