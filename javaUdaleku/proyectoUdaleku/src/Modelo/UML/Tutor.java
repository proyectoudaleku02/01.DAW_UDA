/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.UML;
import java.util.Arrays;
/**
 *
 * @author Noemi
 */
public class Tutor extends Solicitante{
    
    private String dni;
    private int telefonos[];
    
    // Relacion
    private Inscripcion inscripcion;
    
    

    public Tutor(String nombre, String apel1, String apel2, String dni, int[] telefonos) {
        this.setNombre(nombre);
        this.setApel1(apel1);
        this.setApel2(apel2);        
        this.dni = dni;
        this.telefonos = telefonos;
        this.inscripcion = new Inscripcion();
    }

    public Tutor() {
        this.inscripcion = new Inscripcion();
    }  
    
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int[] getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(int[] telefonos) {
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
