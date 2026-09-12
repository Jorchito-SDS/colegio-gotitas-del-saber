package main.java.edu.ingsoft.colegio.gotitas.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import main.java.edu.ingsoft.colegio.gotitas.model.Estudiante;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstudianteRepository {

    // CONSULTA CON LEFT JOIN PARA VER ESTUDIANTES AUN SIN MATRÍCULA
    public ObservableList<Estudiante> findAll() throws Exception {
        String sql = "SELECT " +
                     "    e.id_estudiante, " +
                     "    e.nombre AS nombre_estudiante, " +
                     "    e.apellido AS apellido_estudiante, " +
                     "    e.correo_electronico, " +
                     "    IFNULL(s.nombre_seccion, 'Sin asignación') AS nombre_seccion, " +
                     "    IFNULL(c.nombre_cursos, 'Sin asignación') AS nombre_curso, " +
                     "    IFNULL(d.nombre, 'Sin asignación') AS nombre_docente, " +
                     "    IFNULL(d.apellido, '') AS apellido_docente " +
                     "FROM estudiantes AS e " +
                     "LEFT JOIN matriculas AS m ON m.id_estudiante = e.id_estudiante " +
                     "LEFT JOIN asignacion_cursos AS ac ON ac.id_matricula = m.id_matricula " +
                     "LEFT JOIN secciones AS s ON s.id_seccion = ac.id_seccion " +
                     "LEFT JOIN cursos AS c ON c.id_cursos = ac.id_curso " +
                     "LEFT JOIN docentes AS d ON d.id_docente = ac.id_docente " +
                     "ORDER BY e.id_estudiante DESC;";

        ObservableList<Estudiante> list = FXCollections.observableArrayList();

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                list.add(new Estudiante(
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
            return list;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar estudiantes: " + e.getMessage());
        }
    }

    public boolean save(String idEstudiante, String idCiudad, String nombre, String apellido, String correo, String fechaNacimiento) throws Exception {
        String sql = "INSERT INTO estudiantes (id_estudiante, id_ciudad, nombre, apellido, correo_electronico, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, idEstudiante);
            pstm.setString(2, idCiudad);
            pstm.setString(3, nombre);
            pstm.setString(4, apellido);
            pstm.setString(5, correo);
            pstm.setString(6, fechaNacimiento);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar estudiante: " + e.getMessage());
        }
    }

    public boolean update(String idEstudiante, String idCiudad, String nombre, String apellido, String correo, String fechaNacimiento) throws Exception {
        String sql = "UPDATE estudiantes SET id_ciudad=?, nombre=?, apellido=?, correo_electronico=?, fecha_nacimiento=? WHERE id_estudiante=?";
        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, idCiudad);
            pstm.setString(2, nombre);
            pstm.setString(3, apellido);
            pstm.setString(4, correo);
            pstm.setString(5, fechaNacimiento);
            pstm.setString(6, idEstudiante);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estudiante: " + e.getMessage());
        }
    }

    public boolean delete(String idEstudiante) throws Exception {
        String sql = "DELETE FROM estudiantes WHERE id_estudiante=?";
        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, idEstudiante);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar estudiante: " + e.getMessage());
        }
    }
}