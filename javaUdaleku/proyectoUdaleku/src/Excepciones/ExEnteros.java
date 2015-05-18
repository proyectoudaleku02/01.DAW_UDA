/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 *
 * @author sergio
 */
public class ExEnteros extends Exception{
    
    private final String mensaje = "Debes introducir un número entero.";

    public String getMensaje() {
        return mensaje;
    }    
}
