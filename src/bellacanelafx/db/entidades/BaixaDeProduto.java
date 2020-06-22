package bellacanelafx.db.entidades;

import java.time.LocalDate;

public class BaixaDeProduto {
    private int cod, quantidade;
    private String motivo;
    private LocalDate data;
    private Funcionario funcionario;
    private Produtos produto;

    public BaixaDeProduto(int cod, int quantidade, String motivo, LocalDate data, Funcionario funcionario, Produtos produto) {
        this.cod = cod;
        this.quantidade = quantidade;
        this.motivo = motivo;
        this.data = data;
        this.funcionario = funcionario;
        this.produto = produto;
    }

    public BaixaDeProduto(int quantidade, String motivo, LocalDate data, Funcionario funcionario, Produtos produto) {
        this.quantidade = quantidade;
        this.motivo = motivo;
        this.data = data;
        this.funcionario = funcionario;
        this.produto = produto;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Produtos getProduto() {
        return produto;
    }

    public void setProduto(Produtos produto) {
        this.produto = produto;
    }
}
