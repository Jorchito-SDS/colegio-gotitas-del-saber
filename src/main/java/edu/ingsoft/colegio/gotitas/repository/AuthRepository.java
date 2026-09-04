package main.java.edu.ingsoft.colegio.gotitas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import java.sql.SQLException;
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

    // Divide y vencerás: un metodo debe ser engargado de realizar unicamente una tarea específica
    // el nombre de ese método debe ser modular, directo
    public LoginResponse findUserByEmail(LoginRequest loginRequest) throws Exception {
        String sql = "select d.nombre, d.apellido, u.contrasena_hash from usuarios as u right join docentes as d on d.id_docente = u.id_docente where email = ?;";

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, loginRequest.getEmail());
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return new LoginResponse(rs.getString("nombre"), rs.getString("apellido"), rs.getString("contrasena_hash"));
            }

        } catch (Exception e) {
            System.out.println("Error al encontrar el Email" + e.getMessage());
        }

        return null;

    }

    // Verifica si ya existe un usuario registrado con ese correo
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

    // Inserta el docente y su usuario (correo + contraseña ya hasheada)
    public void saveUser(RegisterRequest registerRequest, String contrasenaHash) throws Exception {
        this.idDocente = UUID.randomUUID().toString();
        this.email = registerRequest.getEmail();
        String sqlDocente = "insert into docentes (id_docente, nombre, apellido,correo_electronico) values (?, ?, ?, ?);";
        String sqlUsuario = "insert into usuarios (id_usuario, id_docente, contrasena_hash, id_rol, email) values (?, ?,?,?,?);";
        String getDocente = "select * from docentes";

        Connection conn = DataBaseConnection.getConnectionDataBase();
        // boolean autoCommitOriginal = conn.getAutoCommit();

        try {
            //conn.setAutoCommit(false);

            try (PreparedStatement pstmDocente = conn.prepareStatement(sqlDocente)) {
                
                pstmDocente.setString(1, idDocente);
                pstmDocente.setString(2, registerRequest.getNombre());
                pstmDocente.setString(3, registerRequest.getApellido());
                pstmDocente.setString(4, email);
                pstmDocente.execute();

                try (PreparedStatement pstmGetDocente = conn.prepareStatement(getDocente)) {
                  //  String idDocente;
               
                   
                        try (PreparedStatement pstmUsuario = conn.prepareStatement(sqlUsuario)) {
                            pstmUsuario.setString(1, UUID.randomUUID().toString());
                            pstmUsuario.setString(2, idDocente);
                            pstmUsuario.setString(3, contrasenaHash);
                            pstmUsuario.setInt(4, 1);
                            pstmUsuario.setString(5, email);
                            pstmUsuario.execute();
                        }
                    
                }

                //   ResultSet rs = pstmDocente.executeQuery();

                /*
                    if (rs.next()) {
                        idDocente = rs.getString("id_docente");
                    } else {
                        throw new RuntimeException("No se pudo obtener el id del docente generado");
                    }
               
                 */
            }
            // conn.commit();

        } catch (Exception e) {
            //  conn.rollback();
            throw new RuntimeException("Error al registrar el usuario: " + e.getMessage() + "rs: " + this.idDocente);
        } finally {
            //  conn.setAutoCommit(autoCommitOriginal);
        }
    }

}
