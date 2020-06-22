package bellacanelafx;

import bellacanela.db.dal.DALRecebimento;
import bellacanela.util.MaskFieldUtil;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
    private TableColumn<Recebimento, Integer> colCliente;
    @FXML
    private TableColumn<Recebimento, String> colTipo;
    @FXML
    private TableColumn<Recebimento, Double> colValor;
    @FXML
    private TableColumn<Recebimento, LocalDate> colRecebimento;
    @FXML
    private TableColumn<Recebimento, LocalDate> colVencimento;
    @FXML
    private TableColumn<Recebimento, Integer> colMesa;
    @FXML
    private JFXButton btReceber;
    @FXML
    private JFXButton btPesquisar;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private TableView<Recebimento> tabela;

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
            tr.add("à vista");
            tr.add("crédito");
            tr.add("débito");
            tr.add("a ver");
        }
        else {
            tr.add("à vista");
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
        
        txValor.clear();
        txValor.setDisable(true);
        btReceber.setDisable(true);
        btExcluir.setDisable(true);
        
        btPesquisar.setDisable(false);
        dtpVencimento.setDisable(false);
    }
    
    private void modoAltera() {
        
        carregarCB("altera");
        tabela.setDisable(false);
        cbTipoRec.setPromptText("Tipo de recebimento");
        
        txValor.clear();
        txValor.setDisable(false);
        btReceber.setDisable(false);
        btExcluir.setDisable(false);
        
        btPesquisar.setDisable(true);
        dtpVencimento.setDisable(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        fadeout();
        MaskFieldUtil.monetaryField(txValor);
        
        colCliente.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, Integer> r) -> new SimpleObjectProperty(r.getValue().getCliente()));
        colTipo.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, String> r) -> new SimpleStringProperty(r.getValue().getTipo()));
        colValor.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, Double> r) -> new SimpleObjectProperty(r.getValue().getValor()));
        colRecebimento.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, LocalDate> r) -> new SimpleObjectProperty(r.getValue().getRecebimento()));
        colVencimento.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, LocalDate> r) -> new SimpleObjectProperty(r.getValue().getVencimento()));
        colMesa.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, Integer> r) -> new SimpleObjectProperty(r.getValue().getMesa()));
    
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
        Double pgto = Double.parseDouble(txValor.getText().replaceAll(",", "."));
        
        if(tabela.getSelectionModel().getSelectedItem().getStatus().equals("N")) {
            
            r = new Recebimento(0, tabela.getSelectionModel().getSelectedItem().getCliente(), 
                                   tabela.getSelectionModel().getSelectedItem().getTipo(), 
                                   tabela.getSelectionModel().getSelectedItem().getValor(), 
                                   tabela.getSelectionModel().getSelectedItem().getRecebimento(), 
                                   tabela.getSelectionModel().getSelectedItem().getVencimento(), 
                                   "S", 
                                   tabela.getSelectionModel().getSelectedItem().getMesa(), 
                                   tabela.getSelectionModel().getSelectedItem().getComanda());
            
            if(pgto > tabela.getSelectionModel().getSelectedItem().getValor()) {
                // pagar com gorjeta - update em rec_status
                if(cbTipoRec.getValue() != null) {
                    if(dalRec.alterar(r)) {
                        r.setValor(r.getValor() - pgto);
                        r.setTipo("gorjeta");
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
                    a.setTitle("Erro:");
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setContentText("Selecione um tipo de pagamento!");
                }
            }
            else {
                if(pgto < tabela.getSelectionModel().getSelectedItem().getValor()) {
                    // update e gerar rec
                    r.setValor(r.getValor() - pgto);
                    r.setStatus("N");
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
            a.setTitle("Erro:");
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Recebimento já efetuado");
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
        
        if(venc == null) {
            if(cbTipoRec.getValue() == null)
                filtro += "rec_tipo like '%"+""+"%'";
            else
                filtro += "rec_tipo like '%"+cbTipoRec.getValue()+"%'";
        }
        else {
            if(cbTipoRec.getValue() == null)
                filtro += "rec_tipo like '%"+""+"%' and rec_vencimento <='"+dtpVencimento.getValue()+"'";
            else
                filtro += "rec_tipo like '%"+cbTipoRec.getValue()+"%' and rec_vencimento <='"+dtpVencimento.getValue()+"'";
        }
        
        DALRecebimento dal = new DALRecebimento();
        List<Recebimento> lista = dal.get(filtro);
        
        for (int i = 0; i < lista.size(); i++) {
            
            if(lista.get(i).getVencimento().isEqual(LocalDate.of(1900, 10, 10)))
                lista.get(i).setVencimento(null);
        }
        
        ObservableList<Recebimento> modelo;
        modelo = FXCollections.observableArrayList(lista);
        tabela.setItems(modelo);
        tabela.refresh();
        
        if(lista.isEmpty()) {
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
