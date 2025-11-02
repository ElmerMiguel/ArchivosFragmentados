package archivosfragmentados.service;

import archivosfragmentados.model.Entidad;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Servicio especializado en el manejo de datos CSV completamente dinámico.
 */
public class ManejadorCSV {
    
    private String cabeceraDetectada = null;
    
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
     * Establece la cabecera detectada del primer archivo procesado.
     * 
     * @param cabecera Cabecera detectada
     */
    public void establecerCabecera(String cabecera) {
        if (this.cabeceraDetectada == null && cabecera != null && !cabecera.trim().isEmpty()) {
            this.cabeceraDetectada = cabecera.trim();
            System.out.println("CABECERA DETECTADA PARA ARCHIVO FINAL: " + this.cabeceraDetectada);
        }
    }
    
    /**
     * Prepara los datos finales CON la cabecera detectada dinámicamente.
     * 
     * @param datosSinDuplicados Lista de datos procesados
     * @return Lista con cabecera dinámica al inicio
     */
    public List<String> prepararArchivoFinal(List<String> datosSinDuplicados) {
        List<String> datosFinales = new ArrayList<>();
        
        if (cabeceraDetectada != null) {
            datosFinales.add(cabeceraDetectada);
        } else {
            System.out.println("ADVERTENCIA: No se detectó cabecera. Archivo sin cabecera.");
        }
        
        datosFinales.addAll(datosSinDuplicados);
        
        return datosFinales;
    }
    
    /**
     * Obtiene la cabecera detectada.
     * 
     * @return Cabecera detectada o null
     */
    public String getCabeceraDetectada() {
        return cabeceraDetectada;
    }
}