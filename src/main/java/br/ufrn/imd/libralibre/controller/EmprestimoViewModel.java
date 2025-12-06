package br.ufrn.imd.libralibre.controller;

import java.time.format.DateTimeFormatter;
import br.ufrn.imd.libralibre.model.Emprestimo;

public class EmprestimoViewModel {
    
    private String tituloLivro;
    private String nomeUsuario;
    private String dataEmprestimo;
    private String dataDevolucao;
    private String status;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EmprestimoViewModel(Emprestimo emprestimo, String tituloLivro, String nomeUsuario) {
        this.tituloLivro = tituloLivro;
        this.nomeUsuario = nomeUsuario;
        
        this.dataEmprestimo = emprestimo.getDataEmprestimo().format(formatter);
        
        if (emprestimo.getDataDevolucao() != null) {
            this.dataDevolucao = emprestimo.getDataDevolucao().format(formatter);
            this.status = "Finalizado";
        } else {
            this.dataDevolucao = "-";
            this.status = "Ativo";
        }
    }

    public String getTituloLivro() { return tituloLivro; }
    public String getNomeUsuario() { return nomeUsuario; }
    public String getDataEmprestimo() { return dataEmprestimo; }
    public String getDataDevolucao() { return dataDevolucao; }
    public String getStatus() { return status; }
}