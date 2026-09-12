package main.java.edu.ingsoft.colegio.gotitas.model;

public class Matricula {
    private String id_matricula;
    private String id_seccion;
    private String id_estudiante;
    private String nombreEstudiante;
    private String nombreSeccion;

    // Constructor completo (usado para cargar datos en la tabla)
    public Matricula(String id_matricula, String id_seccion, String id_estudiante, String nombreEstudiante, String nombreSeccion) {
        this.id_matricula = id_matricula;
        this.id_seccion = id_seccion;
        this.id_estudiante = id_estudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.nombreSeccion = nombreSeccion;
    }

    // Constructor simple (usado para insertar/guardar datos)
    public Matricula(String id_matricula, String id_seccion, String id_estudiante) {
        this.id_matricula = id_matricula;
        this.id_seccion = id_seccion;
        this.id_estudiante = id_estudiante;
    }

    // Getters obligatorios para JavaFX (PropertyValueFactory)
    public String getIdMatricula() { return id_matricula; }
    public String getIdSeccion() { return id_seccion; }
    public String getIdEstudiante() { return id_estudiante; }
    public String getNombreEstudiante() { return nombreEstudiante; }
    public String getNombreSeccion() { return nombreSeccion; }

    // Getters estándar
    public String getId_matricula() { return id_matricula; }
    public String getId_seccion() { return id_seccion; }
    public String getId_estudiante() { return id_estudiante; }

    // Setters
    public void setId_matricula(String id_matricula) { this.id_matricula = id_matricula; }
    public void setId_seccion(String id_seccion) { this.id_seccion = id_seccion; }
    public void setId_estudiante(String id_estudiante) { this.id_estudiante = id_estudiante; }
}