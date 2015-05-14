package proyectoudaleku;

import Modelo.BD.LocalidadJpaController;
import Modelo.BD.ModeloJpaController;
import Modelo.BD.MunicipioJpaController;
import Modelo.BD.ViaJpaController;
import Modelo.UML.*;
import conexionoracle.ConexionOracle;
import gui.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Persistence;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Main {

    private static Inicio inic;
    private static panInicio panInic;
    private static panInscripcion panInscrip;
    private static panLupa panLupa;
    private static confInscrip dConfirmacion;

    private static ConexionOracle conn;
    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet rset;

    private static ArrayList<Via> vias;
    private static ArrayList<Municipio> municipios;
    private static ArrayList<Localidad> localidades;
    private static ArrayList<Centro> centros;
    
    private static Provincia provSelected;
    private static Municipio munSelected;
    private static Localidad locSelected;
    private static Via viaSelected;
    private static Centro centSelected;
    private static Direccion dirSelected;
    private static ArrayList<Modelo> modelosSelected;
    private static Tutor tutorSelected;
    private static Menor menorSelected;
    private static Inscripcion insSelected;
    private static Solicitud solSelected;
    

    public static void main(String[] args) {
        panInic = new panInicio();
        inic = new Inicio();
        inic.setContentPane(panInic);
        panInic.setVisible(true);
        inic.setVisible(true);
        
        municipios = new ArrayList();
        localidades= new ArrayList();
        vias=new ArrayList();
        centros=new ArrayList();
        
        modelosSelected=new ArrayList();
        provSelected=new Provincia();provSelected.setNombreprov("ARB");
        munSelected=new Municipio();locSelected=new Localidad();
        viaSelected=new Via();centSelected=new Centro();
        

        llenarPaPruebas();
    }
    
    private static void llenarPaPruebas() {
        municipios.add(new Municipio());municipios.get(0).setNombremunic("mun1");
        municipios.add(new Municipio());municipios.get(1).setNombremunic("mun2");
        municipios.add(new Municipio());municipios.get(2).setNombremunic("mun3");
        
        localidades.add(new Localidad());localidades.get(0).setNombreloc("loc1");
        localidades.add(new Localidad());localidades.get(1).setNombreloc("loc2");
        localidades.add(new Localidad());localidades.get(2).setNombreloc("loc3");
        
        vias.add(new Via());vias.get(0).setNombrevia("via1");vias.get(0).setTipovia("calle");
        vias.add(new Via());vias.get(1).setNombrevia("via2");vias.get(1).setTipovia("calle");
        vias.add(new Via());vias.get(2).setNombrevia("via3");vias.get(2).setTipovia("calle");
        
        centros.add(new Centro());centros.get(0).setNombrecent("cent1");
        centros.add(new Centro());centros.get(1).setNombrecent("cent2");
        centros.add(new Centro());centros.get(2).setNombrecent("cent3");
        
//        insSelected.setMenor(new Menor());insSelected.getMenor().setNombre("a");insSelected.getMenor().setApel1("a");insSelected.getMenor().setApel2("a");
//        insSelected.setMenor(new Menor());insSelected.getMenor().setNombre("b");insSelected.getMenor().setApel1("b");insSelected.getMenor().setApel2("b");
        
        modelosSelected.add(new Modelo("A"));modelosSelected.add(new Modelo("B"));modelosSelected.add(new Modelo("C"));
    }
    
    public static Provincia getProvSelected() {
        return provSelected;
    }

    public static void setProvSelected(Provincia provSelected) {
        Main.provSelected = provSelected;
    }

    public static Municipio getMunSelected() {
        return munSelected;
    }

    public static void setMunSelected(Municipio munSelected) {
        Main.munSelected = munSelected;
    }

    public static Localidad getLocSelected() {
        return locSelected;
    }

    public static void setLocSelected(Localidad locSelected) {
        Main.locSelected = locSelected;
    }
    
    public static Via getViaSelected() {
        return viaSelected;
    }

    public static void setViaSelected(Via viaSelected) {
        Main.viaSelected = viaSelected;
    }

    public static Centro getCentSelected() {
        return centSelected;
    }

    public static void setCentSelected(Centro centSelected) {
        Main.centSelected = centSelected;
        //findModelosCentro(centSelected.getIdcentro());
    }

    public static ArrayList<Modelo> getModelosSelected() {
        return modelosSelected;
    }

    public static void setModelosSelected(ArrayList<Modelo> modelosSelected) {
        Main.modelosSelected = modelosSelected;
    }

    public static ArrayList<Municipio> getMunicipios() {
        return municipios;
    }

    public static ArrayList<Localidad> getLocalidades() {
        return localidades;
    }
   
    public static ArrayList<Via> getVias() {
        return vias;
    }    

    public static ArrayList<Centro> getCentros() {
        return centros;
    }  

    public static Tutor getTutorSelected() {
        return tutorSelected;
    }

    public static void setTutorSelected(Tutor tutorSelected) {
        Main.tutorSelected = tutorSelected;
    }

    public static Menor getMenorSelected() {
        return menorSelected;
    }

    public static void setMenorSelected(Menor menorSelected) {
        Main.menorSelected = menorSelected;
    }

    public static Inscripcion getInsSelected() {
        return insSelected;
    }

    public static void setInsSelected(Inscripcion insSelected) {
        Main.insSelected = insSelected;
    }

    public static Solicitud getSolSelected() {
        return solSelected;
    }

    public static void setSolSelected(Solicitud solSelected) {
        Main.solSelected = solSelected;
    }      
    
    // Control de paneles.
    public static void verPanInscrip() {
        insSelected=new Inscripcion();
        solSelected=new Solicitud();
        panInscrip = new panInscripcion();
        inic.getContentPane().setVisible(false);
        inic.setContentPane(panInscrip);
        panInscrip.setVisible(true);
    }

    public static void cancelarPanel() {
        inic.getContentPane().setVisible(false);
        inic.setContentPane(panInic);
        panInic.setVisible(true);
    }
    
    public static void buildLupa(String tipo,String loc,String mun) {
        switch(tipo){
            case "calles":
                panLupa=new panLupa(tipo, loc);
                panLupa.setVisible(true);
                break;
            case "centros":
                panLupa=new panLupa(tipo, mun);
                panLupa.setVisible(true);                
                break;
    }
    }
    
    public static void sendViaToInscripcion() {
        cancelarLupa();
        panInscrip.rellenarTfCalle();
    }
    
    public static void sendCentroToInscripcion() {
        cancelarLupa();
        panInscrip.rellenarTfCentro();
    }
        
    public static void cancelarLupa() {
        panLupa.dispose();
    }

    public static void fillComboMun(JComboBox cbMunicipio) {
        // Vaciamos lista desplegable de municipios.
        //cbMunicipio.removeAllItems();
        // Llenar lista desplegable de municipios.
        for(int x=0;x<municipios.size();x++)
        {
            cbMunicipio.insertItemAt(municipios.get(x).getNombremunic(), x);
        }
    }

    public static void fillComboLoc(JComboBox cbLocalidad) {
        // Vaciamos lista desplegable de municipios.
        //cbLocalidad.removeAllItems();
        // Llenar lista desplegable de municipios.
        for(int x=0;x<localidades.size();x++)
        {
            cbLocalidad.insertItemAt(localidades.get(x).getNombreloc(), x);
        }
    }
    
    public static void controlInscripciones() {
        dConfirmacion = new confInscrip(inic, true, solSelected.getInscripciones().size());
        dConfirmacion.setVisible(true);
    }
    
    public static void salir() {
        System.exit(0);
    }
/**
 * 
 * @param idProvincia
 * Método que me devuelve todos los municipios de la provincia seleccionada.
 * @return 
 */
    public static ArrayList<Municipio> findMunicipios(String idProvincia) {

        //MunicipioJpaController munJpa= new MunicipioJpaController(Persistence.createEntityManagerFactory("udalekuPU"));    
        //municipios=(ArrayList<Municipio>) munJpa.findMunicipioEntities2(idProvincia);
        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {
            sentencia = conn.getConexion().prepareStatement("select idMunicipio, nombreMunic from municipios where upper(idProvincia)=? order by nombremunic");
            sentencia.setString(1, idProvincia);
            rset = sentencia.executeQuery();

            while (rset.next()) {
                municipios.add(new Municipio(rset.getLong("idMunicipio"), rset.getString(2)));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        conn.desconectar();
        return municipios;
    }
    /**
     * 
     * @param idMunicipio
     * Método que me devuelve todas las localidades del municipio seleccionado.
     * @return 
     */
        public static ArrayList<Localidad> findLocalidades(String idMunicipio) {
        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {
            sentencia = conn.getConexion().prepareStatement("select IDLOCALIDAD, NOMBRELOC from localidades where IDMUNICIPIO=? order by NOMBRELOC");
            sentencia.setString(1, idMunicipio);
            rset = sentencia.executeQuery();

            while (rset.next()) {
                localidades.add(new Localidad(rset.getLong("IDLOCALIDAD"), rset.getString(2)));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        conn.desconectar();
        return localidades;
    }
        /**
         * 
         * @param idLocalidad
         * Método que devuelva todas las vías perteneciente a la localidad seleccionada
         * @return 
         */
   public static ArrayList<Via> findVias(String idLocalidad) {
        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {
            sentencia = conn.getConexion().prepareStatement("select * from vias where idlocalidad=? order by nombreVia");
            sentencia.setString(1, idLocalidad);
            rset = sentencia.executeQuery();

            while (rset.next()) {
                vias.add(new Via(rset.getLong("idVia"), rset.getString("tipoVia"),rset.getString(3)));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        conn.desconectar();
        return vias;
    }
   /**
    * 
    * @param idProvincia
    * @param seleccion
    * si ha seleccionado la misma provincia por la que ha entrado se cargan los centros de esa provincia (en cuyo caso seleccion = idProvincia)
    * sinocarga los restos que no esten en la provincia seleccionada al comienzo de la aplicacion
    * @return 
    */
      public static ArrayList<Centro> findCentros(String idProvincia, String seleccion) {

        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {           
            if(seleccion.equalsIgnoreCase("ARB")||seleccion.equalsIgnoreCase("GZK")||seleccion.equalsIgnoreCase("BZK")){
            sentencia = conn.getConexion().prepareStatement("select idCentro, nombreCent from centros where upper(idProvincia)=? order by nombreCent");
            sentencia.setString(1, idProvincia);
            rset = sentencia.executeQuery();
            }else{               
            sentencia = conn.getConexion().prepareStatement("select idCentro, nombreCent from centros where upper(idProvincia)<>? order by nombreCent");
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
        conn.desconectar();
        return centros;
    }
      /**
       * 
       * @param idCentro
       * @return 
       *  sentencia para que devuelva todos los modelos que se imparten en el centro seleccionado
       */
      public static ArrayList<Modelo> findModelosCentro(Long idCentro){
           conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {
            sentencia = conn.getConexion().prepareStatement("select idModelo from Centros_Has_Modelos where idCentro=?");
            sentencia.setLong(1, idCentro);
            rset = sentencia.executeQuery();
            ModeloJpaController modJpa=new ModeloJpaController(Persistence.createEntityManagerFactory("udalekuPU"));
            while (rset.next()) {
                modelosSelected.add(modJpa.findModelo(rset.getString(1)));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        conn.desconectar();
        return modelosSelected;
      }
    

    public static void constTutor(String dni, String nombre, String apel1, String apel2) throws Exception{
        
    }

    public static void constMenor(String dni, String nombre, String apel1, String apel2, String sexo, String fechaNac, String discapacidad) throws Exception{
        menorSelected=new Menor(dni, nombre, apel1, apel2, sexo, parseFechaJava(fechaNac));
    }

    public static void constDireccion(String municipio, String localidad, String calle, String cp, String numero, String letra, String piso, String escalera, String mano, ArrayList<String> telefonos) throws Exception{
        dirSelected = new Direccion(cp,numero,letra,piso,escalera,mano,viaSelected);
    }

    private static Date parseFechaJava(String fechaNac) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(fechaNac); 
    }

}
