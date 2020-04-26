package bellacanelafx;

import bellacanelafx.db.util.Banco;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class BellaCanela extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(root);
        
        Image imagem;
        imagem = new Image("imagens/icone_hamburguer.png");
        stage.getIcons().add(imagem);
        
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        if(Banco.conectar())
            launch(args);
        else
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco");
    }
    
}
