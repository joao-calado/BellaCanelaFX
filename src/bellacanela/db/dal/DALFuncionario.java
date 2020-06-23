package bellacanela.db.dal;

import bellacanelafx.db.entidades.Funcionario;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DALFuncionario {
    public boolean insert(Funcionario f){
        String SQL = "INSERT INTO Funcionarios(fun_cod, fun_nome, fun_datanascimento, fun_telefone, fun_salario) VALUES(DEFAULT, '#1', '#2', '#3', '#4')";
        
        SQL = SQL.replaceAll("#1", f.getNome());
        SQL = SQL.replaceAll("#2", f.getDataNascimento().toString());
        SQL = SQL.replaceAll("#3", f.getTelefone());
        SQL = SQL.replaceAll("#4", f.getSalario()+"");
        
        return Banco.getCon().manipular(SQL);
    }
    
    public boolean update(Funcionario f){
        String SQL = "UPDATE Funcionarios SET fun_nome = '#1', fun_datanascimento = '#2', fun_telefone = '#3', fun_salario = '#4' WHERE fun_cod = " + f.getCod();
        
        SQL = SQL.replaceAll("#1", f.getNome());
        SQL = SQL.replaceAll("#2", f.getDataNascimento().toString());
        SQL = SQL.replaceAll("#3", f.getTelefone());
        SQL = SQL.replaceAll("#4", f.getSalario()+"");
        
        return Banco.getCon().manipular(SQL);
    }
    
    public boolean delete(Funcionario f){
        String SQL = "DELETE FROM Funcionarios WHERE fun_cod = " + f.getCod();
        return Banco.getCon().manipular(SQL);
    }
    
    public int getMaxPK(){
        return Banco.getCon().getMaxPK("Funcionarios", "fun_cod");
    }
    
    public Funcionario getFuncionario(int COD){
        String SQL = "SELECT * FROM Funcionarios WHERE fun_cod = " + COD;
        Funcionario f = null;
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        try{
            while(rs.next())
                f = new Funcionario(rs.getInt("fun_cod"), rs.getDate("fun_datanascimento").toLocalDate(), rs.getString("fun_nome"), rs.getString("fun_telefone"), rs.getDouble("fun_salario"));
        }
        catch(SQLException e){
            System.out.println("ERRO AO CONSULTAR FUNCIONARIO.");
        }
        
        return f;
    }
    
    public ArrayList<Funcionario> getFuncionarios(String filter){
        String SQL = "SELECT * FROM Funcionarios";
        if(!filter.equals(""))
            SQL += " WHERE " + filter;
        
        ArrayList<Funcionario> funcionarios = new ArrayList();
        Funcionario f = null;
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        try{
            while(rs.next())
                funcionarios.add(new Funcionario(rs.getInt("fun_cod"), rs.getDate("fun_datanascimento").toLocalDate(), rs.getString("fun_nome"), rs.getString("fun_telefone"), rs.getDouble("fun_salario")));
        }
        catch(SQLException e){
            
        }
        
        return funcionarios;
    }
}
