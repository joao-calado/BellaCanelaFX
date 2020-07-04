package bellacanela.db.dal;

import bellacanelafx.db.entidades.Recebimento;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joao
 */
public class DALRecebimento {
    
    public boolean gravar(Recebimento r) {
        
        String sql;
        
        if(r.getVencimento().isEqual(LocalDate.of(1800, 10, 10))) {
            
            sql = "insert into recebimento(rec_cli,rec_tipo,rec_valor,rec_recebimento,rec_status,rec_mesa,rec_comanda,rec_pai) values ('#1','#2','#3','#4','#5','#6','#7','#8')";
        
            sql = sql.replaceAll("#1", ""+r.getCliente());
            sql = sql.replaceAll("#2", ""+r.getTipo());
            sql = sql.replaceAll("#3", ""+r.getValor());
            sql = sql.replaceAll("#4", ""+r.getRecebimento());
            sql = sql.replaceAll("#5", ""+r.getStatus());
            sql = sql.replaceAll("#6", ""+r.getMesa());
            sql = sql.replaceAll("#7", ""+r.getComanda());
            sql = sql.replaceAll("#8", ""+r.getPai());
        }
        else {
            
            sql = "insert into recebimento(rec_cli,rec_tipo,rec_valor,rec_recebimento,rec_vencimento,rec_status,rec_mesa,rec_comanda,rec_pai) values ('#1','#2','#3','#4','#5','#6','#7','#8','#9')";
        
            sql = sql.replaceAll("#1", ""+r.getCliente());
            sql = sql.replaceAll("#2", ""+r.getTipo());
            sql = sql.replaceAll("#3", ""+r.getValor());
            sql = sql.replaceAll("#4", ""+r.getRecebimento());
            sql = sql.replaceAll("#5", ""+r.getVencimento());
            sql = sql.replaceAll("#6", ""+r.getStatus());
            sql = sql.replaceAll("#7", ""+r.getMesa());
            sql = sql.replaceAll("#8", ""+r.getComanda());
            sql = sql.replaceAll("#9", ""+r.getPai());
        }
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar(Recebimento r) {
        
        String sql = "update recebimento set rec_cli='#1',rec_tipo='#2',rec_valor='#3',rec_recebimento='#4',rec_vencimento='#5',rec_status='#6',rec_mesa='#7',rec_comanda='#8' where rec_cod="+r.getCod();
        
        sql = sql.replaceAll("#1", ""+r.getCliente());
        sql = sql.replaceAll("#2", r.getTipo());
        sql = sql.replaceAll("#3", ""+r.getValor());
        sql = sql.replaceAll("#4", ""+r.getRecebimento());
        sql = sql.replaceAll("#5", ""+r.getVencimento());
        sql = sql.replaceAll("#6", r.getStatus());
        sql = sql.replaceAll("#7", ""+r.getMesa());
        sql = sql.replaceAll("#8", ""+r.getComanda());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar(Recebimento r) {
        return Banco.getCon().manipular("delete from recebimento where rec_cod="+r.getCod());
    }
    
    public int getMax() {
        return Banco.getCon().getMaxPK("recebimento", "rec_cod");
    }
    
    public Recebimento get (int cod) {
        
        Recebimento aux = null;
        
        ResultSet rs = Banco.getCon().consultar("select * from recebimento where rec_cod="+cod);
        try{
            if(rs.next())
                aux = new Recebimento(rs.getInt("rec_cod"),rs.getInt("rec_cli"),rs.getString("rec_tipo"),rs.getDouble("rec_valor"),rs.getDate("rec_recebimento").toLocalDate(),rs.getDate("rec_vencimento").toLocalDate(),rs.getString("rec_status"),rs.getInt("rec_mesa"),rs.getInt("rec_comanda"));
        }
        catch(SQLException sqlEx){}
        
        return aux;
    }
    
    public List<Recebimento> get(String filtro) {
        
        String sql = "select * from recebimento";
        if(!filtro.isEmpty())
            sql += " where "+filtro;
        
        List<Recebimento> aux = new ArrayList();
        ResultSet rs = Banco.getCon().consultar(sql);
        
        try {
            while(rs.next()) {
                
                if(rs.getDate("rec_vencimento") != null) {
                    aux.add(new Recebimento(rs.getInt("rec_cod"),rs.getInt("rec_cli"),rs.getString("rec_tipo"),rs.getDouble("rec_valor"),rs.getDate("rec_recebimento").toLocalDate(),rs.getDate("rec_vencimento").toLocalDate(),rs.getString("rec_status"),rs.getInt("rec_mesa"),rs.getInt("rec_comanda")));
                }
                else {
                    aux.add(new Recebimento(rs.getInt("rec_cod"),rs.getInt("rec_cli"),rs.getString("rec_tipo"),rs.getDouble("rec_valor"),rs.getDate("rec_recebimento").toLocalDate(),LocalDate.of(1900, 10, 10),rs.getString("rec_status"),rs.getInt("rec_mesa"),rs.getInt("rec_comanda")));
                }
            }
        }
        catch(SQLException sqlEx) {}
        
        return aux;
    }
}
