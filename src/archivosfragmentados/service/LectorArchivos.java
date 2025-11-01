package archivosfragmentados.service;

import archivosfragmentados.model.Entidad;
import archivosfragmentados.util.ValidadorArchivos;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio encargado de leer archivos fragmentados de entidades.
 */
public class LectorArchivos {
    
    private final ValidadorArchivos validador;
    
    public LectorArchivos() {
        this.validador = new ValidadorArchivos();
    }
    
    /**
     * Lee todos los archivos CSV fragmentados de un directorio.
     * 
     * @param directorio Directorio que contiene los archivos fragmentados
     * @return Mapa de entidades con sus datos consolidados
     * @throws IOException Si ocurre un error al leer los archivos
     */
    public Map<String, Entidad> leerArchivosFragmentados(Path directorio) throws IOException {
        if (!Files.exists(directorio) || !Files.isDirectory(directorio)) {
            throw new IOException("El directorio especificado no existe o no es valido: " + directorio);
        }
        
        Map<String, Entidad> entidades = new HashMap<>();
        List<Path> archivosCsv = validador.obtenerArchivosCsv(directorio);
        
        System.out.println("Archivos CSV encontrados: " + archivosCsv.size());
        
        for (Path archivo : archivosCsv) {
            if (archivo.getFileName().toString().equalsIgnoreCase("entidad_rec.csv")) {
                System.out.println("Archivo omitido: " + archivo.getFileName());
                continue;
            }
            procesarArchivoFragmentado(archivo, entidades);
        }
        
        return entidades;
    }
    
    /**
     * Lee una lista especifica de archivos CSV seleccionados.
     * 
     * @param archivosSeleccionados Lista de archivos a procesar
     * @return Mapa de entidades con sus datos consolidados
     * @throws IOException Si ocurre un error al leer los archivos
     */
    public Map<String, Entidad> leerArchivosSeleccionados(List<Path> archivosSeleccionados) throws IOException {
        Map<String, Entidad> entidades = new HashMap<>();
        
        System.out.println("Procesando " + archivosSeleccionados.size() + " archivos seleccionados...");
        
        for (Path archivo : archivosSeleccionados) {
            procesarArchivoFragmentado(archivo, entidades);
        }
        
        return entidades;
    }
    
    /**
     * Procesa un archivo fragmentado individual OMITIENDO LA PRIMERA LÍNEA.
     * 
     * @param archivo Archivo a procesar
     * @param entidades Mapa de entidades donde almacenar los datos
     * @throws IOException Si ocurre un error al leer el archivo
     */
    private void procesarArchivoFragmentado(Path archivo, Map<String, Entidad> entidades) throws IOException {
        String nombreArchivo = archivo.getFileName().toString();
        String nombreEntidad = validador.extraerNombreEntidad(nombreArchivo);
        
        if (nombreEntidad == null) {
            System.out.println("Archivo omitido (formato invalido): " + nombreArchivo);
            return;
        }
        
        Entidad entidad = entidades.computeIfAbsent(nombreEntidad, Entidad::new);
        
        try (BufferedReader reader = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
            String primeraLinea = reader.readLine(); // Leer primera línea
            
            if (primeraLinea != null) {
                // Mostrar la cabecera omitida
               // System.out.println("CABECERA OMITIDA DE " + nombreArchivo + ": " + primeraLinea);
                
                // Leer el resto de líneas (datos reales)
                String linea;
                while ((linea = reader.readLine()) != null) {
                    if (!linea.trim().isEmpty()) {
                        entidad.agregarLineaDatos(linea);
                    }
                }
            }
        }
        
        System.out.println("Procesado: " + nombreArchivo + " -> Entidad: " + nombreEntidad);
    }
}