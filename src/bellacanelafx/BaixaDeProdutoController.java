package bellacanelafx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    private JFXComboBox<?> cbProdutos;
    @FXML
    private JFXComboBox<?> cbFuncionarios;
    @FXML
    private Spinner<?> spnQuantidade;
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
    private TableView<?> tabela;
    @FXML
    private TableColumn<?, ?> colProduto;
    @FXML
    private TableColumn<?, ?> colQuantidade;
    @FXML
    private TableColumn<?, ?> colData;
    @FXML
    private TableColumn<?, ?> colFuncionario;
    @FXML
    private TableColumn<?, ?> colMotivo;
    @FXML
    private JFXButton btnPesquisa;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clkBtNovo(ActionEvent event) {
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

    @FXML
    private void evtPesquisar(ActionEvent event) {
    }
    
}
