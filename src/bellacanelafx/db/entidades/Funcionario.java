package bellacanelafx.db.entidades;

public class Funcionario {
    private int cod, idade;
    private String nome, telefone;
    private double salario;

    public Funcionario(int cod, String nome, int idade, String telefone, double salario) {
        this.cod = cod;
        this.idade = idade;
        this.nome = nome;
        this.telefone = telefone;
        this.salario = salario;
    }
    
    public Funcionario(String nome, int idade, String telefone, double salario) {
        this(-1, nome, idade, telefone, salario);
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
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
