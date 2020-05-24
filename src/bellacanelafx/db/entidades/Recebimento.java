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

    public Recebimento() {}

    public Recebimento(int cod, int cliente, String tipo, double valor, LocalDate recebimento, LocalDate vencimento, String status) {
        this.cod = cod;
        this.cliente = cliente;
        this.tipo = tipo;
        this.valor = valor;
        this.recebimento = recebimento;
        this.vencimento = vencimento;
        this.status = status;
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
    
}
