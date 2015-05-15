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
    private Solicitud idSolicitud;
    private Menor menor;
    private Tutor tutor;

    public Inscripcion(String idInscripcion) {
        this.idInscripcion = idInscripcion;
        this.idSolicitud = new Solicitud();
    }

    public Inscripcion() {
        this.idSolicitud = new Solicitud();
    }

    public String getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(String idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Solicitud getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Solicitud idSolicitud) {
        this.idSolicitud = idSolicitud;
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
    
    
}
