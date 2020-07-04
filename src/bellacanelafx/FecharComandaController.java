package bellacanelafx;

import bellacanela.db.dal.DALComanda;
import bellacanela.db.dal.DALItensDaComanda;
import bellacanela.db.dal.DALMesa;
import bellacanela.db.dal.DALProduto;
import bellacanela.db.dal.DALRecebimento;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.Comanda;
import bellacanelafx.db.entidades.ItensDaComanda;
import bellacanelafx.db.entidades.Mesa;
import bellacanelafx.db.entidades.Recebimento;
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
import javafx.scene.control.Alert;
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
public class FecharComandaController implements Initializable {
    
    DALComanda dalCom = new DALComanda();
    DALProduto dalPro = new DALProduto();
    DALItensDaComanda dalItens = new DALItensDaComanda();
    DALMesa dalMesa = new DALMesa();
    Mesa m;

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
    @FXML
    private Label lbPago;
    @FXML
    private JFXButton btFecharComanda;
    @FXML
    private JFXButton btExcluir;
    
    private void fadeout() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), painel);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play(); 
    }
    
    public String formatarPreco(double preco){
        String str = preco+"";
        int i = (str).indexOf(".");
            if(str.length() - i == 2)
                str += "0";
        return str;
    }
    
    public void setMesa(Mesa mesa) {
        System.out.println("oie, setei mesa");
        m = mesa;
        lbMesa.setText(""+m.getCod());
    }
    
    public Mesa getMesa() {
        return m;
    }
    
    private void verificarMesa() {
        
        if(cbComanda.getValue() == null) {
            
            m.setLiberada(true);
            dalMesa.update(m);
            
            ((Stage)(btcancelar.getParent().getScene().getWindow())).close();
        } 
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
        
        try {
            cbComanda.setItems(FXCollections.observableArrayList(new DALComanda().getComandas("mes_cod="+lbMesa.getText() + "and com_aberta='true'")));
            cbComanda.setValue(cbComanda.getItems().get(0));
        }
        catch(Exception e) {verificarMesa();}
    }
    
    public void carregarCBTipoRec() {
        
        if(!cbComanda.getValue().getCliente().getNome().equals("Outro")) {
            
            cbTipoRec.getItems().clear();
            List<String> tr = new ArrayList();
            tr.add("dinheiro");
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
            tr.add("dinheiro");
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
        
        String filtro = "rec_cli="+cbComanda.getValue().getCliente().getCod()+" and rec_mesa="+lbMesa.getText().trim() +" and rec_comanda="+cbComanda.getValue().getCom_num();
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
        
        /*setar valor pago da comanda*/
        double pago = 0;
        List<Recebimento> recs = tabelaRecebimentos.getItems();
        for(Recebimento rdc: recs) {
            pago += rdc.getValor();
        }
        lbPago.setText(""+pago);
    }
    
    private void verificarFechamento() {
        
        double total = 0;
        double pago = 0;
        
        try {
            
            total = Double.parseDouble(lbTotal.getText().trim().replaceAll(",", "."));
            pago = Double.parseDouble(lbPago.getText().trim().replaceAll(",", "."));
            
            if(pago >= total) {
                btInserir.setDisable(true);
                btFecharComanda.setDisable(false);
            }
            else {
                btInserir.setDisable(false);
                btFecharComanda.setDisable(true);
            }
        }
        catch(NumberFormatException e) {}
    }
    
    private Double gorjeta() {
        
        double total = 0;
        double pago = 0;
        double npag = 0;
        double gorjeta = 0;
        double resta;
        
        try {
            
            List<ItensDaComanda> itens = tabelaItens.getItems();
            total = itens.stream().map((idc) -> idc.getProduto().getPreco() * idc.getQtde()).reduce(total, (accumulator, _item) -> accumulator + _item);
            pago = Double.parseDouble(lbPago.getText().trim().replaceAll(",", "."));
            npag = Double.parseDouble(txValor.getText().trim().replaceAll(",", "."));
            
            /*
            total = 100
            pago = 50
            noag = 200
            gorjeta = 150
            */
            
            if(total != 0.0) {
                resta = total-pago;
                if(npag > resta) {
                    gorjeta = npag - resta;
                }
            }
            
        }
        catch(NumberFormatException e) {}
        
        return gorjeta;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        fadeout();
        btFecharComanda.setDisable(true);
        MaskFieldUtil.monetaryField(txValor);
        
        /* colunas da tabela de itens */
        colProduto.setCellValueFactory((TableColumn.CellDataFeatures<ItensDaComanda, String> p) -> new SimpleStringProperty(p.getValue().getProduto().getNome()));
        colQtd.setCellValueFactory((TableColumn.CellDataFeatures<ItensDaComanda, Integer> p) -> new SimpleObjectProperty(p.getValue().getQtde()));
        colTotal.setCellValueFactory((TableColumn.CellDataFeatures<ItensDaComanda, Double> p) -> new SimpleObjectProperty(p.getValue().getQtde()*p.getValue().getProduto().getPreco()));
        
        colTotal.setStyle("-fx-alignment: center-right;");
        
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
                verificarFechamento();
                verificarMesa();
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
        verificarFechamento();
    }

    @FXML
    private void clkAddValor(MouseEvent event) {
        txValor.setText(formatarPreco(tabelaItens.getSelectionModel().getSelectedItem().getProduto().getPreco() * tabelaItens.getSelectionModel().getSelectedItem().getQtde()));
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
            int comanda = 0;
            double gorjeta = gorjeta();
            
            try {
                mesa = Integer.parseInt(lbMesa.getText().trim());
                comanda = cbComanda.getValue().getCom_num();
            }
            catch(NumberFormatException nb) { mesa = 0;comanda=0;}

            if(cbTipoRec.getValue().equals("a ver")) {
                r = new Recebimento(0,cliente,tipo,valor,LocalDate.now(),(LocalDate)dtpVencimento.getValue(),"N",mesa,comanda);
            }
            else {
                r = new Recebimento(0,cliente,tipo,valor,LocalDate.now(),LocalDate.of(1800, 10, 10),"S",mesa,comanda);
                System.out.println(mesa);
                System.out.println(lbMesa.getText().trim());
            }

            DALRecebimento dalRec = new DALRecebimento();
            
            if(gorjeta() == 0.0) {
                if(dalRec.gravar(r)) {
                    carregarTabelaRecebimentos();
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
                r.setValor(valor - gorjeta());
                if(dalRec.gravar(r)) {
                    r.setValor(gorjeta());
                    r.setTipo("gorjeta");
                    if(dalRec.gravar(r)) {
                        carregarTabelaRecebimentos();
                        a.setTitle("Informação:");
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setContentText("Recebimento gravado com sucesso!\nGorjeta: R$"+gorjeta());
                    }
                    else {
                        a.setTitle("Erro:");
                        a.setAlertType(Alert.AlertType.ERROR);
                        a.setContentText("Problemas ao tentar gravar recebimento com gorjeta!");
                    }
                }
                else {
                    a.setTitle("Erro:");
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setContentText("Problemas ao tentar gravar recebimento!");
                }
            }
        }
        else {
            
            a.setTitle("Aviso:");
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("Informe um valor para efetuar o recebimento!");
        }
        
        verificarFechamento();
        verificarMesa();
        a.showAndWait();
    }

    @FXML
    private void clkBtExcluir(ActionEvent event) {
        
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        
        if(tabelaRecebimentos.getSelectionModel().getSelectedIndex() < 0) {
            a.setTitle("Aviso:");
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("Seleione um recebimento!");
        }
        else {
            
            DALRecebimento dal = new DALRecebimento();
            Recebimento r = tabelaRecebimentos.getSelectionModel().getSelectedItem();
            if(dal.apagar(r)) {
                
                a.setTitle("Informação:");
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Recebimento apagado com sucesso!");
                
                carregarTabelaRecebimentos();
                verificarFechamento();
            }
            else {
                a.setTitle("Erro:");
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Problemas ao tentar apagar recebimento!");
            }
        }
        a.showAndWait();
    }

    @FXML
    private void clkFecharComanda(ActionEvent event) {
        
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        cbComanda.getValue().setAberta(false);
        
        if(dalCom.update(cbComanda.getValue())) {
            
            carregarCBComandas();
            a.setTitle("Informação:");
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("Comanda fechada com sucesso!");
        }
        else {
            cbComanda.getValue().setAberta(true);
            a.setTitle("Erro:");
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Problemas ao tentar fechar comanda!");
        }
        a.showAndWait();
    }
    
}
