/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.java.edu.ingsoft.colegio.gotitas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.java.edu.ingsoft.colegio.gotitas.model.Docente;
import main.java.edu.ingsoft.colegio.gotitas.service.DocenteService;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;

public class DocenteController implements Initializable {

    private DocenteService docenteService;
    private SceneManager sceneManager;
    private Docente docenteSeleccionado; // Guarda el docente que toques en la tabla
    

    // Campos de texto de tu interfaz gráfica FXML
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtCorreo;

    // Botones de acción
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnLimpiar;

    // Tabla de JavaFX y sus columnas para Docentes
    @FXML
    private TableView<Docente> tvDocente;
    @FXML
    private TableColumn<Docente, String> colId;
    @FXML
    private TableColumn<Docente, String> colNombre;
    @FXML
    private TableColumn<Docente, String> colApellido;
    @FXML
    private TableColumn<Docente, String> colCorreo;

    // Constructor vacío por defecto para FXMLLoader
    public DocenteController() {
    }

    // Constructor con inyección al estilo de tu grupo
    public DocenteController(DocenteService docenteService, SceneManager sceneManager) {
        this.docenteService = docenteService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if (docenteService != null) {
                handleLoadTableDocente();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1. CARGAR DATOS EN LA TABLA
  private void handleLoadTableDocente() throws Exception {
    if (colId == null || colNombre == null || colApellido == null || colCorreo == null || tvDocente == null) {
        return;
    }

    // Usa el nombre exacto del atributo según tus getters en Docente.java
    // Si tus getters son getId_docente() y getCorreo_electronico(), usa estos valores:
    colId.setCellValueFactory(new PropertyValueFactory<>("id_docente"));
    colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
    colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo_electronico"));

    tvDocente.setItems(docenteService.listDocente());
}

    // 2. ACCIÓN BOTÓN: GUARDAR (Insert)
    @FXML
    private void accionGuardar() {
        try {
            Docente nuevoDocente = new Docente();
            nuevoDocente.setNombre(txtNombre.getText());
            nuevoDocente.setApellido(txtApellido.getText());
            nuevoDocente.setCorreo_electronico(txtCorreo.getText()); // Corregido

            docenteService.guardarDocente(nuevoDocente);
            handleLoadTableDocente(); // Recarga la tabla para ver el cambio
            limpiarCampos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void seleccionarFila(MouseEvent event) {
        docenteSeleccionado = tvDocente.getSelectionModel().getSelectedItem();
        if (docenteSeleccionado != null) {
            txtNombre.setText(docenteSeleccionado.getNombre());
            txtApellido.setText(docenteSeleccionado.getApellido());
            txtCorreo.setText(docenteSeleccionado.getCorreo_electronico()); // Corregido
        }
    }

    // 4. ACCIÓN BOTÓN: ACTUALIZAR (Update)
    @FXML
    private void accionActualizar() {
        try {
            if (docenteSeleccionado != null) {
                docenteSeleccionado.setNombre(txtNombre.getText());
                docenteSeleccionado.setApellido(txtApellido.getText());
                docenteSeleccionado.setCorreo_electronico(txtCorreo.getText()); // Corregido

                docenteService.actualizarDocente(docenteSeleccionado);
                handleLoadTableDocente();
                limpiarCampos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 5. ACCIÓN BOTÓN: ELIMINAR (Delete)
    @FXML
    private void accionEliminar() {
        try {
            if (docenteSeleccionado != null) {
                docenteService.eliminarDocente(docenteSeleccionado.getId_docente()); // Corregido
                handleLoadTableDocente();
                limpiarCampos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 6. LIMPIAR FORMULARIO
    @FXML
    private void accionLimpiar() {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
        docenteSeleccionado = null;
    }
}
