package br.ufrn.imd.libralibre;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/libralibre/view/Principal.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        
        stage.setTitle("Libralibre - Gestão de Biblioteca");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}