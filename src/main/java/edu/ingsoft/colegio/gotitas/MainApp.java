package main.java.edu.ingsoft.colegio.gotitas;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.showLoginView(); // Inicia siempre en el Login
    }

    public static void main(String[] args) {
        launch(args);
    }
}