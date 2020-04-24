/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx.db.entidades;

/**
 *
 * @author joao
 */
public class Cliente {
    
    private int cod;
    private String nome;
    private String cpf;
    private String email;
    private String fone;
    
    public Cliente(){}

    public Cliente(int cod, String nome, String cpf, String email, String fone) {
        this.cod = cod;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.fone = fone;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    @Override
    public String toString() {
        return nome;
    }
    
    
}
