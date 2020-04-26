
package bellacanela.db.dal;

import bellacanelafx.db.entidades.Produtos;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DALProduto {
   
     public boolean gravar(Produtos p) {
        
        String sql = "insert into cliente(prod_nome, prod_cat, prod_med) values ('#1','#2','#3')";
        
        sql = sql.replaceAll("#1", ""+p.getNome());
        sql = sql.replaceAll("#2", ""+p.getCat().getCod());
        sql = sql.replaceAll("#3", ""+p.getMed().getCod());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar(Produtos p) {
        
        String sql = "update produto set prod_nome = '#1', prod_cat = '#2', prod_med ='#3' where prod_cod="+p.getCod();
        
        sql = sql.replaceAll("#1", ""+p.getNome());
        sql = sql.replaceAll("#2", ""+p.getCat().getCod());
        sql = sql.replaceAll("#3", ""+p.getMed().getCod());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar(Produtos p) {
        return Banco.getCon().manipular("delete from produto where cli_cod="+p.getCod());
    }
    
    public int getMax() {
        return Banco.getCon().getMaxPK("produtp", "prod_cod");
    }
    
    public Produtos get(int cod) {
        
        Produtos aux = null;
        
        ResultSet rs = Banco.getCon().consultar("select * from cliente where cli_cod="+cod);
        try{
            if(rs.next())
                aux = new Produtos(rs.getInt("prod_cod"),rs.getString("prod_nome"), new DALCategoria().get(rs.getInt("prod_cad")),new DALMedida().get(rs.getInt("prod_med")));
        }
        catch(SQLException sqlEx){}
        
        return aux;
    }
    
    public List<Produtos> get(String filtro) {
        
        String sql = "select *from produto";
        if(!filtro.isEmpty())
            sql += " where "+filtro;
        
        List<Produtos> aux = new ArrayList();
        ResultSet rs = Banco.getCon().consultar(sql);
        
        try {
            while(rs.next())
                aux.add(new Produtos(rs.getInt("prod_cod"),rs.getString("prod_nome"), new DALCategoria().get(rs.getInt("prod_cad")),new DALMedida().get(rs.getInt("prod_med"))));
        }
        catch(SQLException sqlEx) {}
        
        return aux;
    }
}
