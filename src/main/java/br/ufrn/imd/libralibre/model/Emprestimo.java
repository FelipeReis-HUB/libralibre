package br.ufrn.imd.libralibre.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Emprestimo {

    private String idEmprestimo; 
    private String isbnLivro;
    private String idUsuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    public Emprestimo() {
    }

    public Emprestimo(String isbnLivro, String idUsuario) {
        // Programação Defensiva
        if (isbnLivro == null || isbnLivro.trim().isEmpty()) {
            throw new IllegalArgumentException("O ISBN do livro não pode ser nulo ou vazio.");
        }
        if (idUsuario == null || idUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo ou vazio.");
        }
        
        this.idEmprestimo = UUID.randomUUID().toString();
        this.isbnLivro = isbnLivro;
        this.idUsuario = idUsuario;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucao = null;
    }
    
    // métodos

    public boolean isAtivo() {
        return dataDevolucao == null;
    }

    public void registrarDevolucao() {
        if (isAtivo()) {
            this.dataDevolucao = LocalDate.now();
        } else {
            throw new IllegalStateException("Este empréstimo já foi finalizado.");
        }
    }
    
    // gets e sets

    public String getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(String idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public String getIsbnLivro() {
        return isbnLivro;
    }

    public void setIsbnLivro(String isbnLivro) {
        this.isbnLivro = isbnLivro;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprestimo that = (Emprestimo) o;
        return Objects.equals(idEmprestimo, that.idEmprestimo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmprestimo);
    }
}