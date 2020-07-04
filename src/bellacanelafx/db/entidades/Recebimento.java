package bellacanelafx.db.entidades;

import java.time.LocalDate;

/**
 *
 * @author joao
 */
public class Recebimento {
    
    private int cod;
    private int cliente;
    private String tipo;
    private double valor;
    private LocalDate recebimento;
    private LocalDate vencimento;
    private String status;
    private int mesa;
    private int comanda;
    private int pai;

    public Recebimento() {}

    public Recebimento(int cod, int cliente, String tipo, double valor, LocalDate recebimento, LocalDate vencimento, String status, int mesa, int comanda) {
        this.cod = cod;
        this.cliente = cliente;
        this.tipo = tipo;
        this.valor = valor;
        this.recebimento = recebimento;
        this.vencimento = vencimento;
        this.status = status;
        this.mesa = mesa;
        this.comanda = comanda;
    }

    public Recebimento(int cod, int cliente, String tipo, double valor, LocalDate recebimento, LocalDate vencimento, String status, int mesa, int comanda, int pai) {
        this.cod = cod;
        this.cliente = cliente;
        this.tipo = tipo;
        this.valor = valor;
        this.recebimento = recebimento;
        this.vencimento = vencimento;
        this.status = status;
        this.mesa = mesa;
        this.comanda = comanda;
        this.pai = pai;
    }
    
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getRecebimento() {
        return recebimento;
    }

    public void setRecebimento(LocalDate recebimento) {
        this.recebimento = recebimento;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public int getComanda() {
        return comanda;
    }

    public void setComanda(int comanda) {
        this.comanda = comanda;
    }

    public int getPai() {
        return pai;
    }

    public void setPai(int pai) {
        this.pai = pai;
    }
}
