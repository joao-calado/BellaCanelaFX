package bellacanelafx;

import bellacanela.db.dal.DALCliente;
import bellacanela.db.dal.DALComanda;
import bellacanela.db.dal.DALRecebimento;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.Cliente;
import bellacanelafx.db.entidades.Comanda;
import bellacanelafx.db.entidades.Recebimento;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author joao
 */
public class EfetuarRecebimentoController implements Initializable {

    @FXML
    private SplitPane painel;
    @FXML
    private JFXButton btExcluir;
    @FXML
    private JFXComboBox<String> cbTipoRec;
    @FXML
    private JFXTextField txValor;
    @FXML
    private JFXDatePicker dtpVencimento;
    @FXML
    private TableColumn<Recebimento, String> colTipo;
    @FXML
    private TableColumn<Recebimento, Double> colValor;
    @FXML
    private TableColumn<Recebimento, LocalDate> colRecebimento;
    @FXML
    private TableColumn<Recebimento, LocalDate> colVencimento;
    @FXML
    private JFXButton btReceber;
    @FXML
    private JFXButton btPesquisar;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private TableView<Recebimento> tabela;
    @FXML
    private JFXTextField txCliente;
    @FXML
    private JFXDatePicker dtpVencimento1;

    private void fadeout() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), painel);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play(); 
    }
    
    private String formatarPreco(double preco){
        String str = preco+"";
        int i = (str).indexOf(".");
            if(str.length() - i == 2)
                str += "0";
        return str;
    }
    
    private void carregarCB(String modo) {
        cbTipoRec.getItems().clear();
        List<String> tr = new ArrayList();
        if(modo.equals("pesquisa")) {
            tr.add("dinheiro");
            tr.add("crédito");
            tr.add("débito");
            tr.add("a ver");
            tr.add("gorjeta");
        }
        else {
            tr.add("dinheiro");
            tr.add("crédito");
            tr.add("débito");
        }
        ObservableList<String> mod = FXCollections.observableArrayList(tr);
        cbTipoRec.setItems(mod);
        //cbTipoRec.setValue(cbTipoRec.getItems().get(0));
    }
    
    private void modoPesquisa() {
        
        tabela.setItems(null);
        tabela.refresh();
        
        carregarCB("pesquisa");
        tabela.setDisable(true);
        cbTipoRec.setPromptText("Tipo de recebimento");
        cbTipoRec.setDisable(false);
        
        txValor.clear();
        txValor.setDisable(true);
        txCliente.clear();
        txCliente.setDisable(false);
        btReceber.setDisable(true);
        btExcluir.setDisable(true);
        
        btPesquisar.setDisable(false);
        dtpVencimento.setDisable(false);
        dtpVencimento1.setDisable(false);
    }
    
    private void modoAltera() {
        
        carregarCB("altera");
        tabela.setDisable(false);
        cbTipoRec.setPromptText("Tipo de recebimento");
        cbTipoRec.setDisable(false);
        
        txValor.clear();
        txValor.setDisable(false);
        txCliente.clear();
        txCliente.setDisable(true);
        btReceber.setDisable(false);
        btExcluir.setDisable(false);
        
        btPesquisar.setDisable(true);
        dtpVencimento.setDisable(true);
        dtpVencimento1.setDisable(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        fadeout();
        MaskFieldUtil.monetaryField(txValor);
        
        colTipo.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, String> r) -> new SimpleStringProperty(r.getValue().getTipo()));
        colValor.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, Double> r) -> new SimpleObjectProperty(r.getValue().getValor()));
        colRecebimento.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, LocalDate> r) -> new SimpleObjectProperty(r.getValue().getRecebimento()));
        colVencimento.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, LocalDate> r) -> new SimpleObjectProperty(r.getValue().getVencimento()));
    
        colTipo.setStyle("-fx-alignment: center-right;");
        colValor.setStyle("-fx-alignment: center-right;");
        colRecebimento.setStyle("-fx-alignment: center-right;");
        colVencimento.setStyle("-fx-alignment: center-right;");
        
        Platform.runLater(()->{
            try{
                modoPesquisa();
            }
            catch(Exception e){
                System.out.println("Erro!");
            }
        });
    }    

    @FXML
    private void clkBtExcluir(ActionEvent event) {
    }

    @FXML
    private void clkBtReceber(ActionEvent event) {
        
        Recebimento r;
        DALRecebimento dalRec = new DALRecebimento();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        
        if(tabela.getSelectionModel().getSelectedIndex() >= 0 && tabela.getSelectionModel().getSelectedItem().getStatus().equals("N") && !txValor.getText().isEmpty()) {
            
            Double pgto = Double.parseDouble(txValor.getText().replaceAll(",", "."));
            r = new Recebimento(tabela.getSelectionModel().getSelectedItem().getCod(), 
                                tabela.getSelectionModel().getSelectedItem().getCliente(), 
                                tabela.getSelectionModel().getSelectedItem().getTipo(), 
                                tabela.getSelectionModel().getSelectedItem().getValor(), 
                                tabela.getSelectionModel().getSelectedItem().getRecebimento(), 
                                tabela.getSelectionModel().getSelectedItem().getVencimento(), 
                                "S", 
                                tabela.getSelectionModel().getSelectedItem().getMesa(), 
                                tabela.getSelectionModel().getSelectedItem().getComanda());
            
            if(pgto > tabela.getSelectionModel().getSelectedItem().getValor()) {
                // pagar com gorjeta - update em rec_status
                if(dalRec.alterar(r)) {
                    
                    r.setValor(pgto - r.getValor());
                    r.setTipo("gorjeta");
                    
                    if(r.getPai() == 0)
                        r.setPai(r.getCod());
                    else 
                        r.setPai(r.getPai());
                    
                    if(dalRec.gravar(r)) {
                        a.setTitle("Informação:");
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setContentText("Recebimento gravado com sucesso!\nGorjeta: R$"+r.getValor());
                    }
                    else {
                        a.setTitle("Erro:");
                        a.setAlertType(Alert.AlertType.ERROR);
                        a.setContentText("Problemas ao tentar gravar recebimento com Gorjeta!");
                    }
                }
                else {
                    a.setTitle("Erro:");
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setContentText("Problemas ao tentar gravar recebimento!");
                }
            }
            else {
                if(pgto < tabela.getSelectionModel().getSelectedItem().getValor()) {
                    // update e gerar rec
                    r.setValor(r.getValor() - pgto);
                    r.setStatus("N");
                    if(cbTipoRec.getValue() == null || cbTipoRec.getValue().isEmpty()) {
                        a.setTitle("Erro:");
                        a.setAlertType(Alert.AlertType.ERROR);
                        a.setContentText("Selecione um tipo para o novo recebimento!");
                    }
                    else {
                        if(dalRec.alterar(r)) {

                            r.setValor(pgto);
                            r.setTipo(cbTipoRec.getValue());
                            r.setStatus("S");
                            
                            if(r.getPai() == 0)
                                r.setPai(r.getCod());
                            else 
                                r.setPai(r.getPai());
                            
                            if(dalRec.gravar(r)) {
                                a.setTitle("Informação:");
                                a.setAlertType(Alert.AlertType.INFORMATION);
                                a.setContentText("Recebimento gravado com sucesso!");
                            }
                            else {
                                a.setTitle("Erro:");
                                a.setAlertType(Alert.AlertType.ERROR);
                                a.setContentText("Problemas ao tentar gravar recebimento!");
                            }
                            
                            a.setTitle("Informação:");
                            a.setAlertType(Alert.AlertType.INFORMATION);
                            a.setContentText("Recebimento gravado com sucesso!");
                        }
                        else {
                            a.setTitle("Erro:");
                            a.setAlertType(Alert.AlertType.ERROR);
                            a.setContentText("Problemas ao tentar gravar recebimento!");
                        }
                    }
                }
                else {
                    // update em rec_status
                    if(dalRec.alterar(r)) {
                        a.setTitle("Informação:");
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setContentText("Recebimento gravado com sucesso!");
                    }
                    else {
                        a.setTitle("Erro:");
                        a.setAlertType(Alert.AlertType.ERROR);
                        a.setContentText("Problemas ao tentar gravar recebimento!");
                    }
                }
            }
        }
        else {
            
            if(txValor.getText().isEmpty()) {
                a.setTitle("Erro:");
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Informe um valor");
            }
            else {
                a.setTitle("Erro:");
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Recebimento já efetuado");
            }
        }
       
        modoPesquisa();
        btPesquisar.requestFocus();
        a.showAndWait();
    }

    @FXML
    private void clkBtPesquisar(ActionEvent event) {
        
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        String filtro = "";
        LocalDate venc = dtpVencimento.getValue();
        LocalDate venc1 = dtpVencimento1.getValue();
        
        DALCliente dalCLI = new DALCliente();
        
        DALComanda dalCOM = new DALComanda();
        Comanda com;
        
        DALRecebimento dal = new DALRecebimento();
        List<Recebimento> lista = null;
        
        if((venc != null && venc1 == null) || (venc == null && venc1 != null)) {
            modoPesquisa();
            a.setTitle("Alerta:");
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("Selecione ambas datas!");
            a.showAndWait();
        }
        else {
            if(txCliente.getText().isEmpty()) {
                if(venc == null && venc1 == null) {
                    if(cbTipoRec.getValue() == null)
                        filtro += "rec_tipo like '%"+""+"%'";
                    else
                        filtro += "rec_tipo like '%"+cbTipoRec.getValue()+"%'";
                }
                else {
                    if (cbTipoRec.getValue() == null) {
                        filtro += "rec_tipo like '%" + "" + "%' and rec_vencimento <='" + dtpVencimento1.getValue() + "' and rec_vencimento >='" + dtpVencimento.getValue() + "'";
                    } 
                    else {
                        filtro += "rec_tipo like '%" + cbTipoRec.getValue() + "%' and rec_vencimento <='" + dtpVencimento1.getValue() + "' and rec_vencimento >='" + dtpVencimento.getValue() + "'";
                    }
                }
                lista = dal.get(filtro);
            }
            else {
                
                int cl=0;
                try {
                    cl =  dalCLI.get("lower(cli_nome) like '%" + txCliente.getText().toLowerCase() + "%'").get(0).getCod();
                }
                catch(Exception ex) {
                    cl = -1;
                }
                
                if(cl != -1) {
                    if (venc == null && venc1 == null) {
                        if (cbTipoRec.getValue() == null) {
                            filtro += "rec_tipo like '%" + "" + "%' and rec_cli=" + cl + "";
                        } 
                        else {
                            filtro += "rec_tipo like '%" + cbTipoRec.getValue() + "%' and rec_cli=" + cl + "";
                        }
                    } else {
                        if (cbTipoRec.getValue() == null) {
                            filtro += "rec_tipo like '%" + "" + "%' and rec_vencimento <='" + dtpVencimento1.getValue() + "' and rec_vencimento >='" + dtpVencimento.getValue() + "' and rec_cli=" + cl + "";
                        } 
                        else {
                            filtro += "rec_tipo like '%" + cbTipoRec.getValue() + "%' and rec_vencimento <='" + dtpVencimento1.getValue() + "' and rec_vencimento >='" + dtpVencimento.getValue() + "' and rec_cli=" + cl + "";
                        }
                    }
                    lista = dal.get(filtro);
                }
            }
        }
        
        if(lista != null) {
            for (int i = 0; i < lista.size(); i++) {

                com = dalCOM.getComanda(lista.get(i).getComanda(), lista.get(i).getMesa());

                if(com.isAberta())
                    lista.remove(i);
                else {
                    if(lista.get(i).getVencimento().isEqual(LocalDate.of(1900, 10, 10)))
                        lista.get(i).setVencimento(null);
                }
            }
            
            ObservableList<Recebimento> modelo;
            modelo = FXCollections.observableArrayList(lista);
            tabela.setItems(modelo);
            tabela.refresh();
        }
        
        if(lista == null || lista.isEmpty()) {
            modoPesquisa();
            a.setTitle("Informação:");
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("Não há recebimentos com os filtros informados");
            a.showAndWait();
        }
        else {
            modoAltera();
        }
        dtpVencimento.setValue(null);
        dtpVencimento1.setValue(null);
    }

    @FXML
    private void clkBtCancelar(ActionEvent event) {
        
        if(btPesquisar.isDisable()) {
            modoPesquisa();
        }
        else
            HomeController.spnprincipal.setCenter(null);
    }

    @FXML
    private void clkAddValor(MouseEvent event) {
        txValor.setText(formatarPreco(tabela.getSelectionModel().getSelectedItem().getValor()));
    }
}
