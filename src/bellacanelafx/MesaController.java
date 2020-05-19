package bellacanelafx;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class MesaController implements Initializable {

    @FXML
    private Label tfNMesa;
    @FXML
    private JFXButton btAbrirComanda;
    @FXML
    private JFXButton btFecharComanda;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void evtAbrirComanda(ActionEvent event) {
    }

    @FXML
    private void evtFecharComanda(ActionEvent event) {
    }
    
}
