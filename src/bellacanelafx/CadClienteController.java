/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx;

import bellacanela.db.dal.DALCliente;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.Cliente;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.Toolkit;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
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
 * @author joao
 */
public class CadClienteController implements Initializable {

    @FXML
    private SplitPane painel;
    @FXML
    private AnchorPane apdados;
    @FXML
    private TableView<Cliente> tabela;
    @FXML
    private TableColumn<Cliente, String> colcod;
    @FXML
    private TableColumn<Cliente, String> colnome;
    @FXML
    private TableColumn<Cliente, String> colcpf;
    @FXML
    private TableColumn<Cliente, String> colemail;
    @FXML
    private TableColumn<Cliente, String> colfone;
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
    private JFXTextField txcod;
    @FXML
    private JFXTextField txnome;
    @FXML
    private JFXTextField txcpf;
    @FXML
    private JFXTextField txemail;
    @FXML
    private JFXTextField txfone;
    @FXML
    private JFXTextField txpesquisa;
    @FXML
    private VBox pnpesquisa;

    /**
     * Initializes the controller class.
     */
    
    private void fadeout() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), painel);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play(); 
    }
    
    private void carregarTabela(String filtro) {
        
        DALCliente dal = new DALCliente();
        List<Cliente> lista = dal.get(filtro);
        ObservableList<Cliente> modelo;
        modelo = FXCollections.observableArrayList(lista);
        tabela.setItems(modelo);
    }
    
    private void EstadoEdicao() {
        pnpesquisa.setDisable(true);
        pnpesquisa.setOpacity(0.5);
        apdados.setDisable(false);
        apdados.setOpacity(1);
        btsalvar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txpesquisa.clear();
        txnome.requestFocus();
    }
    
    private void estadoOriginal() {
        pnpesquisa.setDisable(false);
        pnpesquisa.setOpacity(1);
        apdados.setDisable(true);
        apdados.setOpacity(0.5);
        btsalvar.setDisable(true);
        btcancelar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        btnovo.setDisable(false);
        txpesquisa.clear();
        
        ObservableList <Node> componentes = apdados.getChildren(); //”limpa” os componentes
        for(Node n : componentes)
        {
            if (n instanceof TextInputControl)  // textfield, textarea e htmleditor
                ((TextInputControl)n).setText("");
            if(n instanceof ComboBox)
                ((ComboBox)n).getItems().clear();
        }
        carregarTabela("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        fadeout();
        
        colcod.setCellValueFactory(new PropertyValueFactory("cod"));
        colnome.setCellValueFactory(new PropertyValueFactory("nome"));
        colcpf.setCellValueFactory(new PropertyValueFactory("cpf"));
        colemail.setCellValueFactory(new PropertyValueFactory("email"));
        colfone.setCellValueFactory(new PropertyValueFactory("fone"));
        
        MaskFieldUtil.cpfField(txcpf);
        MaskFieldUtil.foneField(txfone);
        
        estadoOriginal();
    }    

    @FXML
    private void clkTabela(MouseEvent event) {
    }

    @FXML
    private void clkBtNovo(ActionEvent event) {
        
        EstadoEdicao();
    }

    @FXML
    private void clkBtAlterar(ActionEvent event) {
    }

    @FXML
    private void clkBtApagar(ActionEvent event) {
    }

    @FXML
    private void clkBtSalvar(ActionEvent event) {
    }

    @FXML
    private void clkBtCancelar(ActionEvent event) {
        
        if (!apdados.isDisabled()) // caso se encontre em estado de edição
            estadoOriginal();
        else 
            HomeController.spnprincipal.setCenter(null);
        //ou HomeController.spnprincipal.getChildren().clear();
    }

    @FXML
    private void dgtPesquisa(KeyEvent event) {
        
        if(txpesquisa.getText().length() > 20) {
            event.consume();
            Toolkit.getDefaultToolkit().beep();
        }
        else
            carregarTabela("upper (cli_nome) like '%"+txpesquisa.getText().toUpperCase()+"%'");
    }
    
}
