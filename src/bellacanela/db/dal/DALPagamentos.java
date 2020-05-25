
package bellacanela.db.dal;

import bellacanelafx.db.entidades.Pagamento;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DALPagamentos {
    public boolean gravar(Pagamento p) {
        
        String sql = "insert into pagamentos(pag_parcela, pag_desc, pag_valor, pag_valorpago, pag_desjur, pag_vencimento, pag_pagamento)"
                + "VALUES ('#2', '#3', '#4', '#5', '#6', '#7', '#8')";
        
        sql = sql.replaceAll("#2", ""+p.getParcela());
        sql = sql.replaceAll("#3", ""+p.getDesc());
        sql = sql.replaceAll("#4", ""+p.getValor());
        sql = sql.replaceAll("#5", ""+0);
        sql = sql.replaceAll("#6", ""+0);
        sql = sql.replaceAll("#7", ""+p.getVencimento());
        sql = sql.replaceAll("#8", "null");
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar(Pagamento p) {
        
        String sql = "update pagamentos SET pag_parcela='#2', pag_desc='#3', pag_valor='#4', pag_valorpago='#5', pag_desjur='#6', pag_vencimento='#7', pag_pagamento='#8' WHERE pag_cod = " +p.getCod();
        
        sql = sql.replaceAll("#2", ""+p.getParcela());
        sql = sql.replaceAll("#3", ""+p.getDesc());
        sql = sql.replaceAll("#4", ""+p.getValor());
        sql = sql.replaceAll("#5", ""+p.getValorpago());
        sql = sql.replaceAll("#6", ""+p.getDesJur());
        sql = sql.replaceAll("#7", ""+p.getVencimento());
        sql = sql.replaceAll("#8", ""+p.getPagamento());

        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar(Pagamento p) {
        return Banco.getCon().manipular("delete from pagamentos where pag_cod= '"+p.getCod()+"'");
    }
    
    public int getMax() {
        return Banco.getCon().getMaxPK("pagamentos", "pag_cod");
    }
    
    public Pagamento get(int cod) {
        
        Pagamento aux = null;
        
        ResultSet rs = Banco.getCon().consultar("select * from pagamentos where pag_cod= '"+cod+"'");
        try{
            if(rs.next())               
                aux = new Pagamento(rs.getInt("pag_cod"),rs.getInt("pag_parcela"), rs.getString("pag_desc"),
                                    rs.getDouble("pag_valor"), rs.getDouble("pag_valorpago"),rs.getDouble("pag_desjur"),
                                    rs.getDate("pag_vencimento"),rs.getDate("pag_pagamento"));
        }
        catch(SQLException sqlEx){}
        
        return aux;
    }
    
    public ArrayList<Pagamento> get(String filtro) {
        
        String sql = "select *from pagamentos";
        if(!filtro.isEmpty())
            sql += " where "+filtro;
        
        ArrayList<Pagamento> aux = new ArrayList();
        ResultSet rs = Banco.getCon().consultar(sql);
        
        try {
            while(rs.next())
                aux.add(new Pagamento(rs.getInt("pag_cod"),rs.getInt("pag_parcela"), rs.getString("pag_desc"),
                                    rs.getDouble("pag_valor"), rs.getDouble("pag_valorpago"),rs.getDouble("pag_desjur"),
                                    rs.getDate("pag_vencimento"),rs.getDate("pag_pagamento")));
        }
        catch(SQLException sqlEx) {}
        
        return aux;
    }
}
