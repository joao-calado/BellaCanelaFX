/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author FIGUEIRINHA
 */
public class QuitarPgtosController implements Initializable {

    @FXML
    private JFXButton btalterar;
    @FXML
    private JFXButton btapagar;
    @FXML
    private JFXButton btsalvar;
    @FXML
    private JFXButton btcancelar;
    @FXML
    private AnchorPane apDados;
    @FXML
    private JFXTextField txCod;
    @FXML
    private JFXTextField txNome;
    @FXML
    private JFXTextField txDesc;
    @FXML
    private JFXTextField txValorpago;
    @FXML
    private JFXDatePicker dtVencimento;
    @FXML
    private JFXDatePicker dtPagamento;
    @FXML
    private JFXTextField txValor;
    @FXML
    private VBox pnSearch;
    @FXML
    private TableView<?> tabela;
    @FXML
    private TableColumn<?, ?> colCod;
    @FXML
    private TableColumn<?, ?> colNome;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colDesc1;
    @FXML
    private TableColumn<?, ?> colFone;
    @FXML
    private TableColumn<?, ?> colDesc;
    @FXML
    private JFXTextField txValorpago1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    }

    @FXML
    private void clkTabela(MouseEvent event) {
    }
    
}
