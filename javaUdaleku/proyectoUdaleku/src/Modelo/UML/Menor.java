/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.UML;

import java.util.Date;

/**
 *
 * @author Noemi
 */
public class Menor extends Solicitante{

    
    private String dni;
    private String sexo;
    private Date fechaNac;
    private boolean discapacidad;

    private String idInscripcion;
    private int idDireccion;
    private int idCentro;
    private String idModelo;
    
    public Menor(String dni, String sexo, Date fechaNac, boolean discapacidad, String idInscripcion, int idDireccion, int idCentro, String idModelo) {
        this.dni = dni;
        this.sexo = sexo;
        this.fechaNac = fechaNac;
        this.discapacidad = discapacidad;
        this.idInscripcion = idInscripcion;
        this.idDireccion = idDireccion;
        this.idCentro = idCentro;
        this.idModelo = idModelo;
    }

 
    
    

    public Menor(String sexo, Date fechaNac, boolean discapacidad) {
        this.sexo = sexo;
        this.fechaNac = fechaNac;
        this.discapacidad = discapacidad;
    }

    public Menor(String dni, String sexo, Date fechaNac, boolean discapacidad) {
        this.dni = dni;
        this.sexo = sexo;
        this.fechaNac = fechaNac;
        this.discapacidad = discapacidad;
    }

    public Menor() {
    }
    

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public boolean isDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(boolean discapacidad) {
        this.discapacidad = discapacidad;
    }
    
       public int getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(int idCentro) {
        this.idCentro = idCentro;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(String idInscripcion) {
        this.idInscripcion = idInscripcion;
    }
    
        public String getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(String idModelo) {
        this.idModelo = idModelo;
    }
}
