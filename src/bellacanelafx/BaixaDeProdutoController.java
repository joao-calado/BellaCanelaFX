package bellacanelafx;

import bellacanela.db.dal.DALBaixaDeProduto;
import bellacanela.db.dal.DALFuncionario;
import bellacanela.db.dal.DALProduto;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.BaixaDeProduto;
import bellacanelafx.db.entidades.Funcionario;
import bellacanelafx.db.entidades.Produtos;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.StringConverter;

public class BaixaDeProdutoController implements Initializable {

    @FXML
    private SplitPane painel;
    @FXML
    private JFXButton btnovo;
    @FXML
    private JFXButton btalterar;
    @FXML
    private JFXButton btapagar;
    @FXML
    private JFXButton btsalvar;
    @FXML
    private JFXButton btcancelar;
    @FXML
    private AnchorPane apdados;
    @FXML
    private JFXTextArea txtMotivo;
    @FXML
    private JFXComboBox<Produtos> cbProdutos;
    @FXML
    private JFXComboBox<Funcionario> cbFuncionarios;
    @FXML
    private Spinner<Integer> spnQuantidade;
    @FXML
    private JFXDatePicker dtpDataBaixa;
    @FXML
    private VBox pnpesquisa;
    @FXML
    private JFXTextField txtPesquisa;
    @FXML
    private JFXDatePicker dtpInicio;
    @FXML
    private JFXRadioButton checkInicio;
    @FXML
    private JFXDatePicker dtpFim;
    @FXML
    private JFXRadioButton checkFim;
    @FXML
    private TableView<BaixaDeProduto> tabela;
    @FXML
    private TableColumn<BaixaDeProduto, String> colProduto;
    @FXML
    private TableColumn<BaixaDeProduto, Integer> colQuantidade;
    @FXML
    private TableColumn<BaixaDeProduto, LocalDate> colData;
    @FXML
    private TableColumn<BaixaDeProduto, String> colFuncionario;
    @FXML
    private TableColumn<BaixaDeProduto, String> colMotivo;
    @FXML
    private JFXButton btnPesquisa;
    @FXML
    private JFXTextField tfCod;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.colProduto.setCellValueFactory((TableColumn.CellDataFeatures<BaixaDeProduto, String> bp) -> new SimpleStringProperty(bp.getValue().getProduto().getNome()));
        this.colFuncionario.setCellValueFactory((TableColumn.CellDataFeatures<BaixaDeProduto, String> bp) -> new SimpleStringProperty(bp.getValue().getFuncionario().getNome()));
        this.colQuantidade.setCellValueFactory(new PropertyValueFactory("quantidade"));
        this.colData.setCellValueFactory(new PropertyValueFactory("data"));
        this.colMotivo.setCellValueFactory(new PropertyValueFactory("motivo"));
        
        SpinnerValueFactory<Integer> valueSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999999, 1);
        spnQuantidade.setValueFactory(valueSpinner);
        
        this.fadeout();
        this.loadMasks();
        this.original();
    }    
    
    private void fadeout() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), this.painel);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play(); 
    }
    
    private void loadTable(String filtro) {
        DALBaixaDeProduto dal = new DALBaixaDeProduto();
        ArrayList<BaixaDeProduto> baixas = dal.getBaixas(filtro);
        ObservableList<BaixaDeProduto> olBaixas = FXCollections.observableArrayList(baixas);
        this.tabela.setItems(olBaixas);
        this.tabela.refresh();
    }
    
    private void loadCBProdutos(String filtro) {
        cbProdutos.setConverter(new StringConverter<Produtos>() {
            @Override
            public String toString(Produtos prod) {
                return prod.getNome();
            }
            
            @Override
            public Produtos fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        DALProduto dalPro = new DALProduto();
        ObservableList<Produtos> olPro = FXCollections.observableArrayList(dalPro.get(filtro));
        cbProdutos.getItems().clear();
        cbProdutos.setItems(olPro);
        cbProdutos.setValue(cbProdutos.getItems().get(0));
    }
    
    private void loadCBFuncionarios(String filtro) {
        cbFuncionarios.setConverter(new StringConverter<Funcionario>() {
            @Override
            public String toString(Funcionario f) {
                return f.getNome();
            }
            
            @Override
            public Funcionario fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        DALFuncionario dal = new DALFuncionario();
        ObservableList<Funcionario> olFun = FXCollections.observableArrayList(dal.getFuncionarios(filtro));
        cbFuncionarios.getItems().clear();
        cbFuncionarios.setItems(olFun);
        cbFuncionarios.setValue(cbFuncionarios.getItems().get(0));
    }
    
    private void loadMasks() {
        
    }
    
    private void snackbar(String message, String cor){
        JFXSnackbar sb = new JFXSnackbar(this.apdados);
        Label mens = new Label(message);
        mens.setStyle("-fx-text-fill: "+cor+";"
                    + "-fx-font-size: 14px;"
                    + "-fx-padding: 10px;");
        sb.enqueue(new JFXSnackbar.SnackbarEvent(mens));
    }
    
    private void edition() {        
        this.pnpesquisa.setDisable(true);
        this.pnpesquisa.setOpacity(0.5);
        this.apdados.setDisable(false);
        this.apdados.setOpacity(1);
        
        this.btsalvar.setDisable(false);
        this.btapagar.setDisable(true);
        this.btalterar.setDisable(true);
    }
    
    private void original() {
        this.pnpesquisa.setDisable(false);
        this.pnpesquisa.setOpacity(1);
        this.apdados.setDisable(true);
        this.apdados.setOpacity(0.5);
        
        this.btsalvar.setDisable(true);
        this.btcancelar.setDisable(false);
        this.btapagar.setDisable(true);
        this.btalterar.setDisable(true);
        this.btnovo.setDisable(false);
        
        ObservableList <Node> componentes = this.apdados.getChildren(); //”limpa” os componentes
        for(Node n : componentes) {
            if (n instanceof TextInputControl)  // textfield, textarea e htmleditor
                ((TextInputControl)n).setText("");
            if(n instanceof ComboBox)
                ((ComboBox)n).getItems().clear();
        }
        
        loadTable("");
        loadCBProdutos("prod_estoque != -1");
        loadCBFuncionarios("");
    }

    @FXML
    private void clkBtNovo(ActionEvent event) {
        this.edition();
    }

    @FXML
    private void clkBtAlterar(ActionEvent event) {
        if(this.tabela.getSelectionModel().getSelectedItem() != null){
            BaixaDeProduto bp = (BaixaDeProduto)this.tabela.getSelectionModel().getSelectedItem();
            
            this.tfCod.setText(bp.getCod()+"");
            this.dtpDataBaixa.setValue(bp.getData());
            this.txtMotivo.setText(bp.getMotivo());
            this.cbFuncionarios.setValue(bp.getFuncionario());
            this.cbProdutos.setValue(bp.getProduto());
            this.spnQuantidade.getValueFactory().setValue(bp.getQuantidade());
            
            //Aumenta o estoque
            bp.getProduto().setEstoque(bp.getProduto().getEstoque() + this.spnQuantidade.getValue());
            new DALProduto().alterEstoque(bp.getProduto());
            
            this.edition();
        }
    }

    @FXML
    private void clkBtApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        
        a.setContentText("Realmente deseja excluir a baixa?");
        if(a.showAndWait().get() == ButtonType.OK){
            DALBaixaDeProduto dal = new DALBaixaDeProduto();
            BaixaDeProduto bp = this.tabela.getSelectionModel().getSelectedItem();
            
            if(dal.delete(bp)){
                this.snackbar("Excluido com sucesso!", "green");
                
                //Aumenta o estoque
                bp.getProduto().setEstoque(bp.getProduto().getEstoque() + this.spnQuantidade.getValue());
                new DALProduto().alterEstoque(bp.getProduto());
                
                this.original();
                this.loadTable("");
            }
            else{
                this.snackbar("Erro ao excluir.", "red");
            }
        }
    }

    @FXML
    private void clkBtSalvar(ActionEvent event) {
        boolean ans = true;

        if(this.txtMotivo.getText() == null || this.dtpDataBaixa.getValue() == null){
            ans = false;
            this.snackbar("Alguns campos ainda precisam ser preenchidos", "red");
        }
        
        if(spnQuantidade.getValue() > cbProdutos.getSelectionModel().getSelectedItem().getEstoque()) {
            ans = false;
            this.snackbar("Quantidade em sestoque insuficiente", "red");
        }
        
        if(ans){
            int COD;
            
            try{
                COD = Integer.parseInt(this.tfCod.getText());
            }
            catch(NumberFormatException e){
                COD = 0;
            }

            DALBaixaDeProduto dal = new DALBaixaDeProduto();
            BaixaDeProduto bp = new BaixaDeProduto (
                COD,
                this.spnQuantidade.getValue(),
                this.txtMotivo.getText(),
                this.dtpDataBaixa.getValue(),
                this.cbFuncionarios.getValue(),
                this.cbProdutos.getValue()
            );
            
            if(bp.getCod() == 0){
                if(dal.insert(bp)){
                    this.snackbar("Baixa gravado com sucesso!", "green");
                    
                    //Diminui o estoque
                    bp.getProduto().setEstoque(bp.getProduto().getEstoque() - this.spnQuantidade.getValue());
                    new DALProduto().alterEstoque(bp.getProduto());
                    
                    this.original();
                    this.loadTable("");
                }
                else{
                    this.snackbar("Problemas ao gravar Baixa!", "red");
                }
            }
            else{
                if(dal.update(bp)){
                    this.snackbar("Baixa atualizada com sucesso!", "green");
                    
                    //Diminui o estoque
                    bp.getProduto().setEstoque(bp.getProduto().getEstoque() - this.spnQuantidade.getValue());
                    new DALProduto().alterEstoque(bp.getProduto());
                    
                    this.original();
                    this.loadTable("");
                }
                else{
                    this.snackbar("Problemas ao atualizar Baixa!", "red");
                }
            }
        }
    }

    @FXML
    private void clkBtCancelar(ActionEvent event) {
        if (!this.apdados.isDisabled()){
            int COD;
            
            try{
                COD = Integer.parseInt(this.tfCod.getText());
            }
            catch(NumberFormatException e){
                COD = 0;
            }
            
            if(COD != 0){
                //Diminui o estoque
                Produtos p = tabela.getSelectionModel().getSelectedItem().getProduto();
                p.setEstoque(p.getEstoque() - tabela.getSelectionModel().getSelectedItem().getQuantidade());
                new DALProduto().alterEstoque(p);
            }
            
            this.original();
        }
        else {
            HomeController.spnprincipal.setCenter(null);
        }
    }


    @FXML
    private void clkTabela(MouseEvent event) {
        if(this.tabela.getSelectionModel().getSelectedItem() != null){
            this.btalterar.setDisable(false);
            this.btapagar.setDisable(false);
        }
    }

    @FXML
    private void evtPesquisar(ActionEvent event) {
        String filtro = "";
        
        if(checkInicio.isSelected() && checkFim.isSelected()) {
            filtro = "bai_data BETWEEN '#1' AND '#2'";
            filtro = filtro.replaceAll("#1", dtpInicio.getValue().toString());
            filtro = filtro.replaceAll("#2", dtpFim.getValue().toString());
        }
        else {
            if(checkInicio.isSelected()) {
                filtro = "bai_data = '#1'";
                filtro = filtro.replaceAll("#1", dtpInicio.getValue().toString());
            }
            else if(checkFim.isSelected()) {
                filtro = "bai_data = '#1'";
                filtro = filtro.replaceAll("#1", dtpFim.getValue().toString());
            }
        }

        loadTable(filtro);
    }
    
}
