package bellacanelafx.db.util;

import java.sql.SQLException;

public class Banco {
    private static Banco banco = null;
    private Conexao con = null;
    
    private Banco() {
        con = new Conexao();
        con.conectar("jdbc:postgresql://localhost/", "bellacanela", "postgres", "postgres123");
    }
    
    public static Banco conectar() {
        if(banco == null)
            banco = new Banco();
        return banco;
    }
    
    public Conexao getCon() {
        return con;
    }
    
    public void desconectar() throws SQLException, Throwable {
        con.getConnect().close();
        banco = null;
    }
}
