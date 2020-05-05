/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx;

import bellacanela.db.dal.DALFornecedor;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.Fornecedor;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
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

/**
 * FXML Controller class
 *
 * @author FIGUEIRINHA
 */
public class CadForncedoresController implements Initializable {

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
    private JFXTextField txCod;
    @FXML
    private JFXTextField txNome;
    @FXML
    private JFXTextField txDesc;
    @FXML
    private JFXTextField txEmail;
    @FXML
    private JFXTextField txFone;
    @FXML
    private JFXTextField txPesquisa;
    @FXML
    private TableView<Fornecedor> tabela;
    @FXML
    private TableColumn <Fornecedor, String> colCod;
    @FXML
    private TableColumn <Fornecedor, String> colNome;
    @FXML
    private TableColumn <Fornecedor, String> colEmail;
    @FXML
    private TableColumn <Fornecedor, String> colFone;
    @FXML
    private TableColumn <Fornecedor, String> colDesc;
    @FXML
    private SplitPane pane;
    @FXML
    private AnchorPane apDados;
    @FXML
    private VBox pnSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory("cod"));
        colNome.setCellValueFactory(new PropertyValueFactory("Nome"));
        colDesc.setCellValueFactory(new PropertyValueFactory("Desc"));
        colFone.setCellValueFactory(new PropertyValueFactory("Telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        
        fadeout();
        loadMasks();
        
    }    

    private void fadeout() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), this.pane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play(); 
    }
    private void loadMasks() {        
        MaskFieldUtil.onlyDigitsValue(txFone);
        MaskFieldUtil.onlyAlfaNumericValue(txDesc);
    }
    
    private boolean verificarEmail(String email) {
        
        boolean res = false;
        
        if((email.contains("@hotmail") || email.contains("@gmail")) && email.contains(".com"))
            res = true;
        
        return res;
    }
    
        private void original() {
        this.pnSearch.setDisable(false);
        this.pnSearch.setOpacity(1);
        this.apDados.setDisable(true);
        this.apDados.setOpacity(0.5);
        
        this.btsalvar.setDisable(true);
        this.btcancelar.setDisable(false);
        this.btapagar.setDisable(true);
        this.btalterar.setDisable(true);
        this.btnovo.setDisable(false);
        this.txPesquisa.clear();
        
        ObservableList <Node> componentes = this.apDados.getChildren(); //”limpa” os componentes
        for(Node n : componentes)
        {
            if (n instanceof TextInputControl)  // textfield, textarea e htmleditor
                ((TextInputControl)n).setText("");
            if(n instanceof ComboBox)
                ((ComboBox)n).getItems().clear();
        }
        
        loadTable("");
    }
 
    private void loadTable(String filtro) {
        DALFornecedor dal = new DALFornecedor();
        
        ArrayList<Fornecedor> Forn = dal.get(filtro);
        ObservableList<Fornecedor> modelo = FXCollections.observableArrayList(Forn);
        this.tabela.setItems(modelo);
        this.tabela.refresh();        
    }
        
    private void snackbar(String message, String cor){
        JFXSnackbar sb = new JFXSnackbar(this.apDados);
        Label mens = new Label(message);
        mens.setStyle("-fx-text-fill: "+cor+";"
                    + "-fx-font-size: 14px;"
                    + "-fx-padding: 10px;");
        sb.enqueue(new JFXSnackbar.SnackbarEvent(mens));
    }   
    
    private void edition() {        
        this.pnSearch.setDisable(true);
        this.pnSearch.setOpacity(0.5);
        this.apDados.setDisable(false);
        this.apDados.setOpacity(1);
        
        this.btsalvar.setDisable(false);
        this.btapagar.setDisable(true);
        this.btalterar.setDisable(true);
        //this.txPesquisa.clear();
        
        this.txNome.requestFocus();
    }
        
    
    
    @FXML
    private void clkBtNovo(ActionEvent event) {
        this.edition();
    }

    @FXML
    private void clkBtAlterar(ActionEvent event) {
        if(this.tabela.getSelectionModel().getSelectedItem() != null){
            Fornecedor f = new DALFornecedor().get(this.tabela.getSelectionModel().getSelectedItem().getCod());

            
            this.txCod.setText(f.getCod()+"");
            this.txDesc.setText(f.getDesc()+"");
            this.txEmail.setText(f.getEmail()+"");
            this.txFone.setText(f.getTelefone()+"");
            this.txNome.setText(f.getNome()+"");
           
            this.edition();
         }
    }

    @FXML
    private void clkBtApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        
        a.setContentText("Realmente deseja excluir o Fornecedor " + this.txNome.getText() + "?");
        if(a.showAndWait().get() == ButtonType.OK){
            DALFornecedor dal = new DALFornecedor();
            
            if(dal.apagar(this.tabela.getSelectionModel().getSelectedItem())){
                this.snackbar("Excluido com sucesso!", "green");
                
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
        
        DALFornecedor dal = new DALFornecedor();
        Fornecedor f;
        
        if(txCod.getText() == ""){
            f = new Fornecedor(this.txNome.getText(), this.txFone.getText(), this.txEmail.getText(), this.txDesc.getText());
            if(dal.gravar(f)){
                this.snackbar("Fornecedor gravado com sucesso!", "green");
                
                this.original();
                this.loadTable("");
            }
            else{
                this.snackbar("Problemas ao gravar Fornecedor!", "red");
            }
        }
        else{
            f = new Fornecedor (Integer.parseInt(this.txCod.getText()),this.txNome.getText(), this.txFone.getText(), this.txEmail.getText(), this.txDesc.getText());
            if(dal.alterar(f)){
                this.snackbar("Fornecedor atualizado com sucesso!", "green");
                
                this.original();
                this.loadTable("");
            }
            else{
                this.snackbar("Problemas ao atualizar Fornecedor!", "red");
            }
        }
    }

    @FXML
    private void clkBtCancelar(ActionEvent event) {
        if (!this.apDados.isDisabled())
            this.original();
        else 
            HomeController.spnprincipal.setCenter(null);
    }


    
    @FXML
    private void dgtPesquisa(KeyEvent event) {        
        String filtro = this.txPesquisa.getText().isEmpty() ? "" : "UPPER(for_nome) LIKE '%#1%'";
        filtro = filtro.replaceAll("#1", this.txPesquisa.getText().toUpperCase());
        loadTable(filtro);
    }

    @FXML
    private void clkTabela(MouseEvent event) {
        if(this.tabela.getSelectionModel().getSelectedItem() != null){
            this.btalterar.setDisable(false);
            this.btapagar.setDisable(false);
        }
    }
    
}
