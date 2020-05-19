package bellacanelafx;

import bellacanelafx.db.entidades.Mesa;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MesaController implements Initializable {

    @FXML
    private Label tfNMesa;
    @FXML
    private JFXButton btAbrirComanda;
    @FXML
    private JFXButton btFecharComanda;
    @FXML
    private AnchorPane apPane;
    
    Mesa mesa = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    private void efeito(ActionEvent event, boolean on) {
        double opi=1, opf=0.2;
        if(!on){opi=0.2; opf=1;}
        Node node = ((Node)event.getSource()).getParent().getParent().getParent().getParent(); // referencia o painel das comandas
        FadeTransition ft = new FadeTransition(Duration.millis(500), node);
        ft.setFromValue(opi);
        ft.setToValue(opf);
        ft.play(); 
    }

    public void setMesa(Mesa m){
        mesa = m;
        
        tfNMesa.setText(m.getCod()+"");
        
        apPane.getStyleClass().clear();
        btAbrirComanda.getStyleClass().clear();
        btFecharComanda.getStyleClass().clear();
        btAbrirComanda.getStyleClass().add("button");
        btFecharComanda.getStyleClass().add("button");
        if(m.isLiberada()){
            apPane.getStyleClass().add("mesa-liberada");
            btAbrirComanda.getStyleClass().add("buttonMesaLiberadada");
            btFecharComanda.getStyleClass().add("buttonMesaLiberadada");
        }
        else{
            apPane.getStyleClass().add("mesa-ocupada");
            btAbrirComanda.getStyleClass().add("buttonMesaOcupada");
            btFecharComanda.getStyleClass().add("buttonMesaOcupada");
        }
    }
    
    @FXML
    private void evtAbrirComanda(ActionEvent event) {
        try{
            FXMLLoader loader;
            Parent root;
            
            if(mesa.isLiberada()){
                loader = new FXMLLoader(getClass().getResource("AbrirComanda.fxml"));
                root = (Parent) loader.load();
                AbrirComandaController ctr = loader.getController();

                //pode adicionar atributos na outra tela
                ctr.setNMesa(tfNMesa.getText());
            }
            else{
                loader = new FXMLLoader(getClass().getResource("RegistrarPedido.fxml"));
                root = (Parent) loader.load();
                RegistrarPedidoController ctr = loader.getController();

                //pode adicionar atributos na outra tela
                ctr.setNMesa(tfNMesa.getText());
            }
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            efeito(event, true);
            stage.showAndWait();
            efeito(event, false);
        }
        catch(Exception e){
            System.out.println("DEU ERRO AO ABRIR COMANDA.");
        }
    }

    @FXML
    private void evtFecharComanda(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Recebimento.fxml"));
            Parent root = (Parent) loader.load();
            RecebimentoController ctr = loader.getController();

            //pode adicionar atributos na outra tela

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            efeito(event, true);
            stage.showAndWait();
            efeito(event, false);
        }
        catch(Exception e){
            System.out.println("DEU ERRO AO FECHAR COMANDA.");
        }
    }
    
}
