/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx;

import bellacanela.db.dal.DALComanda;
import bellacanela.db.dal.DALItensDaComanda;
import bellacanela.db.dal.DALProduto;
import bellacanelafx.db.entidades.Comanda;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author joao
 */
public class RecebimentoController implements Initializable {

    @FXML
    private SplitPane painel;
    @FXML
    private JFXButton btcancelar;
    @FXML
    private Label lbMesa;
    @FXML
    private Label lbTotal;
    @FXML
    private JFXComboBox<Comanda> cbComanda;
    
    DALComanda dalCom = new DALComanda();
    DALProduto dalPro = new DALProduto();
    DALItensDaComanda dalItens = new DALItensDaComanda();

    private void fadeout() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), painel);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play(); 
    }
    
    public void setMesa(String num) {
        lbMesa.setText(" "+num);
    }
    
    public void carregarCBComanda() {
        
        cbComanda.setConverter(new StringConverter<Comanda>(){
            @Override
            public String toString(Comanda com) {
                return com.getCom_num() + " - " + com.getDescricao();
            }
            
            @Override
            public Comanda fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        ObservableList<Comanda> olCom = FXCollections.observableArrayList(dalCom.getComandas("mes_cod = " + lbMesa.getText()));
        cbComanda.setItems(olCom);
        cbComanda.setValue(cbComanda.getItems().get(0));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fadeout();
        carregarCBComanda();
    }    

    @FXML
    private void clkBtCancelar(ActionEvent event) {
        ((Stage)(btcancelar.getParent().getScene().getWindow())).close();
    }
    
}
