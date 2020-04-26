package bellacanelafx;

import bellacanela.db.dal.DALCliente;
import bellacanela.db.dal.DALFuncionario;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.Cliente;
import bellacanelafx.db.entidades.Funcionario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CadFuncionarioController implements Initializable {
    @FXML
    private SplitPane pane;
    @FXML
    private TableView<Funcionario> tbFuncionarios;
    @FXML
    private TableColumn<Funcionario, Integer> colCod;
    @FXML
    private TableColumn<Funcionario, String> colNome;
    @FXML
    private TableColumn<Funcionario, Integer> colIdade;
    @FXML
    private TableColumn<Funcionario, String> colTelefone;
    @FXML
    private TableColumn<Funcionario, Double> colSalario;
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
    private VBox pnSearch;
    @FXML
    private JFXTextField tfCod;
    @FXML
    private JFXTextField tfNome;
    @FXML
    private JFXTextField tfIdade;
    @FXML
    private JFXTextField tfTelefone;
    @FXML
    private JFXTextField tfSalario;
    @FXML
    private JFXTextField tfSearch;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.colCod.setCellValueFactory(new PropertyValueFactory("cod"));
        this.colNome.setCellValueFactory(new PropertyValueFactory("nome"));
        this.colIdade.setCellValueFactory(new PropertyValueFactory("idade"));
        this.colTelefone.setCellValueFactory(new PropertyValueFactory("telefone"));
        this.colSalario.setCellValueFactory(new PropertyValueFactory("salario"));
        
        this.fadeout();
        this.loadMasks();
        this.original();
    }   
    
    private void fadeout() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), this.pane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play(); 
    }
    
    private void loadTable(String filtro) {
        DALFuncionario dal = new DALFuncionario();
        ArrayList<Funcionario> funcionarios = dal.getFuncionarios(filtro);
        ObservableList<Funcionario> modelo = FXCollections.observableArrayList(funcionarios);
        this.tbFuncionarios.setItems(modelo);
        this.tbFuncionarios.refresh();
    }
    
    private void loadMasks() {
        MaskFieldUtil.foneField(this.tfTelefone);
        MaskFieldUtil.monetaryField(this.tfSalario);
    }
    
    private void edition() {        
        this.pnSearch.setDisable(true);
        this.pnSearch.setOpacity(0.5);
        this.apDados.setDisable(false);
        this.apDados.setOpacity(1);
        
        this.btSalvar.setDisable(false);
        this.btApagar.setDisable(true);
        this.btAlterar.setDisable(true);
        this.tfSearch.clear();
        this.tfNome.requestFocus();
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
    }

    @FXML
    private void clkNovo(ActionEvent event) {
        this.edition();
    }

    @FXML
    private void clkAlterar(ActionEvent event) {
        if(this.tbFuncionarios.getSelectionModel().getSelectedItem() != null){
            Funcionario f = (Funcionario)this.tbFuncionarios.getSelectionModel().getSelectedItem();
            
            this.tfCod.setText(f.getCod()+"");
            this.tfNome.setText(f.getNome());
            this.tfIdade.setText(f.getIdade()+"");
            this.tfTelefone.setText(f.getTelefone());
            this.tfSalario.setText(f.getSalario()+"");
            
            this.edition();
        }
    }

    @FXML
    private void clkApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        
        a.setContentText("Realmente deseja excluir o funcionário " + this.tfNome.getText() + "?");
        if(a.showAndWait().get() == ButtonType.OK){
            DALFuncionario dal = new DALFuncionario();
            Funcionario f = this.tbFuncionarios.getSelectionModel().getSelectedItem();
            
            if(dal.delete(f)){
                a.setContentText("Excluido com sucesso!");
                a.setAlertType(Alert.AlertType.INFORMATION);
                
                this.original();
                this.loadTable("");
            }
            else{
                a.setContentText("Erro ao excluir.");
                a.setAlertType(Alert.AlertType.ERROR);
            }
            
            a.showAndWait();
        }
    }

    @FXML
    private void clkSalvar(ActionEvent event) {
        int COD;
        
        try{
            COD = Integer.parseInt(this.tfCod.getText());
        }
        catch(NumberFormatException e){
            COD = 0;
        }
        
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        DALFuncionario dal = new DALFuncionario();
        System.out.println(this.tfSalario.getText().replace('.', '\0').replace(',', '.'));
        Funcionario f = new Funcionario(COD,
                                        this.tfNome.getText(),
                                        Integer.parseInt(this.tfIdade.getText()),
                                        this.tfTelefone.getText(),
                                        Double.parseDouble(this.tfSalario.getText().replace('.', '\0').replace(',', '.')));
        
        if(f.getCod() == 0){
            if(dal.insert(f)){
                a.setContentText("Funcionario gravado com sucesso!");
                
                this.original();
                this.loadTable("");
            }
            else{
                a.setContentText("Problemas ao gravar Funcionario!");
            }
        }
        else{
            if(dal.update(f)){
                a.setContentText("Funcionario atualizado com sucesso!");
                
                this.original();
                this.loadTable("");
            }
            else{
                a.setContentText("Problemas ao atualizar Funcionario!");
            }
        }
        
        a.showAndWait();
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        if (!this.apDados.isDisabled())
            this.original();
        else 
            HomeController.spnprincipal.setCenter(null);
    }

    @FXML
    private void clkTable(MouseEvent event) {
        if(this.tbFuncionarios.getSelectionModel().getSelectedItem() != null){
            this.btAlterar.setDisable(false);
            this.btApagar.setDisable(false);
        }
    }

    @FXML
    private void evtSearch(KeyEvent event) {
        String filtro = this.tfSearch.getText().isEmpty() ? "" : "UPPER(fun_nome) LIKE '%#1%'";
        filtro = filtro.replaceAll("#1", this.tfSearch.getText().toUpperCase());
        loadTable(filtro);
    }
}
