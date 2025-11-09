package br.ufrn.imd.libralibre.model;

import java.util.Objects;

public class Usuario {

    private String id;
    private String nome;

    public Usuario() {
    }

    public Usuario(String id, String nome) {
        // Programação Defensiva: Garantir que dados essenciais não sejam nulos/vazios
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo ou vazio.");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário não pode ser nulo ou vazio.");
        }
        
        this.id = id;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id); 
    }

    @Override
    public int hashCode() {
        // Baseia o hashCode no campo único (ID)
        return Objects.hash(id); 
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}