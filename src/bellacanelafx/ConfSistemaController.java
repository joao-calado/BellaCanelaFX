/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx;

import bellacanela.db.dal.DALConfSistema;
import bellacanela.util.ConsultaAPI;
import bellacanela.util.MaskFieldUtil;
import static bellacanelafx.HomeController.spnprincipal;
import static bellacanelafx.HomeController.toolbarPane;
import bellacanelafx.db.entidades.ConfSistema;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTextField;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author joao
 */
public class ConfSistemaController implements Initializable {

    @FXML
    private SplitPane painel;
    @FXML
    private JFXButton btsalvar;
    @FXML
    private JFXTextField txcod;
    @FXML
    private JFXTextField txnome;
    @FXML
    private JFXTextField txcep;
    @FXML
    private JFXTextField txendereco;
    @FXML
    private JFXTextField txcidade;
    @FXML
    private JFXTextField txuf;
    @FXML
    private JFXTextField txcnpj;
    @FXML
    private JFXTextField txrazao;
    @FXML
    private JFXTextField txfone;
    @FXML
    private JFXTextField txemail;
    @FXML
    private ImageView imgIcone;
    @FXML
    private JFXColorPicker cpcor1;
    @FXML
    private JFXColorPicker cpcor2;
    
    

    
    private Image img;
    static public File arq = null;
    static private int flagIcone = 0;
    @FXML
    private JFXButton btcancelar;
    
    /**
     * Initializes the controller class.
     */
    
    private void fadeout(){
        FadeTransition ft = new FadeTransition(Duration.millis(1000), painel);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play(); 
    }
    
    private boolean verificarEmail(String email) {
        
        boolean res = false;
        
        if((email.contains("@hotmail") || email.contains("@gmail")) || email.contains("@bellacanela") && email.contains(".com"))
            res = true;
        else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText(null);
            a.setTitle("Atenção:");
            a.setContentText("Informe um email em formato válido!");
            txemail.clear();
            txemail.requestFocus();
            a.showAndWait();
        }
       
        
        return res;
    }
    
    private boolean verificarFone (String fone) {
        
        boolean res = false;
        
        if(fone.length() >= 13)
            res = true;
        else {
            
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText(null);
            a.setTitle("Atenção:");
            a.setContentText("Informe um fone em formato válido!");
            txfone.clear();
            txfone.requestFocus();
            a.showAndWait();
        }
        
        return res;
    }
    
    private void carregarCampos() {
        
        DALConfSistema dal = new DALConfSistema();
        ConfSistema cs = dal.get();
        
        if(cs != null) {
            
            txcod.setText(""+cs.getCod());
            txnome.setText(cs.getNome());
            txcep.setText(cs.getCep());
            txendereco.setText(cs.getEndereco());
            txcidade.setText(cs.getCidade());
            txuf.setText(cs.getUf());
            txcnpj.setText(cs.getCnpj());
            txrazao.setText(cs.getRazao());
            txfone.setText(cs.getFone());
            txemail.setText(cs.getEmail());
            
            try {
                cpcor1.setValue(Color.valueOf(cs.getCor1()));
                cpcor2.setValue(Color.valueOf(cs.getCor2()));
            }
            catch(Exception ex) {}
            
            
            InputStream icone = null;
            try {
                icone = dal.getIcone(cs);
                BufferedImage imgConvertida;
                try {
                    
                    if (icone != null) {
                        
                        imgConvertida = ImageIO.read(icone);
                        imgIcone.setImage(SwingFXUtils.toFXImage(imgConvertida, null));
                    }
                    
                }
                catch(IOException ioEx) {}
            } 
            catch (SQLException ex) {
                Logger.getLogger(ConfSistemaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        fadeout();
        MaskFieldUtil.cepField(txcep);
        MaskFieldUtil.cnpjField(txcnpj);
        MaskFieldUtil.foneField(txfone);
        carregarCampos();
    }    

    @FXML
    private void clkBtSalvar(ActionEvent event) throws FileNotFoundException {
        
        FileInputStream imagem;
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        DALConfSistema dal = new DALConfSistema();
        ConfSistema cs = dal.get();
        
        if(verificarEmail(txemail.getText()) && verificarFone(txfone.getText())) {
            
            if(cs == null) { // significa que não tem parametros salvos, portanto é o primeiro acesso

                cs = new ConfSistema(0, txnome.getText(), txcep.getText(), txendereco.getText(), txcidade.getText(), txuf.getText(), txcnpj.getText(), txrazao.getText(), txfone.getText(), txemail.getText(), cpcor1.getValue().toString(), cpcor2.getValue().toString());

                if(dal.gravar(cs)) {

                    if(arq == null) { // caso nenhum icone tenha sido selecionada

                        try {

                            File n = new File("src/imagens/icone_hamburguer.png");
                            arq = n;
                            imagem = new FileInputStream(arq);
                            
                            if(dal.gravarIcone(cs, imagem)) {
                                
                                a.setTitle("Informação:");
                                a.setContentText("Salvo com sucesso (SEM ICONE)!");
                            }
                        }
                        catch(SQLException ex) {}
                    }
                    else { // caso algum icone tenha sido selecionado

                        try {

                            imagem = new FileInputStream(arq);
                            if(dal.gravarIcone(cs, imagem)) {
                                
                                a.setTitle("Informação:");
                                a.setContentText("Salvo com sucesso (COM ICONE)!\nReinicie o programa para ter o ícone atualizado.");
                            }
                        }
                        catch(SQLException ex) {}
                    }
                    toolbarPane.setBackground(new Background(new BackgroundFill(Paint.valueOf(cpcor1.getValue().toString()), CornerRadii.EMPTY, Insets.EMPTY)));
                    spnprincipal.setBackground(new Background(new BackgroundFill(Paint.valueOf(cpcor2.getValue().toString()), CornerRadii.EMPTY, Insets.EMPTY)));
                }
                else {
                    
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setTitle("Informação:");
                    a.setContentText("Problemas ao salvar!");
                }
            }
            else { // significa que não é o primeiro acesso

                cs = new ConfSistema(Integer.parseInt(txcod.getText()), txnome.getText(), txcep.getText(), txendereco.getText(), txcidade.getText(), txuf.getText(), txcnpj.getText(), txrazao.getText(), txfone.getText(), txemail.getText(), cpcor1.getValue().toString(), cpcor2.getValue().toString());
                if(dal.alterar(cs)) {

                    if(arq != null) { // caso tenha selecionado icone

                        try {

                            imagem = new FileInputStream(arq);
                            if(dal.gravarIcone(cs, imagem)) {
                                
                                a.setTitle("Informação:");
                                a.setContentText("Alterado com sucesso (COM ICONE)!\nReinicie o programa para ter o ícone atualizado.");
                            }
                        }
                        catch(SQLException ex) {}
                    }
                    else {
                        
                        a.setTitle("Informação:");
                        a.setContentText("Alterado com sucesso!");
                    }
                    toolbarPane.setBackground(new Background(new BackgroundFill(Paint.valueOf(cs.getCor1()), CornerRadii.EMPTY, Insets.EMPTY)));
                    spnprincipal.setBackground(new Background(new BackgroundFill(Paint.valueOf(cs.getCor2()), CornerRadii.EMPTY, Insets.EMPTY)));
                }
                else {
                    
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setTitle("Informação:");
                    a.setContentText("Problemas ao alterar!");
                }
                
            }
        }    
        
        a.showAndWait();
        arq = null;
        flagIcone = 0;
        HomeController.spnprincipal.setCenter(null);
    }

    @FXML
    private void dgtNome(KeyEvent event) {
        
        if(txnome.getText().length() > 28) {
            event.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }

    @FXML
    private void dgtEndereco(KeyEvent event) {
        
        if(txendereco.getText().length() > 48) {
            event.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }

    @FXML
    private void dgtCidade(KeyEvent event) {
        
        if(txcidade.getText().length() > 18) {
            event.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }

    @FXML
    private void dgtUF(KeyEvent event) {
        
        if(txuf.getText().length() > 1) {
            event.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }

    @FXML
    private void dgtRazao(KeyEvent event) {
        
        if(txrazao.getText().length() > 48) {
            event.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }

    @FXML
    private void dgtEmail(KeyEvent event) {
        
        if(txemail.getText().length() > 28) {
            event.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }

    @FXML
    private void mcEscolherIcone(MouseEvent event) {
        
        FileChooser fc=new FileChooser();
        fc.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Images","*.jpeg","*.png","*.jpg","*.png","*.gif"),
            new FileChooser.ExtensionFilter("All Files","*.*"));
        
        arq = fc.showOpenDialog(null);
        if (arq!=null) {

           img = new Image(arq.toURI().toString());
           imgIcone.setImage(img);
           flagIcone = 1;
        }
    }

    @FXML
    private void dgtCEP(KeyEvent event) {
        
        if(txcep.getText().length() == 8 )
        {
            Task task = new Task() {
                
                @Override
                protected Object call() throws Exception {
                    
                    String dados = ConsultaAPI.consultaCep(txcep.getText().replace("-", ""));
                    JSONObject jason = new JSONObject(dados);
                    
                    txendereco.setText(jason.getString("logradouro"));
                    txcidade.setText(jason.getString("localidade"));
                    txuf.setText(jason.getString("uf"));
                    
                    return null;
                }
            };
            new Thread(task).start();
        }
    }

    @FXML
    private void clkBtCancelar(ActionEvent event) {
        
        HomeController.spnprincipal.setCenter(null);
    }

    
}
