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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    
    private void snackbar(String message, String cor){
        JFXSnackbar sb = new JFXSnackbar(this.pane);
        Label mens = new Label(message);
        mens.setStyle("-fx-text-fill: "+cor+";"
                    + "-fx-font-size: 14px;"
                    + "-fx-padding: 10px;");
        sb.enqueue(new JFXSnackbar.SnackbarEvent(mens));
    }
    
    private void snackbar(Pane pn, String message, String cor){
        JFXSnackbar sb = new JFXSnackbar(pn);
        Label mens = new Label(message);
        mens.setStyle("-fx-text-fill: "+cor+";"
                    + "-fx-font-size: 14px;"
                    + "-fx-padding: 10px;");
        sb.enqueue(new JFXSnackbar.SnackbarEvent(mens));
    }
    
    
    private void fazerLogin() throws SQLException{
        boolean ans = true;
        Usuario u = null;
        
        DALUsuario dal = new DALUsuario();
        
        if(!dal.verificarLogin(this.tfUser.getText())){
            ans = false;
            this.snackbar("Login inexistente", "red");
        }
        else{
            u = dal.getUsuario(this.tfUser.getText());
            
            if(!u.getSenha().equals(this.tfPassword.getText())){
                ans = false;
                this.snackbar("Senha incorreta", "red");
            }
            
            else if(!u.isHabilitado()){
                ans = false;
                this.snackbar("Usuário não está habilitado", "red");
            }
        }
        
        if(ans){
            HomeController.vbnavegacao.setDisable(false);
            HomeController.spnprincipal.setCenter(null);
            if(u.getNivel() == 0){
                //HomeController.micadastros.setDisable(true);
                HomeController.micadastros.setVisible(false);
            }
            
            this.snackbar(HomeController.spnprincipal, "Login realizado com sucesso!", "green");
            
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
