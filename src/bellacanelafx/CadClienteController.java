package bellacanelafx;

import bellacanela.db.dal.DALCliente;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.Cliente;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.Toolkit;
import java.net.URL;
import java.util.List;
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

public class CadClienteController implements Initializable {

    @FXML
    private SplitPane painel;
    @FXML
    private AnchorPane apdados;
    @FXML
    private TableView<Cliente> tabela;
    @FXML
    private TableColumn<Cliente, String> colcod;
    @FXML
    private TableColumn<Cliente, String> colnome;
    @FXML
    private TableColumn<Cliente, String> colcpf;
    @FXML
    private TableColumn<Cliente, String> colemail;
    @FXML
    private TableColumn<Cliente, String> colfone;
    @FXML
    private JFXButton btnovo;
    @FXML
    private JFXButton btalterar;
    @FXML
    private JFXButton btapagar;
    @FXML
    private JFXButton btsalvar;
    @FXML
    private JFXButton btcancelar;
    @FXML
    private JFXTextField txcod;
    @FXML
    private JFXTextField txnome;
    @FXML
    private JFXTextField txcpf;
    @FXML
    private JFXTextField txemail;
    @FXML
    private JFXTextField txfone;
    @FXML
    private JFXTextField txpesquisa;
    @FXML
    private VBox pnpesquisa;
    @FXML
    private JFXTextField txpesquisaCPF;
    
    private void fadeout() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), painel);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play(); 
    }
    
    private void carregarTabela(String filtro) {
        
        DALCliente dal = new DALCliente();
        List<Cliente> lista = dal.get(filtro);
        ObservableList<Cliente> modelo;
        modelo = FXCollections.observableArrayList(lista);
        tabela.setItems(modelo);
    }
    
    private void EstadoEdicao() {
        
        pnpesquisa.setDisable(true);
        pnpesquisa.setOpacity(0.5);
        apdados.setDisable(false);
        apdados.setOpacity(1);
        
        btsalvar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txpesquisa.clear();
        txnome.requestFocus();
    }
    
    private void estadoOriginal() {
        
        pnpesquisa.setDisable(false);
        pnpesquisa.setOpacity(1);
        apdados.setDisable(true);
        apdados.setOpacity(0.5);
        
        btsalvar.setDisable(true);
        btcancelar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        btnovo.setDisable(false);
        txpesquisa.clear();
        
        ObservableList <Node> componentes = apdados.getChildren(); //”limpa” os componentes
        for(Node n : componentes)
        {
            if (n instanceof TextInputControl)  // textfield, textarea e htmleditor
                ((TextInputControl)n).setText("");
            if(n instanceof ComboBox)
                ((ComboBox)n).getItems().clear();
        }
        carregarTabela("");
    }
    
    private boolean verificarEmail(String email) {
        
        boolean res = false;
        
        if((email.contains("@hotmail") || email.contains("@gmail")) && email.contains(".com"))
            res = true;
        
        return res;
    }
    
    private boolean verificarCpf (String cpf) {
        
        boolean res = false;
        
        if(cpf.length() == 14)
            res = true;
        
        return res;
    }
    
    private boolean verificarFone (String fone) {
        
        boolean res = false;
        
        if(fone.length() >= 13)
            res = true;
        
        return res;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        fadeout();
        
        colcod.setCellValueFactory(new PropertyValueFactory("cod"));
        colnome.setCellValueFactory(new PropertyValueFactory("nome"));
        colcpf.setCellValueFactory(new PropertyValueFactory("cpf"));
        colemail.setCellValueFactory(new PropertyValueFactory("email"));
        colfone.setCellValueFactory(new PropertyValueFactory("fone"));
        
        MaskFieldUtil.cpfField(txcpf);
        MaskFieldUtil.foneField(txfone);
        MaskFieldUtil.cpfField(txpesquisaCPF);
        
        estadoOriginal();
    }    

    @FXML
    private void clkTabela(MouseEvent event) {
        
        if(tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    @FXML
    private void clkBtNovo(ActionEvent event) {
        
        EstadoEdicao();
    }

    @FXML
    private void clkBtAlterar(ActionEvent event) {
        
        if(tabela.getSelectionModel().getSelectedItem() != null) {
            
            Cliente c = (Cliente) tabela.getSelectionModel().getSelectedItem();
            
            txcod.setText(""+c.getCod());
            txnome.setText(c.getNome());
            txcpf.setText(c.getCpf());
            txemail.setText(c.getEmail());
            txfone.setText(c.getFone());
            
            EstadoEdicao();
        }
    }

    @FXML
    private void clkBtApagar(ActionEvent event) {
        
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão?");
        
        if(a.showAndWait().get() == ButtonType.OK) {
            
            DALCliente dal = new DALCliente();
            Cliente c = tabela.getSelectionModel().getSelectedItem();
            
            if(dal.apagar(c)) {
                
                a.setTitle("Informação:");
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Cliente apagado com sucesso!");
            }
            else {
                
                a.setTitle("Erro:");
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Problemas ao tentar apagar cliente!");
            }
            
            a.showAndWait();
            carregarTabela("");
        }
    }

    @FXML
    private void clkBtSalvar(ActionEvent event) {
        
        int cod = -1;
        try {
            cod = Integer.parseInt(txcod.getText());
        }
        catch(NumberFormatException ex) {
            cod = 0;
        }
        
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        
        if(verificarEmail(txemail.getText())) {
            
            if(verificarCpf(txcpf.getText())) {
                
                if(verificarFone(txfone.getText())) {
                    
                    DALCliente dal = new DALCliente();
                    Cliente c = new Cliente(cod, txnome.getText(), txcpf.getText(), txemail.getText(), txfone.getText());

                    if(c.getCod() == 0) { // novo cadastro

                        if(dal.gravar(c)) {

                            a.setTitle("Informação:");
                            a.setAlertType(Alert.AlertType.INFORMATION);
                            a.setContentText("Cliente gravado com sucesso!");
                        }
                        else {

                            a.setTitle("Erro:");
                            a.setAlertType(Alert.AlertType.ERROR);
                            a.setContentText("Problemas ao tentar gravar cliente!");
                        }
                    }
                    else { // alteração de cadastro

                        if(dal.alterar(c)) {

                            a.setTitle("Informação:");
                            a.setAlertType(Alert.AlertType.INFORMATION);
                            a.setContentText("Cliente alterado com sucesso!");
                        }
                        else {

                            a.setTitle("Erro:");
                            a.setAlertType(Alert.AlertType.ERROR);
                            a.setContentText("Problemas ao tentar alterar cliente!");
                        }
                    }

                    carregarTabela("");
                    estadoOriginal();
                }
                else {
                    
                    a.setTitle("Atenção:");
                    a.setAlertType(Alert.AlertType.WARNING);
                    a.setContentText("Informe um fone em formato válido!");
                    txfone.clear();
                    txfone.requestFocus();
                }
            }
            else {
                
                a.setTitle("Atenção:");
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("Informe um CPF em formato válido!");
                txcpf.clear();
                txcpf.requestFocus();
            }
        }
        else {
            
            a.setTitle("Atenção:");
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("Informe um email em formato válido!");
            txemail.clear();
            txemail.requestFocus();
        }
        a.showAndWait();
    }

    @FXML
    private void clkBtCancelar(ActionEvent event) {
        
        if (!apdados.isDisabled()) // caso se encontre em estado de edição
            estadoOriginal();
        else 
            HomeController.spnprincipal.setCenter(null);
        //ou HomeController.spnprincipal.getChildren().clear();
    }

    @FXML
    private void dgtPesquisa(KeyEvent event) {
        
        txpesquisaCPF.clear();
        carregarTabela("");
        
        if(txpesquisa.getText().length() > 20) {
            event.consume();
            Toolkit.getDefaultToolkit().beep();
        }
        else {
            
            if(!txpesquisa.getText().isEmpty())
                carregarTabela("upper (cli_nome) like '%"+txpesquisa.getText().toUpperCase()+"%'");
        }
    }

    @FXML
    private void dgtNome(KeyEvent event) {
        
        if(txnome.getText().length() > 40) {
            event.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }

    @FXML
    private void dgtEmail(KeyEvent event) {
        
        if(txemail.getText().length() > 30) {
            event.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }

    @FXML
    private void dgtPesquisaCPF(KeyEvent event) {
        
        txpesquisa.clear();
        carregarTabela("");
        
        if(!txpesquisaCPF.getText().isEmpty())
            carregarTabela("upper (cli_cpf) like '%"+txpesquisaCPF.getText().toUpperCase()+"%'");
    }
    
}
