package bellacanelafx.db.entidades;

import java.util.Date;




public class NotaFiscal {
    private int cod;
    private String numero;
    private Fornecedor fornecedor;
    private String desc;
    private Date vencimento;
    private int parcelas;
    private double total;

    public NotaFiscal() {
    }

        
    public NotaFiscal(int cod, String numero, Fornecedor fornecedor, String desc, Date vencimento, int parcelas, double total) {
        this.cod = cod;
        this.numero = numero;
        this.fornecedor = fornecedor;
        this.desc = desc;
        this.vencimento = vencimento;
        this.parcelas = parcelas;
        this.total = total;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }  
        

}
