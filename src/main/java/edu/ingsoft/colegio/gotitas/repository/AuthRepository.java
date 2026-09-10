package main.java.edu.ingsoft.colegio.gotitas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import java.util.UUID;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.LoginRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.RegisterRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.LoginResponse;

public class AuthRepository {

    private boolean sqlStatus = false;
    private String idDocente; 
    private String email; 

    public AuthRepository() {
    }

    public LoginResponse findUserByEmail(LoginRequest loginRequest) throws Exception {
        // CORREGIDO: u.id_docente cambiado a u.id_docentes para coincidir con la base de datos
        String sql = "select d.nombre, d.apellido, u.contrasena_hash from usuarios as u right join docentes as d on d.id_docente = u.id_docentes where email = ?;";

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, loginRequest.getEmail());
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return new LoginResponse(rs.getString("nombre"), rs.getString("apellido"), rs.getString("contrasena_hash"));
            }

        } catch (Exception e) {
            System.out.println("Error al encontrar el Email: " + e.getMessage());
        }

        return null;
    }

    public boolean existsByEmail(String email) throws Exception {
        String sql = "select 1 from usuarios where email = ?;";

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();
            return rs.next();
        } catch (Exception e) {
            throw new RuntimeException("Error al validar el correo: " + e.getMessage());
        }
    }

    public void saveUser(RegisterRequest registerRequest, String contrasenaHash) throws Exception {
        this.idDocente = UUID.randomUUID().toString();
        this.email = registerRequest.getEmail();
        
        String sqlDocente = "insert into docentes (id_docente, nombre, apellido, correo_electronico) values (?, ?, ?, ?);";
        // CORREGIDO: id_docente cambiado a id_docentes en la tabla usuarios
        String sqlUsuario = "insert into usuarios (id_usuario, id_docentes, contrasena_hash, id_rol, email) values (?, ?, ?, ?, ?);";

        Connection conn = DataBaseConnection.getConnectionDataBase();
        boolean autoCommitOriginal = conn.getAutoCommit();

        try {
            conn.setAutoCommit(false); // Activamos la transacción para asegurar consistencia

            // 1. Insertar Docente
            try (PreparedStatement pstmDocente = conn.prepareStatement(sqlDocente)) {
                pstmDocente.setString(1, idDocente);
                pstmDocente.setString(2, registerRequest.getNombre());
                pstmDocente.setString(3, registerRequest.getApellido());
                pstmDocente.setString(4, email);
                pstmDocente.execute();
            }

            // 2. Insertar Usuario asociado
            try (PreparedStatement pstmUsuario = conn.prepareStatement(sqlUsuario)) {
                pstmUsuario.setString(1, UUID.randomUUID().toString());
                pstmUsuario.setString(2, idDocente);
                pstmUsuario.setString(3, contrasenaHash);
                pstmUsuario.setInt(4, 1);
                pstmUsuario.setString(5, email);
                pstmUsuario.execute();
            }

            conn.commit(); // Confirmamos los cambios si todo sale bien

        } catch (Exception e) {
            conn.rollback(); // Revertimos si ocurre algún fallo
            throw new RuntimeException("Error al registrar el usuario: " + e.getMessage());
        } finally {
            conn.setAutoCommit(autoCommitOriginal);
        }
    }
}