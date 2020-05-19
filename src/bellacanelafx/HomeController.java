package bellacanelafx;

import bellacanela.db.dal.DALConfSistema;
import bellacanelafx.db.entidades.ConfSistema;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class HomeController implements Initializable {

    //dados estáticos
    public static BorderPane spnprincipal=null;
    public static ToolBar toolbarPane = null;
    public static AnchorPane apcentro = null;
    public static VBox vbnavegacao = null;
    public static Menu micadastros = null;
    
    @FXML
    private BorderPane pnprincipal;
    @FXML
    private VBox vbNavegacao;
    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ToolBar toolbarpn;
    @FXML
    private Menu miCadastros;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        spnprincipal = pnprincipal;
        toolbarPane = toolbarpn;
        apcentro = apPrincipal;
        vbnavegacao = vbNavegacao;
        micadastros = miCadastros;
        
//        this.vbNavegacao.setDisable(true);
//        
//        try 
//        {
//            Parent root;
//            root = FXMLLoader.load(getClass().getResource("login.fxml"));
//            pnprincipal.setCenter(root);
//        } 
//        catch (IOException ex) {
//            System.out.println(ex);
//        }
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
            BellaCanela.sStage.setTitle("CRUD Funcionario");
            pnprincipal.setCenter(root);
        } 
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void clkCadUsuario(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("CadUsuario.fxml"));
            BellaCanela.sStage.setTitle("CRUD Usuario");
            pnprincipal.setCenter(root);
        } 
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void clkCadProdutos(ActionEvent event) {
         try 
        {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("CadProdutos.fxml"));
            pnprincipal.setCenter(root);
        } 
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void clkCadFornecedores(ActionEvent event) {
         try 
        {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("CadForncedores.fxml"));
            pnprincipal.setCenter(root);
        } 
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void clkCadMedida(ActionEvent event) {
        try 
        {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("CadMedida.fxml"));
            pnprincipal.setCenter(root);
        } 
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void clkCadCategoria(ActionEvent event) {
        try 
        {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("CadCategoria.fxml"));
            pnprincipal.setCenter(root);
        } 
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
}
