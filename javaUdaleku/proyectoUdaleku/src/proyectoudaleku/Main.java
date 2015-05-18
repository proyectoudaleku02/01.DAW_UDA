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
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import oracle.jdbc.OracleTypes;
import static org.eclipse.persistence.expressions.ExpressionOperator.all;

public class Main {

    private static boolean periodoIns;
    private static boolean sorteo;
    // Declaración del número máximo de inscripciones por solicitud.
    private static final int maxInscrip = 3;

    private static ArrayList<Via> vias;
    private static ArrayList<Municipio> municipios;
    private static ArrayList<Localidad> localidades;
    private static ArrayList<Centro> centros;
    private static ArrayList<Modelo> modelos;

    private static Provincia provSelected;
    private static Municipio munSelected;
    private static Localidad locSelected;
    private static Via viaSelected;
    private static Centro cenSelected;
    private static Direccion dirSelected;
    private static ArrayList<Modelo> modelosSelected;
    private static Tutor tutorSelected;
    private static Menor menorSelected;
    private static Inscripcion insSelected;
    private static Solicitud solSelected;

    private static Inicio inic;
    private static PanInicio panInic;
    private static PanInscripcion panInscrip;
    private static PanLupa panLupa;
    private static ConfInscrip dConfirmacion;

    private static ConexionOracle conn;
    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet rset;

    public static void main(String[] args) {

        municipios = new ArrayList();
        localidades = new ArrayList();
        vias = new ArrayList();
        centros = new ArrayList();
        modelos = new ArrayList();

        modelosSelected = new ArrayList();
        provSelected = new Provincia();
        munSelected = new Municipio();
        locSelected = new Localidad();
        viaSelected = new Via();
        cenSelected = new Centro();

        //llenarPaPruebas();
        DSimulacion dProv = new DSimulacion(null, true);
        dProv.setVisible(true);
    }

//    // SECCION DE PRUEBAS
//    private static void llenarPaPruebas() {
//        municipios.add(new Municipio());
//        municipios.get(0).setNombremunic("mun1");
//        municipios.add(new Municipio());
//        municipios.get(1).setNombremunic("mun2");
//        municipios.add(new Municipio());
//        municipios.get(2).setNombremunic("mun3");
//
//        localidades.add(new Localidad());
//        localidades.get(0).setNombreloc("loc1");
//        localidades.add(new Localidad());
//        localidades.get(1).setNombreloc("loc2");
//        localidades.add(new Localidad());
//        localidades.get(2).setNombreloc("loc3");
//
//        vias.add(new Via());
//        vias.get(0).setNombrevia("via1");
//        vias.get(0).setTipovia("calle");
//        vias.add(new Via());
//        vias.get(1).setNombrevia("via2");
//        vias.get(1).setTipovia("calle");
//        vias.add(new Via());
//        vias.get(2).setNombrevia("via3");
//        vias.get(2).setTipovia("calle");
//
//        centros.add(new Centro());
//        centros.get(0).setNombrecent("cent1");
//        centros.add(new Centro());
//        centros.get(1).setNombrecent("cent2");
//        centros.add(new Centro());
//        centros.get(2).setNombrecent("cent3");
//
////        insSelected.setMenor(new Menor());insSelected.getMenor().setNombre("a");insSelected.getMenor().setApel1("a");insSelected.getMenor().setApel2("a");
////        insSelected.setMenor(new Menor());insSelected.getMenor().setNombre("b");insSelected.getMenor().setApel1("b");insSelected.getMenor().setApel2("b");
//        modelosSelected.add(new Modelo("A"));
//        modelosSelected.add(new Modelo("B"));
//        modelosSelected.add(new Modelo("C"));
//    }
//
//    public static ArrayList<Via> getVias() {
//        return vias;
//    }
//
//    public static void setVias(ArrayList<Via> vias) {
//        Main.vias = vias;
//    }
//
//    public static ArrayList<Municipio> getMunicipios() {
//        return municipios;
//    }
//
//    public static void setMunicipios(ArrayList<Municipio> municipios) {
//        Main.municipios = municipios;
//    }
//
//    public static ArrayList<Localidad> getLocalidades() {
//        return localidades;
//    }
//
//    public static void setLocalidades(ArrayList<Localidad> localidades) {
//        Main.localidades = localidades;
//    }
//
//    public static ArrayList<Centro> getCentros() {
//        return centros;
//    }
//
//    public static void setCentros(ArrayList<Centro> centros) {
//        Main.centros = centros;
//    }
//
//    public static ArrayList<Modelo> getModelos() {
//        return modelos;
//    }
//
//    public static void setModelos(ArrayList<Modelo> modelos) {
//        Main.modelos = modelos;
//    }
//
//    public static ArrayList<Modelo> getModelosSelected() {
//        return modelosSelected;
//    }
//
//    //////////////////////////// FIN SECCION DE PRUEBAS
    public static void cambioProv(String prov) {
        provSelected = new Provincia();
        provSelected.setIdprovincia(prov);
    }

    // Control de paneles.
    public static void terminarSimulacion(DSimulacion dialogo, String prov, boolean periodoInscripcion, boolean estadoSorteo) {
        dialogo.dispose();

        //Recogida de datos de simulación.
        provSelected.setIdprovincia(prov);
        periodoIns = periodoInscripcion;
        sorteo = estadoSorteo;

        panInic = new PanInicio(provSelected.getIdprovincia(), periodoIns, sorteo);
        inic = new Inicio();
        inic.setContentPane(panInic);
        panInic.setVisible(true);
        inic.setVisible(true);
    }

    public static void verPanInscrip() {
        // Inicialización de la solicitud e inscripción.
        solSelected = new Solicitud();
        insSelected = new Inscripcion();
        // Creación del arbol de objetos que cuelgan de la inscripción.
        crearArbolInscripción();

        panInscrip = new PanInscripcion(provSelected.getIdprovincia());
        inic.getContentPane().setVisible(false);
        inic.setContentPane(panInscrip);
        panInscrip.setVisible(true);
    }

    private static void crearArbolInscripción() {
        // Inicialización de los objetos distintos por cada inscripción.
        menorSelected = new Menor();
        tutorSelected = new Tutor();
        dirSelected = new Direccion();
        viaSelected = new Via();
        locSelected = new Localidad();
        munSelected = new Municipio();
        cenSelected = new Centro();
        // Relación bidireccional de tutor y menor con la inscripción.
        menorSelected.setInscripcion(insSelected);
        insSelected.setMenor(menorSelected);
        tutorSelected.setInscripcion(insSelected);
        insSelected.setTutor(tutorSelected);
        // Relación entre inscripción y dirección.
        insSelected.setDireccion(dirSelected);
        // Relación entre objetos que forman la dirección.
        dirSelected.setIdvia(viaSelected);
        viaSelected.setIdlocalidad(locSelected);
        locSelected.setIdmunicipio(munSelected);
        munSelected.setIdprovincia(provSelected);
        // Relación entre provincia y centro.
        cenSelected.setIdprovincia(provSelected);
        // Relación entre menor y centro.
        menorSelected.setModelo(new Modelo());
    }

    public static void cancelarPanel() {
        inic.getContentPane().setVisible(false);
        inic.setContentPane(panInic);
        panInic.setVisible(true);
    }

    public static void buildLupa(String tipo, String prov, int indice) {

        switch (tipo) {
            case "calles":
                // El índice elegido en el combo box coincide con el del array de localidades.
                panLupa = new PanLupa(tipo, String.valueOf(locSelected.getNombreloc()));
                panLupa.setVisible(true);
                break;
            case "centros":
                if (prov.equalsIgnoreCase("seleccionada")) {
                    panLupa = new PanLupa(tipo, "tu provincia");
                    panLupa.setVisible(true);
                } else {
                    panLupa = new PanLupa(tipo, "otra provincia");
                }
                panLupa.setVisible(true);
                break;

        }
    }

    public static void sendViaToInscripcion(int viaIndex) {
        cancelarLupa();
        viaSelected = vias.get(viaIndex);
        panInscrip.rellenarTfCalle(viaSelected);
    }

    public static void sendCentroToInscripcion(int cenIndex) {
        cancelarLupa();
        cenSelected = centros.get(cenIndex);
        panInscrip.rellenarTfCentro(cenSelected);
    }

    public static void cancelarLupa() {
        panLupa.dispose();
    }

    public static void cancelarDialogo(ConfInscrip dialogo) {
        dialogo.dispose();
    }

    public static boolean controlInscripciones() {
        if (solSelected.getInscripciones().size() < maxInscrip) {
            dConfirmacion = new ConfInscrip(inic, true, solSelected, insSelected);
            dConfirmacion.setVisible(true);
            return true;
        } else {
            return false;
        }
    }

    public static void salir() {
        System.exit(0);
    }

    /**
     *
     * @param idProvincia Método que me devuelve todos los municipios de la
     * provincia seleccionada.
     * @return
     */
    public static ArrayList<Municipio> findMunicipios() {

        //MunicipioJpaController munJpa= new MunicipioJpaController(Persistence.createEntityManagerFactory("udalekuPU"));    
        //municipios=(ArrayList<Municipio>) munJpa.findMunicipioEntities2(idProvincia);
        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {
            sentencia = conn.getConexion().prepareStatement("select idMunicipio, nombreMunic from municipios where upper(idProvincia)=? order by nombremunic");
            sentencia.setString(1, provSelected.getIdprovincia());
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
     * @param idMunicipio Método que me devuelve todas las localidades del
     * municipio seleccionado.
     * @return
     */
    public static ArrayList<Localidad> findLocalidades(String idMunicipio) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyectoUdalekuPU");
        EntityManager em = emf.createEntityManager();
        
        LocalidadJpaController locJpa= new LocalidadJpaController(Persistence.createEntityManagerFactory("proyectoUdalekuPU"));
        
        localidades=(ArrayList<Localidad>) locJpa.findLocalidadEntities();
        

        em.getTransaction().begin();

        Query q = em.createQuery("select loc.* FROM LOCALIDADES loc order by loc.nombreLoc");
        //q.setParameter("mun", idMunicipio);
        List<Object[]> result = q.getResultList();
        for (Object[] resultElement : result) {
            Long idLocalidad = (Long) resultElement[0];
            String nombreLoc = (String) resultElement[1];
            localidades.add(new Localidad(idLocalidad,nombreLoc));
        }

            em.close();
            emf.close();
            return localidades;
//        
//        conn = new ConexionOracle();
//        //conectamos
//        conn.setConexion();
//
//        try {
//            sentencia = conn.getConexion().prepareStatement("select IDLOCALIDAD, NOMBRELOC from localidades where IDMUNICIPIO=? order by NOMBRELOC");
//            sentencia.setString(1, idMunicipio);
//            rset = sentencia.executeQuery();
//
//            while (rset.next()) {
//                localidades.add(new Localidad(rset.getLong("IDLOCALIDAD"), rset.getString(2)));
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
//        }
//        //desconectamos
//        conn.desconectar();

        }
        /**
         *
         * @param idLocalidad Método que devuelva todas las vías perteneciente a
         * la localidad seleccionada
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
                vias.add(new Via(rset.getLong("idVia"), rset.getString("tipoVia"), rset.getString(3)));
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
     * @param seleccion si ha seleccionado la misma provincia por la que ha
     * entrado se cargan los centros de esa provincia (en cuyo caso seleccion =
     * idProvincia) sinocarga los restos que no esten en la provincia
     * seleccionada al comienzo de la aplicacion
     * @return
     */
    public static ArrayList<Centro> findCentros(String idProvincia) {

        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {
            if (idProvincia.equalsIgnoreCase("ARB") || idProvincia.equalsIgnoreCase("GZK") || idProvincia.equalsIgnoreCase("BZK")) {
                sentencia = conn.getConexion().prepareStatement("select idCentro, nombreCent from centros where upper(idProvincia)=? order by nombreCent");
                sentencia.setString(1, idProvincia);
                rset = sentencia.executeQuery();
            } else {
                sentencia = conn.getConexion().prepareStatement("select idCentro, nombreCent from centros where upper(idProvincia)<>? order by nombreCent");
                sentencia.setString(1, provSelected.getIdprovincia());
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
     * @return sentencia para que devuelva todos los modelos que se imparten en
     * el centro seleccionado
     */
    public static ArrayList<Modelo> findModelosCentro(Long idCentro) {
        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {
            sentencia = conn.getConexion().prepareStatement("select idModelo from Centros_Has_Modelos where idCentro=?");
            sentencia.setLong(1, idCentro);
            rset = sentencia.executeQuery();
            while (rset.next()) {
                modelos.add(new Modelo(rset.getString("idModelo")));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        conn.desconectar();
        return modelosSelected;
    }

    // Construccion de objetos para registros en base de datos;
    public static void constTutor(String dni, String nombre, String apel1, String apel2, ArrayList<String> telefonos) throws Exception {
        // Llenamos los datos del tutor
        tutorSelected.setDni(dni);
        tutorSelected.setNombre(nombre);
        tutorSelected.setApel1(apel1);
        tutorSelected.setApel2(apel2);
        tutorSelected.setTelefonos(telefonos);
    }

    public static void constMenor(String dni, String nombre, String apel1, String apel2, String sexo, String fechaNac, String discapacidad, String modelo) throws Exception {
        // Buscamos el modelo educativo seleccionado.
        for (int x = 0; x < modelosSelected.size(); x++) {
            if (modelosSelected.get(x).getIdmodelo().equalsIgnoreCase(modelo)) {
                menorSelected.setModelo(modelosSelected.get(x));
            }
        }
        // Llenamos los datos del menor
        menorSelected.setDni(dni);
        menorSelected.setNombre(nombre);
        menorSelected.setApel1(apel1);
        menorSelected.setApel2(apel2);
        menorSelected.setSexo(sexo);
        menorSelected.setFechaNac(parseFechaJava(fechaNac));
        menorSelected.setDiscapacidad(discapacidad);
    }

    public static void constDireccion(String cp, String numero, String letra, String piso, String escalera, String mano) throws Exception {
        dirSelected.setCp(cp);
        dirSelected.setNumdir(numero);
        dirSelected.setLetra(letra);
        dirSelected.setPiso(piso);
        dirSelected.setEscalera(escalera);
        dirSelected.setMano(mano);

    }

    private static Date parseFechaJava(String fechaNac) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(fechaNac);
    }

    public static void inscribir(String tipo) {
        // Relacionamos bidireccionalmente la inscripción con la solicitud.
        solSelected.getInscripciones().add(insSelected);
        insSelected.setSolicitud(solSelected);
        ////
        /// IDSOLICITUD / IDINSCRIPCIÓN
        ////
        dConfirmacion.mostrarHecho(tipo);
        cancelarDialogo(dConfirmacion);
        if (tipo.equalsIgnoreCase("end")) {
            cancelarPanel();
        }

    }

    public static void insertDireccion(int num, String letra, int piso, String escalera, String mano, String cp, Long idVia) throws SQLException {
        // Ejecución de un procedimiento contenido dentro del paquete que gestiona las altas de inscripciones
        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        String sql = "{call alta_inscripciones.insertar_direccion(?,?,?,?,?,?,?)}";
        CallableStatement cs = conexion.prepareCall(sql);
        try {
            // Cargamos los parametros de entrada IN
            cs.setInt(1, num);
            cs.setString(2, letra);
            cs.setInt(3, piso);
            cs.setString(4, escalera);
            cs.setString(5, mano);
            cs.setString(6, cp);
            cs.setLong(7, idVia);

            // la select devuelve datos (parámetro de salida)
            cs.registerOutParameter(1, OracleTypes.NUMBER);
            cs.registerOutParameter(2, OracleTypes.VARCHAR);

            // Con getObject Obtenemos un valor generico al que posteriormente se le hará cast para convertirlo en el tipo adecuado en este caso ResultSet
            rset = (ResultSet) cs.getObject(1);
            // Ejecutamos
            cs.execute();

            //desconectamos
            conn.desconectar();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @throws SQLException constructor de Solicitud. llama a un procedimiento
     * definido dentro de un paquete de base de datos mediante el cual insertará
     * ala solicitud
     */
    public static void insertSolicitud() throws SQLException {
        // Ejecución de un procedimiento contenido dentro del paquete que gestiona las altas de inscripciones
        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        String sql = "{call alta_inscripciones.insertar_solicitud(?)}";
        CallableStatement cs = conexion.prepareCall(sql);
        try {
            // Cargamos los parametros de entrada IN
            cs.setString(1, "Solicitud realizada a espera de sorteo");

            // la select devuelve datos (parámetro de salida)
            cs.registerOutParameter(1, OracleTypes.NUMBER);
            cs.registerOutParameter(2, OracleTypes.VARCHAR);

            // Con getObject Obtenemos un valor generico al que posteriormente se le hará cast para convertirlo en el tipo adecuado en este caso ResultSet
            rset = (ResultSet) cs.getObject(1);
            // Ejecutamos
            cs.execute();

            //desconectamos
            conn.desconectar();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param idSolicitud
     * @param numero
     * @param idDireccion
     * @throws SQLException
     */
    public static void insertInscrip(long idSolicitud, int numero, long idDireccion) throws SQLException {
        // Ejecución de un procedimiento contenido dentro del paquete que gestiona las altas de inscripciones
        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        String sql = "{call alta_inscripciones.insertar_inscripcion(?,?,?)}";
        CallableStatement cs = conexion.prepareCall(sql);
        try {
            // Cargamos los parametros de entrada IN
            cs.setLong(1, idSolicitud);
            cs.setInt(2, numero);
            cs.setLong(3, idDireccion);

            // la select devuelve datos (parámetro de salida)
            cs.registerOutParameter(1, OracleTypes.VARCHAR);
            cs.registerOutParameter(2, OracleTypes.VARCHAR);

            // Con getObject Obtenemos un valor generico al que posteriormente se le hará cast para convertirlo en el tipo adecuado en este caso ResultSet
            rset = (ResultSet) cs.getObject(1);
            // Ejecutamos
            cs.execute();

            //desconectamos
            conn.desconectar();
        } catch (Exception e) {
        }
    }

}
