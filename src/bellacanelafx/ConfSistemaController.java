/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellacanelafx;

import bellacanela.db.dal.DALConfSistema;
import bellacanela.util.MaskFieldUtil;
import bellacanelafx.db.entidades.ConfSistema;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javax.imageio.ImageIO;

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
    static private int flag = 0;
    
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
        
        if((email.contains("@hotmail") || email.contains("@gmail")) && email.contains(".com"))
            res = true;
        
        return res;
    }
    
    private boolean verificarCpf (String cpf) {
        
        boolean res = false;
        
        if(cpf.length() == 14)
            res = true;
        
        return res;
    }
    
    private boolean verificarFone (String fone) {
        
        boolean res = false;
        
        if(fone.length() >= 13)
            res = true;
        
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
            cpcor1.setValue(Color.valueOf(cs.getCor1()));
            cpcor2.setValue(Color.valueOf(cs.getCor2()));
            
            InputStream icone = null;
            try {
                icone = dal.getIcone(cs);
                BufferedImage imgConvertida;
                try {
                    imgConvertida = ImageIO.read(icone);
                    imgIcone.setImage(SwingFXUtils.toFXImage(imgConvertida, null));
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
    private void clkBtSalvar(ActionEvent event) {
    }

    @FXML
    private void dgtNome(KeyEvent event) {
    }

    @FXML
    private void dgtEndereco(KeyEvent event) {
    }

    @FXML
    private void dgtCidade(KeyEvent event) {
    }

    @FXML
    private void dgtUF(KeyEvent event) {
    }

    @FXML
    private void dgtRazao(KeyEvent event) {
    }

    @FXML
    private void dgtEmail(KeyEvent event) {
    }

    @FXML
    private void mcEscolherIcone(MouseEvent event) {
    }
    
}
