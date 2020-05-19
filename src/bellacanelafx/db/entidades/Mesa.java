package bellacanelafx.db.entidades;

public class Mesa {
    private int cod;
    private boolean liberada;

    public Mesa(int cod, boolean liberada) {
        this.cod = cod;
        this.liberada = liberada;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public boolean isLiberada() {
        return liberada;
    }

    public void setLiberada(boolean liberada) {
        this.liberada = liberada;
    }
}
