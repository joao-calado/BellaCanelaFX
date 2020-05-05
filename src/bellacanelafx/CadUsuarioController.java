package bellacanelafx;

import bellacanela.db.dal.DALUsuario;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CadUsuarioController implements Initializable {

    @FXML
    private SplitPane pane;
    @FXML
    private JFXButton btNovo;
    @FXML
    private JFXButton btAlterar;
    @FXML
    private JFXButton btApagar;
    @FXML
    private JFXButton btSalvar;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private AnchorPane apDados;
    @FXML
    private JFXTextField tfLogin;
    @FXML
    private JFXTextField tfSenha;
    @FXML
    private VBox pnSearch;
    @FXML
    private JFXTextField tfSearch;
    @FXML
    private TableColumn<Usuario, String> colLogin;
    @FXML
    private TableColumn<Usuario, String> colSenha;
    @FXML
    private TableView<Usuario> tbUsuarios;

    private boolean update = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.colLogin.setCellValueFactory(new PropertyValueFactory("login"));
        this.colSenha.setCellValueFactory(new PropertyValueFactory("senha"));
        
        this.fadeout();
        this.loadMasks();
        this.original();
    }
    
    private void fadeout() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), this.pane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play(); 
    }
    
    private void loadTable(String filtro) {
        DALUsuario dal = new DALUsuario();
        ArrayList<Usuario> usuarios = dal.getUsuarios(filtro);
        ObservableList<Usuario> modelo = FXCollections.observableArrayList(usuarios);
        this.tbUsuarios.setItems(modelo);
        this.tbUsuarios.refresh();
    }
    
    private void loadMasks() {
        MaskFieldUtil.maxField(this.tfLogin, 16);
        MaskFieldUtil.maxField(this.tfSenha, 32);
    }
    
    private void edition() {        
        this.pnSearch.setDisable(true);
        this.pnSearch.setOpacity(0.5);
        this.apDados.setDisable(false);
        this.apDados.setOpacity(1);
        
        this.btSalvar.setDisable(false);
        this.btApagar.setDisable(true);
        this.btAlterar.setDisable(true);
        this.tfSearch.clear();
        this.tfLogin.requestFocus();
    }
    
    private void original() {
        this.pnSearch.setDisable(false);
        this.pnSearch.setOpacity(1);
        this.apDados.setDisable(true);
        this.apDados.setOpacity(0.5);
        
        this.btSalvar.setDisable(true);
        this.btCancelar.setDisable(false);
        this.btApagar.setDisable(true);
        this.btAlterar.setDisable(true);
        this.btNovo.setDisable(false);
        this.tfSearch.clear();
        
        ObservableList <Node> componentes = this.apDados.getChildren(); //”limpa” os componentes
        for(Node n : componentes)
        {
            if (n instanceof TextInputControl)  // textfield, textarea e htmleditor
                ((TextInputControl)n).setText("");
            if(n instanceof ComboBox)
                ((ComboBox)n).getItems().clear();
        }
        
        loadTable("");
    }
    
    private void snackbar(String message, String cor){
        JFXSnackbar sb = new JFXSnackbar(this.apDados);
        Label mens = new Label(message);
        mens.setStyle("-fx-text-fill: "+cor+";"
                    + "-fx-font-size: 14px;"
                    + "-fx-padding: 10px;");
        sb.enqueue(new JFXSnackbar.SnackbarEvent(mens));
    }

    @FXML
    private void clkNovo(ActionEvent event) {
        this.edition();
    }

    @FXML
    private void clkAlterar(ActionEvent event) {
        if(this.tbUsuarios.getSelectionModel().getSelectedItem() != null){
            Usuario u = (Usuario)this.tbUsuarios.getSelectionModel().getSelectedItem();
            
            this.tfLogin.setText(u.getLogin());
            this.tfLogin.setDisable(true);
            this.tfSenha.setText(u.getSenha());
            
            this.update = true;
            this.edition();
        }
    }

    @FXML
    private void clkApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        
        a.setContentText("Realmente deseja excluir o usuário?");
        if(a.showAndWait().get() == ButtonType.OK){
            DALUsuario dal = new DALUsuario();
            Usuario u = (Usuario)this.tbUsuarios.getSelectionModel().getSelectedItem();
            
            if(dal.delete(u.getLogin())){
                this.snackbar("Exluido com sucesso!", "green");
                
                this.original();
                this.loadTable("");
            }
            else{
                this.snackbar("Problemas ao excluir o usuario", "red");
            }
        }
    }

    @FXML
    private void clkSalvar(ActionEvent event) {
        boolean ans = true;
        
        if(this.tfLogin.getText().isEmpty() || this.tfSenha.getText().isEmpty()){
            ans = false;
            this.snackbar("Alguns campos ainda precisam ser preenchidos", "red");
        }
        
        if(ans){
            DALUsuario dal = new DALUsuario();
            Usuario u = new Usuario(this.tfLogin.getText(), this.tfSenha.getText());

            if(this.update){
                if(dal.update(u)){
                    this.snackbar("Usuario alterado com sucesso!", "green");
                    this.update = false;
                    this.tfLogin.setDisable(false);
                    this.original();
                    this.loadTable("");
                }
                else{
                    this.snackbar("Erro ao tentar alterar o usuario", "red");
                }
            }
            else{
                if(dal.insert(u)){
                    this.snackbar("Usuario cadastrado com sucesso!", "green");
                    this.original();
                    this.loadTable("");
                }
                else{
                    this.snackbar("Erro ao tentar cadastrar o usuario", "red");
                }
            }
        }
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        if (!this.apDados.isDisabled())
            this.original();
        else 
            HomeController.spnprincipal.setCenter(null);
    }

    @FXML
    private void evtSearch(KeyEvent event) {
        String filtro = this.tfSearch.getText().isEmpty() ? "" : "UPPER(user_login) LIKE '%#1%'";
        filtro = filtro.replaceAll("#1", this.tfSearch.getText().toUpperCase());
        loadTable(filtro);
    }

    @FXML
    private void clkTable(MouseEvent event) {
        if(this.tbUsuarios.getSelectionModel().getSelectedItem() != null){
            this.btAlterar.setDisable(false);
            this.btApagar.setDisable(false);
        }
    }
    
}
