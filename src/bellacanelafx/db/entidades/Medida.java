
package bellacanelafx.db.entidades;

public class Medida {
    private int cod;
    private String Nome;

    public Medida() {
    }

        public Medida(int cod, String Nome) {
        this.cod = cod;
        this.Nome = Nome;
    }

    public Medida(String Nome) {
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
