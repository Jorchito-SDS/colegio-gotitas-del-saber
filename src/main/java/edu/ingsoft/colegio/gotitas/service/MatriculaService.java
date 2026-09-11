package main.java.edu.ingsoft.colegio.gotitas.service;

import main.java.edu.ingsoft.colegio.gotitas.repository.MatriculaRepository;

public class MatriculaService {
    private final MatriculaRepository matriculaRepository = new MatriculaRepository();

    public boolean registrarMatricula(String idMatricula, String idEstudiante, String idSeccion) {
        return matriculaRepository.inscribirEstudiante(idMatricula, idEstudiante, idSeccion);
    }

    public boolean eliminarMatricula(String idMatricula) {
        return matriculaRepository.retirarEstudiante(idMatricula);
    }
}