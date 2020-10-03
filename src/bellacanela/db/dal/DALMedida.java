
package bellacanela.db.dal;

import bellacanelafx.db.entidades.Medida;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DALMedida {
    
    
     public boolean gravar(Medida m) {
        
        String sql = "insert into medida(med_nome) values ('#1')";
        
        sql = sql.replaceAll("#1", ""+m.getNome());
        
        return Banco.conectar().getCon().manipular(sql);
    }
    
    public boolean alterar(Medida m) {
        
        String sql = "update medida set med_nome='#1' where med_cod="+m.getCod();
        
        sql = sql.replaceAll("#1", ""+m.getNome());

        
        return Banco.conectar().getCon().manipular(sql);
    }
    
    public boolean apagar(Medida m) {
        return Banco.conectar().getCon().manipular("delete from medida where med_cod= '"+m.getCod()+"'");
    }
    
    public int getMax() {
        return Banco.conectar().getCon().getMaxPK("medida", "med_cod");
    }
    
    public Medida get(int cod) {
        
        Medida aux = null;
        
        ResultSet rs = Banco.conectar().getCon().consultar("select * from medida where med_cod= '"+cod+"'");
        try{
            if(rs.next())
                aux = new Medida(rs.getInt("med_cod"),rs.getString("med_nome"));
        }
        catch(SQLException sqlEx){}
        
        return aux;
    }
    
    public ArrayList<Medida> get(String filtro) {
        
        String sql = "select *from medida";
        if(!filtro.isEmpty())
            sql += " where "+filtro;
        
        ArrayList<Medida> aux = new ArrayList();
        ResultSet rs = Banco.conectar().getCon().consultar(sql);
        
        try {
            while(rs.next())
                aux.add(new Medida(rs.getInt("med_cod"),rs.getString("med_nome")));
        }
        catch(SQLException sqlEx) {}
        
        return aux;
    }
}
