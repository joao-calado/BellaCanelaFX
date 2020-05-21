package bellacanela.db.dal;

import bellacanelafx.db.entidades.Comanda;
import bellacanelafx.db.entidades.Mesa;
import bellacanelafx.db.entidades.Produtos;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DALComanda {

    public boolean insert(Comanda c) {
        String SQL = "INSERT INTO Comanda(com_num, mes_cod, cli_cod, com_data, com_descricao) VALUES('#1', '#2', '#3', '#4', '#5')";

        SQL = SQL.replaceAll("#1", c.getCom_num() + "");
        SQL = SQL.replaceAll("#2", c.getMes_cod() + "");
        SQL = SQL.replaceAll("#3", c.getCliente().getCod() + "");
        SQL = SQL.replaceAll("#4", c.getData().toString());
        SQL = SQL.replaceAll("#5", c.getDescricao());

        return Banco.getCon().manipular(SQL);
    }

    public boolean update(Comanda c) {
        boolean ans;

        String SQL = "UPDATE Comanda SET cli_cod = '#1', com_data = '#2', com_descricao = '#3' WHERE com_num = '#4' AND mes_cod = '#5'";

        SQL = SQL.replaceAll("#1", c.getCliente().getCod() + "");
        SQL = SQL.replaceAll("#2", c.getData().toString());
        SQL = SQL.replaceAll("#3", c.getDescricao());
        SQL = SQL.replaceAll("#4", c.getCom_num() + "");
        SQL = SQL.replaceAll("#5", c.getMes_cod() + "");

        ans = Banco.getCon().manipular(SQL);

        if (ans) {
            SQL = "DELETE FROM ItensDaComanda WHERE com_num = '#1' AND mes_cod = '#2'";

            SQL = SQL.replaceAll("#1", c.getCom_num() + "");
            SQL = SQL.replaceAll("#2", c.getMes_cod() + "");

            ans = Banco.getCon().manipular(SQL);

            if (ans) {
                for (Produtos p : c.getProdutos()) {
                    SQL = "INSERT INTO ItensDaComanda(com_num, mes_cod, prod_cod, qtde_itens) VALUES('#1', '#2', '#3', '#4')";

                    SQL = SQL.replaceAll("#1", c.getCom_num() + "");
                    SQL = SQL.replaceAll("#2", c.getMes_cod() + "");
                    SQL = SQL.replaceAll("#3", p.getCod() + "");
                    SQL = SQL.replaceAll("#4", 10 + "");

                    Banco.getCon().manipular(SQL);
                }
            }
        }

        return ans;
    }

    public boolean delete(Comanda c) {
        boolean ans;

        String SQL = "DELETE FROM ItensDaComanda WHERE com_num = '#1' AND mes_cod = '#2'";

        SQL = SQL.replaceAll("#1", c.getCom_num() + "");
        SQL = SQL.replaceAll("#2", c.getMes_cod() + "");

        ans = Banco.getCon().manipular(SQL);

        if (ans) {
            SQL = "DELETE FROM Comanda WHERE com_num = '#1' AND mes_cod = '#2'";

            SQL = SQL.replaceAll("#1", c.getCom_num() + "");
            SQL = SQL.replaceAll("#2", c.getMes_cod() + "");

            ans = Banco.getCon().manipular(SQL);
        }

        return ans;
    }

    public boolean setMesa(int mes_cod, boolean liberada) {
        String SQL = "UPDATE Mesa SET mes_liberada = '" + liberada + "' WHERE mes_cod = " + mes_cod;
        return Banco.getCon().manipular(SQL);
    }

    public Mesa getMesa(int mes_cod) {
        Mesa m = null;
        String SQL = "SELECT * FROM Mesa WHERE mes_cod = " + mes_cod;
        ResultSet rs = Banco.getCon().consultar(SQL);
        
        try {
            if (rs.next()) 
                m = new Mesa(rs.getInt("mes_cod"), rs.getBoolean("mes_liberada"));
        }
        catch (SQLException e) {
            System.out.println("ERRO AO CONSULTAR MESA.");
        }
        
        return m;
    }

    public Comanda getComanda(int com_num, int mes_cod) {
        String SQL = "SELECT * FROM Comanda WHERE com_num = '#1' AND mes_cod = '#2'";
        SQL = SQL.replaceAll("#1", com_num + "");
        SQL = SQL.replaceAll("#2", mes_cod + "");
        ArrayList<Produtos> produtos = new ArrayList();
        Comanda c = null;

        ResultSet rs = Banco.getCon().consultar(SQL), rsAux;
        try {
            while (rs.next()) {
                SQL = "SELECT * FROM ItensDaComanda WHERE com_num = '#1' AND mes_cod = '#2'";
                SQL = SQL.replaceAll("#1", com_num + "");
                SQL = SQL.replaceAll("#2", mes_cod + "");
                rsAux = Banco.getCon().consultar(SQL);
                produtos.clear();
                while (rsAux.next()) {
                    produtos.add(new Produtos(
                            rs.getInt("prod_cod"),
                            rs.getString("prod_nome"),
                            new DALCategoria().get(rs.getInt("prod_cat")),
                            new DALMedida().get(rs.getInt("prod_med")),
                            rs.getDouble("prod_preco")
                    ));
                }
                c = new Comanda(
                        rs.getInt("com_num"), rs.getInt("mes_cod"),
                        rs.getDate("com_data").toLocalDate(), rs.getString("com_descricao"),
                        new DALCliente().get(rs.getInt("cli_cod")),
                        produtos
                );
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO CONSULTAR FUNCIONARIO.");
        }

        return c;
    }

    public ArrayList<Comanda> getComandas(String filter) {
        String SQL = "SELECT * FROM Funcionarios";
        ArrayList<Produtos> produtos = new ArrayList();

        if (!filter.equals("")) {
            SQL += " WHERE " + filter;
        }

        ArrayList<Comanda> comandas = new ArrayList();

        ResultSet rs = Banco.getCon().consultar(SQL), rsAux;
        try {
            while (rs.next()) {
                SQL = "SELECT * FROM ItensDaComanda WHERE com_num = '#1' AND mes_cod = '#2'";
                SQL = SQL.replaceAll("#1", rs.getInt("com_num") + "");
                SQL = SQL.replaceAll("#2", rs.getInt("mes_cod") + "");
                rsAux = Banco.getCon().consultar(SQL);
                produtos.clear();
                while (rsAux.next()) {
                    produtos.add(new Produtos(
                            rs.getInt("prod_cod"),
                            rs.getString("prod_nome"),
                            new DALCategoria().get(rs.getInt("prod_cat")),
                            new DALMedida().get(rs.getInt("prod_med")),
                            rs.getDouble("prod_preco")
                    ));
                }
                comandas.add(new Comanda(
                        rs.getInt("com_num"), rs.getInt("mes_cod"),
                        rs.getDate("com_data").toLocalDate(), rs.getString("com_descricao"),
                        new DALCliente().get(rs.getInt("cli_cod")),
                        produtos
                ));
            }
        } catch (SQLException e) {

        }

        return comandas;
    }
}
