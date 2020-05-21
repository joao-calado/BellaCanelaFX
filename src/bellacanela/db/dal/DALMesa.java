package bellacanela.db.dal;

import bellacanelafx.db.entidades.Mesa;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DALMesa {
    public boolean insert(Mesa m){
        String SQL = "INSERT INTO Mesa(mes_cod, mes_liberada) VALUES(DEFAULT, '#1')";
        
        SQL = SQL.replaceAll("#1", m.isLiberada()+"");
        
        return Banco.getCon().manipular(SQL);
    }
    
    public boolean update(Mesa m){
        String SQL = "UPDATE Mesa SET mes_liberada = '#1' WHERE mes_cod = " + m.getCod();
        
        SQL = SQL.replaceAll("#1", m.isLiberada()+"");
        
        return Banco.getCon().manipular(SQL);
    }
    
    public boolean delete(int cod){
        String SQL = "DELETE FROM Mesa WHERE mes_cod = " + cod;
        return Banco.getCon().manipular(SQL);
    }
    
    public Mesa getMesa(int cod){
        String SQL = "SELECT * FROM Mesa WHERE mes_cod = " + cod;
        Mesa m = null;
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        try{
            while(rs.next())
                m = new Mesa(rs.getInt("mes_cod"), rs.getBoolean("mes_liberada"));
        }
        catch(SQLException e){
            System.out.println("ERRO AO CONSULTAR MESA.");
        }
        
        return m;
    }
    
    public ArrayList<Mesa> getMesas(String filter){
        String SQL = "SELECT * FROM Mesa";
        if(!filter.equals(""))
            SQL += " WHERE " + filter;
        SQL += " ORDER BY mes_cod";
        
        ArrayList<Mesa> mesas = new ArrayList();
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        try{
            while(rs.next())
                mesas.add(new Mesa(rs.getInt("mes_cod"), rs.getBoolean("mes_liberada")));
        }
        catch(SQLException e){
            System.out.println("ERRO AO CONSULTAR MESAS.");
        }
        
        return mesas;
    }
}
