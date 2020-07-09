
package bellacanela.db.dal;

import bellacanelafx.db.entidades.ItensNF;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DALItensNF {
    
   public boolean gravar(ItensNF nf) {
        
        String sql = "insert into itensnf(ite_cod, ite_notafiscal, ite_produto, ite_qtde, ite_preco, ite_total, ite_ctrl)"
                + "VALUES (default, '#2', '#3', '#4', '#5', '#6', '#7')";
        
        sql = sql.replaceAll("#2", ""+nf.getNf().getCod());
        sql = sql.replaceAll("#3", ""+nf.getProd().getCod());
        sql = sql.replaceAll("#4", ""+nf.getQtde());
        sql = sql.replaceAll("#5", ""+nf.getPreco());
        sql = sql.replaceAll("#6", ""+nf.getTotal());        
       
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar(ItensNF nf) {
        
        String sql = "update itensnf SET ite_notafiscal='#2', ite_produto='#3', ite_qtde='#4', ite_preco='#5', ite_total='#6', ite_ctrl = '#7' WHERE ite_cod = " +nf.getCod();
        
        sql = sql.replaceAll("#2", ""+nf.getNf().getCod());
        sql = sql.replaceAll("#3", ""+nf.getProd().getCod());
        sql = sql.replaceAll("#4", ""+nf.getQtde());
        sql = sql.replaceAll("#5", ""+nf.getPreco());
        sql = sql.replaceAll("#6", ""+nf.getTotal());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar(ItensNF nf) {
        return Banco.getCon().manipular("delete from itensnf where ite_cod= '"+nf.getCod()+"'");
    }
    
    public int getMax() {
        return Banco.getCon().getMaxPK("itensnf", "ite_cod");
    }
    

    
    public ItensNF get(int cod) {
        
        ItensNF aux = null;
        
        ResultSet rs = Banco.getCon().consultar("select * from itensnf where ite_cod= '"+cod+"'");
        try{
            if(rs.next())
                //(ite_cod, ite_notafiscal, ite_produto, ite_qtde, ite_preco, ite_total)
                aux = new ItensNF(rs.getInt("ite_cod"), new DALNotafiscal().get(rs.getInt("ite_notafiscal")),
                                  new DALProduto().get(rs.getInt("ite_produto")), rs.getInt("ite_qtde"),
                                  rs.getDouble("ite_preco"), rs.getDouble("ite_total"));
        }
        catch(SQLException sqlEx){}
        
        return aux;
    }
    
    public ArrayList<ItensNF> get(String filtro) {
        
        String sql = "select *from itensnf";
        if(!filtro.isEmpty())
            sql += " where "+filtro;
        
        ArrayList<ItensNF> aux = new ArrayList();
        ResultSet rs = Banco.getCon().consultar(sql);
               
        try {
            while(rs.next())
                aux.add(new ItensNF(rs.getInt("ite_cod"), new DALNotafiscal().get(rs.getInt("ite_notafiscal")),
                                  new DALProduto().get(rs.getInt("ite_produto")), rs.getInt("ite_qtde"),
                                  rs.getDouble("ite_preco"), rs.getDouble("ite_total")));
        }
        catch(SQLException sqlEx) {}
        
        return aux;
    }
    
    public ArrayList<ItensNF> getNF(String filtro) {
        
        String sql = "select *from itensnf where ite_notafiscal = '" + filtro + "'";
        System.out.println(sql);
        
        ArrayList<ItensNF> aux = new ArrayList();
        ResultSet rs = Banco.getCon().consultar(sql);
        
        try {
            while(rs.next())
                aux.add(new ItensNF(rs.getInt("ite_cod"), new DALNotafiscal().get(rs.getInt("ite_notafiscal")),
                                  new DALProduto().get(rs.getInt("ite_produto")), rs.getInt("ite_qtde"),
                                  rs.getDouble("ite_preco"), rs.getDouble("ite_total")));
        }
        catch(SQLException sqlEx) {}
        
        return aux;
    }
    
}
