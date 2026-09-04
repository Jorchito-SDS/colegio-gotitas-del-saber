package main.java.edu.ingsoft.colegio.gotitas;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;
import java.sql.SQLException;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.showLoginView();
        primaryStage.show();

    }

    public static void main(String[] args) throws Exception {

        try {
            DataBaseConnection.getConnectionDataBase();
            System.out.println("Conectado!");

        } catch (SQLException e) {
            System.out.println("Error en la conexión"+ e.getMessage());
        }
        launch();

    }

}
