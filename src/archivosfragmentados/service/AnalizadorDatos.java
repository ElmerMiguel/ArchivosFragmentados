package archivosfragmentados.service;

import archivosfragmentados.model.Entidad;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio especializado en analisis y procesamiento de datos.
 */
public class AnalizadorDatos {
    
    /**
     * Unifica todos los datos de las entidades en una sola lista.
     * 
     * @param entidades Mapa de entidades
     * @return Lista unificada de datos
     */
    public List<String> unificarDatos(Map<String, Entidad> entidades) {
        return entidades.values().stream()
                .flatMap(entidad -> entidad.getLineasDatos().stream())
                .collect(Collectors.toList());
    }
    
    /**
     * Analiza duplicados y elimina repeticiones.
     * 
     * @param datos Lista de datos originales
     * @return Resultado del analisis con datos procesados
     */
    public ResultadoAnalisis analizarYEliminarDuplicados(List<String> datos) {
        Map<String, Integer> conteoLineas = new LinkedHashMap<>();
        List<String> duplicados = new ArrayList<>();
        
        for (String linea : datos) {
            if (!linea.trim().isEmpty()) {
                int count = conteoLineas.getOrDefault(linea, 0) + 1;
                conteoLineas.put(linea, count);
                
                if (count == 2) {
                    duplicados.add(linea);
                }
            }
        }
        
        List<String> datosSinDuplicados = datos.stream()
                .filter(linea -> !linea.trim().isEmpty())
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));
        
        return new ResultadoAnalisis(datosSinDuplicados, duplicados, conteoLineas);
    }
    
    /**
     * Clase para encapsular el resultado del analisis de datos.
     */
    public static class ResultadoAnalisis {
        private final List<String> datosSinDuplicados;
        private final List<String> duplicados;
        private final Map<String, Integer> conteoLineas;
        
        public ResultadoAnalisis(List<String> datosSinDuplicados, List<String> duplicados, 
                               Map<String, Integer> conteoLineas) {
            this.datosSinDuplicados = datosSinDuplicados;
            this.duplicados = duplicados;
            this.conteoLineas = conteoLineas;
        }
        
        public List<String> getDatosSinDuplicados() { return datosSinDuplicados; }
        public List<String> getDuplicados() { return duplicados; }
        public Map<String, Integer> getConteoLineas() { return conteoLineas; }
    }
}