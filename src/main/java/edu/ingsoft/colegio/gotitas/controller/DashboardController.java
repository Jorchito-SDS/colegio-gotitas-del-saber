package main.java.edu.ingsoft.colegio.gotitas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.edu.ingsoft.colegio.gotitas.model.Estudiante;
import main.java.edu.ingsoft.colegio.gotitas.repository.EstudianteRepository;
import main.java.edu.ingsoft.colegio.gotitas.service.DashBoardService;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;

public class DashboardController implements Initializable {

    private DashBoardService dashboardService;
    private SceneManager sceneManager;
    private EstudianteRepository estudianteRepository;

    @FXML private TableView<Estudiante> tvEstudiante;
    @FXML private TableColumn<Estudiante, String> tvColumnIdEstudiante;
    @FXML private TableColumn<Estudiante, String> tvColumnNombreEstudiante;
    @FXML private TableColumn<Estudiante, String> tvColumnApellidoEstudiante;
    @FXML private TableColumn<Estudiante, String> tvColumnCorreo;
    @FXML private TableColumn<Estudiante, String> tvColumnSeccion;
    @FXML private TableColumn<Estudiante, String> tvColumnCurso;
    @FXML private TableColumn<Estudiante, String> tvColumnNombreDocente;
    @FXML private TableColumn<Estudiante, String> tvColumnApellidoDocente;

    @FXML private TextField txtIdEstudiante;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCorreo;

    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    public DashboardController() {
        this.estudianteRepository = new EstudianteRepository();
    }

    public DashboardController(DashBoardService dashboardService, SceneManager sceneManager) {
        this();
        this.dashboardService = dashboardService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            configurarColumnas();
            handleLoadTableStuden();

            tvEstudiante.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    txtIdEstudiante.setText(newSelection.getIdEstudiante());
                    txtNombre.setText(newSelection.getNombre());
                    txtApellido.setText(newSelection.getApellido());
                    txtCorreo.setText(newSelection.getCorreoElectronico());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarColumnas() {
        tvColumnIdEstudiante.setCellValueFactory(new PropertyValueFactory<>("idEstudiante"));
        tvColumnNombreEstudiante.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tvColumnApellidoEstudiante.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        tvColumnCorreo.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));
        tvColumnSeccion.setCellValueFactory(new PropertyValueFactory<>("nombreSeccion"));
        tvColumnCurso.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        tvColumnNombreDocente.setCellValueFactory(new PropertyValueFactory<>("nombreDocente"));
        tvColumnApellidoDocente.setCellValueFactory(new PropertyValueFactory<>("apellidoDocente"));
    }

    private void handleLoadTableStuden() {
        try {
            tvEstudiante.getItems().clear();
            if (dashboardService != null) {
                tvEstudiante.setItems(dashboardService.listStudent());
            } else {
                tvEstudiante.setItems(estudianteRepository.findAll());
            }
            tvEstudiante.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        try {
            String id = txtIdEstudiante.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String correo = txtCorreo.getText();

            if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty()) {
                mostrarAlerta("Advertencia", "Completa todos los campos obligatorios.", Alert.AlertType.WARNING);
                return;
            }

            boolean guardado = estudianteRepository.save(id, "1", nombre, apellido, correo, "2005-01-01");

            if (guardado) {
                mostrarAlerta("Éxito", "Estudiante guardado en la base de datos.", Alert.AlertType.INFORMATION);
                handleLoadTableStuden();
                handleLimpiar(null);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al guardar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleActualizar(ActionEvent event) {
        try {
            String id = txtIdEstudiante.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String correo = txtCorreo.getText();

            if (id == null || id.trim().isEmpty()) {
                mostrarAlerta("Advertencia", "Selecciona un estudiante de la tabla.", Alert.AlertType.WARNING);
                return;
            }

            boolean actualizado = estudianteRepository.update(id, "1", nombre, apellido, correo, "2005-01-01");

            if (actualizado) {
                mostrarAlerta("Éxito", "Estudiante actualizado.", Alert.AlertType.INFORMATION);
                handleLoadTableStuden();
                handleLimpiar(null);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al actualizar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleEliminar(ActionEvent event) {
        try {
            String id = txtIdEstudiante.getText();

            if (id == null || id.trim().isEmpty()) {
                mostrarAlerta("Advertencia", "Selecciona el estudiante que deseas eliminar.", Alert.AlertType.WARNING);
                return;
            }

            boolean eliminado = estudianteRepository.delete(id);

            if (eliminado) {
                mostrarAlerta("Éxito", "Estudiante eliminado.", Alert.AlertType.INFORMATION);
                handleLoadTableStuden();
                handleLimpiar(null);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo eliminar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleLimpiar(ActionEvent event) {
        if (txtIdEstudiante != null) txtIdEstudiante.clear();
        if (txtNombre != null) txtNombre.clear();
        if (txtApellido != null) txtApellido.clear();
        if (txtCorreo != null) txtCorreo.clear();
        tvEstudiante.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}