/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author joao
 */
public class HomeController implements Initializable {

    //dados estáticos
    public static BorderPane spnprincipal=null;
    
    @FXML
    private BorderPane pnprincipal;
    @FXML
    private VBox vbNavegacao;
    @FXML
    private AnchorPane apPrincipal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spnprincipal=pnprincipal;
    }    

    @FXML
    private void clkCadCliente(ActionEvent event) {
        try 
        {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("CadCliente.fxml"));
            pnprincipal.setCenter(root);
        } 
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void clkConfigurarSistema(ActionEvent event) {
        
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("ConfSistema.fxml"));
            pnprincipal.setCenter(root);
        } 
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void clkCadFuncionario(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("CadFuncionario.fxml"));
            pnprincipal.setCenter(root);
        } 
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
}
