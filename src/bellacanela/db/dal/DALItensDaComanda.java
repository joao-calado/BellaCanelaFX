package bellacanela.db.dal;

import bellacanelafx.db.entidades.ItensDaComanda;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DALItensDaComanda {
    public boolean insert(ItensDaComanda itens){
        String SQL = "INSERT INTO ItensDaComanda(com_num, mes_cod, prod_cod, itens_qtde) VALUES('#1', '#2', '#3', '#4')";

        SQL = SQL.replaceAll("#1", itens.getCom_num()+"");
        SQL = SQL.replaceAll("#2", itens.getMes_cod()+"");
        SQL = SQL.replaceAll("#3", itens.getProduto().getCod()+"");
        SQL = SQL.replaceAll("#4", itens.getQtde()+"");

        return Banco.getCon().manipular(SQL);
    }
    
    public boolean delete(int com_num, int mes_cod){
        String SQL = "DELETE FROM ItensDaComanda WHERE com_num = '#1' AND mes_cod = '#2'";
        
        SQL = SQL.replaceAll("#1", com_num+"");
        SQL = SQL.replaceAll("#2", mes_cod+"");
        
        return Banco.getCon().manipular(SQL);
    }
    
    public ArrayList<ItensDaComanda> getItens(String filtro){
        ArrayList<ItensDaComanda> itens = new ArrayList();
        String SQL = "SELECT * FROM ItensDaComanda";
        
        if(!filtro.equals("")) SQL += " WHERE " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        try{
            while(rs.next()){
                itens.add(
                    new ItensDaComanda(rs.getInt("com_num"), rs.getInt("mes_cod"), new DALProduto().get(rs.getInt("prod_cod")), rs.getInt("itens_qtde"))
                );
            }
        }
        catch(SQLException e){
            System.out.println("DEU ERRO AO CONSULTAR ITENS DA COMANDA.");
        }
        
        return itens;
    }
}
