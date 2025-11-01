package archivosfragmentados.service;

import archivosfragmentados.model.Entidad;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Servicio especializado en el manejo de datos CSV.
 * Las cabeceras ya fueron omitidas en el LectorArchivos.
 */
public class ManejadorCSV {
    
    private static final String CABECERA_ESTANDAR = "ID,Nombre,Ciudad";
    
    /**
     * Unifica los datos de las entidades (las cabeceras ya fueron omitidas).
     * 
     * @param entidades Mapa de entidades leídas (sin cabeceras)
     * @return Lista de datos unificados
     */
    public List<String> procesarDatos(Map<String, Entidad> entidades) {
        List<String> datosUnificados = new ArrayList<>();
        
        for (Entidad entidad : entidades.values()) {
            datosUnificados.addAll(entidad.getLineasDatos());
        }
        
        return datosUnificados;
    }
    
    /**
     * Prepara los datos finales CON cabecera para el archivo de salida.
     * 
     * @param datosSinDuplicados Lista de datos procesados
     * @return Lista con cabecera al inicio
     */
    public List<String> prepararArchivoFinal(List<String> datosSinDuplicados) {
        List<String> datosFinales = new ArrayList<>();
        datosFinales.add(CABECERA_ESTANDAR);
        datosFinales.addAll(datosSinDuplicados);
        
        // System.out.println("CABECERA AGREGADA AL ARCHIVO FINAL: " + CABECERA_ESTANDAR);
        
        return datosFinales;
    }
}