package main.java.edu.ingsoft.colegio.gotitas.repository;

import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MatriculaRepository {

    // 1. Inscribir estudiante en una sección (Crear Matrícula)
    public boolean inscribirEstudiante(String idMatricula, String idEstudiante, String idSeccion) {
        String sql = "INSERT INTO matriculas (id_matricula, id_estudiante, id_seccion) VALUES (?, ?, ?)";
        
        try (Connection conn = DataBaseConnection.getConnectionDataBase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, idMatricula);
            pstmt.setString(2, idEstudiante);
            pstmt.setString(3, idSeccion);
            
            pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Quitar estudiante de una sección (Eliminar Matrícula)
    public boolean retirarEstudiante(String idMatricula) {
        String sql = "DELETE FROM matriculas WHERE id_matricula = ?";
        
        try (Connection conn = DataBaseConnection.getConnectionDataBase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, idMatricula);
            pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}