/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.BD;

import Modelo.UML.Centro;
import Modelo.UML.Localidad;
import Modelo.UML.Modelo;
import Modelo.UML.Municipio;
import Modelo.UML.Via;
import conexionoracle.ConexionOracle;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ConsultasBD {
    
    public static ArrayList<Centro> findCentByProv(String idProvincia,String text) {
        PreparedStatement sentencia;
        ResultSet rset;
        ArrayList<Centro> centros=new ArrayList();
        //conectamos
        ConexionOracle.setConexion();

        try {
            if (text.equalsIgnoreCase("tu provincia")) {
                sentencia = ConexionOracle.getConexion().prepareStatement("select idCentro, nombreCent from centros where upper(idProvincia)=? order by nombreCent");
                sentencia.setString(1, idProvincia);
                rset = sentencia.executeQuery();
            } else {
                sentencia = ConexionOracle.getConexion().prepareStatement("select idCentro, nombreCent from centros where upper(idProvincia)<>? order by nombreCent");
                sentencia.setString(1, idProvincia);
                rset = sentencia.executeQuery();
            }

            while (rset.next()) {
                centros.add(new Centro(rset.getLong("idCentro"), rset.getString("nombreCent")));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        ConexionOracle.desconectar();
        return centros;
    }
    
    public static ArrayList<Localidad> findLocByMun(String idMunicipio) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyectoUdalekuPU");
//        EntityManager em = emf.createEntityManager();
//
//        em.getTransaction().begin();
//
//        Query q = em.createQuery("select l from LOCALIDADES l order by l.nombreLoc",Localidad.class);
//        //q.setParameter("mun", idMunicipio);
//        List<Object[]> result = q.getResultList();
//        for (Object[] resultElement : result) {
//            Long idLocalidad = (Long) resultElement[0];
//            String nombreLoc = (String) resultElement[1];
//            localidades.add(new Localidad(idLocalidad,nombreLoc));
//        }
//
//            em.close();
//            emf.close();
//            return localidades;
//        
        PreparedStatement sentencia;
        ResultSet rset;
        ArrayList<Localidad> localidades=new ArrayList();
        //conectamos
        ConexionOracle.setConexion();

        try {
            sentencia = ConexionOracle.getConexion().prepareStatement("select IDLOCALIDAD, NOMBRELOC from localidades where IDMUNICIPIO=? order by NOMBRELOC");
            sentencia.setString(1, idMunicipio);
            rset = sentencia.executeQuery();
            localidades=new ArrayList();
            while (rset.next()) {
                localidades.add(new Localidad(rset.getLong("IDLOCALIDAD"), rset.getString(2)));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        ConexionOracle.desconectar();
        return localidades;
        }
    
    public static ArrayList<Modelo> findModByCent(String idCentro) {
        PreparedStatement sentencia;
        ResultSet rset;
        ArrayList<Modelo> modelos=new ArrayList();

        //conectamos
        ConexionOracle.setConexion();

        try {
            sentencia = ConexionOracle.getConexion().prepareStatement("select idModelo from Centros_Has_Modelos where idCentro=?");
            sentencia.setString(1, idCentro);
            rset = sentencia.executeQuery();
            while (rset.next()) {
                modelos.add(new Modelo(rset.getString("idModelo")));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        ConexionOracle.desconectar();
        return modelos;
    }
    
    public static ArrayList<Municipio> findMunByProv(String idProvincia) {
        PreparedStatement sentencia;
        ResultSet rset;
        ArrayList<Municipio> municipios=new ArrayList();
        //conectamos
        ConexionOracle.setConexion();

        try {
            sentencia = ConexionOracle.getConexion().prepareStatement("select idMunicipio, nombreMunic from municipios where upper(idProvincia)=? order by nombremunic");
            sentencia.setString(1, idProvincia);
            rset = sentencia.executeQuery();

            while (rset.next()) {
                municipios.add(new Municipio(rset.getLong("idMunicipio"), rset.getString(2)));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        ConexionOracle.desconectar();
        return municipios;
    }
    
    public static ArrayList<Via> findViasByLoc(String idLocalidad) {
        ConexionOracle conn = new ConexionOracle();
        PreparedStatement sentencia;
        ResultSet rset;
        ArrayList<Via> vias=new ArrayList();
        //conectamos
        conn.setConexion();

        try {
            sentencia = conn.getConexion().prepareStatement("select * from vias where idlocalidad=? order by nombreVia");
            sentencia.setString(1, idLocalidad);
            rset = sentencia.executeQuery();

            while (rset.next()) {
                vias.add(new Via(rset.getLong("idVia"), rset.getString("tipoVia"), rset.getString(3)));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        conn.desconectar();
        return vias;
    }
    public static Long findIdSolicitud() throws SQLException {
        ConexionOracle conn = new ConexionOracle();
        Statement sentencia;
        ResultSet rset = null;
        Long idSolicitud = null;
      
        //conectamos
        conn.setConexion();

        try {
                sentencia = ConexionOracle.getConexion().createStatement();
                rset = sentencia.executeQuery("select max(idSolicitud) as idSolicitud from solicitudes");
           while (rset.next()){
                 idSolicitud=rset.getLong("idSolicitud"); 
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }      
        //desconectamos
        conn.desconectar();
        return idSolicitud;
    }

    public static Long findIdDireccion() throws SQLException {
        ConexionOracle conn = new ConexionOracle();
        Statement sentencia;
        ResultSet rset = null;
        Long idDireccion = null;
      
        //conectamos
        conn.setConexion();

        try {
                sentencia = ConexionOracle.getConexion().createStatement();
                rset = sentencia.executeQuery("select max(idDireccion) as idDireccion from direcciones");
           while (rset.next()){
                 idDireccion=rset.getLong("idDireccion"); 
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }      
        //desconectamos
        conn.desconectar();
        return idDireccion;
    }

    public static String findIdInscripcion() {
        ConexionOracle conn = new ConexionOracle();
        Statement sentencia;
        ResultSet rset = null;
        String idInscripcion = null;
      
        //conectamos
        conn.setConexion();

        try {
                sentencia = ConexionOracle.getConexion().createStatement();
                rset = sentencia.executeQuery("select max(idInscripcion) as idInscripcion from inscripciones");
           while (rset.next()){
                 idInscripcion=rset.getString("idInscripcion"); 
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }      
        //desconectamos
        conn.desconectar();
        return idInscripcion;
    }
}
