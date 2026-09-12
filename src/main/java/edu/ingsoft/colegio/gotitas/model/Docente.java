/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.edu.ingsoft.colegio.gotitas.model;

/**
 *
 * @author informatica
 */
public class Docente {
    private String id_docente;
    private String nombre;
    private String apellido;
    private String correo_electronico;
    
    public Docente(){}

    public Docente(String id_docente, String nombre, String apellido, String correo_electronico) {
        this.id_docente = id_docente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo_electronico = correo_electronico;
    }

    public String getId_docente() {
        return id_docente;
    }

    public void setId_docente(String id_docente) {
        this.id_docente = id_docente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }
    
    
    
}
