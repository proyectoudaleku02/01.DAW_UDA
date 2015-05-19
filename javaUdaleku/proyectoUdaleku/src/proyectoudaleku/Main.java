package proyectoudaleku;

import Modelo.BD.*;
import Modelo.UML.*;
import conexionoracle.ConexionOracle;
import gui.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    private static boolean periodoIns;
    private static boolean sorteo;
    private static String situacion = "pendiente";
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
        // Inicialización de la solicitud y de la inscripción.
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
        vias = new ArrayList();
        centros = new ArrayList();
        locSelected = localidades.get(indice);
        switch (tipo) {
            case "calles":
                // El índice elegido en el combo box coincide con el del array de localidades.
                panLupa = new PanLupa(tipo, null, locSelected.getIdlocalidad().toString());
                panLupa.setVisible(true);
                break;
            case "centros":
                if (prov.equalsIgnoreCase("seleccionada")) {
                    panLupa = new PanLupa(tipo, "tu provincia", provSelected.getIdprovincia());
                    panLupa.setVisible(true);
                } else {
                    panLupa = new PanLupa(tipo, "otra provincia", provSelected.getIdprovincia());
                }
                panLupa.setVisible(true);
                break;

        }
    }

    public static void sendViaToInscripcion(int viaIndex) throws SQLException {
        cancelarLupa();
        viaSelected = vias.get(viaIndex);
        panInscrip.rellenarTfCalle(viaSelected);
    }

    public static void sendCentroToInscripcion(int cenIndex) {
        cancelarLupa();
        cenSelected = centros.get(cenIndex);
        modelos = findModByCent(cenSelected.getIdcentro().toString());
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
            dConfirmacion = new ConfInscrip(inic, true, solSelected, insSelected, maxInscrip);
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
    public static ArrayList<Municipio> findMunByProv() {
        municipios = ConsultasBD.findMunByProv(provSelected.getIdprovincia());
        return municipios;
    }

    /**
     *
     * @param idMunicipio Método que me devuelve todas las localidades del
     * municipio seleccionado.
     * @return
     */
    public static ArrayList<Localidad> findLocByMun(String idMunicipio) {
        localidades = ConsultasBD.findLocByMun(idMunicipio);
        return localidades;
    }

    /**
     *
     * @param idLocalidad Método que devuelva todas las vías perteneciente a la
     * localidad seleccionada
     * @return
     */
    public static ArrayList<Via> findViasByLoc(String idLocalidad) {
        vias = ConsultasBD.findViasByLoc(idLocalidad);
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
    public static ArrayList<Centro> findCentByProv(String idProvincia, String text) {
        centros = ConsultasBD.findCentByProv(idProvincia, text);
        return centros;
    }

    /**
     *
     * @param idCentro
     * @return sentencia para que devuelva todos los modelos que se imparten en
     * el centro seleccionado
     */
    public static ArrayList<Modelo> findModByCent(String idCentro) {
        modelos = ConsultasBD.findModByCent(idCentro);
        return modelos;
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

    public static boolean inscribir(String tipo) {
        // Relacionamos bidireccionalmente la inscripción con la solicitud.
        solSelected.getInscripciones().add(insSelected);
        insSelected.setSolicitud(solSelected);
        if (tipo.equalsIgnoreCase("end")) {
            for (int x = 0; x < solSelected.getInscripciones().size(); x++) {
                try {
                    AltasBD.insertSolicitud(situacion);
                    AltasBD.insertDireccion(dirSelected.getNumdir(), dirSelected.getLetra(), dirSelected.getPiso(), dirSelected.getEscalera(), dirSelected.getMano(), dirSelected.getCp(), viaSelected.getIdvia());
                    AltasBD.insertInscrip(ConsultasBD.findIdSolicitud(), solSelected.getInscripciones().size(), ConsultasBD.findIdDireccion());
                    AltasBD.insertTutor(tutorSelected.getDni(), tutorSelected.getNombre(), tutorSelected.getApel1(), tutorSelected.getApel2(), ConsultasBD.findIdInscripcion());
                    AltasBD.insertTelefono(ConsultasBD.findLastSolicitante(),tutorSelected.getTelefonos());
                    if (ConsultasBD.findMenor(menorSelected.getDni(), menorSelected.getNombre(), menorSelected.getApel1(), menorSelected.getApel2(), menorSelected.getSexo(), menorSelected.getFechaNac(), menorSelected.getDiscapacidad(), cenSelected.getIdcentro(),menorSelected.getModelo().getIdmodelo())) {
                        return false;//el menor ya esta dado de alta no se puede otra vez 
                    } else {
                        //faltaria pasarle el idModelo aqui
                        AltasBD.insertMenor(menorSelected.getDni(), menorSelected.getNombre(), menorSelected.getApel1(), menorSelected.getApel2(), menorSelected.getSexo(), menorSelected.getFechaNac(), menorSelected.getDiscapacidad(), ConsultasBD.findIdInscripcion(),cenSelected.getIdcentro(),menorSelected.getModelo().getIdmodelo());
                    }
                 
               } catch (SQLException ex) {
                    return false;
                }
            }

        }
        dConfirmacion.mostrarHecho(tipo);
        cancelarDialogo(dConfirmacion);
        cancelarPanel();
        return true;
    }
}
