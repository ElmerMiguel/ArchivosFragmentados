package archivosfragmentados.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Servicio encargado de escribir archivos de salida.
 */
public class EscritorArchivos {
    
    /**
     * Escribe una lista de datos en un archivo CSV.
     * 
     * @param archivo Ruta del archivo de salida
     * @param datos Lista de líneas a escribir
     * @throws IOException Si ocurre un error al escribir el archivo
     */
    public void escribirArchivo(Path archivo, List<String> datos) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8)) {
            for (String linea : datos) {
                writer.write(linea);
                writer.newLine();
            }
        }
    }
}