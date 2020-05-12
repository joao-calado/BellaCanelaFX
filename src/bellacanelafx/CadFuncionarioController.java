package bellacanelafx;

import bellacanela.db.dal.DALFuncionario;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.Funcionario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableColumn<Funcionario, LocalDate> colDataNascimento;
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
    private JFXTextField tfTelefone;
    @FXML
    private JFXTextField tfSalario;
    @FXML
    private JFXTextField tfSearch;
    @FXML
    private JFXDatePicker dpDataNascimento;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.colCod.setCellValueFactory(new PropertyValueFactory("cod"));
        this.colNome.setCellValueFactory(new PropertyValueFactory("nome"));
        this.colDataNascimento.setCellValueFactory(new PropertyValueFactory("dataNascimento"));
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
        MaskFieldUtil.maxField(this.tfNome, 50);
        MaskFieldUtil.onlyAlfa(this.tfNome);
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
    
    private void snackbar(String message, String cor){
        JFXSnackbar sb = new JFXSnackbar(this.apDados);
        Label mens = new Label(message);
        mens.setStyle("-fx-text-fill: "+cor+";"
                    + "-fx-font-size: 14px;"
                    + "-fx-padding: 10px;");
        sb.enqueue(new JFXSnackbar.SnackbarEvent(mens));
    }
    
    private String convertStr(String s){
        String dst = "";
        
        for(char c : s.toCharArray())
            if(c != '.')
                dst += c == ',' ? '.' : c;
        
        return dst;
    }

    @FXML
    private void clkNovo(ActionEvent event) {
        this.edition();
    }

    @FXML
    private void clkAlterar(ActionEvent event) {
        if(this.tbFuncionarios.getSelectionModel().getSelectedItem() != null){
            Funcionario f = (Funcionario)this.tbFuncionarios.getSelectionModel().getSelectedItem();
            
            String strSal = f.getSalario()+"";
            int i = (strSal).indexOf(".");
            if(strSal.length() - i == 2)
                strSal += "0";
            
            this.tfCod.setText(f.getCod()+"");
            this.tfNome.setText(f.getNome());
            this.dpDataNascimento.setValue(f.getDataNascimento());
            this.tfTelefone.setText(f.getTelefone());
            this.tfSalario.setText(strSal);
            
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
                this.snackbar("Excluido com sucesso!", "green");
                
                this.original();
                this.loadTable("");
            }
            else{
                this.snackbar("Erro ao excluir.", "red");
            }
        }
    }

    @FXML
    private void clkSalvar(ActionEvent event) {
        boolean ans = true;

        if(this.dpDataNascimento.getValue() == null || this.tfNome.getText().isEmpty() ||
           this.tfSalario.getText().isEmpty() || this.tfTelefone.getText().isEmpty()){
            ans = false;
            this.snackbar("Alguns campos ainda precisam ser preenchidos", "red");
        }
        
        if(ans){
            int COD;
            
            try{
                COD = Integer.parseInt(this.tfCod.getText());
            }
            catch(NumberFormatException e){
                COD = 0;
            }

            DALFuncionario dal = new DALFuncionario();
            Funcionario f = new Funcionario(COD,
                                            this.dpDataNascimento.getValue(),                        
                                            this.tfNome.getText(),
                                            this.tfTelefone.getText(),
                                            Double.parseDouble(this.convertStr(this.tfSalario.getText())));
            
            if(f.getCod() == 0){
                if(dal.insert(f)){
                    this.snackbar("Funcionario gravado com sucesso!", "green");

                    this.original();
                    this.loadTable("");
                }
                else{
                    this.snackbar("Problemas ao gravar Funcionario!", "red");
                }
            }
            else{
                if(dal.update(f)){
                    this.snackbar("Funcionario atualizado com sucesso!", "green");

                    this.original();
                    this.loadTable("");
                }
                else{
                    this.snackbar("Problemas ao atualizar Funcionario!", "red");
                }
            }
        }
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        if (!this.apDados.isDisabled())
            this.original();
        else {
            HomeController.spnprincipal.setCenter(null);
            BellaCanela.sStage.setTitle("Bella Canela");
        }
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
