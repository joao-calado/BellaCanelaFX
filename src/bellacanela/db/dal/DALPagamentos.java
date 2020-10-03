
package bellacanela.db.dal;

import bellacanelafx.db.entidades.NotaFiscal;
import bellacanelafx.db.entidades.Pagamento;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DALPagamentos {
    public boolean gravar(Pagamento p) {
        
        String sql = "insert into pagamentos(pag_cod, pag_parcela, pag_desc, pag_valor, pag_valorpago, pag_desjur, pag_vencimento, pag_pagamento, pag_parcial, pag_father, pag_nf)"
                + "VALUES (default, '#2', '#3', '#4', '#5', '#6', '#7', null, false, #8, #9)";
        
        sql = sql.replaceAll("#2", ""+p.getParcela());
        sql = sql.replaceAll("#3", ""+p.getDesc());
        sql = sql.replaceAll("#4", ""+p.getValor());
        sql = sql.replaceAll("#5", ""+0);
        sql = sql.replaceAll("#6", ""+0);
        sql = sql.replaceAll("#7", ""+p.getVencimento());
        System.out.println(sql);
        if(p.getPai() == 0)
            sql = sql.replaceAll("#8", "null");
        else
            sql = sql.replaceAll("#8", ""+p.getPai());
        System.out.println(sql);
        if(p.getNf()== 0)
            sql = sql.replaceAll("#9", "null");
        else
            sql = sql.replaceAll("#9", ""+p.getNf());
        
        return Banco.conectar().getCon().manipular(sql);
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

        
        return Banco.conectar().getCon().manipular(sql);
    }
    
    public boolean verifica(NotaFiscal nota) {

        boolean aux = false;

        ResultSet rs = Banco.conectar().getCon().consultar("select COUNT(*) as total from pagamentos where pag_nf = '"+nota.getCod()+"' AND pag_pagamento IS NOT NULL GROUP BY pag_nf");
        try{
            if(rs.next())
                if(rs.getInt("total") > 0)
                    aux = true;
        }
        catch(SQLException sqlEx){}

        return aux;
    }
    
    public boolean quitar(Pagamento p) {
        
        String sql = "update pagamentos SET pag_valorpago='#5', pag_desjur='#6', pag_pagamento='#8', pag_parcial = '#9' WHERE pag_cod = " +p.getCod();
        
        
        sql = sql.replaceAll("#5", ""+p.getValorpago());
        sql = sql.replaceAll("#6", ""+p.getDesJur());
        sql = sql.replaceAll("#8", ""+p.getPagamento());
        sql = sql.replaceAll("#9", ""+p.isParcial());

        return Banco.conectar().getCon().manipular(sql);
    }
    
    public boolean estornar(int p) {
        
        String sql = "update pagamentos SET pag_valorpago=0, pag_desjur=0, pag_pagamento = null, pag_parcial = false WHERE pag_cod = " +p;    
        
        return Banco.conectar().getCon().manipular(sql);
    }
    public boolean apagarNF(Pagamento p) {
        return Banco.conectar().getCon().manipular("delete from pagamentos where pag_nf= '"+p.getNf()+"'");
    }
    public boolean apagarEstonro(int p) {
        return Banco.conectar().getCon().manipular("delete from pagamentos where pag_father= "+p);
    }
    public boolean apagar(Pagamento p) {
        return Banco.conectar().getCon().manipular("delete from pagamentos where pag_cod= '"+p.getCod()+"'");
    }
    
    public int getMax() {
        return Banco.conectar().getCon().getMaxPK("pagamentos", "pag_cod");
    }
    
    public Pagamento get(int cod) {
        
        Pagamento aux = null;
        
        ResultSet rs = Banco.conectar().getCon().consultar("select * from pagamentos where pag_cod= '"+cod+"'");
        try{
            if(rs.next())               
                aux = new Pagamento(rs.getInt("pag_cod"),rs.getInt("pag_parcela"), rs.getString("pag_desc"),
                                    rs.getDouble("pag_valor"), rs.getDouble("pag_valorpago"),rs.getDouble("pag_desjur"),
                                    rs.getDate("pag_vencimento"),rs.getDate("pag_pagamento"), rs.getBoolean("pag_parcial"), rs.getInt("pag_father"));
        }
        catch(SQLException sqlEx){
            System.out.println("aqi??");
        }
        
        return aux;
    }
    
    public ArrayList<Pagamento> get(String filtro) {
        
        String sql = "select *from pagamentos";
        if(!filtro.isEmpty())
            sql += " where "+filtro;
        
        ArrayList<Pagamento> aux = new ArrayList();
        ResultSet rs = Banco.conectar().getCon().consultar(sql);
        
        try {
            while(rs.next())
                aux.add(new Pagamento(rs.getInt("pag_cod"),rs.getInt("pag_parcela"), rs.getString("pag_desc"),
                                    rs.getDouble("pag_valor"), rs.getDouble("pag_valorpago"),rs.getDouble("pag_desjur"),
                                    rs.getDate("pag_vencimento"),rs.getDate("pag_pagamento"), rs.getBoolean("pag_parcial"), rs.getInt("pag_father")));
        }
        catch(SQLException sqlEx) {}
        
        return aux;
    }
    
    public ArrayList<Pagamento> getNF(String filtro) {
        
        String sql = "select *from pagamentos where pag_nf = '" + filtro + "'";
        
        
        ArrayList<Pagamento> aux = new ArrayList();
        ResultSet rs = Banco.conectar().getCon().consultar(sql);
        
        try {
            while(rs.next())
                aux.add(new Pagamento(rs.getInt("pag_cod"),rs.getInt("pag_parcela"), rs.getString("pag_desc"),
                                    rs.getDouble("pag_valor"), rs.getDouble("pag_valorpago"),rs.getDouble("pag_desjur"),
                                    rs.getDate("pag_vencimento"),rs.getDate("pag_pagamento"), rs.getBoolean("pag_parcial"), rs.getInt("pag_father")));
        }
        catch(SQLException sqlEx) {}
        
        return aux;
    }
}
