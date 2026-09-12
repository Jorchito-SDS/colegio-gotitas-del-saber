package main.java.edu.ingsoft.colegio.gotitas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.edu.ingsoft.colegio.gotitas.model.Matricula;
import main.java.edu.ingsoft.colegio.gotitas.repository.MatriculaRepository;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;

public class MatriculaController implements Initializable {

    private final MatriculaRepository matriculaRepository = new MatriculaRepository();
    private final SceneManager sceneManager;

    @FXML private TableView<Matricula> tblMatriculas;
    @FXML private TableColumn<Matricula, String> colIdMatricula;
    @FXML private TableColumn<Matricula, String> colEstudiante;
    @FXML private TableColumn<Matricula, String> colSeccion;
    @FXML private Button btnActualizar;

    @FXML private TextField txtIdMatricula;
    @FXML private TextField txtIdEstudiante;
    @FXML private TextField txtIdSeccion;

    public MatriculaController() {
        this.sceneManager = null;
    }

    public MatriculaController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colIdMatricula.setCellValueFactory(new PropertyValueFactory<>("id_matricula"));
        colEstudiante.setCellValueFactory(new PropertyValueFactory<>("nombreEstudiante"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<>("nombreSeccion"));

        cargarDatos();
    }

    public void cargarDatos() {
        try {
            System.out.println("Cargando vista de matrículas...");
            var lista = matriculaRepository.findAll();
            tblMatriculas.setItems(FXCollections.observableArrayList(lista));
            System.out.println("Registros obtenidos: " + lista.size());
        } catch (Exception e) {
            System.err.println("Error al cargar matrículas en la vista: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleActualizar() {
        cargarDatos();
    }

    @FXML
    private void handleGuardarMatricula() {
        try {
            if (txtIdMatricula.getText().isEmpty() || txtIdEstudiante.getText().isEmpty() || txtIdSeccion.getText().isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor llena todos los campos.");
                return;
            }

            String idMatricula = txtIdMatricula.getText();
            String idEstudiante = txtIdEstudiante.getText();
            String idSeccion = txtIdSeccion.getText();

            Matricula nuevaMatricula = new Matricula(idMatricula, idSeccion, idEstudiante);
            matriculaRepository.save(nuevaMatricula);

            cargarDatos();
            
            txtIdMatricula.clear();
            txtIdEstudiante.clear();
            txtIdSeccion.clear();
            
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Matrícula guardada exitosamente.");
            
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Base de Datos", 
                "No se pudo guardar. Asegúrate de que el ID del estudiante y el ID de la sección existan realmente.");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}