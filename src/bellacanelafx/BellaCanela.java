package bellacanelafx;

import bellacanela.db.dal.DALConfSistema;
import static bellacanelafx.HomeController.spnprincipal;
import static bellacanelafx.HomeController.toolbarPane;
import bellacanelafx.db.entidades.ConfSistema;
import bellacanelafx.db.util.Banco;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class BellaCanela extends Application {
    public static Stage sStage;
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(root);
        
        DALConfSistema dal = new DALConfSistema();
        ConfSistema cs = dal.get();
        
        if(cs == null) {
            
            Image imagem;
            imagem = new Image("imagens/icone_hamburguer.png");
            stage.getIcons().add(imagem);
        }
        else {
            
            InputStream icone = null;
            try {
                icone = dal.getIcone(cs);
                BufferedImage imgConvertida;
                try {
                    
                    if (icone != null) {
                        
                        imgConvertida = ImageIO.read(icone);
                        stage.getIcons().add(SwingFXUtils.toFXImage(imgConvertida, null));
                    }
                }
                catch(IOException ioEx) {}
            } 
            catch (SQLException ex) {
                Logger.getLogger(ConfSistemaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        sStage = stage;
        stage.setScene(scene);
        stage.setTitle("Bella Canela");
        stage.setMaximized(true);
        stage.show();
        
        // recuperando cores salvas no banco se setando na tela com sucesso! (tem que ser depois do stage.show();)
        if(cs != null) {
            
            toolbarPane.setBackground(new Background(new BackgroundFill(Paint.valueOf(cs.getCor1()), CornerRadii.EMPTY, Insets.EMPTY)));
            spnprincipal.setBackground(new Background(new BackgroundFill(Paint.valueOf(cs.getCor2()), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    public static void main(String[] args) {
        if(Banco.conectar())
            launch(args);
        else
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco");
    }
    
}
