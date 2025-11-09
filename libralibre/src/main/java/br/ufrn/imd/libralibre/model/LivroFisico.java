package br.ufrn.imd.libralibre.model;

/**
 * livro físico possui cópias
 * herda de livro (Princípio 'L' do SOLID).
 */

public class LivroFisico extends Livro {

    private int quantidadeTotal;
    private int quantidadeEmprestada;

    public LivroFisico() {
        super();
    }

    public LivroFisico(String isbn, String titulo, String autor, int quantidadeTotal) {
        super(isbn, titulo, autor);
        if (quantidadeTotal < 0) {
            throw new IllegalArgumentException("A quantidade total não pode ser negativa.");
        }
        this.quantidadeTotal = quantidadeTotal;
        this.quantidadeEmprestada = 0; // Começa com 0 emprestados
    }
    
    // boas práticas: override em métodos que especializam outros já existentes.
    @Override
    public boolean estaDisponivel() {
        return getCopiasDisponiveis() > 0;
    }
    
    public int getCopiasDisponiveis() {
        return quantidadeTotal - quantidadeEmprestada;
    }

    public void emprestarCopia() {
        if (estaDisponivel()) {
            this.quantidadeEmprestada++;
        } else {
            throw new IllegalStateException("Nenhuma cópia disponível para empréstimo.");
        }
    }
    
    public void devolverCopia() {
        if (this.quantidadeEmprestada > 0) {
            this.quantidadeEmprestada--;
        } else { // programação defensiva: não se pode devolver mais livros do que foi emprestado
            throw new IllegalStateException("Não há cópias emprestadas para devolver.");
        }
    }

    //gets e sets
    
    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(int quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public int getQuantidadeEmprestada() {
        return quantidadeEmprestada;
    }

    public void setQuantidadeEmprestada(int quantidadeEmprestada) {
        this.quantidadeEmprestada = quantidadeEmprestada;
    }

    @Override
    public String toString() {
        return String.format("LivroFisico [ISBN: %s, Titulo: %s, Disponíveis: %d/%d]",
                isbn, titulo, getCopiasDisponiveis(), quantidadeTotal);
    }
}