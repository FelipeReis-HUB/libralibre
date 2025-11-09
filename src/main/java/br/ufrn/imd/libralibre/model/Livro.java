package br.ufrn.imd.libralibre.model;

import java.util.Objects;

public abstract class Livro {

    protected String isbn;
    protected String titulo;
    protected String autor;

    public Livro() {
    }

    public Livro(String isbn, String titulo, String autor) {
        // Programação Defensiva
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("O ISBN não pode ser nulo ou vazio.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O Título não pode ser nulo ou vazio.");
        }
        
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
    }
    
    // métodos

    /**
     * método abstrato. cada tipo de livro (físico, digital) deve implementar sua própria lógica
     * para dizer se está disponível.
     */
    public abstract boolean estaDisponivel();


    // gets e sets

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Livro)) return false; // verifica se é um Livro ou se vazio
        Livro livro = (Livro) o;
        return Objects.equals(isbn, livro.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}