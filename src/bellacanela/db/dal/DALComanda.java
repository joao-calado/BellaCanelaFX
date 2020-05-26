package bellacanela.db.dal;

import bellacanelafx.db.entidades.Comanda;
import bellacanelafx.db.entidades.Mesa;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DALComanda {

    public boolean insert(Comanda c) {
        String SQL = "INSERT INTO Comanda(com_num, mes_cod, cli_cod, com_data, com_descricao, com_aberta) VALUES('#1', '#2', '#3', '#4', '#5', '#6')";

        SQL = SQL.replaceAll("#1", c.getCom_num() + "");
        SQL = SQL.replaceAll("#2", c.getMes_cod() + "");
        SQL = SQL.replaceAll("#3", c.getCliente().getCod() + "");
        SQL = SQL.replaceAll("#4", c.getData().toString());
        SQL = SQL.replaceAll("#5", c.getDescricao());
        SQL = SQL.replaceAll("#6", c.isAberta()+"");
        
        return Banco.getCon().manipular(SQL);
    }

    public boolean update(Comanda c) {
        boolean ans;

        String SQL = "UPDATE Comanda SET cli_cod = '#1', com_data = '#2', com_descricao = '#3', com_aberta = '#4' WHERE com_num = '#5' AND mes_cod = '#6'";

        SQL = SQL.replaceAll("#1", c.getCliente().getCod() + "");
        SQL = SQL.replaceAll("#2", c.getData().toString());
        SQL = SQL.replaceAll("#3", c.getDescricao());
        SQL = SQL.replaceAll("#4", c.isAberta()+"");
        SQL = SQL.replaceAll("#5", c.getCom_num() + "");
        SQL = SQL.replaceAll("#6", c.getMes_cod() + "");
        ans = Banco.getCon().manipular(SQL);
        
        if (ans) {
            new DALItensDaComanda().delete(c.getCom_num(), c.getMes_cod());

            if (ans) {
                c.getItens().forEach((itens) -> {
                    new DALProduto().alterEstoque(itens.getProduto());
                    new DALItensDaComanda().insert(itens);
                });
            }
        }

        return ans;
    }

    public boolean delete(Comanda c) {
        boolean ans;

        ans = new DALItensDaComanda().delete(c.getCom_num(), c.getMes_cod());

        if (ans) {
            String SQL = "DELETE FROM Comanda WHERE com_num = '#1' AND mes_cod = '#2'";

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
            if (rs.next()) {
                m = new Mesa(rs.getInt("mes_cod"), rs.getBoolean("mes_liberada"));
            }
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
        Comanda c = null;

        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            while (rs.next()) {
                c = new Comanda(
                            rs.getInt("com_num"), rs.getInt("mes_cod"),
                            rs.getBoolean("com_aberta"),
                            rs.getDate("com_data").toLocalDate(),
                            rs.getString("com_descricao"),
                            new DALCliente().get(rs.getInt("cli_cod")),
                            new DALItensDaComanda().getItens("com_num = " + rs.getInt("com_num") + " AND mes_cod = " + rs.getInt("mes_cod"))
                        );
            }
        }
        catch (SQLException e) {
            System.out.println("DEU ERRO AO CONSULTAR AS COMANDAS.");
        }

        return c;
    }

    public ArrayList<Comanda> getComandas(String filter) {
        String SQL = "SELECT * FROM Comanda";
        ArrayList<Comanda> comandas = new ArrayList();

        if (!filter.equals("")) {
            SQL += " WHERE " + filter;
        }
        
        ResultSet rs = Banco.getCon().consultar(SQL), rsAux;
        try {
            while (rs.next()) {
                
                comandas.add(
                        new Comanda(
                            rs.getInt("com_num"), rs.getInt("mes_cod"),
                            rs.getBoolean("com_aberta"),
                            rs.getDate("com_data").toLocalDate(),
                            rs.getString("com_descricao"),
                            new DALCliente().get(rs.getInt("cli_cod")),
                            new DALItensDaComanda().getItens("com_num = " + rs.getInt("com_num") + " AND mes_cod = " + rs.getInt("mes_cod"))
                        )
                );
            }
        }
        catch (SQLException e) {
            System.out.println("DEU ERRO AO CONSULTAR AS COMANDAS.");
        }

        return comandas;
    }
}
