
package bellacanelafx.db.entidades;


public class Produtos {
    private int cod;
    private String nome;
    private Categoria cat;
    private Medida med;
    private double preco;

    public Produtos() {
    }

    
    public Produtos(int cod, String nome, Categoria cat, Medida med, double preco) {
        this.cod = cod;
        this.nome = nome;
        this.cat = cat;
        this.med = med;
        this.preco = preco;
    }
    
    public Produtos(String nome, Categoria cat, Medida med, double preco) {
        this.nome = nome;
        this.cat = cat;
        this.med = med;
        this.preco = preco;        
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }    
    
}
