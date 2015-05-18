/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.UML;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author Noemi
 */
public class Tutor extends Solicitante{
    
    private String dni;
    private ArrayList<String> telefonos;
    
    // Relacion
    private Inscripcion inscripcion;

    public Tutor() {
    }  
    
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public ArrayList<String> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(ArrayList<String> telefonos) {
        this.telefonos = telefonos;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }
    
    public String getNomAps(){
        return this.getNombre()+" "+this.getApel1()+" "+this.getApel2();
    }

}
