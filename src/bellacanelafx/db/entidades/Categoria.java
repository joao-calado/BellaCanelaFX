
package bellacanelafx.db.entidades;

public class Categoria {
    private int cod;
    private String Nome;

    public Categoria() {
    }

        public Categoria(int cod, String Nome) {
        this.cod = cod;
        this.Nome = Nome;
    }

    public Categoria(String Nome) {
        this.Nome = Nome;
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
    
    
}
