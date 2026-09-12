package main.java.edu.ingsoft.colegio.gotitas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    public MatriculaController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colIdMatricula.setCellValueFactory(new PropertyValueFactory<>("idMatricula"));
        colEstudiante.setCellValueFactory(new PropertyValueFactory<>("nombreEstudiante"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<>("nombreSeccion"));

        cargarDatos();
    }

    private void cargarDatos() {
        try {
            tblMatriculas.setItems(FXCollections.observableArrayList(matriculaRepository.findAll()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}