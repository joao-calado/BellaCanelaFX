/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx;

import bellacanela.db.dal.DALPagamentos;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.Pagamento;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class QuitarPgtosController implements Initializable {

    @FXML
    private JFXButton btcancelar;
    @FXML
    private AnchorPane apDados;
    @FXML
    private JFXTextField txCod;
    @FXML
    private JFXTextField txDesc;
    @FXML
    private JFXTextField txValorpago;
    @FXML
    private JFXDatePicker dtVencimento;
    @FXML
    private JFXDatePicker dtPagamento;
    @FXML
    private JFXTextField txValor;
    @FXML
    private VBox pnSearch;
    @FXML
    private SplitPane pane;
    @FXML
    private JFXButton btQuitar;
    @FXML
    private JFXButton btEstornar;
    @FXML
    private JFXButton btSelecionar;
    @FXML
    private JFXTextField txDescJuros;
    @FXML
    private RadioButton rbAntesde;
    @FXML
    private JFXDatePicker dpAntesde;
    @FXML
    private RadioButton rbDepoisde;
    @FXML
    private JFXDatePicker dpDepoisde;
    @FXML
    private RadioButton rbPago;
    @FXML
    private RadioButton rbAPagar;
    @FXML
    private RadioButton rbVencido;
    
    @FXML
    private TableView<Pagamento> tabela;
    @FXML
    private TableColumn<Pagamento, String> colCod;
    @FXML
    private TableColumn<Pagamento, String> colDesc;
    @FXML
    private TableColumn<Pagamento, String> colValor;
    @FXML
    private TableColumn<Pagamento, String> colVencimento;
    @FXML
    private TableColumn<Pagamento, String> colPagamento;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory("cod"));        
        colDesc.setCellValueFactory(new PropertyValueFactory("desc"));
        colValor.setCellValueFactory(new PropertyValueFactory("valor"));
        colVencimento.setCellValueFactory(new PropertyValueFactory("vencimento"));
        colPagamento.setCellValueFactory(new PropertyValueFactory("pagamento"));
        
       fadeout();
       loadMasks();
       original(); 
        
    }
    
    private void loadMasks() {
        //MaskFieldUtil.monetaryField(this.tfPreço);
        //MaskFieldUtil.onlyAlfa(tfNome);
    }
    private void fadeout() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), this.pane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play(); 
    }   
    
    private void loadTable(String filtro) {
        DALPagamentos dal = new DALPagamentos();
        
        ArrayList<Pagamento> Prod = dal.get(filtro);
      
        ObservableList<Pagamento> modelo = FXCollections.observableArrayList(Prod);
        
        this.tabela.setItems(modelo);
        this.tabela.refresh();       
        
        
    }   
    private void original() {
        this.pnSearch.setDisable(false);
        this.pnSearch.setOpacity(1);
        this.apDados.setDisable(true);
        this.apDados.setOpacity(0.5);
        
        this.btEstornar.setDisable(true);
        this.btQuitar.setDisable(true);
        this.btSelecionar.setDisable(true);
        this.btcancelar.setDisable(true);
        
        ObservableList <Node> componentes = this.apDados.getChildren(); //”limpa” os componentes
        for(Node n : componentes)
        {
            if (n instanceof TextInputControl)  // textfield, textarea e htmleditor
                ((TextInputControl)n).setText("");
            
        }
        
        loadTable("");
    }
    
    @FXML
    private void clkBtCancelar(ActionEvent event) {
        if (!this.apDados.isDisabled())
            this.original();
        else 
            HomeController.spnprincipal.setCenter(null);
    }

  
    @FXML
    private void clkBtQuitar(ActionEvent event) {
    }

    @FXML
    private void clkBtEstornar(ActionEvent event) {
    }

    @FXML
    private void clkBtSelecionar(ActionEvent event) {
    }
    
    
    @FXML
    private void clkTabela(MouseEvent event) {
        
    }

    
}
