/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author joao
 */
public class Conexao {
    
    private Connection connect;
    private String erro;
    
    public Conexao() {
        erro = "";
        connect = null;
    }
    
    public boolean conectar(String local, String banco, String usuario, String senha) {
        
        boolean conectado = false;
        
        try {
            String url = local+banco;
            connect = DriverManager.getConnection(url, usuario, senha);
            conectado = true;
        }
        catch(SQLException sqlEx) {
            erro = "Não foi possível conectar a base de dados: "+sqlEx.toString();
        }
        
        return conectado;
    }
    
    public boolean manipular(String sql) {
        
        boolean executou = false;
        
        try {
            Statement statement = connect.createStatement();
            int result = statement.executeUpdate(sql);
            statement.close();
            if(result >= 1)
                executou = true;
        }
        catch(SQLException sqlEx) {
            erro = "Não foi possível manipular: "+sqlEx.toString();
        }
        
        return executou;
    }
    
    public ResultSet consultar(String sql) {
        
        ResultSet rs = null;
        
        try {
            Statement statement = connect.createStatement();
            rs = statement.executeQuery(sql);
        }
        catch(SQLException sqlEx) {
            erro = "Não foi possível consultar: "+sqlEx.toString();
            rs = null;
        }
        
        return rs;
    }
    
    public int getMaxPK(String tabela, String chave) {
        
        int max = 0;
        String sql = "select max("+chave+") from "+tabela;
        ResultSet rs = consultar(sql);
        
        try {
            if(rs.next())
                max = rs.getInt(1);
        }
        catch(SQLException sqlEx) {
           erro = "Erro: "+sqlEx.toString();
           max = -1;
        }
            
        return max;
    }
    
    public Connection getConnect() {
        return connect;
    }
    
    public String getMensagemErro() {
        return erro;
    }
    
    public boolean getEstadoConexao() {
        return (connect!=null);
    }
    
    
    
}
