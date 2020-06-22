package bellacanelafx;

import bellacanela.db.dal.DALComanda;
import bellacanela.db.dal.DALItensDaComanda;
import bellacanela.db.dal.DALProduto;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.Comanda;
import bellacanelafx.db.entidades.ItensDaComanda;
import bellacanelafx.db.entidades.Produtos;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.SpinnerValueFactory;
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
    private TableView<ItensDaComanda> tabela;
    @FXML
    private TableColumn<ItensDaComanda, Integer> colComanda;
    @FXML
    private TableColumn<ItensDaComanda, String> colNome;
    @FXML
    private TableColumn<ItensDaComanda, Integer> colQtde;
    @FXML
    private TableColumn<ItensDaComanda, Double> colPrecoTot;
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
    private Spinner<Integer> spQtde;
    @FXML
    private JFXButton btInserir;
    @FXML
    private JFXButton btDeletar;
    @FXML
    private JFXCheckBox checkTodasComandas;
    @FXML
    private JFXButton btSair;
    @FXML
    private JFXTextField tfPrecoUni;
    
    DALComanda dalCom = new DALComanda();
    DALProduto dalPro = new DALProduto();
    DALItensDaComanda dalItens = new DALItensDaComanda();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colComanda.setCellValueFactory(new PropertyValueFactory("com_num"));
        colNome.setCellValueFactory((TableColumn.CellDataFeatures<ItensDaComanda, String> p) -> new SimpleStringProperty(p.getValue().getProduto().getNome()));
        colQtde.setCellValueFactory((TableColumn.CellDataFeatures<ItensDaComanda, Integer> p) -> new SimpleObjectProperty(p.getValue().getQtde()));
        colPrecoTot.setCellValueFactory((TableColumn.CellDataFeatures<ItensDaComanda, Double> p) -> new SimpleObjectProperty(p.getValue().getQtde() * p.getValue().getProduto().getPreco()));
        
        SpinnerValueFactory<Integer> valueSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spQtde.setValueFactory(valueSpinner);
        
        btDeletar.setDisable(true);
        
        Platform.runLater(() -> {
            try {
                loadMasks();
                loadCBComanda();
                loadCBProdutos();
                loadTable("com_num = " + cbComanda.getValue().getCom_num() + " AND mes_cod = " + cbComanda.getValue().getMes_cod());
                atualizarComponentesEstaticos();
            }
            catch (Exception e) {
                System.out.println("Erro ao carregar a janela de registrar pedidos!");
            }
        });
        
    }
    
    public void loadTable(String filtro) {
        ObservableList<ItensDaComanda> olItens = FXCollections.observableArrayList(dalItens.getItens(filtro));
        tabela.setItems(olItens);
        tabela.refresh();
    }
    
    public void loadCBComanda() {
        cbComanda.setConverter(new StringConverter<Comanda>() {
            @Override
            public String toString(Comanda com) {
                return com.getCom_num() + (com.getDescricao().isEmpty() ? "" : " - " + com.getDescricao());
            }
            
            @Override
            public Comanda fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        ObservableList<Comanda> olCom = FXCollections.observableArrayList(dalCom.getComandas("mes_cod = " + lbNMesa.getText() + " AND com_aberta = 'true'"));
        cbComanda.setItems(olCom);
        cbComanda.setValue(cbComanda.getItems().get(0));
    }
    
    public void loadCBProdutos() {
        cbProdutos.setConverter(new StringConverter<Produtos>() {
            @Override
            public String toString(Produtos prod) {
                return prod.getNome() + ", " + prod.getMed().getNome();
            }
            
            @Override
            public Produtos fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        ObservableList<Produtos> olPro = FXCollections.observableArrayList(dalPro.get("prod_avenda = 'S'"));
        cbProdutos.getItems().clear();
        cbProdutos.setItems(olPro);
        cbProdutos.setValue(cbProdutos.getItems().get(0));
    }
    
    public void loadMasks() {
        MaskFieldUtil.monetaryField(tfPrecoUni);
        MaskFieldUtil.monetaryField(tfPrecoTotComanda);
    }
    
    @FXML
    public void atualizarComponentesEstaticos() {
        tfNome.setText(cbComanda.getValue().getCliente().getNome());
        dpData.setValue(cbComanda.getValue().getData());
        tfDescricao.setText(cbComanda.getValue().getDescricao());
        if (cbProdutos.getValue() != null) {
            tfPrecoUni.setText(formatarPreco(cbProdutos.getValue().getPreco()));
        }
        atualizarValorComanda();
        atualizarValorMesa();
    }
    
    public void setNMesa(String num) {
        lbNMesa.setText(num);
    }
    
    private void snackbar(String message, String cor) {
        JFXSnackbar sb = new JFXSnackbar(apdados);
        Label mens = new Label(message);
        mens.setStyle("-fx-text-fill: " + cor + ";"
                + "-fx-font-size: 14px;"
                + "-fx-padding: 10px;");
        sb.enqueue(new JFXSnackbar.SnackbarEvent(mens));
    }
    
    public void fecharJanela() {
        salvarComandas();
        ((Stage) (btSair.getParent().getScene().getWindow())).close();
    }
    
    public void salvarComandas() {
        cbComanda.getItems().forEach((c) -> {
            dalCom.update(c);
        });
    }
    
    public String formatarPreco(double preco) {
        String str = preco + "";
        int i = (str).indexOf(".");
        if (str.length() - i == 2) {
            str += "0";
        }
        return str;
    }
    
    public void atualizarValorMesa() {
        double total = 0.0;
        for (Comanda com : cbComanda.getItems()) {
            for (ItensDaComanda itens : dalItens.getItens("mes_cod = " + lbNMesa.getText() + " AND com_num = " + com.getCom_num())) {
                total += itens.getQtde() * itens.getProduto().getPreco();
            }
        }
        lbPrecoTotMesa.setText(formatarPreco(total));
    }
    
    public void atualizarValorComanda() {
        double total = 0.0;
        
        for (ItensDaComanda itens : tabela.getItems()) {
            if (cbComanda.getValue().getCom_num() == itens.getCom_num()) {
                total += itens.getQtde() * itens.getProduto().getPreco();
            }
        }
        
        tfPrecoTotComanda.setText(formatarPreco(total));
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
        if (tabela.getSelectionModel().getSelectedItem() != null) {
            btDeletar.setDisable(false);
        }
        else {
            btDeletar.setDisable(true);
        }
    }
    
    @FXML
    private void evtAddComanda(ActionEvent event) {
        try {
            salvarComandas();
            
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
            btDeletar.setDisable(true);
        }
        catch (Exception e) {
            System.out.println("ERRO AO ABRIR NOVA COMANDA.");
        }
    }
    
    @FXML
    private void evtInserirProduto(ActionEvent event) {
        if (cbProdutos.getValue() != null) {
            Comanda c = cbComanda.getValue();
            Produtos p = cbProdutos.getValue();
            if (p.getEstoque() != -1) {
                if (p.getEstoque() >= spQtde.getValue()) {
                    p.setEstoque(p.getEstoque() - spQtde.getValue());
                }
                else {
                    snackbar("Quantidade em estoque insuficiente!", "red");
                    return;
                }
            }
            c.inserirItem(new ItensDaComanda(c.getCom_num(), c.getMes_cod(), p, spQtde.getValue()));
            salvarComandas();
            evtMostrarTodasAsComandas(event);
            atualizarComponentesEstaticos();
        }
    }
    
    @FXML
    private void evtDeletarProduto(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem().getQtde() < spQtde.getValue()){
            snackbar("Quantidade maior do que a inserida na tabela", "red");
        }
        else{
            Comanda c = cbComanda.getValue();
            c.deletarItem(tabela.getSelectionModel().getSelectedItem(), spQtde.getValue());
            salvarComandas();
            evtMostrarTodasAsComandas(event);
            atualizarComponentesEstaticos();
            loadCBProdutos();
            btDeletar.setDisable(true);
        }
    }
    
    @FXML
    private void evtMostrarTodasAsComandas(ActionEvent event) {
        if (checkTodasComandas.isSelected()) {
            loadTable("mes_cod = " + lbNMesa.getText());
        }
        else {
            loadTable("com_num = " + cbComanda.getValue().getCom_num() + " AND mes_cod = " + cbComanda.getValue().getMes_cod());
        }
        btDeletar.setDisable(true);
    }
    
    @FXML
    private void evtTrocarComanda(ActionEvent event) {
        Platform.runLater(() -> {
            evtMostrarTodasAsComandas(event);
            atualizarComponentesEstaticos();
        });
    }
}
