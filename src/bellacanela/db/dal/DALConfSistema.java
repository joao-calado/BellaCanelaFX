/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanela.db.dal;

import bellacanelafx.db.entidades.ConfSistema;
import bellacanelafx.db.util.Banco;

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
}
