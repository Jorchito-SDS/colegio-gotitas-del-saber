package main.java.edu.ingsoft.colegio.gotitas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.RegisterRequest;
import main.java.edu.ingsoft.colegio.gotitas.service.AuthService;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;

public class RegisterController implements Initializable {

    private final AuthService authService;
    private final SceneManager sceneManager;

    @FXML
    private TextField txtFieldNombre;

    @FXML
    private TextField txtFieldApellido;

    @FXML
    private TextField txtFieldEmail;

    @FXML
    private PasswordField txtFieldPass;

    @FXML
    private PasswordField txtFieldConfirmPass;

    public RegisterController(AuthService authService, SceneManager sceneManager) {
        this.authService = authService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void handleRegister() throws Exception {
        if (txtFieldNombre.getText().isEmpty() || txtFieldApellido.getText().isEmpty()
                || txtFieldEmail.getText().isEmpty() || txtFieldPass.getText().isEmpty()
                || txtFieldConfirmPass.getText().isEmpty()) {
            sceneManager.showInfoAlert("Te faltan campos", "Revisar información", "Uno o más campos están vacíos... ¯|_(ツ)_/¯", AlertType.WARNING);
            return;
        }

        if (!txtFieldPass.getText().equals(txtFieldConfirmPass.getText())) {
            sceneManager.showInfoAlert("Las contraseñas no coinciden", "Revisa tu información", "Verifica que ambas contraseñas sean iguales", AlertType.WARNING);
            return;
        }

        try {
            authService.register(new RegisterRequest(
                    txtFieldNombre.getText(),
                    txtFieldApellido.getText(),
                    txtFieldEmail.getText(),
                    txtFieldPass.getText()
            ));

            sceneManager.showInfoAlert("Registro exitoso", "Cuenta creada", "Ya puedes iniciar sesión con tu correo y contraseña", AlertType.INFORMATION);

            sceneManager.showLoginView();

        } catch (RuntimeException e) {
            sceneManager.showInfoAlert("No se pudo registrar", "Revisa tu información", e.getMessage(), AlertType.WARNING);
        }
    }

    public void handleGoToLogin() throws Exception {
        sceneManager.showLoginView();
    }
}
