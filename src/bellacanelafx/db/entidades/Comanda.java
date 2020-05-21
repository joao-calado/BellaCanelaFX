package bellacanelafx.db.entidades;

import java.time.LocalDate;
import java.util.ArrayList;

public class Comanda {
    private int com_num, mes_cod;
    private LocalDate data;
    private String descricao;
    private Cliente cliente;
    private ArrayList<Produtos> produtos;

    public Comanda(int com_num, int mes_cod, LocalDate data, String descricao, Cliente cliente, ArrayList<Produtos> produtos) {
        this.com_num = com_num;
        this.mes_cod = mes_cod;
        this.data = data;
        this.descricao = descricao;
        this.cliente = cliente;
        this.produtos = produtos;
    }

    public int getCom_num() {
        return com_num;
    }

    public void setCom_num(int com_num) {
        this.com_num = com_num;
    }

    public int getMes_cod() {
        return mes_cod;
    }

    public void setMes_cod(int mes_cod) {
        this.mes_cod = mes_cod;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Produtos> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produtos> produtos) {
        this.produtos = produtos;
    }
}
