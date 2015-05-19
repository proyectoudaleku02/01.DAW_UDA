/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.BD;

import conexionoracle.ConexionOracle;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import oracle.jdbc.OracleTypes;

public class AltasBD {

    public static boolean insertDireccion(String num, String letra, String piso, String escalera, String mano, String cp, Long idVia) throws SQLException {
        // Ejecución de un procedimiento contenido dentro del paquete que gestiona las altas de inscripciones
        ResultSet rset;
        //conectamos
        ConexionOracle.setConexion();

        String sql = "{call alta_inscripciones.insertar_direccion(?,?,?,?,?,?,?)}";
        CallableStatement cs = ConexionOracle.getConexion().prepareCall(sql);
        try {
            // Cargamos los parametros de entrada IN
            cs.setString(1, num);
            cs.setString(2, letra);
            cs.setString(3, piso);
            cs.setString(4, escalera);
            cs.setString(5, mano);
            cs.setString(6, cp);
            cs.setLong(7, idVia);

            // Ejecutamos
            cs.execute();

            //desconectamos
            ConexionOracle.desconectar();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean insertSolicitud(String situacion) throws SQLException {
        // Ejecución de un procedimiento contenido dentro del paquete que gestiona las altas de inscripciones
        ResultSet rset;
        //conectamos
        ConexionOracle.setConexion();

        String sql = "{call alta_inscripciones.insertar_solicitud(?)}";
        CallableStatement cs = ConexionOracle.getConexion().prepareCall(sql);
        try {
            // Cargamos los parametros de entrada IN
            cs.setString(1, situacion);

            // Ejecutamos
            cs.execute();

            //desconectamos
            ConexionOracle.desconectar();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean insertInscrip(long idSolicitud, int numero, long idDireccion) throws SQLException {
        // Ejecución de un procedimiento contenido dentro del paquete que gestiona las altas de inscripciones
        ResultSet rset;
        //conectamos
        ConexionOracle.setConexion();

        String sql = "{call alta_inscripciones.insert_inscripcion(?,?,?)}";
        CallableStatement cs = ConexionOracle.getConexion().prepareCall(sql);
        try {
            // Cargamos los parametros de entrada IN
            cs.setLong(1, idSolicitud);
            cs.setInt(2, numero);
            cs.setLong(3, idDireccion);

            // Ejecutamos
            cs.execute();

            //desconectamos
            ConexionOracle.desconectar();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean insertTutor(String dni, String nombre, String apel1, String apel2, String idInscripcion) throws SQLException {
        // Ejecución de un procedimiento contenido dentro del paquete que gestiona las altas de inscripciones
        ResultSet rset;
        //conectamos
        ConexionOracle.setConexion();

        String sql = "{call alta_inscripciones.alta_tutor(?,?,?,?,?)}";
        CallableStatement cs = ConexionOracle.getConexion().prepareCall(sql);
        try {
            // Cargamos los parametros de entrada IN
            cs.setString(1, dni);
            cs.setString(2, nombre);
            cs.setString(3, apel1);
            cs.setString(4, apel2);
            cs.setString(5, idInscripcion);

            // Ejecutamos
            cs.execute();

            //desconectamos
            ConexionOracle.desconectar();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean insertMenor(String dni, String nombre, String apel1, String apel2, String sexo, Date fechaNac, String discapacidad, String idInscripcion, Long idCentro, String modelo) {
     
         ResultSet rset;
        //conectamos
        ConexionOracle.setConexion();
        
        String sql = "{call alta_inscripciones.insert_menor(?,?,?,?,?,?,?,?,?,?,?)}";

        try {
         CallableStatement cs = ConexionOracle.getConexion().prepareCall(sql);
            // Cargamos los parametros de entrada IN
            cs.setString(1, dni);
            cs.setString(2, nombre);
            cs.setString(3, apel1);
            cs.setString(4, apel2);
            cs.setString(5, sexo);
            cs.setDate(6, (java.sql.Date) fechaNac);
            cs.setString(7, discapacidad);
            cs.setString(8, idInscripcion);
            cs.setLong(9, idCentro);
            cs.setString(10, modelo);

            // Ejecutamos
            cs.execute();
            //desconectamos
            ConexionOracle.desconectar();

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean insertTelefono(Long idSolicitante, ArrayList<String> telefonos) {
        String telefono1 = null;
        String telefono2 = null;
        String telefono3 = null;
        String telefono4 = null;
        switch (telefonos.size()) {
            case 1:
                telefono1 = telefonos.get(0);
                break;
            case 2:
                telefono1 = telefonos.get(0);
                telefono2 = telefonos.get(1);
                break;
            case 3:
                telefono1 = telefonos.get(0);
                telefono2 = telefonos.get(1);
                telefono3 = telefonos.get(2);
                break;
            case 4:
                telefono1 = telefonos.get(0);
                telefono2 = telefonos.get(1);
                telefono3 = telefonos.get(2);
                telefono4 = telefonos.get(3);
                break;
        }

        ResultSet rset;
        //conectamos
        ConexionOracle.setConexion();

        String sql = "{call alta_inscripciones.insertar_tlfn(?,?,?,?,?)}";

        try {
            CallableStatement cs = ConexionOracle.getConexion().prepareCall(sql);
            // Cargamos los parametros de entrada IN
            cs.setLong(1, idSolicitante);
            cs.setString(2, telefono1);
            cs.setString(3, telefono2);
            cs.setString(4, telefono3);
            cs.setString(5, telefono4);

            // Ejecutamos
            cs.execute();

            //desconectamos
            ConexionOracle.desconectar();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
