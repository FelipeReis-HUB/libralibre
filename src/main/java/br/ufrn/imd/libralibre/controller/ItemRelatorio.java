package br.ufrn.imd.libralibre.controller;

// serve apenas para facilitar a exibição no javaFX

public class ItemRelatorio {
    private int rank;
    private String titulo;
    private String isbn;
    private Long qtdEmprestimos;

    public ItemRelatorio(int rank, String titulo, String isbn, Long qtdEmprestimos) {
        this.rank = rank;
        this.titulo = titulo;
        this.isbn = isbn;
        this.qtdEmprestimos = qtdEmprestimos;
    }

    public int getRank() { return rank; }
    public String getTitulo() { return titulo; }
    public String getIsbn() { return isbn; }
    public Long getQtdEmprestimos() { return qtdEmprestimos; }
}