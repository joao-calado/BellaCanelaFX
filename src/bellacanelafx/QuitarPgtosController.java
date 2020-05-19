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
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
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
    private TableColumn<?, ?> colDesc;
    @FXML
    private SplitPane pane;
    @FXML
    private JFXButton btQuitar;
    @FXML
    private JFXButton btEstornar;
    @FXML
    private JFXButton btSelecionar;
    @FXML
    private JFXTextField txDescJuros;
    @FXML
    private RadioButton rbAntesde;
    @FXML
    private JFXDatePicker dpAntesde;
    @FXML
    private RadioButton rbDepoisde;
    @FXML
    private JFXDatePicker dpDepoisde;
    @FXML
    private RadioButton rbPago;
    @FXML
    private RadioButton rbAPagar;
    @FXML
    private RadioButton rbVencido;
    @FXML
    private TableColumn<?, ?> colValor;
    @FXML
    private TableColumn<?, ?> colVencimento;
    @FXML
    private TableColumn<?, ?> colPagamento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void clkBtCancelar(ActionEvent event) {
    }

    @FXML
    private void clkTabela(MouseEvent event) {
    }

    @FXML
    private void clkBtQuitar(ActionEvent event) {
    }

    @FXML
    private void clkBtEstornar(ActionEvent event) {
    }

    @FXML
    private void clkBtSelecionar(ActionEvent event) {
    }
    
}
