package bellacanelafx.db.entidades;

public class Usuario {
    private String login, senha;
    private int nivel;
    private boolean habilitado;

    public Usuario(String login, String senha, int nivel, boolean habilitado) {
        this.login = login;
        this.senha = senha;
        this.nivel = nivel;
        this.habilitado = habilitado;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
}
