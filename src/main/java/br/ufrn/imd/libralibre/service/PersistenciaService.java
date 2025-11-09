package br.ufrn.imd.libralibre.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.ufrn.imd.libralibre.model.Emprestimo;
import br.ufrn.imd.libralibre.model.Livro;
import br.ufrn.imd.libralibre.model.LivroDigital;
import br.ufrn.imd.libralibre.model.LivroFisico;
import br.ufrn.imd.libralibre.model.Usuario;
import br.ufrn.imd.libralibre.util.LocalDateAdapter;


public class PersistenciaService {

    // Boas Praticas: gsonBuilder para formatar o JSON de forma legível ("pretty printing")
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setPrettyPrinting().create();

    private static final String USUARIOS_FILE = "usuarios.json";
    private static final String EMPRESTIMOS_FILE = "emprestimos.json";
    
    // (KISS) para lidar com a herança, salvamos em dois ficheiros separados
    private static final String LIVROS_FISICOS_FILE = "livros_fisicos.json";
    private static final String LIVROS_DIGITAIS_FILE = "livros_digitais.json";


    /**
     * (Programação Defensiva) usa try-with-resources para garantir que os
     * ficheiros são fechados mesmo se ocorrer um erro.
     */
    public void salvarDados(List<Livro> acervo, List<Usuario> usuarios, List<Emprestimo> emprestimos) {
        
        // separa o acervo de volta nas duas listas concretas
        List<LivroFisico> fisicos = acervo.stream()
                .filter(l -> l instanceof LivroFisico)
                .map(l -> (LivroFisico) l)
                .collect(Collectors.toList());
        
        List<LivroDigital> digitais = acervo.stream()
                .filter(l -> l instanceof LivroDigital)
                .map(l -> (LivroDigital) l)
                .collect(Collectors.toList());

        salvarJson(LIVROS_FISICOS_FILE, fisicos);
        salvarJson(LIVROS_DIGITAIS_FILE, digitais);
        salvarJson(USUARIOS_FILE, usuarios);
        salvarJson(EMPRESTIMOS_FILE, emprestimos);
    }
    
    public DataHolder carregarDados() {
        // carrega as listas individuais
        List<LivroFisico> fisicos = carregarJson(LIVROS_FISICOS_FILE, new TypeToken<List<LivroFisico>>(){}.getType());
        List<LivroDigital> digitais = carregarJson(LIVROS_DIGITAIS_FILE, new TypeToken<List<LivroDigital>>(){}.getType());
        
        // (liskov) duas listas em uma única List<Livro>
        List<Livro> acervo = Stream.concat(fisicos.stream(), digitais.stream())
                                 .collect(Collectors.toList());
        
        List<Usuario> usuarios = carregarJson(USUARIOS_FILE, new TypeToken<List<Usuario>>(){}.getType());
        List<Emprestimo> emprestimos = carregarJson(EMPRESTIMOS_FILE, new TypeToken<List<Emprestimo>>(){}.getType());

        return new DataHolder(acervo, usuarios, emprestimos);
    }
    
    // metodos auxiliadores 

  
    private <T> void salvarJson(String filename, List<T> lista) {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o ficheiro " + filename + ": " + e.getMessage());
        }
    }


    private <T> List<T> carregarJson(String filename, Type tipoDaLista) {
        if (!Files.exists(Paths.get(filename))) {
            return new ArrayList<>(); // aqui retorna uma lista vazia se não existe o ficheiro
        }
        
        try (FileReader reader = new FileReader(filename)) {
            List<T> lista = gson.fromJson(reader, tipoDaLista);
            return (lista != null) ? lista : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o ficheiro " + filename + ": " + e.getMessage());
            return new ArrayList<>(); // retorna lista vazia em caso de erro
        }
    }

    
    public static class DataHolder {
        public final List<Livro> acervo;
        public final List<Usuario> usuarios;
        public final List<Emprestimo> emprestimos;

        public DataHolder(List<Livro> acervo, List<Usuario> usuarios, List<Emprestimo> emprestimos) {
            this.acervo = acervo;
            this.usuarios = usuarios;
            this.emprestimos = emprestimos;
        }
    }
}