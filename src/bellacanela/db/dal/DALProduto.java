package bellacanela.db.dal;

import bellacanelafx.db.entidades.Categoria;
import bellacanelafx.db.entidades.Medida;
import bellacanelafx.db.entidades.Produtos;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DALProduto {
   
     public boolean gravar(Produtos p) {
        
        String sql = "insert into produto(prod_cod, prod_nome, prod_cat, prod_med, prod_preco) values (default, '#1','#2','#3','#4')";
        
        sql = sql.replaceAll("#1", ""+p.getNome());
        sql = sql.replaceAll("#2", ""+p.getCat().getCod());
        sql = sql.replaceAll("#3", ""+p.getMed().getCod());
        sql = sql.replaceAll("#4", ""+p.getPreco());
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar(Produtos p) {
        
        String sql = "update produto set prod_nome = '#1', prod_cat = '#2', prod_med ='#3', prod_preco ='#4' where prod_cod="+p.getCod();
        
        sql = sql.replaceAll("#1", ""+p.getNome());
        sql = sql.replaceAll("#2", ""+p.getCat().getCod());
        sql = sql.replaceAll("#3", ""+p.getMed().getCod());
        sql = sql.replaceAll("#4", ""+p.getPreco());        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar(Produtos p) {
        return Banco.getCon().manipular("delete from produto where prod_cod="+p.getCod());
    }
    
    public int getMax() {
        return Banco.getCon().getMaxPK("produto", "prod_cod");
    }
    
    public Produtos get(int cod) {
        
        Produtos aux = null;
        
        ResultSet rs = Banco.getCon().consultar("select * from produto where prod_cod="+cod);
        try{
            if(rs.next())
                aux = new Produtos(rs.getInt("prod_cod"),rs.getString("prod_nome"), new DALCategoria().get(rs.getInt("prod_cad")),new DALMedida().get(rs.getInt("prod_med")),rs.getDouble("prod_preco"));
        }
        catch(SQLException sqlEx){}
        
        return aux;
    }
    
    public ArrayList<Produtos> get(String filtro) {
        
        String sql = "select *from produto";
        if(!filtro.isEmpty())
            sql += " where "+filtro;
        
        ArrayList<Produtos> aux = new ArrayList();
        ResultSet rs = Banco.getCon().consultar(sql);
        Categoria cat = null;
        Medida med = null;
        try {
            while(rs.next()){
                
                cat = new DALCategoria().get(rs.getInt("prod_cat"));
                med = new DALMedida().get(rs.getInt("prod_med"));                
                aux.add(new Produtos(rs.getInt("prod_cod"),rs.getString("prod_nome"), cat , med ,rs.getDouble("prod_preco")));
            }
                
        }
        catch(SQLException sqlEx) {}
        
        return aux;
    }
}

