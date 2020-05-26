package bellacanelafx.db.entidades;

import java.time.LocalDate;
import java.util.ArrayList;

public class Comanda {
    private int com_num, mes_cod;
    private boolean aberta;
    private LocalDate data;
    private String descricao;
    private Cliente cliente;
    private ArrayList<ItensDaComanda> itens;

    public Comanda(int com_num, int mes_cod, boolean aberta, LocalDate data, String descricao, Cliente cliente, ArrayList<ItensDaComanda> itens) {
        this.com_num = com_num;
        this.mes_cod = mes_cod;
        this.aberta = aberta;
        this.data = data;
        this.descricao = descricao;
        this.cliente = cliente;
        this.itens = itens;
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

    public boolean isAberta() {
        return aberta;
    }

    public void setAberta(boolean aberta) {
        this.aberta = aberta;
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

    public boolean inserirItem(ItensDaComanda item){
        for(int i = 0; i < itens.size(); i++)
            if(itens.get(i).getProduto().getCod() == item.getProduto().getCod()){
                itens.get(i).setQtde(itens.get(i).getQtde()+item.getQtde());
                return true;
            }
        itens.add(item);
        return true;
    }
    
    public int deletarItem(ItensDaComanda item, int qtde){
        for(int i = 0; i < itens.size(); i++){
            if(itens.get(i).getProduto().getCod() == item.getProduto().getCod()){
                if(itens.get(i).getQtde() >= qtde){
                    if(itens.get(i).getQtde() == qtde)
                        itens.remove(i);
                    else
                        itens.get(i).setQtde(itens.get(i).getQtde() - qtde);
                    return 0;
                }
                return 1;
            }
        }
        return 2;
    }
    
    public ArrayList<ItensDaComanda> getItens() {
        return itens;
    }

    public void setItens(ArrayList<ItensDaComanda> itens) {
        this.itens = itens;
    }
}
