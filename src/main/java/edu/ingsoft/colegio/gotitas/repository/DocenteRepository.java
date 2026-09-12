package main.java.edu.ingsoft.colegio.gotitas.repository;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import main.java.edu.ingsoft.colegio.gotitas.model.Docente;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DocenteRepository {

 
    public ObservableList<Docente> findAll() throws Exception {
        String sql = "SELECT id_docente, nombre, apellido, correo_electronico FROM docentes;";
        ObservableList<Docente> listaDocentes = FXCollections.observableArrayList();

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            
            while (rs.next()) {
                listaDocentes.add(new Docente(
                        rs.getString("id_docente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo_electronico")
                ));
            }
            return listaDocentes;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar docentes: " + e.getMessage());
        }
    }

    public void save(Docente docente) throws Exception {
        String sql = "INSERT INTO docentes (id_docente, nombre, apellido, correo_electronico) VALUES (?, ?, ?, ?);";

      try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {

          
            if (docente.getId_docente() == null || docente.getId_docente().isEmpty()) {
                docente.setId_docente(UUID.randomUUID().toString());
            }
            
            pstm.setString(1, docente.getId_docente());
            pstm.setString(2, docente.getNombre());
            pstm.setString(3, docente.getApellido());
            pstm.setString(4, docente.getCorreo_electronico());
            
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar docente: " + e.getMessage());
        }
    }

    
    public void update(Docente docente) throws Exception {
        String sql = "UPDATE docentes SET nombre = ?, apellido = ?, correo_electronico = ? WHERE id_docente = ?;";

     try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {

            pstm.setString(1, docente.getNombre());
            pstm.setString(2, docente.getApellido());
            pstm.setString(3, docente.getCorreo_electronico());
            pstm.setString(4, docente.getId_docente());
            
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar docente: " + e.getMessage());
        }
    }
    public void delete(String idDocente) throws Exception {
        String sql = "DELETE FROM docentes WHERE id_docente = ?;";

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, idDocente);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar docente: " + e.getMessage());
        }
    }
}
