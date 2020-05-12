package bellacanelafx.db.entidades;

import java.time.LocalDate;

public class Funcionario {
    private int cod;
    private LocalDate dataNascimento;
    private String nome, telefone;
    private double salario;

    public Funcionario(int cod, LocalDate dataNascimento, String nome, String telefone, double salario) {
        this.cod = cod;
        this.dataNascimento = dataNascimento;
        this.nome = nome;
        this.telefone = telefone;
        this.salario = salario;
    }
    
    public Funcionario(LocalDate dataNascimento, String nome, String telefone, double salario) {
        this(0, dataNascimento, nome, telefone, salario);
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}
