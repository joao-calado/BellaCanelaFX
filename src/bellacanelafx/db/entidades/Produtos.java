
package bellacanelafx.db.entidades;


public class Produtos {
    private int cod;
    private String nome;
    private Categoria cat;
    private Medida med;
    private double preco;
    private char AVenda;
    private int Estoque = 0;

    public Produtos() {        
    }

    public Produtos(int cod, String nome, double preco) {
        this.cod = cod;
        this.nome = nome;    
        this.preco = preco;
    }
    public Produtos(int cod, String nome, Categoria cat, Medida med, double preco) {
        this.cod = cod;
        this.nome = nome;
        this.cat = cat;
        this.med = med;
        this.preco = preco;
    }
    public Produtos(int cod, String nome, Categoria cat, Medida med, double preco, char AVenda) {
        this.cod = cod;
        this.nome = nome;
        this.cat = cat;
        this.med = med;
        this.preco = preco;
        this.AVenda = AVenda;
    }
    
    public Produtos(String nome, Categoria cat, Medida med, double preco, char AVenda) {
        this.nome = nome;
        this.cat = cat;
        this.med = med;
        this.preco = preco;
        this.AVenda = AVenda;        
    }

    public Produtos(int cod, String nome, Categoria cat, Medida med, double preco, char AVenda, int Estoque) {
        this.cod = cod;
        this.nome = nome;
        this.cat = cat;
        this.med = med;
        this.preco = preco;
        this.AVenda = AVenda;
        this.Estoque = Estoque;
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

    public char getAVenda() {
        return AVenda;
    }

    public void setAVenda(char AVenda) {
        this.AVenda = AVenda;
    }

    public int getEstoque() {
        return Estoque;
    }

    public void setEstoque(int Estoque) {
        this.Estoque = Estoque;
    }
    
}
