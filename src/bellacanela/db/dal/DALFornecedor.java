
package bellacanela.db.dal;

import bellacanelafx.db.entidades.Fornecedor;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DALFornecedor {
    
    public boolean gravar(Fornecedor f) {
        
        String sql = "insert into fornecedor(for_cod, for_nome,for_telefone,for_email,for_desc) values (default, '#1','#2','#3','#4')";
        
        sql = sql.replaceAll("#1", ""+f.getNome());
        sql = sql.replaceAll("#2", ""+f.getTelefone());
        sql = sql.replaceAll("#3", ""+f.getEmail());
        sql = sql.replaceAll("#4", ""+f.getDesc());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar(Fornecedor f) {
        
        String sql = "update fornecedor set for_nome ='#1',for_telefone = '#2' ,for_email = '#3', for_desc= '#4' where for_cod="+f.getCod();
        
        sql = sql.replaceAll("#1", ""+f.getNome());
        sql = sql.replaceAll("#2", ""+f.getTelefone());
        sql = sql.replaceAll("#3", ""+f.getEmail());
        sql = sql.replaceAll("#4", ""+f.getDesc());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar(Fornecedor f) {
        return Banco.getCon().manipular("delete from fornecedor where for_cod="+f.getCod());
    }
    
    public int getMax() {
        return Banco.getCon().getMaxPK("fornecedor", "for_cod");
    }
    
    public Fornecedor get(int cod) {
        
        Fornecedor aux = null;
        
        ResultSet rs = Banco.getCon().consultar("select * from fornecedor where for_cod="+cod);
        try{
            if(rs.next())
                aux = new Fornecedor(rs.getInt("for_cod"),rs.getString("for_nome"),rs.getString("for_telefone"),rs.getString("for_email"),rs.getString("for_desc"));
        }
        catch(SQLException sqlEx){}
        
        return aux;
    }
    
    public ArrayList<Fornecedor> get(String filtro) {
        
        String sql = "select * from fornecedor";
        if(!filtro.isEmpty())
            sql += " where "+filtro;
        
        ArrayList<Fornecedor> aux = new ArrayList();
        ResultSet rs = Banco.getCon().consultar(sql);
        
        try {
            while(rs.next())
                aux.add(new Fornecedor(rs.getInt("for_cod"),rs.getString("for_nome"),rs.getString("for_telefone"),rs.getString("for_email"),rs.getString("for_desc")));
        }
        catch(SQLException sqlEx) {}
        
        return aux;
    }
    
    
    
}
