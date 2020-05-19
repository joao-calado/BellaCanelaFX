package bellacanelafx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class AbrirComandaController implements Initializable {

    @FXML
    private AnchorPane apdados;
    @FXML
    private JFXDatePicker dpData;
    @FXML
    private JFXTextField tfDescricao;
    @FXML
    private JFXButton btAbrirComanda;
    @FXML
    private JFXComboBox<?> cbNome;
    @FXML
    private JFXTextField tfNumComanda;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
