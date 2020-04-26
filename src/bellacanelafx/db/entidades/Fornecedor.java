/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx.db.entidades;

/**
 *
 * @author FIGUEIRINHA
 */
public class Fornecedor {

    private int cod;
    private String Nome;
    private String Telefone;
    private String email;
    private String Desc;
    
    public Fornecedor(){  
    }

    public Fornecedor(int cod, String Nome, String Telefone, String email, String Desc) {
        this.cod = cod;
        this.Nome = Nome;
        this.Telefone = Telefone;
        this.email = email;
        this.Desc = Desc;
    }
    
    public Fornecedor(String Nome, String Telefone, String email, String Desc) {        
        this.Nome = Nome;
        this.Telefone = Telefone;
        this.email = email;
        this.Desc = Desc;
    }
    
    
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String Telefone) {
        this.Telefone = Telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }    
    
    
}
