package bellacanelafx;

import bellacanela.db.dal.DALCliente;
import bellacanela.db.dal.DALComanda;
import bellacanelafx.db.entidades.Cliente;
import bellacanelafx.db.entidades.Comanda;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AbrirComandaController implements Initializable {

    @FXML
    private AnchorPane apdados;
    @FXML
    private JFXDatePicker dpData;
    @FXML
    private JFXTextField tfDescricao;
    @FXML
    private JFXButton btAbrirComanda;
    @FXML
    private JFXComboBox<Cliente> cbNome;
    @FXML
    private JFXTextField tfNumComanda;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private JFXTextField tfNMesa;

    DALCliente dalCli = new DALCliente();
    DALComanda dalCom = new DALComanda();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dpData.setValue(LocalDate.now());
        iniciarCBClientes();
    }

    public void iniciarCBClientes() {
        ObservableList<Cliente> olCli = FXCollections.observableArrayList(dalCli.get(""));
        cbNome.setItems(olCli);
    }

    public void setNMesa(String num) {
        tfNMesa.setText(num);
    }

    public void fecharJanela(){
        ((Stage) (btCancelar.getParent().getScene().getWindow())).close();
    }
    
    private void snackbar(String message, String cor){
        JFXSnackbar sb = new JFXSnackbar(apdados);
        Label mens = new Label(message);
        mens.setStyle("-fx-text-fill: "+cor+";"
                    + "-fx-font-size: 14px;"
                    + "-fx-padding: 10px;");
        sb.enqueue(new JFXSnackbar.SnackbarEvent(mens));
    }
    
    @FXML
    private void evtCancelar(ActionEvent event) {
        fecharJanela();
    }

    @FXML
    private void evtAbrirComanda(ActionEvent event) {
        boolean ans = true;

        cbNome.setStyle("-fx-background-color: transparent;");
        tfNumComanda.setStyle("-fx-background-color: transparent;");
        if (cbNome.getValue() == null) {
            cbNome.setStyle("-fx-background-color: rgba(235, 55, 55, 0.85);");
            ans = false;
        }
        if (tfNumComanda.getText().equals("")) {
            tfNumComanda.setStyle("-fx-background-color: rgba(235, 55, 55, 0.85);");
            ans = false;
        }

        if (ans) {
            ans = dalCom.insert(
                    new Comanda(
                            Integer.parseInt(tfNumComanda.getText()),
                            Integer.parseInt(tfNMesa.getText()),
                            dpData.getValue(),
                            tfDescricao.getText(),
                            cbNome.getValue(),
                            null
                    )
            );
            if(ans){
                dalCom.setMesa(Integer.parseInt(tfNMesa.getText()), false);
                fecharJanela();
            }
            else{
                snackbar("Algo deu errado ao abrir a comanda", "red");
            }
        }
    }

}
