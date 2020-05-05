
package bellacanela.db.dal;

import bellacanelafx.db.entidades.Categoria;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DALCategoria {
    
    public boolean gravar(Categoria c) {
        
        String sql = "insert into categoria(cat_nome) values ('#1')";
        
        sql = sql.replaceAll("#1", ""+c.getNome());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar(Categoria c) {
        
        String sql = "update categoria set cat_nome='#1' where cat_cod="+c.getCod();
        
        sql = sql.replaceAll("#1", ""+c.getNome());

        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar(Categoria c) {
        return Banco.getCon().manipular("delete from categoria where cat_cod="+c.getCod());
    }
    
    public int getMax() {
        return Banco.getCon().getMaxPK("categoria", "cat_cod");
    }
    
    public Categoria get(int cod) {
        
        Categoria aux = null;
        
        ResultSet rs = Banco.getCon().consultar("select * from categoria where cat_cod='"+cod+"'");
        try{
            if(rs.next())
                aux = new Categoria(rs.getInt("cat_cod"),rs.getString("cat_nome"));
        }
        catch(SQLException sqlEx){}
        
        return aux;
    }
    
    public List<Categoria> get(String filtro) {
        
        String sql = "select *from categoria";
        if(!filtro.isEmpty())
            sql += " where "+filtro;
        
        List<Categoria> aux = new ArrayList();
        ResultSet rs = Banco.getCon().consultar(sql);
        
        try {
            while(rs.next())
                aux.add(new Categoria(rs.getInt("cat_cod"),rs.getString("cat_nome")));
        }
        catch(SQLException sqlEx) {}
        
        return aux;
    }
    
}
