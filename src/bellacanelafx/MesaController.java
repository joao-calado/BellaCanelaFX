package bellacanelafx;

import bellacanela.db.dal.DALComanda;
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
    DALComanda dalCom = new DALComanda();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void efeito(ActionEvent event, boolean on) {
        double opi = 1, opf = 0.2;
        if (!on) {
            opi = 0.2;
            opf = 1;
        }
        Node node = ((Node) event.getSource()).getParent().getParent().getParent().getParent(); // referencia o painel das comandas
        FadeTransition ft = new FadeTransition(Duration.millis(500), node);
        ft.setFromValue(opi);
        ft.setToValue(opf);
        ft.play();
    }

    public void setMesa(Mesa m) {
        mesa = m;

        tfNMesa.setText(m.getCod() + "");
        verificarMesa();
    }

    public void verificarMesa(){
        apPane.getStyleClass().clear();
        btAbrirComanda.getStyleClass().clear();
        btFecharComanda.getStyleClass().clear();
        btAbrirComanda.getStyleClass().add("button");
        btFecharComanda.getStyleClass().add("button");
        if (mesa.isLiberada())
            setMesaOcupada();
        else
            setMesaDesocupada();
    }
    
    public void setMesaOcupada() {
        apPane.getStyleClass().add("mesa-liberada");
        btAbrirComanda.getStyleClass().add("buttonMesaLiberadada");
        btFecharComanda.setDisable(true);
        btFecharComanda.getStyleClass().add("buttonMesaLiberadada");
    }

    public void setMesaDesocupada() {
        apPane.getStyleClass().add("mesa-ocupada");
        btAbrirComanda.getStyleClass().add("buttonMesaOcupada");
        btFecharComanda.setDisable(false);
        btFecharComanda.getStyleClass().add("buttonMesaOcupada");
    }

    public void abrirMesaLiberada(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AbrirComanda.fxml"));
            Parent root = (Parent) loader.load();
            AbrirComandaController ctr = loader.getController();

            //pode adicionar atributos na outra tela
            ctr.setNMesa(tfNMesa.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            efeito(event, true);
            stage.showAndWait();
            efeito(event, false);
        }
        catch(Exception e){
            System.out.println("ERRO AO ABRIR MESA LIBERADA.");
        }
    }

    @FXML
    private void evtAbrirComanda(ActionEvent event) {
        try {
            if (mesa.isLiberada())
                abrirMesaLiberada(event);
            
            mesa = dalCom.getMesa(mesa.getCod());
            
            if(!mesa.isLiberada()){
                verificarMesa();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrarPedido.fxml"));
                Parent root = (Parent) loader.load();
                RegistrarPedidoController ctr = loader.getController();

                //pode adicionar atributos na outra tela
                ctr.setNMesa(tfNMesa.getText());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                efeito(event, true);
                stage.showAndWait();
                efeito(event, false);
            }
        }
        catch (Exception e) {
            System.out.println("DEU ERRO AO ABRIR COMANDA.");
        }
    }

    @FXML
    private void evtFecharComanda(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FecharComanda.fxml"));
            Parent root = (Parent) loader.load();
            FecharComandaController ctr = loader.getController();

            //pode adicionar atributos na outra tela
            ctr.setMesa(tfNMesa.getText());
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            efeito(event, true);
            stage.showAndWait();
            efeito(event, false);
        }
        catch (Exception e) {
            System.out.println("DEU ERRO AO FECHAR COMANDA.");
        }
    }

}
