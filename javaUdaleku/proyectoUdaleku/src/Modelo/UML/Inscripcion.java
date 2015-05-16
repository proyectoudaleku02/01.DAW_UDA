/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.UML;

/**
 *
 * @author Noemi
 */
public class Inscripcion {
    private String idInscripcion;
    
    // Relacion
    private Solicitud solicitud;
    private Menor menor;
    private Tutor tutor;
    private Direccion direccion;

    public Inscripcion(String idInscripcion) {
        this.idInscripcion = idInscripcion;
        this.solicitud = new Solicitud();
        this.menor = new Menor();
        this.tutor = new Tutor();
    }

    public Inscripcion() {
        this.solicitud = new Solicitud();
        this.menor = new Menor();
        this.tutor = new Tutor();
    }

    public String getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(String idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud idSolicitud) {
        this.solicitud = idSolicitud;
    }  

    public Menor getMenor() {
        return menor;
    }

    public void setMenor(Menor menor) {
        this.menor = menor;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
    
    
    
}
