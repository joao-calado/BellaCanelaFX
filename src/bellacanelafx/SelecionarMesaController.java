package bellacanelafx;

import bellacanela.db.dal.DALMesa;
import bellacanelafx.db.entidades.Mesa;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

public class SelecionarMesaController implements Initializable {

    @FXML
    private TilePane tpPane;
    
    DALMesa dal = new DALMesa();
    @FXML
    private AnchorPane apPane;
    @FXML
    private JFXButton btAddMesa;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadMesas();
    }    
    
    public void loadMesas(){
        tpPane.getChildren().clear();
        dal.getMesas("").forEach((m) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Mesa.fxml"));
                Parent root = (Parent)loader.load();
                MesaController ctr = loader.getController();
                
                ctr.setMesa(m);
                tpPane.getChildren().add(root);
            }
            catch(IOException e){
                System.out.println("IOEXCEPTION AO CARREGAR AS MESAS.");
            }
            catch(Exception e){
                System.out.println("DEU ERRO AO CARREGAR AS MESAS.");
            }
        });
    }

    @FXML
    private void evtAddMesa(ActionEvent event) {
        dal.insert(new Mesa(true));
        loadMesas();
    }
    
}
