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
       String sql = "SELECT \n" +
"    e.id_estudiante,\n" +
"    e.nombre,\n" +
"    e.apellido,\n" +
"    e.correo_electronico,\n" +
"    s.nombre_seccion,\n" +
"    c.nombre_curso,\n" +
"    d.nombre,\n" +
"    d.apellido\n" +
"FROM asignacion_cursos AS ac\n" +
"INNER JOIN matriculas AS m \n" +
"    ON m.id_matricula = ac.id_matricula\n" +
"INNER JOIN secciones AS s \n" +
"    ON s.id_seccion = ac.id_seccion\n" +
"INNER JOIN cursos AS c \n" +
"    ON c.id_curso = ac.id_curso\n" +
"INNER JOIN docentes AS d \n" +
"    ON d.id_docente = ac.id_docente\n" +
"INNER JOIN estudiantes AS e \n" +
"    ON e.id_estudiante = m.id_estudiante;";
        
        
        

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            
            ResultSet rs = pstm.executeQuery();
            ObservableList<Estudiante> studentList = FXCollections.observableArrayList();
            
            while (rs.next()) {
                studentList.add(new Estudiante(
                        rs.getString("id_estudiante"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo_electronico"),
                        rs.getString("nombre_seccion"),
                        rs.getString("nombre_curso"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                ));

            }
            return studentList;

        } catch (SQLException e) {
            throw new RuntimeException("Error en la consulta: " + e.getMessage());
        }
    }

}
