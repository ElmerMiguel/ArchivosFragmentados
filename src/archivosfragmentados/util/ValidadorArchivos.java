package archivosfragmentados.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Utilidad para validar y procesar nombres de archivos fragmentados.
 */
public class ValidadorArchivos {
    
    private static final Pattern PATRON_ARCHIVO_FRAGMENTADO = Pattern.compile("^(.+)_\\d+\\.csv$");
    
    /**
     * Obtiene todos los archivos CSV de un directorio.
     * 
     * @param directorio Directorio a examinar
     * @return Lista de archivos CSV encontrados
     * @throws IOException Si ocurre un error al acceder al directorio
     */
    public List<Path> obtenerArchivosCsv(Path directorio) throws IOException {
    return Files.list(directorio)
            .filter(Files::isRegularFile)
            .filter(archivo -> archivo.toString().toLowerCase().endsWith(".csv"))
            // Excluir "entidad_rec.csv"
            .filter(archivo -> !archivo.getFileName().toString().equalsIgnoreCase("entidad_rec.csv"))
            .collect(Collectors.toList());
}
    
    /**
     * Extrae el nombre de la entidad de un nombre de archivo fragmentado.
     * 
     * @param nombreArchivo Nombre del archivo (ej: "entidad_1.csv")
     * @return Nombre de la entidad o null si el formato no es válido
     */
    public String extraerNombreEntidad(String nombreArchivo) {
        Matcher matcher = PATRON_ARCHIVO_FRAGMENTADO.matcher(nombreArchivo);
        return matcher.matches() ? matcher.group(1) : null;
    }
}