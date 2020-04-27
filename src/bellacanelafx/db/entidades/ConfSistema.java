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
public class ConfSistema {
    
    private int cod;
    private String nome;
    private String cep;
    private String endereco;
    private String cidade;
    private String uf;
    private String cnpj;
    private String razao;
    private String fone;
    private String email;
    private String cor1;
    private String cor2;

    public ConfSistema() {}

    public ConfSistema(int cod, String nome, String cep, String endereco, String cidade, String uf, String cnpj, String razao, String fone, String email, String cor1, String cor2) {
        this.cod = cod;
        this.nome = nome;
        this.cep = cep;
        this.endereco = endereco;
        this.cidade = cidade;
        this.uf = uf;
        this.cnpj = cnpj;
        this.razao = razao;
        this.fone = fone;
        this.email = email;
        this.cor1 = cor1;
        this.cor2 = cor2;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCor1() {
        return cor1;
    }

    public void setCor1(String cor1) {
        this.cor1 = cor1;
    }

    public String getCor2() {
        return cor2;
    }

    public void setCor2(String cor2) {
        this.cor2 = cor2;
    }

    @Override
    public String toString() {
        return "ConfSistema{" + "nome=" + nome + '}';
    }
}
