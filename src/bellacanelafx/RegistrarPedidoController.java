
package bellacanelafx;

import bellacanela.db.dal.DALComanda;
import bellacanela.db.dal.DALProduto;
import bellacanelafx.db.entidades.Comanda;
import bellacanelafx.db.entidades.Produtos;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.StringConverter;

public class RegistrarPedidoController implements Initializable {

    @FXML
    private SplitPane painel;
    @FXML
    private AnchorPane apdados;
    @FXML
    private VBox pnpesquisa;
    @FXML
    private TableView<Comanda> tabela;
    @FXML
    private TableColumn<Comanda, Integer> colComanda;
    @FXML
    private TableColumn<Comanda, String> colNome;
    @FXML
    private TableColumn<Produtos, Double> colPrecoUni;
    @FXML
    private TableColumn<?, ?> colQtde;
    @FXML
    private TableColumn<Integer, Integer> colPrecoTot;
    @FXML
    private Label lbNMesa;
    @FXML
    private Label lbPrecoTotMesa;
    @FXML
    private JFXTextField tfNome;
    @FXML
    private JFXDatePicker dpData;
    @FXML
    private JFXTextField tfDescricao;
    @FXML
    private JFXComboBox<Comanda> cbComanda;
    @FXML
    private JFXTextField tfPrecoTotComanda;
    @FXML
    private JFXButton btAddComanda;
    @FXML
    private JFXComboBox<Produtos> cbProdutos;
    @FXML
    private Spinner<?> spQtde;
    @FXML
    private JFXButton btInserir;
    @FXML
    private JFXButton btDeletar;
    @FXML
    private JFXCheckBox checkTodasComandas;
    @FXML
    private JFXButton btSair;
    
    DALComanda dalCom = new DALComanda();
    DALProduto dalPro = new DALProduto();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colComanda.setCellValueFactory(new PropertyValueFactory("com_num"));
        colNome.setCellValueFactory((TableColumn.CellDataFeatures<Comanda, String> p) -> new SimpleStringProperty(p.getValue().getCliente().getNome()));
        Platform.runLater(()->{
            try{
                loadCBComanda();
                loadCBProdutos();
                atualizarComponentesEstaticos();
                loadTable("mes_cod = " + cbComanda.getValue().getCom_num() + " AND com_num = " + cbComanda.getValue().getMes_cod());
            }
            catch(Exception e){
                System.out.println("Erro ao carregar a janela de registrar pedidos!");
            }
        });
        
        
    }    
    
    public void loadTable(String filtro){
        ObservableList<Comanda> olCom = FXCollections.observableArrayList(dalCom.getComandas(filtro));
        tabela.setItems(olCom);
        tabela.refresh();
    }
    
    public void loadCBComanda(){
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
        ObservableList<Comanda> olCom = FXCollections.observableArrayList(dalCom.getComandas("mes_cod = " + lbNMesa.getText()));
        cbComanda.setItems(olCom);
        cbComanda.setValue(cbComanda.getItems().get(0));
    }
    
    public void loadCBProdutos(){
        cbProdutos.setConverter(new StringConverter<Produtos>(){
            @Override
            public String toString(Produtos prod) {
                return prod.getNome();
            }

            @Override
            public Produtos fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        ObservableList<Produtos> olPro = FXCollections.observableArrayList(dalPro.get(""));
        cbProdutos.setItems(olPro);
    }

    public void atualizarComponentesEstaticos(){
        tfNome.setText(cbComanda.getValue().getCliente().getNome());
        dpData.setValue(cbComanda.getValue().getData());
        tfDescricao.setText(cbComanda.getValue().getDescricao());
    }
    
    public void setNMesa(String num){
        lbNMesa.setText(num);
    }

    public void fecharJanela(){
        ((Stage)(btSair.getParent().getScene().getWindow())).close();
    }
    
    private void efeito(ActionEvent event, boolean on) {
        double opi = 1, opf = 0.2;
        if (!on) {
            opi = 0.2;
            opf = 1;
        }
        Node node = ((Node) event.getSource()).getParent().getParent().getParent().getParent(); // referencia o painel das comandas
        FadeTransition ft = new FadeTransition(Duration.millis(500), node);
        ft.setFromValue(opi);
        ft.setToValue(opf);
        ft.play();
    }
    
    @FXML
    private void evtSair(ActionEvent event) {
        fecharJanela();
    }
    
    @FXML
    private void clkTabela(MouseEvent event) {
        
    }

    @FXML
    private void evtAddComanda(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AbrirComanda.fxml"));
            Parent root = (Parent) loader.load();
            AbrirComandaController ctr = loader.getController();

            //pode adicionar atributos na outra tela
            ctr.setNMesa(lbNMesa.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            efeito(event, true);
            stage.showAndWait();
            efeito(event, false);
            loadCBComanda();
        }
        catch(Exception e){
            System.out.println("ERRO AO ABRIR NOVA COMANDA.");
        }
    }

    @FXML
    private void evtInserirProduto(ActionEvent event) {
    }

    @FXML
    private void evtDeletarProduto(ActionEvent event) {
    }

    @FXML
    private void evtMostrarTodasAsComandas(ActionEvent event) {
    }

    @FXML
    private void evtTrocarComanda(ActionEvent event) {
        Platform.runLater(()->{atualizarComponentesEstaticos();});
    }
    
}
