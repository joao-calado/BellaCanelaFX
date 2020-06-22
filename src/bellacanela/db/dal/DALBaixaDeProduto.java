package bellacanela.db.dal;

import bellacanelafx.db.entidades.BaixaDeProduto;
import bellacanelafx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DALBaixaDeProduto {
    public boolean insert(BaixaDeProduto bp){
        String SQL = "INSERT INTO BaixaDeProduto(bai_cod, prod_cod, fun_cod, bai_qtde, bai_data, bai_motivo) VALUES(DEFAULT, '#1', '#2', '#3', '#4', '#5')";
        
        SQL = SQL.replaceAll("#1", bp.getProduto().getCod()+"");
        SQL = SQL.replaceAll("#2", bp.getFuncionario().getCod()+"");
        SQL = SQL.replaceAll("#3", bp.getQuantidade()+"");
        SQL = SQL.replaceAll("#4", bp.getData().toString());
        SQL = SQL.replaceAll("#5", bp.getMotivo());
        
        return Banco.getCon().manipular(SQL);
    }
    
    public boolean update(BaixaDeProduto bp){
        String SQL = "UPDATE BaixaDeProduto SET prod_cod = '#1', fun_cod = '#2', bai_qtde = '#3', bai_data = '#4', bai_motivo = '#5' WHERE bai_cod = " + bp.getCod();
        
        SQL = SQL.replaceAll("#1", bp.getProduto().getCod()+"");
        SQL = SQL.replaceAll("#2", bp.getFuncionario().getCod()+"");
        SQL = SQL.replaceAll("#3", bp.getQuantidade()+"");
        SQL = SQL.replaceAll("#4", bp.getData().toString());
        SQL = SQL.replaceAll("#5", bp.getMotivo());
        
        return Banco.getCon().manipular(SQL);
    }
    
    public boolean delete(BaixaDeProduto bp){
        String SQL = "DELETE FROM BaixaDeProduto WHERE bai_cod = " + bp.getCod();
        return Banco.getCon().manipular(SQL);
    }
    
    public int getMaxPK(){
        return Banco.getCon().getMaxPK("BaixaDeProduto", "bai_cod");
    }
    
    public BaixaDeProduto getBaixa(int COD){
        String SQL = "SELECT * FROM BaixaDeProduto WHERE bai_cod = " + COD;
        BaixaDeProduto bp = null;
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        try{
            while(rs.next()) {
                bp = new BaixaDeProduto (
                        rs.getInt("bai_cod"),
                        rs.getInt("bai_qtde"),
                        rs.getString("bai_motivo"),
                        rs.getDate("bai_data").toLocalDate(),
                        new DALFuncionario().getFuncionario(rs.getInt("fun_cod")),
                        new DALProduto().get(rs.getInt("prod_cod"))
                );
            }
        }
        catch(SQLException e){
            System.out.println("ERRO AO CONSULTAR BaixaDeProduto.");
        }
        
        return bp;
    }
    
    public ArrayList<BaixaDeProduto> getBaixas(String filter){
        String SQL = "SELECT * FROM BaixaDeProduto";
        
        if(!filter.equals(""))
            SQL += " WHERE " + filter;
        
        ArrayList<BaixaDeProduto> baixas = new ArrayList();
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        try{
            while(rs.next()) {
                baixas.add(
                    new BaixaDeProduto (
                        rs.getInt("bai_cod"),
                        rs.getInt("bai_qtde"),
                        rs.getString("bai_motivo"),
                        rs.getDate("bai_data").toLocalDate(),
                        new DALFuncionario().getFuncionario(rs.getInt("fun_cod")),
                        new DALProduto().get(rs.getInt("prod_cod"))
                    )
                );
            }
        }
        catch(SQLException e){
            System.out.println("ERRO AO CONSULTAR BaixaDeProduto.");
        }
        
        return baixas;
    }
}
