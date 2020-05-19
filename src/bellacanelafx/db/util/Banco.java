/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx.db.util;

/**
 *
 * @author joao
 */
public class Banco {
    
    static private Conexao con = null;
    
    static public Conexao getCon() {
        return con;
    }
    
    private Banco(){}
    
    static public boolean conectar() {
        con = new Conexao();
        return con.conectar("jdbc:postgresql://localhost/", "bellacanella", "postgres", "postgres123");
    }
}
