package br.ufrn.imd.libralibre;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import atlantafx.base.theme.PrimerDark;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/libralibre/view/Principal.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        try {
            // O caminho começa com "/" indicando a raiz dos resources
            Image icon = new Image(getClass().getResourceAsStream("/br/ufrn/imd/libralibre/images/logo.png"));
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Erro ao carregar o ícone: " + e.getMessage());
        }
        
        stage.setTitle("Libralibre - Gestão de Biblioteca");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}