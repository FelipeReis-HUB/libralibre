package br.ufrn.imd.libralibre.model;

/**
 * representa um livro digital.
 * Herda de Livro (Princípio 'L' do SOLID).
 */
public class LivroDigital extends Livro {

    public LivroDigital() {
        super();
    }

    public LivroDigital(String isbn, String titulo, String autor) {
        super(isbn, titulo, autor);
    }

    /**
     * livro digital está sempre disponivel
     */
    @Override
    public boolean estaDisponivel() {
        return true;
    }
    
    @Override
    public String toString() {
        return String.format("LivroDigital [ISBN: %s, Titulo: %s, (Sempre Disponível)]",
                isbn, titulo);
    }
}