/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author FIGUEIRINHA
 */
public class RegistrarCompraController implements Initializable {

    @FXML
    private JFXButton btNovo;
    @FXML
    private JFXButton btAlterar;
    @FXML
    private JFXButton btApagar;
    @FXML
    private JFXButton btSalvar;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private AnchorPane apDados;
    @FXML
    private JFXComboBox<?> cbCategoria;
    @FXML
    private JFXTextField tfCod;
    @FXML
    private JFXTextField tfNome;
    @FXML
    private JFXTextField tfPreço;
    @FXML
    private JFXTextField tfPreço1;
    @FXML
    private JFXComboBox<?> cbCategoria1;
    @FXML
    private VBox pnSearch;
    @FXML
    private JFXTextField tfSearch;
    @FXML
    private JFXComboBox<?> cbCategoriaSearch;
    @FXML
    private TableView<?> tbProdutos;
    @FXML
    private TableColumn<?, ?> colCod;
    @FXML
    private TableColumn<?, ?> colNome;
    @FXML
    private TableColumn<?, ?> colPreço;
    @FXML
    private TableColumn<?, ?> colCategoria;
    @FXML
    private TableColumn<?, ?> colMedida;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clkNovo(ActionEvent event) {
    }

    @FXML
    private void clkAlterar(ActionEvent event) {
    }

    @FXML
    private void clkApagar(ActionEvent event) {
    }

    @FXML
    private void clkSalvar(ActionEvent event) {
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
    }

    @FXML
    private void evtSearch(KeyEvent event) {
    }

    @FXML
    private void clkTable(MouseEvent event) {
    }
    
}
