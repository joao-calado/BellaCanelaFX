package bellacanela.db.dal;

import bellacanelafx.db.entidades.Funcionario;
import bellacanelafx.db.entidades.Usuario;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DALUsuario {
    public boolean insert(Usuario u){
        String SQL = "INSERT INTO Usuarios(user_login, user_senha) VALUES('#1', '#2')";
        
        SQL = SQL.replaceAll("#1", u.getLogin());
        SQL = SQL.replaceAll("#2", u.getSenha());
        
        return Banco.getCon().manipular(SQL);
    }
    
    public boolean update(Usuario u){
        String SQL = "UPDATE Usuarios SET user_senha = '#1' WHERE user_login = '" + u.getLogin() + "'";
        
        SQL = SQL.replaceAll("#1", u.getSenha());
        
        return Banco.getCon().manipular(SQL);
    }
    
    public boolean delete(String login){
        String SQL = "DELETE FROM Usuarios WHERE user_login = '" + login + "'";
        return Banco.getCon().manipular(SQL);
    }
    
    public boolean autenticacao(Usuario u) throws SQLException {
        String SQL = "SELECT * FROM Usuarios WHERE user_login = '#1' AND user_senha = '#2'";
        
        SQL = SQL.replaceAll("#1", u.getLogin());
        SQL = SQL.replaceAll("#2", u.getSenha());
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        
        return rs.next();
    }
    
    public Usuario getUsuario(String login){
        String SQL = "SELECT * FROM Usuarios WHERE user_login = " + login;
        Usuario u = null;
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        try{
            u = new Usuario(rs.getString("user_login"), rs.getString("user_senha"));
        }
        catch(SQLException e){
            System.out.println("ERRO AO CONSULTAR USUARIOS.");
        }
        
        return u;
    }
    
    public ArrayList<Usuario> getUsuarios(String filter){
        String SQL = "SELECT * FROM Usuarios";
        if(!filter.equals(""))
            SQL += " WHERE " + filter;
        
        ArrayList<Usuario> usuarios = new ArrayList();
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        try{
            while(rs.next())
                usuarios.add(new Usuario(rs.getString("user_login"), rs.getString("user_senha")));
        }
        catch(SQLException e){
            System.out.println("ERRO AO CONSULTAR USUARIOS.");
        }
        
        return usuarios;
    }
}
