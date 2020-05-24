package bellacanela.db.dal;

import bellacanelafx.db.entidades.Cliente;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joao
 */
public class DALCliente {
    
    public boolean gravar(Cliente c) {
        
        String sql = "insert into cliente(cli_nome,cli_cpf,cli_email,cli_fone) values ('#1','#2','#3','#4')";
        
        sql = sql.replaceAll("#1", ""+c.getNome());
        sql = sql.replaceAll("#2", ""+c.getCpf());
        sql = sql.replaceAll("#3", ""+c.getEmail());
        sql = sql.replaceAll("#4", ""+c.getFone());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar(Cliente c) {
        
        String sql = "update cliente set cli_nome='#1',cli_cpf='#2',cli_email='#3',cli_fone='#4' where cli_cod="+c.getCod();
        
        sql = sql.replaceAll("#1", ""+c.getNome());
        sql = sql.replaceAll("#2", ""+c.getCpf());
        sql = sql.replaceAll("#3", ""+c.getEmail());
        sql = sql.replaceAll("#4", ""+c.getFone());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar(Cliente c) {
        return Banco.getCon().manipular("delete from cliente where cli_cod="+c.getCod());
    }
    
    public int getMax() {
        return Banco.getCon().getMaxPK("cliente", "cli_cod");
    }
    
    public Cliente get(int cod) {
        
        Cliente aux = null;
        
        ResultSet rs = Banco.getCon().consultar("select * from cliente where cli_cod="+cod);
        try{
            if(rs.next())
                aux = new Cliente(rs.getInt("cli_cod"),rs.getString("cli_nome"),rs.getString("cli_cpf"),rs.getString("cli_email"),rs.getString("cli_fone"));
        }
        catch(SQLException sqlEx){}
        
        return aux;
    }
    
    public List<Cliente> get(String filtro) {
        
        String sql = "select *from cliente";
        if(!filtro.isEmpty())
            sql += " where "+filtro;
        
        List<Cliente> aux = new ArrayList();
        ResultSet rs = Banco.getCon().consultar(sql);
        
        try {
            while(rs.next())
                aux.add(new Cliente(rs.getInt("cli_cod"),rs.getString("cli_nome"),rs.getString("cli_cpf"),rs.getString("cli_email"),rs.getString("cli_fone")));
        }
        catch(SQLException sqlEx) {}
        
        return aux;
    }
    
    
    
    
    
}
