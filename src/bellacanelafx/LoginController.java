package bellacanelafx;

import bellacanela.db.dal.DALConfSistema;
import bellacanela.db.dal.DALUsuario;
import bellacanelafx.db.entidades.ConfSistema;
import bellacanelafx.db.entidades.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class LoginController implements Initializable {

    @FXML
    private JFXTextField tfUser;
    @FXML
    private JFXPasswordField tfPassword;
    @FXML
    private JFXButton btLogin;
    @FXML
    private BorderPane pane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.fadeout();
    }    

    private void fadeout() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), this.pane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play(); 
    }
    
    private void fazerLogin() throws SQLException{
        DALUsuario dal = new DALUsuario();
        Usuario u = new Usuario(this.tfUser.getText(), this.tfPassword.getText());
        
        if(dal.autenticacao(u)){
            ObservableList teste = btLogin.getScene().getRoot().getChildrenUnmodifiable();
            for(int i = 0; i < teste.size(); i++){
                if(teste.get(i) instanceof VBox){
                    VBox aux = (VBox)teste.get(i);
                    aux.setDisable(false);
                }
            }
            
            DALConfSistema dalCONF = new DALConfSistema();
            ConfSistema cs = dalCONF.get();
            if(cs == null) { // significa que é o primeiro acesso ao sistema
                
                try {
                    Parent root;
                    root = FXMLLoader.load(getClass().getResource("ConfSistema.fxml"));
                    HomeController.spnprincipal.setCenter(root);
                }
                catch (IOException ex) {
                    System.out.println(ex);
                }
            }
            else
                HomeController.spnprincipal.setCenter(null);
        }
        else{
            JFXSnackbar sb = new JFXSnackbar(pane);
            Label mens = new Label("Login ou senha incorretos, tente novamente!");
            mens.setStyle("-fx-text-fill: red;"
                        + "-fx-font-size: 14px;"
                        + "-fx-padding: 10px;");
            sb.enqueue(new SnackbarEvent(mens));
        }
    }
    
    @FXML
    private void evtLogin(ActionEvent event) throws SQLException {
        this.fazerLogin();
    }

    @FXML
    private void evtKey(KeyEvent event) throws SQLException {
        if(event.getCode() == KeyCode.ENTER)
            this.fazerLogin();
    }
    
}
