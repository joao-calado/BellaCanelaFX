package bellacanelafx;

import bellacanela.db.dal.DALConfSistema;
import static bellacanelafx.HomeController.spnprincipal;
import static bellacanelafx.HomeController.toolbarPane;
import bellacanelafx.db.entidades.ConfSistema;
import bellacanelafx.db.util.Banco;
import java.io.IOException;
import javafx.application.Application;
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
import javax.swing.JOptionPane;

public class BellaCanela extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(root);
        
        DALConfSistema dal = new DALConfSistema();
        ConfSistema cs = dal.get();
        
        Image imagem;
        imagem = new Image("imagens/icone_hamburguer.png");
        stage.getIcons().add(imagem);
        
        
        
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        
        // recuperando cores salvas no banco se setando na tela com sucesso!
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
