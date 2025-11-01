package archivosfragmentados.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Servicio para la seleccion interactiva de archivos CSV.
 */
public class SelectorArchivos {
    
    private final Scanner scanner;
    
    public SelectorArchivos(Scanner scanner) {
        this.scanner = scanner;
    }
    
    /**
     * Permite al usuario seleccionar archivos CSV de un directorio.
     * 
     * @param directorio Directorio con archivos CSV
     * @return Lista de archivos seleccionados
     * @throws IOException Si hay error al acceder a los archivos
     */
    public List<Path> seleccionarArchivos(Path directorio) throws IOException {
        List<Path> archivosDisponibles = obtenerArchivosCsv(directorio);
        
        if (archivosDisponibles.isEmpty()) {
            System.out.println("No se encontraron archivos CSV en el directorio.");
            return new ArrayList<>();
        }
        
        mostrarArchivosDisponibles(archivosDisponibles);
        
        if (!confirmarProcesamiento()) {
            System.out.println("Procesamiento cancelado por el usuario.");
            return new ArrayList<>();
        }
        
        return seleccionarArchivosEspecificos(archivosDisponibles);
    }
    
    /**
     * Muestra la lista de archivos CSV disponibles.
     * 
     * @param archivos Lista de archivos encontrados
     */
    private void mostrarArchivosDisponibles(List<Path> archivos) throws IOException {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ARCHIVOS CSV ENCONTRADOS:");
        System.out.println("=".repeat(60));
        
        for (int i = 0; i < archivos.size(); i++) {
            Path archivo = archivos.get(i);
            long tamano = Files.size(archivo);
            long lineas = contarLineas(archivo);
            
            System.out.printf("%2d. %-25s (%d bytes, ~%d lineas)%n", 
                i + 1, archivo.getFileName(), tamano, lineas);
        }
    }
    
    /**
     * Cuenta las lineas de un archivo.
     * 
     * @param archivo Archivo a contar
     * @return Numero de lineas
     */
    private long contarLineas(Path archivo) {
        try {
            return Files.lines(archivo).count();
        } catch (IOException e) {
            return 0;
        }
    }
    
    /**
     * Confirma si el usuario desea procesar los archivos.
     * 
     * @return true si confirma el procesamiento
     */
    private boolean confirmarProcesamiento() {
        System.out.print("\nDesea procesar estos archivos? (S/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        return respuesta.isEmpty() || respuesta.equals("s") || respuesta.equals("si");
    }
    
    /**
     * Permite seleccionar archivos especificos o todos.
     * 
     * @param archivosDisponibles Lista de archivos disponibles
     * @return Lista de archivos seleccionados
     */
    private List<Path> seleccionarArchivosEspecificos(List<Path> archivosDisponibles) {
        System.out.println("\nOPCIONES DE SELECCION:");
        System.out.println("- Presione Enter para procesar TODOS los archivos");
        System.out.println("- Escriba los numeros separados por comas (ej: 1,3,5)");
        System.out.println("- Escriba un rango (ej: 1-5)");
        
        while (true) {
            System.out.print("\nSeleccion: ");
            String seleccion = scanner.nextLine().trim();
            
            // Si presiona Enter (cadena vacía), seleccionar todos
            if (seleccion.isEmpty()) {
                System.out.println("Seleccionados TODOS los archivos (" + archivosDisponibles.size() + ")");
                return archivosDisponibles;
            }
            
            // Si escribe "todos" explícitamente
            if (seleccion.toLowerCase().equals("todos")) {
                System.out.println("Seleccionados todos los archivos (" + archivosDisponibles.size() + ")");
                return archivosDisponibles;
            }
            
            try {
                List<Path> seleccionados = procesarSeleccionNumerica(seleccion, archivosDisponibles);
                if (!seleccionados.isEmpty()) {
                    mostrarArchivosSeleccionados(seleccionados);
                    return seleccionados;
                }
            } catch (Exception e) {
                System.out.println("ERROR: Seleccion invalida. " + e.getMessage());
            }
            
            System.out.println("Intente nuevamente con un formato valido o presione Enter para todos.");
        }
    }
    
    /**
     * Procesa la seleccion numerica del usuario.
     * 
     * @param seleccion Texto de seleccion ingresado
     * @param archivosDisponibles Lista de archivos disponibles
     * @return Lista de archivos seleccionados
     */
    private List<Path> procesarSeleccionNumerica(String seleccion, List<Path> archivosDisponibles) {
        List<Path> seleccionados = new ArrayList<>();
        
        if (seleccion.contains("-")) {
            // Manejo de rangos (ej: 1-5)
            String[] partes = seleccion.split("-");
            if (partes.length == 2) {
                int inicio = Integer.parseInt(partes[0].trim()) - 1;
                int fin = Integer.parseInt(partes[1].trim()) - 1;
                
                for (int i = inicio; i <= fin && i < archivosDisponibles.size(); i++) {
                    if (i >= 0) seleccionados.add(archivosDisponibles.get(i));
                }
            }
        } else {
            // Manejo de numeros separados por comas
            String[] numeros = seleccion.split(",");
            for (String numero : numeros) {
                int indice = Integer.parseInt(numero.trim()) - 1;
                if (indice >= 0 && indice < archivosDisponibles.size()) {
                    seleccionados.add(archivosDisponibles.get(indice));
                }
            }
        }
        
        return seleccionados;
    }
    
    /**
     * Muestra los archivos que fueron seleccionados.
     * 
     * @param seleccionados Lista de archivos seleccionados
     */
    private void mostrarArchivosSeleccionados(List<Path> seleccionados) {
        System.out.println("\nARCHIVOS SELECCIONADOS PARA PROCESAMIENTO:");
        for (int i = 0; i < seleccionados.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, seleccionados.get(i).getFileName());
        }
    }
    
    /**
     * Obtiene todos los archivos CSV de un directorio.
     * 
     * @param directorio Directorio a examinar
     * @return Lista de archivos CSV
     * @throws IOException Si hay error al acceder al directorio
     */
    private List<Path> obtenerArchivosCsv(Path directorio) throws IOException {
        return Files.list(directorio)
                .filter(Files::isRegularFile)
                .filter(archivo -> archivo.toString().toLowerCase().endsWith(".csv"))
                .filter(archivo -> !archivo.getFileName().toString().equalsIgnoreCase("entidad_rec.csv"))
                .sorted()
                .collect(Collectors.toList());
    }
}