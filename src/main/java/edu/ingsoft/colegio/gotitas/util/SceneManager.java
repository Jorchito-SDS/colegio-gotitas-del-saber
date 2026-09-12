package main.java.edu.ingsoft.colegio.gotitas.util;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.java.edu.ingsoft.colegio.gotitas.controller.DashboardController;
import main.java.edu.ingsoft.colegio.gotitas.controller.DocenteController;
import main.java.edu.ingsoft.colegio.gotitas.controller.LoginController;
import main.java.edu.ingsoft.colegio.gotitas.controller.MainLayoutController;
import main.java.edu.ingsoft.colegio.gotitas.controller.MatriculaController;
import main.java.edu.ingsoft.colegio.gotitas.controller.RegisterController;
import main.java.edu.ingsoft.colegio.gotitas.repository.AuthRepository;
import main.java.edu.ingsoft.colegio.gotitas.repository.DocenteRepository;
import main.java.edu.ingsoft.colegio.gotitas.repository.EstudianteRepository;
import main.java.edu.ingsoft.colegio.gotitas.service.AuthService;
import main.java.edu.ingsoft.colegio.gotitas.service.DashBoardService;
import main.java.edu.ingsoft.colegio.gotitas.service.DocenteService;

public class SceneManager {

    private final Stage primaryStage;
    private final AuthService authService;
    private final DocenteService docenteService;
    private final DashBoardService dashBoardService;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Inyección manual de repositorios a sus respectivos servicios
        this.authService = new AuthService(new AuthRepository());
        this.docenteService = new DocenteService(new DocenteRepository());
        this.dashBoardService = new DashBoardService(new EstudianteRepository());
    }

    private FXMLLoader createFXMLLoader(String fxmlPath) {
        URL url = getClass().getResource(fxmlPath);

        // 1. Intento por ClassLoader de la aplicación
        if (url == null) {
            String cleanPath = fxmlPath.startsWith("/") ? fxmlPath.substring(1) : fxmlPath;
            url = getClass().getClassLoader().getResource(cleanPath);
        }

        // 2. Intento agregando 'resources' a la ruta del ClassLoader
        if (url == null) {
            String cleanPath = fxmlPath.startsWith("/") ? fxmlPath.substring(1) : fxmlPath;
            url = getClass().getClassLoader().getResource("resources/" + cleanPath);
        }

        // 3. Fallback directo a Sistema de Archivos (Para proyectos Ant)
        if (url == null) {
            try {
                java.io.File file = new java.io.File("resources" + fxmlPath);
                if (!file.exists()) {
                    file = new java.io.File("src/resources" + fxmlPath);
                }
                if (!file.exists()) {
                    file = new java.io.File("src/main/resources" + fxmlPath);
                }

                if (file.exists()) {
                    url = file.toURI().toURL();
                }
            } catch (Exception e) {
                // Continuar hacia la excepción
            }
        }

        if (url == null) {
            throw new IllegalArgumentException("No se encontró el archivo FXML en la ruta de clases ni en disco: " + fxmlPath);
        }

        FXMLLoader loader = new FXMLLoader(url);
        loader.setControllerFactory(clazz -> {
            if (clazz == LoginController.class) {
                return new LoginController(authService, this);
            } else if (clazz == RegisterController.class) {
                return new RegisterController(authService, this);
            } else if (clazz == MainLayoutController.class) {
                return new MainLayoutController(this);
            } else if (clazz == DashboardController.class) {
                return new DashboardController(dashBoardService, this);
            } else if (clazz == DocenteController.class) {
                return new DocenteController(docenteService, this);
            } else if (clazz == MatriculaController.class) {
                return new MatriculaController(this);
            }

            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al instanciar controlador: " + clazz.getName(), e);
            }
        });

        return loader;
    }

 
    private void setSceneWithCSS(Parent root, String title) {
        Scene scene = new Scene(root);
        try {
            
            URL cssUrl = getClass().getResource("/CSS/global-styles.css");
            if (cssUrl == null) {
                cssUrl = getClass().getResource("/css/global-styles.css"); 
            }
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("Advertencia: No se pudo localizar global-styles.css en /CSS/");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void showLoginView() throws IOException {
        FXMLLoader loader = createFXMLLoader("/view/login-view.fxml");
        Parent root = loader.load();
        setSceneWithCSS(root, "Iniciar Sesión - Colegio Gotitas del Saber");
    }

    public void showRegisterView() throws IOException {
        FXMLLoader loader = createFXMLLoader("/view/register-view.fxml");
        Parent root = loader.load();
        setSceneWithCSS(root, "Registro de Usuario");
    }

    public void showMainLayout() throws IOException {
        FXMLLoader loader = createFXMLLoader("/view/MainLayout.fxml");
        Parent root = loader.load();
        setSceneWithCSS(root, "Sistema Principal - Colegio Gotitas del Saber");
    }

    public void showMatriculaView() throws IOException {
        FXMLLoader loader = createFXMLLoader("/view/matricula-view.fxml");
        Parent root = loader.load();
        setSceneWithCSS(root, "Matrículas - Colegio Gotitas del Saber");
    }

    // Alias de compatibilidad
    public void showDashBoardView() throws IOException {
        showMainLayout();
    }

    public Parent loadView(String fxmlPath) throws IOException {
        FXMLLoader loader = createFXMLLoader(fxmlPath);
        return loader.load();
    }

    public void showInfoAlert(String title, String header, String content, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initOwner(primaryStage);
        alert.showAndWait();
    }
}