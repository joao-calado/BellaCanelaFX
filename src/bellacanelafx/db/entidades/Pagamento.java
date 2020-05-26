package bellacanelafx.db.entidades;
import java.sql.Date;

public class Pagamento {    
    private int cod;
    private int parcela;
    private String desc;
    private double valor;
    private double valorpago;
    private double DesJur;
    private Date vencimento;
    private Date pagamento;
    private boolean parcial;

    public Pagamento() {
    }

    public Pagamento(int cod, int parcela, String desc, double valor, double valorpago, double DesJur, Date vencimento, Date pagamento, boolean parcial) {
        this.cod = cod;
        this.parcela = parcela;
        this.desc = desc;
        this.valor = valor;
        this.valorpago = valorpago;
        this.DesJur = DesJur;
        this.vencimento = vencimento;
        this.pagamento = pagamento;
        this.parcial = parcial;
    }

    public boolean isParcial() {
        return parcial;
    }

    public void setParcial(boolean parcial) {
        this.parcial = parcial;
    }   
    

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getParcela() {
        return parcela;
    }

    public void setParcela(int parcela) {
        this.parcela = parcela;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValorpago() {
        return valorpago;
    }

    public void setValorpago(double valorpago) {
        this.valorpago = valorpago;
    }

    public double getDesJur() {
        return DesJur;
    }

    public void setDesJur(double DesJur) {
        this.DesJur = DesJur;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Date getPagamento() {
        return pagamento;
    }

    public void setPagamento(Date pagamento) {
        this.pagamento = pagamento;
    }
      
        
}
