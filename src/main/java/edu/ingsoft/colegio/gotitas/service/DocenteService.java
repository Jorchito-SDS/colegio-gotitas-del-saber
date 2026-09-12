    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package main.java.edu.ingsoft.colegio.gotitas.service;

    import javafx.collections.ObservableList;
    import main.java.edu.ingsoft.colegio.gotitas.model.Docente;
    import main.java.edu.ingsoft.colegio.gotitas.repository.DocenteRepository;

    /**
     *
     * @author informatica
     */
    public class DocenteService {
        private final DocenteRepository docenteRepository;

        // Inyección de dependencias mediante el constructor al estilo de tu grupo
        public DocenteService(DocenteRepository docenteRepository) {
            this.docenteRepository = docenteRepository;
        }

        // 1. Obtener todos los docentes con validación de datos
        public ObservableList<Docente> listDocente() throws Exception {
            ObservableList<Docente> lista = docenteRepository.findAll();
            if (lista == null || lista.isEmpty()) {
                throw new RuntimeException("Sin datos de docentes que mostrar");
            }
            return lista;
        }

        // 2. Registrar un nuevo docente
        public void guardarDocente(Docente docente) throws Exception {
            if (docente.getNombre() == null || docente.getNombre().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del docente es obligatorio.");
            }
            docenteRepository.save(docente);
        }

        // 3. Modificar un docente existente
        public void actualizarDocente(Docente docente) throws Exception {
            docenteRepository.update(docente);
        }

        // 4. Eliminar un docente por su ID
        public void eliminarDocente(String id_docente) throws Exception {
            docenteRepository.delete(id_docente);
        }
    }