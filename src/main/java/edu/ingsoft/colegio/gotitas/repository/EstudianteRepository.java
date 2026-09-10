package main.java.edu.ingsoft.colegio.gotitas.repository;

import javafx.collections.ObservableList;
import main.java.edu.ingsoft.colegio.gotitas.model.Estudiante;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;

public class EstudianteRepository {

    public ObservableList<Estudiante> findAll() throws Exception {
        String sql = "SELECT " +
                     "    e.id_estudiante, " +
                     "    e.nombre AS nombre_estudiante, " +
                     "    e.apellido AS apellido_estudiante, " +
                     "    e.correo_electronico, " +
                     "    s.nombre_seccion, " +
                     "    c.nombre_curso, " +
                     "    d.nombre AS nombre_docente, " +
                     "    d.apellido AS apellido_docente " +
                     "FROM estudiantes AS e " +
                     "INNER JOIN matriculas AS m ON e.id_estudiante = m.id_estudiante " +
                     "INNER JOIN secciones AS s ON m.id_seccion = s.id_seccion " +
                     "INNER JOIN cursos AS c ON s.id_curso = c.id_curso " +
                     "INNER JOIN docentes AS d ON s.id_docente = d.id_docente;";
        
        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            
            ResultSet rs = pstm.executeQuery();
            ObservableList<Estudiante> studentList = FXCollections.observableArrayList();
            
            while (rs.next()) {
                studentList.add(new Estudiante(
                        rs.getString("id_estudiante"),
                        rs.getString("nombre_estudiante"),
                        rs.getString("apellido_estudiante"),
                        rs.getString("correo_electronico"),
                        rs.getString("nombre_seccion"),
                        rs.getString("nombre_curso"),
                        rs.getString("nombre_docente"),
                        rs.getString("apellido_docente")
                ));
            }
            return studentList;

        } catch (SQLException e) {
            throw new RuntimeException("Error en la consulta: " + e.getMessage());
        }
    }
         }    