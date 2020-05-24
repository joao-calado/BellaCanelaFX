package bellacanelafx;

import bellacanela.db.dal.DALComanda;
import bellacanela.db.dal.DALItensDaComanda;
import bellacanela.db.dal.DALProduto;
import bellacanela.db.dal.DALRecebimento;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.Comanda;
import bellacanelafx.db.entidades.ItensDaComanda;
import bellacanelafx.db.entidades.Recebimento;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author joao
 */
public class FecharComandaController implements Initializable {
    
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
    @FXML
    private JFXButton btInserir;
    @FXML
    private TableView<Recebimento> tabelaRecebimentos;
    @FXML
    private TableColumn<Recebimento, Integer> colCliente;
    @FXML
    private TableColumn<Recebimento, String> colTipo;
    @FXML
    private TableColumn<Recebimento, Double> colValor;
    @FXML
    private TableColumn<Recebimento, LocalDate> colRecebimento;
    @FXML
    private TableColumn<Recebimento, LocalDate> colVencimento;
    @FXML
    private TableColumn<Recebimento, Integer> colMesa;
    
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
    
    public void carregarTabelaRecebimentos() {
        
        String filtro = "rec_cli="+cbComanda.getValue().getCliente().getCod()+" and rec_mesa="+lbMesa.getText().trim();
        DALRecebimento dal = new DALRecebimento();
        List<Recebimento> lista = dal.get(filtro);
        
        for (int i = 0; i < lista.size(); i++) {
            
            if(lista.get(i).getVencimento().isEqual(LocalDate.of(1900, 10, 10)))
                lista.get(i).setVencimento(null);
        }
        
        ObservableList<Recebimento> modelo;
        modelo = FXCollections.observableArrayList(lista);
        tabelaRecebimentos.setItems(modelo);
        tabelaRecebimentos.refresh();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        fadeout();
        MaskFieldUtil.monetaryField(txValor);
        
        /* colunas da tabela de itens */
        colProduto.setCellValueFactory((TableColumn.CellDataFeatures<ItensDaComanda, String> p) -> new SimpleStringProperty(p.getValue().getProduto().getNome()));
        colQtd.setCellValueFactory((TableColumn.CellDataFeatures<ItensDaComanda, Integer> p) -> new SimpleObjectProperty(p.getValue().getQtde()));
        colTotal.setCellValueFactory((TableColumn.CellDataFeatures<ItensDaComanda, Double> p) -> new SimpleObjectProperty(p.getValue().getQtde()*p.getValue().getProduto().getPreco()));
        
        /* colunas da tabela de recebimentos */
        colCliente.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, Integer> r) -> new SimpleObjectProperty(r.getValue().getCliente()));
        colTipo.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, String> r) -> new SimpleStringProperty(r.getValue().getTipo()));
        colValor.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, Double> r) -> new SimpleObjectProperty(r.getValue().getValor()));
        colRecebimento.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, LocalDate> r) -> new SimpleObjectProperty(r.getValue().getRecebimento()));
        colVencimento.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, LocalDate> r) -> new SimpleObjectProperty(r.getValue().getVencimento()));
        colMesa.setCellValueFactory((TableColumn.CellDataFeatures<Recebimento, Integer> r) -> new SimpleObjectProperty(r.getValue().getMesa()));
        
        Platform.runLater(()->{
            try{
                carregarCBComandas();
                carregarTabelaItens();
                carregarCBTipoRec();
                carregarTabelaRecebimentos();
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
        carregarTabelaRecebimentos();
        txValor.clear();
        carregarCBTipoRec();
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

    @FXML
    private void clkBtInserir(ActionEvent event) {
        
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        
        if(!txValor.getText().isEmpty()) {
            
            Recebimento r;
            int cliente = cbComanda.getValue().getCliente().getCod();
            String tipo = cbTipoRec.getValue();
            double valor = Double.parseDouble(txValor.getText().replaceAll(",", "."));
            int mesa = 0;
            try {
                mesa = Integer.parseInt(lbMesa.getText().trim());
            }
            catch(NumberFormatException nb) { mesa = 0;}

            if(cbTipoRec.getValue().equals("a ver")) {
                r = new Recebimento(0,cliente,tipo,valor,LocalDate.now(),(LocalDate)dtpVencimento.getValue(),"N",mesa);
            }
            else {
                r = new Recebimento(0,cliente,tipo,valor,LocalDate.now(),LocalDate.of(1800, 10, 10),"S",mesa);
                System.out.println(mesa);
                System.out.println(lbMesa.getText().trim());
            }

            DALRecebimento dalRec = new DALRecebimento();

            if(dalRec.gravar(r)) {
                a.setTitle("Informação:");
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Recebimento gravado com sucesso!");
            }
            else {
                a.setTitle("Erro:");
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Problemas ao tentar gravar recebimento!");
            }
        }
        else {
            
            a.setTitle("Aviso:");
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("Informe um valor para efetuar o recebimento!");
        }
        a.showAndWait();
    }
    
}
