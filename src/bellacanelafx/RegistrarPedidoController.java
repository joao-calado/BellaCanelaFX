
package bellacanelafx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class RegistrarPedidoController implements Initializable {

    @FXML
    private SplitPane painel;
    @FXML
    private AnchorPane apdados;
    @FXML
    private VBox pnpesquisa;
    @FXML
    private TableView<?> tabela;
    @FXML
    private TableColumn<?, ?> colComanda;
    @FXML
    private TableColumn<?, ?> colNome;
    @FXML
    private TableColumn<?, ?> colPrecoUni;
    @FXML
    private TableColumn<?, ?> colQtde;
    @FXML
    private TableColumn<?, ?> colPrecoTot;
    @FXML
    private Label lbNMesa;
    @FXML
    private Label lbPrecoTotMesa;
    @FXML
    private JFXTextField tfNome;
    @FXML
    private JFXDatePicker dpData;
    @FXML
    private JFXTextField tfDescricao;
    @FXML
    private JFXComboBox<?> cbComanda;
    @FXML
    private JFXTextField tfPrecoTotComanda;
    @FXML
    private JFXButton btAddComanda;
    @FXML
    private JFXComboBox<?> cbProdutos;
    @FXML
    private Spinner<?> spQtde;
    @FXML
    private JFXButton btInserir;
    @FXML
    private JFXButton btDeletar;
    @FXML
    private JFXCheckBox checkTodasComandas;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clkTabela(MouseEvent event) {
        
    }
    
}
