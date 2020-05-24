package bellacanelafx;

import bellacanela.db.dal.DALComanda;
import bellacanela.db.dal.DALItensDaComanda;
import bellacanela.db.dal.DALProduto;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.Comanda;
import bellacanelafx.db.entidades.ItensDaComanda;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author joao
 */
public class RecebimentoController implements Initializable {
    
    DALComanda dalCom = new DALComanda();
    DALProduto dalPro = new DALProduto();
    DALItensDaComanda dalItens = new DALItensDaComanda();

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
    @FXML
    private TableColumn<ItensDaComanda, String> colProduto;
    @FXML
    private TableColumn<ItensDaComanda, Integer> colQtd;
    @FXML
    private TableColumn<ItensDaComanda, Double> colTotal;
    @FXML
    private TableView<ItensDaComanda> tabelaItens;
    @FXML
    private JFXComboBox<String> cbTipoRec;
    @FXML
    private JFXTextField txValor;
    @FXML
    private JFXDatePicker dtpVencimento;
    
    private void fadeout() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), painel);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play(); 
    }
    
    public void setMesa(String num) {
        lbMesa.setText(" "+num);
    }
    
    public void carregarCBComandas() {
        
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
        cbComanda.setItems(FXCollections.observableArrayList(new DALComanda().getComandas("mes_cod="+lbMesa.getText())));
        cbComanda.setValue(cbComanda.getItems().get(0));
    }
    
    public void carregarCBTipoRec() {
        
        if(!cbComanda.getValue().getCliente().getNome().equals("Outro")) {
            
            cbTipoRec.getItems().clear();
            List<String> tr = new ArrayList();
            tr.add("à vista");
            tr.add("crédito");
            tr.add("débito");
            tr.add("a ver");
            ObservableList<String> mod = FXCollections.observableArrayList(tr);
            cbTipoRec.setItems(mod);
            cbTipoRec.setValue(cbTipoRec.getItems().get(0));
        }
        else {
               
            cbTipoRec.getItems().clear();
            List<String> tr = new ArrayList();
            tr.add("à vista");
            tr.add("crédito");
            tr.add("débito");
            ObservableList<String> mod = FXCollections.observableArrayList(tr);
            cbTipoRec.setItems(mod);
            cbTipoRec.setValue(cbTipoRec.getItems().get(0));
        }
    }
    
    
    public void carregarTabelaItens() {
        
        ObservableList<ItensDaComanda> olItens = FXCollections.observableArrayList(cbComanda.getSelectionModel().getSelectedItem().getItens());
        tabelaItens.setItems(olItens);
        tabelaItens.refresh();
        
        /*setar valor total da comanda*/
        double tot = 0;
        List<ItensDaComanda> itens = tabelaItens.getItems();
        tot = itens.stream().map((idc) -> idc.getProduto().getPreco() * idc.getQtde()).reduce(tot, (accumulator, _item) -> accumulator + _item);
        /* ou: 
        for(ItensDaComanda idc : itens) {
            tot += idc.getProduto().getPreco() * idc.getQtde();
        }
        */
        lbTotal.setText(""+tot);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        fadeout();
        MaskFieldUtil.monetaryField(txValor);
        colProduto.setCellValueFactory((TableColumn.CellDataFeatures<ItensDaComanda, String> p) -> new SimpleStringProperty(p.getValue().getProduto().getNome()));
        colQtd.setCellValueFactory((TableColumn.CellDataFeatures<ItensDaComanda, Integer> p) -> new SimpleObjectProperty(p.getValue().getQtde()));
        colTotal.setCellValueFactory((TableColumn.CellDataFeatures<ItensDaComanda, Double> p) -> new SimpleObjectProperty(p.getValue().getQtde()*p.getValue().getProduto().getPreco()));
        
        Platform.runLater(()->{
            try{
                carregarCBComandas();
                carregarTabelaItens();
                carregarCBTipoRec();
            }
            catch(Exception e){
                System.out.println("Erro!");
            }
        });
    }

    @FXML
    private void clkBtCancelar(ActionEvent event) {
        ((Stage)(btcancelar.getParent().getScene().getWindow())).close();
    }

    @FXML
    private void clkCBComanda(ActionEvent event) {
        
        carregarTabelaItens();
        txValor.clear();
        if(cbComanda.getValue().getCliente().getNome().equals("Outro")) {
            carregarCBTipoRec();
        }
    }

    @FXML
    private void clkAddValor(MouseEvent event) {
        txValor.setText(""+tabelaItens.getSelectionModel().getSelectedItem().getProduto().getPreco() * tabelaItens.getSelectionModel().getSelectedItem().getQtde()+"0");
    }

    @FXML
    private void clkCBTipoRec(ActionEvent event) {
        
        if(cbTipoRec.getValue().equals("a ver")) {
            dtpVencimento.setDisable(false);
            dtpVencimento.setValue(LocalDate.now());
        }
        else {
            dtpVencimento.setDisable(true);
            dtpVencimento.setValue(null);
            dtpVencimento.setPromptText("Vencimento");
        }
    }
    
}
