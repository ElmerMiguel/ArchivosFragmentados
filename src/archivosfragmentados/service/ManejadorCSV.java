package archivosfragmentados.service;

import archivosfragmentados.model.Entidad;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio especializado en el manejo de datos CSV.
 * Omite la primera línea de CADA ARCHIVO individualmente.
 */
public class ManejadorCSV {
    
    /**
     * Procesa las entidades y omite la primera línea de cada una.
     * 
     * @param entidades Mapa de entidades leídas
     * @return Lista de datos sin cabeceras y sin líneas vacías
     */
    public List<String> procesarCabeceras(Map<String, Entidad> entidades) {
        List<String> todosSinCabeceras = new ArrayList<>();
        
        for (Entidad entidad : entidades.values()) {
            List<String> lineasEntidad = entidad.getLineasDatos();
            
            if (!lineasEntidad.isEmpty()) {
                // Mostrar la primera línea que se omitirá
                System.out.println("PRIMERA LINEA OMITIDA DE " + entidad.getNombre() + ": " + lineasEntidad.get(0));
                
                // Omitir la primera línea y agregar el resto (filtrando vacías)
                List<String> sinPrimera = lineasEntidad.stream()
                    .skip(1)
                    .filter(linea -> !linea.trim().isEmpty())
                    .collect(Collectors.toList());
                
                todosSinCabeceras.addAll(sinPrimera);
            }
        }
        
        return todosSinCabeceras;
    }
}