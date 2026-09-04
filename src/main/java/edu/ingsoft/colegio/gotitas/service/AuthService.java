package main.java.edu.ingsoft.colegio.gotitas.service;

import main.java.edu.ingsoft.colegio.gotitas.dto.request.LoginRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.RegisterRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.LoginResponse;
import main.java.edu.ingsoft.colegio.gotitas.repository.AuthRepository;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    private final AuthRepository authRepository;
    private boolean status = false;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;

    }

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        if (loginRequest == null) {
            throw new RuntimeException("Credenciales vacías");
        } else if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            throw new RuntimeException("el correo o contraseña no pueden estar vacíos");
        }
        LoginResponse response = authRepository.findUserByEmail(loginRequest);

        if (response == null) {
            throw new RuntimeException("usuario no encontrado");
        }

        String contrasenaHashed = response.getContrasena_hash();

        if (contrasenaHashed == null) {
            throw new RuntimeException("contrasena invalida. ");
        }

        if (!BCrypt.checkpw(loginRequest.getPassword(), contrasenaHashed)) {
            throw new RuntimeException("correo o contraseña incorrectos");
        }

        return response;
    }

    public void register(RegisterRequest registerRequest) throws Exception {
        if (registerRequest == null) {
            throw new RuntimeException("Datos de registro vacíos");
        }
        if (isBlank(registerRequest.getNombre()) || isBlank(registerRequest.getApellido())
                || isBlank(registerRequest.getEmail()) || isBlank(registerRequest.getPassword())) {
            throw new RuntimeException("Todos los campos son obligatorios");
        }
        if (registerRequest.getPassword().length() < 6) {
            throw new RuntimeException("La contraseña debe tener al menos 6 caracteres");
        }
        if (authRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Ya existe un usuario registrado con ese correo");
        }

        String contrasenaHash = BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt());
        authRepository.saveUser(registerRequest, contrasenaHash);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

}
