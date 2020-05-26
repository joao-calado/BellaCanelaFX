/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx;

import bellacanela.db.dal.DALFornecedor;
import bellacanela.db.dal.DALNotafiscal;
import bellacanela.db.dal.DALProduto;
import bellacanelafx.db.entidades.Fornecedor;
import bellacanelafx.db.entidades.ItensNF;
import bellacanelafx.db.entidades.NotaFiscal;
import bellacanelafx.db.entidades.Produtos;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
    private TableView<Produtos> tbProdutos;  
    @FXML
    private TableColumn<Produtos, String> colPrCod;
    @FXML
    private TableColumn<Produtos, String> colPrProduto;
    @FXML
    private TableColumn<Produtos, String> colPrQuantidade;
    @FXML
    private TableColumn<Produtos, String> colPrPreco;
    @FXML
    private TableColumn<Produtos, String> colPrTotal;
    
    
    
    @FXML
    private JFXButton btApagarProd;
    @FXML
    private JFXButton btSalvarProd;
    @FXML
    private JFXButton btCancelarProd;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private void loadTable(String filtro) {
        DALNotafiscal dal = new DALNotafiscal();
        
        ArrayList<NotaFiscal> NF = dal.get(filtro);
      
        ObservableList<NotaFiscal> modelo = FXCollections.observableArrayList(NF);
        
        this.tbCompras.setItems(modelo);
        this.tbCompras.refresh();       
        
        
    }
       
    private void loadCB (){
        
        
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
    }

    @FXML
    private void clkSalvarProd(ActionEvent event) {
    }

    @FXML
    private void clkCancelarProd(ActionEvent event) {
    }
    
    
    @FXML
    private void clkCancelar(ActionEvent event) {
        if (!this.apDados.isDisabled())
            this.original();
        else 
            HomeController.spnprincipal.setCenter(null);
    }
    
}
