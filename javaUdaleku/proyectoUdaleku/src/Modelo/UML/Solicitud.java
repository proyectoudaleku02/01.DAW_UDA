/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.UML;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Noemi
 */
public class Solicitud {
    private long idSolicitud;
    
    private String situacionSolc;
    private int ordenSorteo;
    private Date fechaHoraCita;
    
    // Relación
    private ArrayList<Inscripcion> inscripciones;

    public Solicitud() {
        this.inscripciones = new ArrayList();
    }

    public Solicitud(int idSolicitud, String situacionSolc) {
        this.idSolicitud = idSolicitud;
        this.situacionSolc = situacionSolc;
        this.inscripciones = new ArrayList();
    }

    public Solicitud(int idSolicitud, String situacionSolc, int ordenSorteo, Date fechaHoraCita) {
        this.idSolicitud = idSolicitud;
        this.situacionSolc = situacionSolc;
        this.ordenSorteo = ordenSorteo;
        this.fechaHoraCita = fechaHoraCita;
    }

    public long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getSituacionSolc() {
        return situacionSolc;
    }

    public void setSituacionSolc(String situacionSolc) {
        this.situacionSolc = situacionSolc;
    }

    public int getOrdenSorteo() {
        return ordenSorteo;
    }

    public void setOrdenSorteo(int ordenSorteo) {
        this.ordenSorteo = ordenSorteo;
    }

    public Date getFechaHoraCita() {
        return fechaHoraCita;
    }

    public void setFechaHoraCita(Date fechaHoraCita) {
        this.fechaHoraCita = fechaHoraCita;
    }

    public ArrayList<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(ArrayList<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
    
    
    
    
    
}
