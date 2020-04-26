/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author joao
 */
public class ConfSistemaController implements Initializable {

    @FXML
    private SplitPane painel;
    @FXML
    private JFXButton btsalvar;

    /**
     * Initializes the controller class.
     */
    
    private void fadeout(){
        FadeTransition ft = new FadeTransition(Duration.millis(1000), painel);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play(); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fadeout();
    }    

    @FXML
    private void clkBtSalvar(ActionEvent event) {
    }
    
}
