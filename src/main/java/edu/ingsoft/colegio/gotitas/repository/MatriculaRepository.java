package main.java.edu.ingsoft.colegio.gotitas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import main.java.edu.ingsoft.colegio.gotitas.model.Matricula;

public class MatriculaRepository {
public ObservableList<Matricula> findAll() throws Exception {
    ObservableList<Matricula> lista = FXCollections.observableArrayList();
    
    // Cambiamos CONCAT(e.nombres, ' ', e.apellidos) por e.nombre (o el nombre real de tu columna)
    String sql = "SELECT m.id_matricula, m.id_seccion, m.id_estudiante, " +
                 "e.nombre AS nombreEstudiante, " +
                 "s.nombre_seccion AS nombreSeccion " +
                 "FROM matriculas m " +
                 "INNER JOIN estudiantes e ON m.id_estudiante = e.id_estudiante " +
                 "INNER JOIN secciones s ON m.id_seccion = s.id_seccion";

    try (Connection conn = DataBaseConnection.getConnectionDataBase();
         PreparedStatement pstm = conn.prepareStatement(sql);
         ResultSet rs = pstm.executeQuery()) {

        while (rs.next()) {
            lista.add(new Matricula(
                rs.getString("id_matricula"),
                rs.getString("id_seccion"),
                rs.getString("id_estudiante"),
                rs.getString("nombreEstudiante"),
                rs.getString("nombreSeccion")
            ));
        }
    } catch (Exception e) {
        System.err.println("Error al listar matrículas: " + e.getMessage());
        throw e;
    }
    return lista;
}
    public void save(Matricula m) throws Exception {
        String sql = "INSERT INTO matriculas (id_matricula, id_seccion, id_estudiante) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnectionDataBase();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, m.getId_matricula());
            pstm.setString(2, m.getId_seccion());
            pstm.setString(3, m.getId_estudiante());
            pstm.executeUpdate();
        }
    }

    public void update(Matricula m) throws Exception {
        String sql = "UPDATE matriculas SET id_seccion = ?, id_estudiante = ? WHERE id_matricula = ?";
        try (Connection conn = DataBaseConnection.getConnectionDataBase();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, m.getId_seccion());
            pstm.setString(2, m.getId_estudiante());
            pstm.setString(3, m.getId_matricula());
            pstm.executeUpdate();
        }
    }

    public void delete(String idMatricula) throws Exception {
        String sql = "DELETE FROM matriculas WHERE id_matricula = ?";
        try (Connection conn = DataBaseConnection.getConnectionDataBase();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, idMatricula);
            pstm.executeUpdate();
        }
    }
}