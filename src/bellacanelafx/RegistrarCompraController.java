
package bellacanelafx;

import bellacanela.db.dal.DALFornecedor;
import bellacanela.db.dal.DALItensNF;
import bellacanela.db.dal.DALNotafiscal;
import bellacanela.db.dal.DALPagamentos;
import bellacanela.db.dal.DALProduto;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.Fornecedor;
import bellacanelafx.db.entidades.ItensNF;
import bellacanelafx.db.entidades.NotaFiscal;
import bellacanelafx.db.entidades.Pagamento;
import bellacanelafx.db.entidades.Produtos;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
import javax.swing.JOptionPane;

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
    private TableColumn<NotaFiscal, String> colNFDescricao;
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
    private JFXTextField tfPrecoProduto;    
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
    private TableView<Pagamento> tbParcelas;
    @FXML
    private TableColumn<Pagamento, String> colParValor;
    @FXML
    private TableColumn<Pagamento, String> colParVencimento;
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
        colNFDescricao.setCellValueFactory(new PropertyValueFactory("desc"));
        
        colPrProduto.setCellValueFactory((TableColumn.CellDataFeatures<ItensNF, String> nf) -> new SimpleStringProperty(nf.getValue().getProd().getNome()));
        colPrQuantidade.setCellValueFactory(new PropertyValueFactory("qtde"));
        colPrTotal.setCellValueFactory(new PropertyValueFactory("total"));
        colPrPreco.setCellValueFactory(new PropertyValueFactory("preco"));
        colPrEstoque.setCellValueFactory(new PropertyValueFactory("ctrl"));
        
        
        colParValor.setCellValueFactory(new PropertyValueFactory("valor"));
        colParVencimento.setCellValueFactory(new PropertyValueFactory("vencimento"));
        
        
        Platform.runLater(() -> {
            try {
                MaskFieldUtil.monetaryField(this.tfPrecoProduto);
            } catch (Exception e) {
            }
            
        
        });
        this.original();
        //original();
        
    } 
    private void snackbar(String message, String cor){
        JFXSnackbar sb = new JFXSnackbar(this.apDados);
        Label mens = new Label(message);
        mens.setStyle("-fx-text-fill: "+cor+";"
                    + "-fx-font-size: 14px;"
                    + "-fx-padding: 10px;");
        sb.enqueue(new JFXSnackbar.SnackbarEvent(mens));
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

    private void edition() {        
        
        
    }
    
    private void original() {
                
        loadTable("");
        loadCB();
    }
    
    
    @FXML
    private void clkNovo(ActionEvent event) {
        
        apDados.setDisable(false);
    }

    @FXML
    private void clkAlterar(ActionEvent event) {
        
        if(this.tbCompras.getSelectionModel().getSelectedItem() != null){            
            NotaFiscal f = this.tbCompras.getSelectionModel().getSelectedItem();
            DALItensNF dalnf = new DALItensNF();
            DALPagamentos dalpag = new DALPagamentos();
            ArrayList<ItensNF> itens ;
            ArrayList<Pagamento> pags ;
            
            
            
            if(dalpag.verifica(this.tbCompras.getSelectionModel().getSelectedItem())){
               this.snackbar("Não é possivel alterar, já houve pagamento de alguma parcela !!", "red");
            }
            else{
                double valor = f.getTotal();

                itens = dalnf.getNF(f.getCod()+ "");
                pags = dalpag.getNF(f.getCod() + "");

                for(Pagamento i: pags){
                    valor-=i.getValor();
                }

                String strSal = f.getTotal()+"";
                int i = (strSal).indexOf(".");
                if(strSal.length() - i == 2)
                    strSal += "0";

                this.tfCod.setText(f.getCod()+"");
                this.tfDescricao.setText(f.getDesc());
                this.tfNotaFiscal.setText(f.getNumero());            
                this.tfPrecoTotal.setText(strSal);

                strSal = valor+"";
                i = (strSal).indexOf(".");
                if(strSal.length() - i == 2)
                    strSal += "0";

                this.tfValorFaltante.setText(strSal);

                this.cbFornecedor.getSelectionModel().select(0);


                this.cbFornecedor.getSelectionModel().select(f.getFornecedor());

                tbParcelas.setItems(FXCollections.observableArrayList(pags));
                tbProdutos.setItems(FXCollections.observableArrayList(itens));           

                original();
            }
            
     }
        
    }

    @FXML
    private void clkApagar(ActionEvent event) {
        
        if(this.tbCompras.getSelectionModel().getSelectedItem() != null){
            
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            DALItensNF dalnf = new DALItensNF();
            DALProduto dalprod = new DALProduto();
            DALPagamentos dalpag = new DALPagamentos();
            Produtos prod;
            ArrayList<ItensNF> itens ;
            ArrayList<Pagamento> pags ;

            a.setContentText("Realmente deseja excluir a Compra ?");     

            if(dalpag.verifica(this.tbCompras.getSelectionModel().getSelectedItem())){
               this.snackbar("Não é possivel apagar, já houve pagamento de alguma parcela !!", "red");
            }
            else if(a.showAndWait().get() == ButtonType.OK){
                DALNotafiscal dal = new DALNotafiscal();

               itens = dalnf.getNF(this.tbCompras.getSelectionModel().getSelectedItem().getCod() + "");
               pags = dalpag.getNF(this.tbCompras.getSelectionModel().getSelectedItem().getCod() + "");

              for( ItensNF i : itens){
                  prod = i.getProd();

                  if(prod.getEstoque() != -1){                  
                    if(prod.getEstoque() < i.getQtde()){
                        prod.setEstoque(0);
                    }
                    else{
                        prod.setEstoque(prod.getEstoque() - i.getQtde());
                    }
                    dalprod.alterar(prod);

                    }

                  dalnf.apagar(i);
                }

              for( Pagamento i : pags){              
                  dalpag.apagar(i);
                }

              dal.apagar(this.tbCompras.getSelectionModel().getSelectedItem());          



            }
            ObservableList <Node> componentes = this.apDados.getChildren(); //”limpa” os componentes
            for(Node n : componentes)
            {
                if (n instanceof TextInputControl)  // textfield, textarea e htmleditor
                    ((TextInputControl)n).setText("");
                if(n instanceof ComboBox)
                    ((ComboBox)n).getItems().clear();
            }

            tbParcelas.getItems().clear();
            tbProdutos.getItems().clear();

           this.original();
           this.loadTable("");
        }
    }

    @FXML
    private void clkSalvar(ActionEvent event) {
        DALItensNF dalnf = new DALItensNF();
        DALPagamentos dalpag = new DALPagamentos();
        DALProduto dalprod = new DALProduto();
        DALNotafiscal dalnota = new DALNotafiscal();
        
        ArrayList<ItensNF> itens ;
        ArrayList<Pagamento> pags ;
        Date maior;
        Produtos prod;
        NotaFiscal nota;
        
        
        if(Double.parseDouble(tfValorFaltante.getText()) == 0){
            int sla = tbParcelas.getItems().size();
            maior = tbParcelas.getItems().get(0).getVencimento();

            for (Pagamento i : tbParcelas.getItems()){
                if(tbParcelas.getItems().get(0).getVencimento().compareTo(maior) > 0){
                    maior = tbParcelas.getItems().get(0).getVencimento();
                }
                System.out.println("data");
            }

            if(!"".equals(tfCod.getText())){
                itens = dalnf.getNF(Integer.parseInt(tfNotaFiscal.getText()) + "");
                pags = dalpag.getNF(Integer.parseInt(tfNotaFiscal.getText()) + "");

               for( ItensNF i : itens){
                   prod = i.getProd();

                   if(prod.getEstoque() != -1){                  
                        if(prod.getEstoque() < i.getQtde()){
                            prod.setEstoque(0);
                        }
                        else{
                            prod.setEstoque(prod.getEstoque() - i.getQtde());
                        }
                        dalprod.alterar(prod);
                     }

                   dalnf.apagar(i);
                 }

               for( Pagamento i : pags){              
                   dalpag.apagar(i);
                 }

               nota = new NotaFiscal( Integer.parseInt(tfCod.getText()), tfNotaFiscal.getText(), cbFornecedor.getValue(), tfDescricao.getText(), maior, sla, Double.parseDouble(tfPrecoTotal.getText()));
               dalnota.alterar(nota);

                 System.out.println("alteração");
            }
            else{
                nota = new NotaFiscal(tfNotaFiscal.getText(), cbFornecedor.getValue(), tfDescricao.getText(), maior, sla, Double.parseDouble(tfPrecoTotal.getText()));
                dalnota.gravar(nota);

                System.out.println("inserção");
            }
            int ajuda = dalnota.getMax();


            for ( ItensNF i : tbProdutos.getItems()){                      

                prod = i.getProd();

                i.setNf(new NotaFiscal(ajuda)); 

                if(prod.getEstoque() != -1){
                    prod.setEstoque(prod.getEstoque() + i.getQtde());
                }

                dalnf.gravar(i);

                dalprod.alterEstoque(prod);            

            }
            int aux = 1;

            for (Pagamento i : tbParcelas.getItems()){
                i.setNf(ajuda);               
                i.setDesc(tfDescricao.getText() + "  " + aux);            
                i.setParcela(aux++);   
                i.setPai(0);

                dalpag.gravar(i);


            }
            ObservableList <Node> componentes = this.apDados.getChildren(); //”limpa” os componentes
            for(Node n : componentes)
            {
                if (n instanceof TextInputControl)  // textfield, textarea e htmleditor
                    ((TextInputControl)n).setText("");
                if(n instanceof ComboBox)
                    ((ComboBox)n).getItems().clear();
            }

            tbParcelas.getItems().clear();
            tbProdutos.getItems().clear();

           this.original();
           this.loadTable("");           
        }
        else{
            this.snackbar("Valor de pagamento inferior ao da compra!!", "red"); 
        }
        
        
    }
    
    @FXML
    private void evtSearch(KeyEvent event) {
        String f1, f2, filtro;
        
        f1 = this.tfSearch.getText().isEmpty() ? "" : "UPPER(not_nf) LIKE '%#1%'";
        f1 = f1.replaceAll("#1", this.tfSearch.getText().toUpperCase());
        
        if(this.cbFornecedorSearch.getValue() != null && !"".equals(this.cbFornecedorSearch.getValue().getNome())){
            f2 = "not_fornecedor = '#2'";
            f2 = f2.replaceAll("#2", this.cbFornecedorSearch.getValue().getCod()+"");
        }
        else
            f2 = "";
        
        
        if("".equals(f1)){
            filtro = f2;
        }
        else
            if("".equals(f2)){
                filtro = f1;
            }
            else
                filtro = f1 + " and " + f2;
         
        
        
        
        loadTable(filtro);
        
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
        try{
            Object[] options = { "Confirmar", "Cancelar" };
            double dou =0;
            boolean flag = true;
            
            if(!tbParcelas.getItems().isEmpty()){
                if(0 != JOptionPane.showOptionDialog(null, "A mudança apagará as parcelas, deseja continuar?", "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0])){
                    flag = false;
                }
                else{
                    tbParcelas.getItems().clear();                    
                }
                
            }
            
            if(flag){
                dou = tbProdutos.getItems().get(tbProdutos.getSelectionModel().getSelectedIndex()).getPreco() * tbProdutos.getItems().get(tbProdutos.getSelectionModel().getSelectedIndex()).getQtde();
                tbProdutos.getItems().remove(tbProdutos.getSelectionModel().getSelectedIndex()); 
            }  

            tfPrecoTotal.setText(("" +  ( Double.parseDouble((tfPrecoTotal.getText())) - dou )));
            tfValorFaltante.setText(tfPrecoTotal.getText());
        }
        catch(Exception e){
            
        }

    }

    @FXML
    private void clkSalvarProd(ActionEvent event) {
        boolean flag = true;
        Object[] options = { "Confirmar", "Cancelar" };
        
        this.tfPrecoProduto.setStyle("-fx-background-color: transparent");
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
        
        if(flag ){            
            for ( ItensNF i : tbProdutos.getItems()){
                if(i.getProd().getCod() == cbProduto.getValue().getCod())
                    flag = false;
            }
            if(!flag)
                this.snackbar("Produto ja cadastrado, apague-o e insira novamente!", "red"); 
        }
        
        if(flag && !tbParcelas.getItems().isEmpty()){
            if(0 != JOptionPane.showOptionDialog(null, "A mudança apagará as parcelas, deseja continuar?", "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0])){
                flag = false;
            }
            else{
                tbParcelas.getItems().clear();
                tfValorFaltante.setText(tfPrecoTotal.getText());
            }
                
        }
        
        if(flag){
            
            if(tbProdutos.getItems().isEmpty()){
                tfPrecoTotal.setText("0");               
            }
            if(tbProdutos.getItems().isEmpty() && tbParcelas.getItems().isEmpty()){
                tfValorFaltante.setText("0");                
            }
            
            try {
                int qtde = Integer.parseInt(convertStr(tfQTDProduto.getText()));
                double preco = Double.parseDouble(convertStr2(tfPrecoProduto.getText()));
                
            tbProdutos.getItems().add(new ItensNF(cbProduto.getValue(), qtde, preco));   
            
                
                tfPrecoTotal.setText(("" +  ( Double.parseDouble((tfPrecoTotal.getText())) + preco*qtde)));
                tfValorFaltante.setText(("" +  ( Double.parseDouble((tfValorFaltante.getText())) + preco*qtde)));
                
                tfPrecoProduto.clear();
                tfQTDProduto.clear();
                
            } catch (Exception e) {                
             
            }       
   
        }
        
    }
    

    @FXML
    private void clkApagarParc(ActionEvent event) {
        
        try{
            double dou = 0;
            if(this.tbParcelas.getSelectionModel().getSelectedItem() != null){
                dou = tbParcelas.getItems().get(tbParcelas.getSelectionModel().getSelectedIndex()).getValor();
                tbParcelas.getItems().remove(tbParcelas.getSelectionModel().getSelectedIndex()); 
            }  

            tfValorFaltante.setText(("" +  ( Double.parseDouble((tfValorFaltante.getText())) + dou )));
        }
        catch(Exception e){
            
        }
    }

    @FXML
    private void clkSalvarParc(ActionEvent event) {
        
        boolean flag = true;
        
        this.tfValorParc.setStyle("-fx-background-color: transparent");
        this.dpParVencimento.setStyle("-fx-background-color: transparent");        
        
        
        if("".equals(tfValorParc.getText())){
            flag = false;
            this.tfValorParc.setStyle("-fx-background-color: #FF6347");
        }
        
        if(dpParVencimento.getValue()== null){
            flag = false;
            this.dpParVencimento.setStyle("-fx-background-color: #FF6347");
        }
        
       
        
        
        if(flag){
            if(tbProdutos.getItems().isEmpty() && tbParcelas.getItems().isEmpty()){
                tfValorFaltante.setText("0");                
            }
            
            double valor ,atual;
            valor = Double.parseDouble(convertStr2(tfValorParc.getText()));
            //atual = Double.parseDouble(convertStr2(tfValorFaltante.getText()));
            
            //if(valor <= atual){
                
                try {

                  tfValorFaltante.setText(("" +  ( Double.parseDouble((tfValorFaltante.getText())) - valor)));

                  tbParcelas.getItems().add(new Pagamento(valor, java.sql.Date.valueOf(dpParVencimento.getValue())));



              } catch (Exception e) {} 
                
            //}else{
                //this.snackbar("Parcela com valor maior que o faltante", "red");
            //}
                 
   
        }
        
        
    }


    
    
    @FXML
    private void clkCancelar(ActionEvent event) {
        if (!this.apDados.isDisabled()){
            this.original();
            apDados.setDisable(true);
        }
            
        else {
            HomeController.spnprincipal.setCenter(null);
            BellaCanela.sStage.setTitle("Bella Canela");
        }
    }

    @FXML
    private void clkProduto(ActionEvent event) {
        try{
        tfPrecoProduto.setText(convertStr(""+cbProduto.getValue().getPreco()));
        }
        catch(Exception e){
            
        }
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

    
} 
