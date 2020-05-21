package bellacanelafx.db.entidades;

public class ItensDaComanda {
    private int com_num, mes_cod, qtde;
    private Produtos produto;

    public ItensDaComanda(int com_num, int mes_cod, Produtos produto, int qtde) {
        this.com_num = com_num;
        this.mes_cod = mes_cod;
        this.produto = produto;
        this.qtde = qtde;
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

    public Produtos getProduto() {
        return produto;
    }

    public void setProduto(Produtos produto) {
        this.produto = produto;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }
}
