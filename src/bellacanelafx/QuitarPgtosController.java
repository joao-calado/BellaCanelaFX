
package bellacanelafx;

import bellacanela.db.dal.DALPagamentos;

import bellacanelafx.db.entidades.Pagamento;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
    @FXML
    private RadioButton rbParcial;


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
        //MaskFieldUtil.monetaryField(this.txDescJuros);
        //MaskFieldUtil.monetaryField(this.txValorpago);
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
    private void Status(boolean aux){ 
        dtPagamento.setDisable(aux);
        txValorpago.setDisable(aux);
        txDescJuros.setDisable(aux);
        rbParcial.setDisable(aux);
        this.btEstornar.setDisable(!aux);
        this.btQuitar.setDisable(aux);
        this.btSelecionar.setDisable(aux);
        
        this.pnSearch.setDisable(true);
        this.pnSearch.setOpacity(0.5);
        this.apDados.setDisable(false);
        this.apDados.setOpacity(1);
    }    
     
    private void original() {
        this.pnSearch.setDisable(false);
        this.pnSearch.setOpacity(1);
        this.apDados.setDisable(true);
        this.apDados.setOpacity(0.5);
        
        this.btEstornar.setDisable(true);
        this.btQuitar.setDisable(true);
        this.btSelecionar.setDisable(true);
        
        ObservableList <Node> componentes = this.apDados.getChildren(); //”limpa” os componentes
        for(Node n : componentes)
        {
            if (n instanceof TextInputControl)  // textfield, textarea e htmleditor
                ((TextInputControl)n).setText("");
            
        }
        loadTable("");
        dtPagamento.setValue(null);
        dtVencimento.setValue(null);
    }
    
    @FXML
    private void clkBtCancelar(ActionEvent event) {
        if (!this.apDados.isDisabled()){
            this.txValorpago.setStyle("-fx-background-color: transparent");
            this.txDescJuros.setStyle("-fx-background-color: transparent");
            this.dtPagamento.setStyle("-fx-background-color: transparent");
            this.original();
        }
            
        else 
            HomeController.spnprincipal.setCenter(null);
    }
    private String convertStr(String s){
        String dst = "";
        
        for(char c : s.toCharArray())
            if(c != '.')
                dst += c == ',' ? '.' : c;
        
        return dst;
    } 
    private void snackbar(String message, String cor){
        JFXSnackbar sb = new JFXSnackbar(this.apDados);
        Label mens = new Label(message);
        mens.setStyle("-fx-text-fill: "+cor+";"
                    + "-fx-font-size: 14px;"
                    + "-fx-padding: 10px;");
        sb.enqueue(new JFXSnackbar.SnackbarEvent(mens));
    }  

  
    @FXML
    private void clkBtQuitar(ActionEvent event) {
        boolean flag = true;
        double valor = 0, total = 0, dife = 0, novo=0;
        Pagamento p = new Pagamento();
        DALPagamentos dal = new DALPagamentos();

        
        this.txValorpago.setStyle("-fx-background-color: transparent");
        this.txDescJuros.setStyle("-fx-background-color: transparent");
        this.dtPagamento.setStyle("-fx-background-color: transparent");

        if("".equals(this.txValorpago.getText())){
            this.txValorpago.setStyle("-fx-background-color: #FF6347");
            flag = false;
        }else{                        
            try {                            
                valor = Double.parseDouble(convertStr(txValorpago.getText()+""));
                System.out.println(valor);

                total = Double.parseDouble(txValor.getText());
                System.out.println(total);
                
                if(!"".equals(this.txDescJuros.getText()))
                    dife = Double.parseDouble(convertStr(txDescJuros.getText()+""));                    
                
                System.out.println(dife);

                if(rbParcial.isSelected() && valor - total != 0){
                     novo = total - valor - dife;
                }
                else if((total - valor + dife) != 0){
                        this.txValorpago.setStyle("-fx-background-color: #FF6347");
                        this.txDescJuros.setStyle("-fx-background-color: #FF6347"); 
                        flag = false;
                    }
                
            } catch (Exception e) {
                this.txValorpago.setStyle("-fx-background-color: #FF6347");
                this.txDescJuros.setStyle("-fx-background-color: #FF6347"); 
                flag= false;
            }
        }
        if(dtPagamento.getValue() == null){
            this.dtPagamento.setStyle("-fx-background-color: #FF6347");
            flag = false;
        }
        
        if(flag){
            p=dal.get(Integer.parseInt( txCod.getText()));            
            p.setValorpago(valor);
            p.setDesJur(dife);
            p.setPagamento(java.sql.Date.valueOf(dtPagamento.getValue()));
            if(novo != 0){
                p.setParcial(rbParcial.isSelected());
                Pagamento n = dal.get(Integer.parseInt( txCod.getText()));
                n.setDesc(n.getDesc() + " - Faltante");                
                n.setParcial(false);
                n.setValor(novo);
                n.setParcela(-1);
                n.setPai(p.getCod());
                dal.gravar(n);    
            }else
                p.setParcial(false);
            
            
            if(dal.quitar(p)){
                this.snackbar("Pagamento quitado com sucesso!", "green");
                
                this.original();
                this.loadTable("");
            }
            else{
                this.snackbar("Problemas ao quitar Pagamento!", "red");
            }
            
        }
        else{
            this.snackbar("Problemas ao quitar Pagamento!", "red");
        }
        
    }

    @FXML
    private void clkBtEstornar(ActionEvent event) {
        double valor = 0;
        DALPagamentos dal = new DALPagamentos();
        
        if(rbParcial.isSelected()){
            valor = Double.parseDouble(txValorpago.getText()) + Double.parseDouble(txDescJuros.getText()) ;
        }
        else
            valor = Double.parseDouble(txValor.getText());
        
        
        if(dal.estornar(Integer.parseInt( txCod.getText()))){
                
                this.snackbar("Pagamento estornado com sucesso!", "green");
                dal.apagarEstonro(Integer.parseInt( txCod.getText()));
                this.original();
                this.loadTable("");
            }
            else{
                this.snackbar("Problemas ao estornar Pagamento!", "red");
            }
        
    }

    @FXML
    private void clkBtSelecionar(ActionEvent event) {
        if(this.tabela.getSelectionModel().getSelectedItem() != null){            
            Pagamento p = new DALPagamentos().get(this.tabela.getSelectionModel().getSelectedItem().getCod());
            
            txCod.setText(""+ p.getCod());
            txDesc.setText(""+ p.getDesc());            
            txValor.setText(""+ p.getValor());            
            rbParcial.setSelected(p.isParcial());
            
            dtVencimento.setValue(p.getVencimento().toLocalDate());
            if(p.getPagamento() != null){
                dtPagamento.setValue(p.getPagamento().toLocalDate());
                txValorpago.setText(""+ p.getValorpago());                
                txDescJuros.setText(""+ p.getDesJur());
                Status(true);
            }else{
                Status(false);                
            }
                               
     }
    }
    
    
    @FXML
    private void clkTabela(MouseEvent event) {
        if(this.tabela.getSelectionModel().getSelectedItem() != null){
            this.btSelecionar.setDisable(false);
        }
    }

    @FXML
    private void clkfiltro(ActionEvent event) {
        boolean flag = false;
        String filtro = "";        
        Date data = new Date(System.currentTimeMillis()); 
        SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd"); 
        
        if(rbPago.isSelected()){
            flag = true;
            filtro+= "pag_pagamento IS NOT NULL";
        }
        if(rbAPagar.isSelected()){
            if(flag)
               filtro+=" and ";
            filtro+= "pag_pagamento IS NULL";
            flag = true;
        }
        if(rbVencido.isSelected()){
            if(flag)
               filtro+=" and ";
            filtro+= "pag_vencimento < '"+formatarDate.format(data)+"'";
            flag = true;
        }
        if(rbAntesde.isSelected() && dpAntesde.getValue() != null){
            if(flag)
                filtro+=" and ";
            filtro+= "pag_vencimento < '"+ dpAntesde.getValue()+"'";
            flag = true;
        } 
        if(rbDepoisde.isSelected() && dpDepoisde.getValue() != null){
            if(flag)
               filtro+=" and ";
            filtro+= "pag_vencimento >= '"+ dpDepoisde.getValue()+"'";
        }
        
        loadTable(filtro);
    }

    
}
