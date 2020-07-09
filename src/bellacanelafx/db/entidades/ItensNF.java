package bellacanelafx.db.entidades;


public class ItensNF {
    private int cod;
    private NotaFiscal nf;
    private Produtos prod;
    private int qtde;
    private double preco;
    private double total;

    public ItensNF() {
    }

    public ItensNF(int cod, NotaFiscal nf, Produtos prod, int qtde, double preco, double total) {
        this.cod = cod;
        this.nf = nf;
        this.prod = prod;
        this.qtde = qtde;
        this.preco = preco;
        this.total = total;
    }
    public ItensNF(Produtos prod, int qtde, double preco) {
        this.prod = prod;
        this.qtde = qtde;
        this.preco = preco;
        this.total = preco * qtde;
    }    
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public NotaFiscal getNf() {
        return nf;
    }

    public void setNf(NotaFiscal nf) {
        this.nf = nf;
    }

    public Produtos getProd() {
        return prod;
    }

    public void setProd(Produtos prod) {
        this.prod = prod;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    

}
