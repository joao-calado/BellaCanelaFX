
package bellacanelafx;

import bellacanela.db.dal.DALFornecedor;
import bellacanela.db.dal.DALNotafiscal;
import bellacanela.db.dal.DALProduto;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.Fornecedor;
import bellacanelafx.db.entidades.ItensNF;
import bellacanelafx.db.entidades.NotaFiscal;
import bellacanelafx.db.entidades.Produtos;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class RegistrarCompraController implements Initializable {

    @FXML
    private JFXButton btNovo;
    @FXML
    private JFXButton btAlterar;
    @FXML
    private JFXButton btApagar;
    @FXML
    private JFXButton btSalvar;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private AnchorPane apDados;
    @FXML
    private JFXTextField tfCod;
    @FXML
    private VBox pnSearch;
    @FXML
    private JFXTextField tfSearch;
    @FXML
    private JFXTextField tfNotaFiscal;
    @FXML
    private JFXTextField tfPrecoTotal;
    @FXML
    private JFXTextField tfQTDProduto;
    @FXML
    private JFXComboBox<Fornecedor> cbFornecedor;    
    @FXML
    private JFXComboBox<Produtos> cbProduto;
    
    
    @FXML
    private TableView<ItensNF> tbProdutos;
    @FXML
    private TableColumn<ItensNF, String> colPrProduto;
    @FXML
    private TableColumn<ItensNF, String> colPrQuantidade;
    @FXML
    private TableColumn<ItensNF, String> colPrPreco;
    @FXML
    private TableColumn<ItensNF, String> colPrTotal;
    @FXML
    private TableColumn<ItensNF, String> colPrEstoque;
    
    
    
    @FXML
    private JFXButton btApagarProd;
    @FXML
    private JFXButton btSalvarProd;
    @FXML
    private JFXComboBox<Fornecedor> cbFornecedorSearch;
    @FXML
    private TableView<NotaFiscal> tbCompras;
    @FXML
    private TableColumn<NotaFiscal, String> colNFCod;
    @FXML
    private TableColumn<NotaFiscal, String> colNFNotaFiscal;
    @FXML
    private TableColumn<NotaFiscal, String> colNFFornecedor;
    @FXML
    private TableColumn<NotaFiscal, String> colNFParcelas;
    @FXML
    private TableColumn<NotaFiscal, String> colNFValorTotal;
    @FXML
    private JFXTextField tfDescricao;
    @FXML
    private JFXTextField tfParcelas;
    @FXML
    private JFXDatePicker dpVencimento;    
    @FXML
    private JFXTextField tfPrecoProduto;
    @FXML
    private JFXComboBox<Character> cbProdEstoque;
    @FXML
    private JFXRadioButton GroupProd;
    @FXML
    private ToggleGroup propar;
    @FXML
    private JFXRadioButton GroupParc;
    @FXML
    private AnchorPane paneProdutos;
    @FXML
    private AnchorPane paneParcelas;
    @FXML
    private TableView<?> tbParcelas;
    @FXML
    private TableColumn<?, ?> colParParcela;
    @FXML
    private TableColumn<?, ?> colParDescricao;
    @FXML
    private TableColumn<?, ?> colParValor;
    @FXML
    private TableColumn<?, ?> colParVencimento;
    @FXML
    private JFXTextField tfParcelaParc;
    @FXML
    private JFXButton btApagarParc;
    @FXML
    private JFXButton btSalvarParc;
    @FXML
    private JFXTextField tfValorParc;
    @FXML
    private JFXDatePicker dpParVencimento;
    @FXML
    private JFXTextField tfValorFaltante;
    

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNFCod.setCellValueFactory(new PropertyValueFactory("cod"));   
        colNFNotaFiscal.setCellValueFactory(new PropertyValueFactory("numero"));
        colNFParcelas.setCellValueFactory(new PropertyValueFactory("parcelas"));
        colNFValorTotal.setCellValueFactory(new PropertyValueFactory("total"));
        colNFFornecedor.setCellValueFactory((TableColumn.CellDataFeatures<NotaFiscal, String> nf) -> new SimpleStringProperty(nf.getValue().getFornecedor().getNome()));
        
        colPrProduto.setCellValueFactory((TableColumn.CellDataFeatures<ItensNF, String> nf) -> new SimpleStringProperty(nf.getValue().getProd().getNome()));
        colPrQuantidade.setCellValueFactory(new PropertyValueFactory("qtde"));
        colPrTotal.setCellValueFactory(new PropertyValueFactory("total"));
        colPrPreco.setCellValueFactory(new PropertyValueFactory("preco"));
        colPrEstoque.setCellValueFactory(new PropertyValueFactory("ctrl"));
        
        
        
        Platform.runLater(() -> {
            try {
                MaskFieldUtil.monetaryField(this.tfPrecoProduto);
            } catch (Exception e) {
            }
            
        
        });
        iniciar();
        
        
    }    
    private void iniciar(){
        loadTable("");
        loadCB();
    }
    private void loadTable(String filtro) {
        DALNotafiscal dal = new DALNotafiscal();
        
        ArrayList<NotaFiscal> NF = dal.get(filtro);
      
        ObservableList<NotaFiscal> modelo = FXCollections.observableArrayList(NF);
        
        this.tbCompras.setItems(modelo);
        this.tbCompras.refresh();       
        
        
    }
       
    private void loadCB (){
        ArrayList <Character> auxc = new ArrayList<>();
        auxc.add('S');
        auxc.add('N');
        
        this.cbProdEstoque.setItems(FXCollections.observableArrayList(auxc));
        
        this.cbFornecedor.setItems(FXCollections.observableArrayList(new DALFornecedor().get("")));
        this.cbFornecedorSearch.setItems(FXCollections.observableArrayList(new DALFornecedor().get("")));
        this.cbProduto.setItems(FXCollections.observableArrayList(new DALProduto().get("")));    
        
        
        
        this.cbFornecedor.setConverter(new StringConverter<Fornecedor>(){
            @Override
            public String toString(Fornecedor forn) {
                return forn.getNome();
            }

            @Override
            public Fornecedor fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            } 
            
            public Fornecedor toValue(Fornecedor forn){
               return forn;
            }
    
    
         });
        
        this.cbProduto.setConverter(new StringConverter<Produtos>(){
            @Override
            public String toString(Produtos prod) {
                return prod.getNome();
            }

            @Override
            public Produtos fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            public Produtos toValue(Produtos prod){
               return prod;
            }
    
         });
        
        this.cbFornecedorSearch.setConverter(new StringConverter<Fornecedor>(){
            @Override
            public String toString(Fornecedor forn) {
                return forn.getNome();
            }

            @Override
            public Fornecedor fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            } 
            
            public Fornecedor toValue(Fornecedor forn){
               return forn;
            }
    
    
         });
    }

    private void original() {
        this.pnSearch.setDisable(false);
        this.pnSearch.setOpacity(1);
        this.apDados.setDisable(true);
        this.apDados.setOpacity(0.5);
        
        this.btSalvar.setDisable(true);
        this.btCancelar.setDisable(false);
        this.btApagar.setDisable(true);
        this.btAlterar.setDisable(true);
        this.btNovo.setDisable(false);
        this.tfSearch.clear();
        
        ObservableList <Node> componentes = this.apDados.getChildren(); //”limpa” os componentes
        for(Node n : componentes)
        {
            if (n instanceof TextInputControl)  // textfield, textarea e htmleditor
                ((TextInputControl)n).setText("");
            if(n instanceof ComboBox)
                ((ComboBox)n).getItems().clear();
        }
        
        loadTable("");
        loadCB();;
    }    
    
    @FXML
    private void clkNovo(ActionEvent event) {
    }

    @FXML
    private void clkAlterar(ActionEvent event) {
    }

    @FXML
    private void clkApagar(ActionEvent event) {
    }

    @FXML
    private void clkSalvar(ActionEvent event) {
    }



    @FXML
    private void evtSearch(KeyEvent event) {
    }

    @FXML
    private void clkTable(MouseEvent event) {
        if(this.tbProdutos.getSelectionModel().getSelectedItem() != null){
            this.btAlterar.setDisable(false);
            this.btApagar.setDisable(false);
        }
    }

    @FXML
    private void clkApagarProd(ActionEvent event) {
        double dou = 0;
        if(this.tbProdutos.getSelectionModel().getSelectedItem() != null){
            dou = tbProdutos.getItems().get(tbProdutos.getSelectionModel().getSelectedIndex()).getPreco();
            tbProdutos.getItems().remove(tbProdutos.getSelectionModel().getSelectedIndex()); 
        }  
        
        tfPrecoTotal.setText(("" +  ( Double.parseDouble((tfPrecoTotal.getText())) - dou )));

    }

    @FXML
    private void clkSalvarProd(ActionEvent event) {
        boolean flag = true;
        
        this.tfPrecoProduto.setStyle("-fx-background-color: transparent");
        this.cbProdEstoque.setStyle("-fx-background-color: transparent");
        this.cbProduto.setStyle("-fx-background-color: transparent");
        this.tfQTDProduto.setStyle("-fx-background-color: transparent");
        
        
        if("".equals(tfPrecoProduto.getText())){
            flag = false;
            this.tfPrecoProduto.setStyle("-fx-background-color: #FF6347");
        }
        if("".equals(tfQTDProduto.getText())){
            flag = false;
            this.tfQTDProduto.setStyle("-fx-background-color: #FF6347");
        }
        if(cbProduto.getValue()== null){
            flag = false;
            this.cbProduto.setStyle("-fx-background-color: #FF6347");
        }
        if(cbProdEstoque.getValue()== null){
            flag = false;
            this.cbProdEstoque.setStyle("-fx-background-color: #FF6347");
        }
        
        
        if(flag){
            if(tbProdutos.getItems().isEmpty()){
                tfPrecoTotal.setText("0");                
            }
            try {
                int qtde = Integer.parseInt(convertStr(tfQTDProduto.getText()));
                double preco = Double.parseDouble(convertStr2(tfPrecoProduto.getText()));
                
            tbProdutos.getItems().add(new ItensNF(cbProduto.getValue(), qtde,
                preco,cbProdEstoque.getValue()));   
            
                System.out.println(tfPrecoTotal.getText());
                
                tfPrecoTotal.setText(("" +  ( Double.parseDouble((tfPrecoTotal.getText())) + preco*qtde)));
            } catch (Exception e) {                
             
            }       
   
        }
        
    }

    
    
    @FXML
    private void clkCancelar(ActionEvent event) {
        if (!this.apDados.isDisabled())
            this.original();
        else 
            HomeController.spnprincipal.setCenter(null);
    }

    @FXML
    private void clkProduto(ActionEvent event) {
        tfPrecoProduto.setText(convertStr(""+cbProduto.getValue().getPreco()));
    }
    
    private String convertStr(String s){
        String dst = "";
        
        
        if(s.contains(".") && (s.length() - s.indexOf('.') - 1) < 2){
            s+=0;
        }
        
        for(char c : s.toCharArray())
            if(c != '.')
                dst += c == ',' ? '.' : c;
        
        
        return dst;
    }
    
    private String convertStr2(String s){
        String dst = "";
        
        for(char c : s.toCharArray())
            if(c != '.')
                dst += c == ',' ? '.' : c;
        
        return dst;
    } 

    @FXML
    private void clkgroup(ActionEvent event) {
        paneParcelas.setVisible(GroupParc.isSelected());
        paneProdutos.setVisible(GroupProd.isSelected());        
    }

    @FXML
    private void clkApagarParc(ActionEvent event) {
    }

    @FXML
    private void clkSalvarParc(ActionEvent event) {
    }
}
