package main.java.edu.ingsoft.colegio.gotitas.repository;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeccionRepository {

    // 1. Listar Secciones
    public List<String> listarSecciones() throws Exception {
        List<String> listaSecciones = new ArrayList<>();
        String sql = "SELECT id_seccion, id_docente, id_curso, nombre_seccion FROM secciones";
        
        try (Connection conn = DataBaseConnection.getConnectionDataBase();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String info = rs.getString("nombre_seccion");
                listaSecciones.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaSecciones;
    }

    // 2. Insertar Sección (Usando UUID/char(36) tal como está en tu base de datos)
    public boolean insertarSeccion(String idSeccion, String idDocente, String idCurso, String nombreSeccion) throws Exception {
        String sql = "INSERT INTO secciones (id_seccion, id_docente, id_curso, nombre_seccion) VALUES (?, ?, ?, ?)";
        
       try (Connection conn = DataBaseConnection.getConnectionDataBase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, idSeccion);
            pstmt.setString(2, idDocente);
            pstmt.setString(3, idCurso);
            pstmt.setString(4, nombreSeccion);
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. Actualizar Sección
    public boolean actualizarSeccion(String idSeccion, String idDocente, String idCurso, String nombreSeccion) throws Exception {
        String sql = "UPDATE secciones SET id_docente = ?, id_curso = ?, nombre_seccion = ? WHERE id_seccion = ?";
        
      try (Connection conn = DataBaseConnection.getConnectionDataBase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, idDocente);
            pstmt.setString(2, idCurso);
            pstmt.setString(3, nombreSeccion);
            pstmt.setString(4, idSeccion);
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. Eliminar Sección
    public boolean eliminarSeccion(String idSeccion) throws Exception {
        String sql = "DELETE FROM secciones WHERE id_seccion = ?";
        
       try (Connection conn = DataBaseConnection.getConnectionDataBase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, idSeccion);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }    
}
