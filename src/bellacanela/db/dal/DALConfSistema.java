/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanela.db.dal;

import bellacanelafx.db.entidades.ConfSistema;
import bellacanelafx.db.util.Banco;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author joao
 */
public class DALConfSistema {
    
    public boolean gravar(ConfSistema cs) {
        
        String sql = "insert into parametrizacao(par_nome,par_cep,par_endereco,par_cidade,par_uf,par_cnpj,par_razao_social,par_fone,par_email,par_cor1,par_cor2) values ('#a','#b','#c','#d','#e','#f','#g','#h','#i','#j','#k')";
        
        sql = sql.replaceAll("#a", cs.getNome());
        sql = sql.replaceAll("#b", cs.getCep());
        sql = sql.replaceAll("#c", cs.getEndereco());
        sql = sql.replaceAll("#d", cs.getCidade());
        sql = sql.replaceAll("#e", cs.getUf());
        sql = sql.replaceAll("#f", cs.getCnpj());
        sql = sql.replaceAll("#g", cs.getRazao());
        sql = sql.replaceAll("#h", cs.getFone());
        sql = sql.replaceAll("#i", cs.getEmail());
        sql = sql.replaceAll("#j", cs.getCor1());
        sql = sql.replaceAll("#k", cs.getCor2());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar(ConfSistema cs) {
        
        String sql = "update parametrizacao set par_nome='#a',par_cep='#b',par_endereco='#c',par_cidade='#d',par_uf='#e',par_cnpj='#f',par_razao_social='#g',par_fone='#h',par_email='#i',par_cor1='#j',par_cor2='#k' where par_cod="+cs.getCod();
        
        sql = sql.replaceAll("#a", cs.getNome());
        sql = sql.replaceAll("#b", cs.getCep());
        sql = sql.replaceAll("#c", cs.getEndereco());
        sql = sql.replaceAll("#d", cs.getCidade());
        sql = sql.replaceAll("#e", cs.getUf());
        sql = sql.replaceAll("#f", cs.getCnpj());
        sql = sql.replaceAll("#g", cs.getRazao());
        sql = sql.replaceAll("#h", cs.getFone());
        sql = sql.replaceAll("#i", cs.getEmail());
        sql = sql.replaceAll("#j", cs.getCor1());
        sql = sql.replaceAll("#k", cs.getCor2());
        
        return Banco.getCon().manipular(sql);
    }
    
    public ConfSistema get() {
        
        ConfSistema aux = null;
        ResultSet rs = Banco.getCon().consultar("select * from parametrizacao");
        
        try {
            if(rs.next()) {
                aux = new ConfSistema(rs.getInt("par_cod"),rs.getString("gar_nome"),rs.getString("gar_cep"),rs.getString("gar_endereco"),rs.getString("gar_cidade"),rs.getString("gar_uf"),rs.getString("gar_cnpj"),rs.getString("gar_razao_social"),rs.getString("gar_fone"),rs.getString("gar_email"),rs.getString("gar_cor1"),rs.getString("gar_2"));
            }
        }
        catch (SQLException sqlEx) {}
        
        return aux;
    }
    
    public boolean gravarIcone(ConfSistema cs, FileInputStream icone) throws SQLException {
        
        try {
            
            String sql = "update parametrizacao set par_icone = ? where par_cod = ?";
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(sql);
            ps.setBinaryStream(1, icone);
            ps.setInt(2, cs.getCod());
            ps.executeUpdate();
            ps.close();
            icone.close();
        }
        catch(IOException ioEx) {
            return false;
        }
        
        return true;
    }
    
    public InputStream getIcone(ConfSistema cs) throws SQLException {
        
        InputStream is = null;
        
        try {
            
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement("select par_icone from parametrizacao where par_cod = ?");
            ps.setInt(1, cs.getCod());
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                byte[] bytes = rs.getBytes("par_icone");
                is = new ByteArrayInputStream(bytes);
            }
            ps.close();
        }
        catch(SQLException sqlEx) {
            return null;
        }
        
        return is;
    }
}
