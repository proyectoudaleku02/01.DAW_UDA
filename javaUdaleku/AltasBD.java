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
}
