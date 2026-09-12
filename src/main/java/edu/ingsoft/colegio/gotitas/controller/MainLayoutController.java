package main.java.edu.ingsoft.colegio.gotitas.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;

public class MainLayoutController {

    @FXML
    private StackPane contentArea;

    private final SceneManager sceneManager;

    // Constructor inyectado desde SceneManager
    public MainLayoutController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    public void handleMostrarEstudiantes() {
        cargarVista("/view/dashboard-view.fxml");
    }

    @FXML
    public void handleMostrarDocentes() {
        cargarVista("/view/docente-view.fxml");
    }

    @FXML
    public void handleMostrarMatriculas() {
        System.out.println("Cargando vista de matrículas...");
        cargarVista("/view/matricula-view.fxml");
    }

    @FXML
    public void handleCerrarSesion() {
        try {
            sceneManager.showLoginView();
        } catch (Exception e) {
            System.err.println("Error al cerrar sesión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarVista(String fxmlPath) {
        try {
            // Se utiliza el SceneManager para cargar la vista respetando las rutas y la fábrica de controladores
            Parent view = sceneManager.loadView(fxmlPath);
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
        } catch (Exception e) {
            System.err.println("Error al cargar la subvista " + fxmlPath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}