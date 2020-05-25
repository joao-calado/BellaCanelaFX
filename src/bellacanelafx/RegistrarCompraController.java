/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
    private JFXTextField tfCod;
    @FXML
    private VBox pnSearch;
    @FXML
    private JFXTextField tfSearch;
    @FXML
    private TableView<?> tbProdutos;
    @FXML
    private JFXComboBox<?> cbProduto;
    @FXML
    private JFXTextField tfNotaFiscal;
    @FXML
    private JFXTextField tfPrecoTotal;
    @FXML
    private JFXTextField tfQTDProduto;
    @FXML
    private JFXComboBox<?> cbFornecedor;
    @FXML
    private TableColumn<?, ?> colPrCod;
    @FXML
    private TableColumn<?, ?> colPrProduto;
    @FXML
    private TableColumn<?, ?> colPrQuantidade;
    @FXML
    private TableColumn<?, ?> colPrPreco;
    @FXML
    private TableColumn<?, ?> colPrTotal;
    @FXML
    private JFXButton btApagarProd;
    @FXML
    private JFXButton btSalvarProd;
    @FXML
    private JFXButton btCancelarProd;
    @FXML
    private JFXComboBox<?> cbFornecedorSearch;
    @FXML
    private TableView<?> tbCompras;
    @FXML
    private TableColumn<?, ?> colNFCod;
    @FXML
    private TableColumn<?, ?> colNFNotaFiscal;
    @FXML
    private TableColumn<?, ?> colNFFornecedor;
    @FXML
    private TableColumn<?, ?> colNFParcelas;
    @FXML
    private TableColumn<?, ?> colNFValorTotal;
    @FXML
    private JFXTextField tfDescricao;
    @FXML
    private JFXTextField tfParcelas;
    @FXML
    private JFXDatePicker dpVencimento;

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

    @FXML
    private void clkApagarProd(ActionEvent event) {
    }

    @FXML
    private void clkSalvarProd(ActionEvent event) {
    }

    @FXML
    private void clkCancelarProd(ActionEvent event) {
    }
    
}
