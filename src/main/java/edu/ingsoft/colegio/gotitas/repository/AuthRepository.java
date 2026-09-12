package main.java.edu.ingsoft.colegio.gotitas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.LoginRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.RegisterRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.LoginResponse;

public class AuthRepository {

    public AuthRepository() {
    }

    public LoginResponse findUserByEmail(LoginRequest loginRequest) throws Exception {
        // SQL corregido para consultar el campo u.email o u.contrasena_hash
        String sql = "SELECT d.nombre, d.apellido, u.contrasena_hash " +
                     "FROM usuarios u " +
                     "INNER JOIN docentes d ON d.id_docente = u.id_docente " +
                     "WHERE u.email = ?;";

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, loginRequest.getEmail());
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return new LoginResponse(
                        rs.getString("nombre"), 
                        rs.getString("apellido"), 
                        rs.getString("contrasena_hash")
                    );
                }
            }
        } catch (Exception e) {
            System.err.println("Error al encontrar usuario por email: " + e.getMessage());
            throw e;
        }

        return null;
    }

    public boolean existsByEmail(String email) throws Exception {
        String sql = "SELECT 1 FROM usuarios WHERE email = ?;";

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, email);
            try (ResultSet rs = pstm.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al validar el correo: " + e.getMessage(), e);
        }
    }

    public void saveUser(RegisterRequest registerRequest, String contrasenaHash) throws Exception {
        String idDocente = UUID.randomUUID().toString();
        String idUsuario = UUID.randomUUID().toString();

        String sqlDocente = "INSERT INTO docentes (id_docente, nombre, apellido, correo_electronico) VALUES (?, ?, ?, ?);";
        String sqlUsuario = "INSERT INTO usuarios (id_usuario, id_docente, contrasena_hash, id_rol, email) VALUES (?, ?, ?, ?, ?);";

        Connection conn = DataBaseConnection.getConnectionDataBase();
        boolean autoCommitOriginal = conn.getAutoCommit();

        try {
            conn.setAutoCommit(false); // Transacción segura

            // 1. Insertar Docente
            try (PreparedStatement pstmDocente = conn.prepareStatement(sqlDocente)) {
                pstmDocente.setString(1, idDocente);
                pstmDocente.setString(2, registerRequest.getNombre());
                pstmDocente.setString(3, registerRequest.getApellido());
                pstmDocente.setString(4, registerRequest.getEmail());
                pstmDocente.executeUpdate();
            }

            // 2. Insertar Usuario
            try (PreparedStatement pstmUsuario = conn.prepareStatement(sqlUsuario)) {
                pstmUsuario.setString(1, idUsuario);
                pstmUsuario.setString(2, idDocente);
                pstmUsuario.setString(3, contrasenaHash);
                pstmUsuario.setInt(4, 1); // Rol por defecto
                pstmUsuario.setString(5, registerRequest.getEmail());
                pstmUsuario.executeUpdate();
            }

            conn.commit(); // Confirmar cambios en BD
        } catch (Exception e) {
            conn.rollback(); // Revertir en caso de fallo
            throw new RuntimeException("Error al registrar el usuario: " + e.getMessage(), e);
        } finally {
            conn.setAutoCommit(autoCommitOriginal);
        }
    }
}