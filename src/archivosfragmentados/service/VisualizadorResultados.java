package archivosfragmentados.service;

import archivosfragmentados.model.Entidad;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Servicio encargado de mostrar resultados y estadisticas del procesamiento.
 */
public class VisualizadorResultados {
    
    /**
     * Muestra el encabezado del programa.
     */
    public void mostrarEncabezado() {
        System.out.println("================================================================");
        System.out.println("           PROCESADOR DE ARCHIVOS FRAGMENTADOS v1.0           ");
        System.out.println("================================================================");
        System.out.println("Este programa reconstruye archivos fragmentados eliminando");
        System.out.println("duplicados y unificando el contenido en un archivo maestro.");
        System.out.println("================================================================");
    }
    
    /**
     * Muestra un separador visual.
     */
    public void mostrarSeparador() {
        System.out.println("\n" + "=".repeat(64));
    }
    
    /**
     * Muestra un resumen detallado de las entidades encontradas.
     * 
     * @param entidades Mapa de entidades procesadas
     */
    public void mostrarResumenDetallado(Map<String, Entidad> entidades) {
        mostrarSeparador();
        System.out.println("RESUMEN DE ENTIDADES ENCONTRADAS");
        mostrarSeparador();
        
        System.out.printf("%-20s %-15s %-20s%n", "ENTIDAD", "ARCHIVOS", "LINEAS TOTALES");
        System.out.println("-".repeat(64));
        
        int totalLineas = 0;
        for (Entidad entidad : entidades.values()) {
            int lineasEntidad = entidad.getLineasDatos().size();
            totalLineas += lineasEntidad;
            System.out.printf("%-20s %-15s %-20d%n", 
                entidad.getNombre(), "Multiples", lineasEntidad);
        }
        
        System.out.println("-".repeat(64));
    }
    
    /**
     * Muestra el analisis de duplicados encontrados.
     * 
     * @param resultado Resultado del analisis de duplicados
     */
    public void mostrarAnalisisDuplicados(AnalizadorDatos.ResultadoAnalisis resultado) {
        mostrarSeparador();
        System.out.println("ANALISIS DE DUPLICADOS");
        mostrarSeparador();
        
        if (resultado.getDuplicados().isEmpty()) {
            System.out.println("EXCELENTE: No se encontraron lineas duplicadas.");
        } else {
            System.out.println("Se encontraron " + resultado.getDuplicados().size() + " lineas duplicadas:");
            System.out.println();
            
            int contador = 1;
            for (String duplicado : resultado.getDuplicados()) {
                int repeticiones = resultado.getConteoLineas().get(duplicado);
                System.out.printf("%d. [%d repeticiones] %s%n", 
                    contador++, repeticiones, 
                    duplicado.length() > 60 ? duplicado.substring(0, 60) + "..." : duplicado);
                
                if (contador > 10) {
                    int restantes = resultado.getDuplicados().size() - 10;
                    System.out.println("... y " + restantes + " duplicados mas.");
                    break;
                }
            }
        }
    }
    
    /**
     * Muestra el resultado final del procesamiento.
     * 
     * @param archivoSalida Ruta del archivo generado
     * @param lineasOriginales Numero de lineas originales
     * @param lineasFinales Numero de lineas finales
     * @param duplicadosEliminados Numero de duplicados eliminados
     */

public void mostrarResultadoFinal(Path archivoSalida, int lineasOriginales, 
                                 int lineasFinales, int duplicadosEliminados) {
    mostrarSeparador();
    System.out.println("PROCESAMIENTO COMPLETADO EXITOSAMENTE");
    mostrarSeparador();
    
    System.out.printf("Lineas procesadas:      %d%n", lineasOriginales);
    System.out.printf("Duplicados eliminados:  %d%n", duplicadosEliminados);
    System.out.printf("Lineas finales:         %d%n", lineasFinales);
    
    double porcentajeReduccion = duplicadosEliminados > 0 ? 
        (duplicadosEliminados * 100.0 / lineasOriginales) : 0.0;
    System.out.printf("Reduccion de datos:     %.1f%%%n", porcentajeReduccion);
    
    System.out.println();
    System.out.println("Archivo generado: " + archivoSalida.getFileName());
    System.out.println("Ubicacion: " + archivoSalida.toAbsolutePath());
}
    
    /**
     * Muestra el contenido del archivo final generado.
     * 
     * @param contenido Lista con el contenido del archivo
     */
    public void mostrarContenidoArchivo(List<String> contenido) {
        mostrarSeparador();
        System.out.println("CONTENIDO DEL ARCHIVO GENERADO");
        mostrarSeparador();
        
        if (contenido.isEmpty()) {
            System.out.println("El archivo esta vacio.");
            return;
        }
        
        System.out.printf("Mostrando las primeras %d lineas:%n%n", 
            Math.min(15, contenido.size()));
        
        for (int i = 0; i < Math.min(15, contenido.size()); i++) {
            System.out.printf("%3d: %s%n", i + 1, contenido.get(i));
        }
        
        if (contenido.size() > 15) {
            System.out.printf("%n... y %d lineas adicionales.%n", contenido.size() - 15);
        }
    }
}