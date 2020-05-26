
package bellacanela.db.dal;

import bellacanelafx.db.entidades.NotaFiscal;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DALNotafiscal {
    public boolean gravar(NotaFiscal nf) {
        
        String sql = "insert into notafiscal(not_cod, not_nf, not_fornecedor, not_desc, not_vencimento, not_parcelas, not_total)"
                + "VALUES (default, '#2', '#3', '#4', '#5', '#6', '#7')";
        
        sql = sql.replaceAll("#2", ""+nf.getNumero());
        sql = sql.replaceAll("#3", ""+nf.getFornecedor().getCod());
        sql = sql.replaceAll("#4", ""+nf.getDesc());
        sql = sql.replaceAll("#5", ""+nf.getVencimento());
        sql = sql.replaceAll("#6", ""+nf.getParcelas());
        sql = sql.replaceAll("#7", ""+nf.getTotal());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar(NotaFiscal nf) {
        
        String sql = "update notafiscal SET not_nf='#2', not_fornecedor='#3', not_desc='#4', not_vencimento='#5', not_parcelas='#6', not_total='#7' WHERE not_cod = " +nf.getCod();
        
        sql = sql.replaceAll("#2", ""+nf.getNumero());
        sql = sql.replaceAll("#3", ""+nf.getFornecedor().getCod());
        sql = sql.replaceAll("#4", ""+nf.getDesc());
        sql = sql.replaceAll("#5", ""+nf.getVencimento());
        sql = sql.replaceAll("#6", ""+nf.getParcelas());
        sql = sql.replaceAll("#7", ""+nf.getTotal());

        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar(NotaFiscal nf) {
        return Banco.getCon().manipular("delete from notafiscal where not_cod= '"+nf.getCod()+"'");
    }
    
    public int getMax() {
        return Banco.getCon().getMaxPK("notafiscal", "not_cod");
    }
    
    public NotaFiscal get(int cod) {
        
        NotaFiscal aux = null;
        
        ResultSet rs = Banco.getCon().consultar("select * from notafiscal where not_cod= '"+cod+"'");
        try{
            if(rs.next())
              
                aux = new NotaFiscal(rs.getInt("not_cod"), rs.getString("not_nf"),
                                    new DALFornecedor().get(rs.getInt("not_fornecedor")),
                                    rs.getString("not_desc"), rs.getDate("not_vencimento"), rs.getInt("not_parcelas"),
                                    rs.getDouble("not-total"));
        }
        catch(SQLException sqlEx){}
        
        return aux;
    }
    
    public ArrayList<NotaFiscal> get(String filtro) {
        
        String sql = "select *from notafiscal";
        if(!filtro.isEmpty())
            sql += " where "+filtro;
        
        ArrayList<NotaFiscal> aux = new ArrayList();
        ResultSet rs = Banco.getCon().consultar(sql);
        
        try {
            while(rs.next())
                aux.add(new NotaFiscal(rs.getInt("not_cod"), rs.getString("not_nf"),
                                    new DALFornecedor().get(rs.getInt("not_fornecedor")),
                                    rs.getString("not_desc"), rs.getDate("not_vencimento"), rs.getInt("not_parcelas"),
                                    rs.getDouble("not-total")));
        }
        catch(SQLException sqlEx) {}
        
        return aux;
    }
}
