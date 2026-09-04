package main.java.edu.ingsoft.colegio.gotitas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import main.java.edu.ingsoft.colegio.gotitas.service.AuthService;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.LoginRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.LoginResponse;

public class LoginController implements Initializable {

    private final AuthService authService;
    private final SceneManager sceneManager;

    @FXML
    private TextField txtFieldEmail;

    @FXML
    private PasswordField txtFieldPass;

    public LoginController(AuthService authService, SceneManager sceneManager) {
        this.authService = authService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void handleTestDataBaseConnection() throws Exception {
        try {
            DataBaseConnection.getConnectionDataBase();
            System.out.println("Conectado");

        } catch (Exception e) {
            System.out.println("error al conectar: " + e.getMessage());
        }
    }

    public void handleLogin() throws Exception {
        if (txtFieldEmail.getText().isEmpty() || txtFieldPass.getText().isEmpty()) {
            sceneManager.showInfoAlert("Te faltan campos", "Revisar información", "Uno o más campos están vacíos... ¯|_(ツ)_/¯", AlertType.WARNING);
            return;
        }

        try {
            LoginResponse responseService = authService.login(new LoginRequest(txtFieldEmail.getText(), txtFieldPass.getText()));
            LoginResponse userLogged = new LoginResponse(responseService.getNombre(), responseService.getApellido());
            sceneManager.showDashBoardView();
        } catch (RuntimeException e) {
            sceneManager.showInfoAlert("Datos incorrectos", "Revisa tu información", "Intenta de nuevo", AlertType.INFORMATION);
            System.out.println(" " + e.getMessage());
        }
    }

    public void handleGoToRegister() throws Exception {
        sceneManager.showRegisterView();
    }
}
