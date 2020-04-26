
package bellacanelafx.db.entidades;


public class Produtos {
    private int cod;
    private String nome;
    private Categoria cat;
    private Medida med;

    public Produtos() {
    }

    
    public Produtos(int cod, String nome, Categoria cat, Medida med) {
        this.cod = cod;
        this.nome = nome;
        this.cat = cat;
        this.med = med;
    }
    
    public Produtos(String nome, Categoria cat, Medida med) {
        this.nome = nome;
        this.cat = cat;
        this.med = med;
    }
    
    
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria getCat() {
        return cat;
    }

    public void setCat(Categoria cat) {
        this.cat = cat;
    }

    public Medida getMed() {
        return med;
    }

    public void setMed(Medida med) {
        this.med = med;
    }
    
    
}
