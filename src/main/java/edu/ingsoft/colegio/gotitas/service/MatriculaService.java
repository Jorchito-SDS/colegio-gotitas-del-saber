package main.java.edu.ingsoft.colegio.gotitas.service;

import javafx.collections.ObservableList;
import main.java.edu.ingsoft.colegio.gotitas.model.Matricula;
import main.java.edu.ingsoft.colegio.gotitas.repository.MatriculaRepository;

public class MatriculaService {

    private final MatriculaRepository repository;

    public MatriculaService(MatriculaRepository repository) {
        this.repository = repository;
    }

    public ObservableList<Matricula> listMatricula() throws Exception {
        return repository.findAll(); 
    }

    public void guardarMatricula(Matricula m) throws Exception {
        repository.save(m);
    }

    public void actualizarMatricula(Matricula m) throws Exception {
        repository.update(m);
    }

    // Cambiado de int a String para alinearlo con el modelo y repositorio
    public void eliminarMatricula(String idMatricula) throws Exception {
        repository.delete(idMatricula);
    }
}