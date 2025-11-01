package archivosfragmentados.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Servicio para gestionar y validar rutas de archivos y directorios.
 */
public class GestorRutas {
    
    private static final String DIRECTORIO_CSV_DEFECTO = "csv";
    private static final String ARCHIVO_SALIDA_DEFECTO = "entidad_rec.csv";
    
    private final Scanner scanner;
    private Path directorioCSVEncontrado;
    
    public GestorRutas() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Obtiene el scanner para uso de otros servicios.
     * 
     * @return Scanner compartido
     */
    public Scanner getScanner() {
        return scanner;
    }
    
    /**
     * Obtiene el directorio de entrada con confirmacion del usuario.
     * 
     * @return Path del directorio de entrada valido
     */
    public Path obtenerDirectorioEntrada() {
        Path directorioEjecucion = obtenerDirectorioEjecucion();
        Path directorioCSV = directorioEjecucion.resolve(DIRECTORIO_CSV_DEFECTO);
        
        if (validarDirectorioConArchivos(directorioCSV)) {
            System.out.println("DIRECTORIO ENCONTRADO: " + directorioCSV.toAbsolutePath());
            mostrarArchivosEncontrados(directorioCSV);
            
            if (confirmarUsoDirectorio()) {
                this.directorioCSVEncontrado = directorioCSV;
                return directorioCSV;
            }
        }
        
        System.out.println("BUSQUEDA MANUAL DE DIRECTORIO:");
        Path directorioUsuario = solicitarDirectorioUsuario();
        this.directorioCSVEncontrado = directorioUsuario;
        return directorioUsuario;
    }
    
    /**
     * Confirma si usar el directorio encontrado automaticamente.
     * 
     * @return true si confirma usar el directorio
     */
    private boolean confirmarUsoDirectorio() {
        System.out.print("Desea usar este directorio? (S/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        return respuesta.isEmpty() || respuesta.equals("s") || respuesta.equals("si");
    }
    
    /**
     * Obtiene la ruta del archivo de salida EN LA RAIZ del programa.
     * 
     * @return Path del archivo de salida EN LA RAIZ
     */
    public Path obtenerArchivoSalida() {
        System.out.println("\nCONFIGURACION DEL ARCHIVO DE SALIDA:");
        System.out.print("Ingrese el nombre del archivo de salida (Enter para '" + ARCHIVO_SALIDA_DEFECTO + "'): ");
        String entrada = scanner.nextLine().trim();
        
        String nombreArchivo = entrada.isEmpty() ? ARCHIVO_SALIDA_DEFECTO : entrada;
        
        if (!nombreArchivo.toLowerCase().endsWith(".csv")) {
            nombreArchivo += ".csv";
        }
        
        Path directorioRaiz = obtenerDirectorioEjecucion();
        Path archivoCompleto = directorioRaiz.resolve(nombreArchivo);
        
        if (Files.exists(archivoCompleto)) {
            System.out.println("ADVERTENCIA: El archivo ya existe y sera sobrescrito.");
        }
        
        System.out.println("El archivo se guardara en la RAIZ: " + archivoCompleto.toAbsolutePath());
        
        return archivoCompleto;
    }
    
    /**
     * Pregunta al usuario si desea ver el contenido del archivo final.
     * 
     * @return true si desea ver el contenido
     */
    public boolean mostrarContenidoFinal() {
        System.out.print("\nDesea ver el contenido del archivo generado? (s/N): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        return respuesta.equals("s") || respuesta.equals("si");
    }
    
    /**
     * Muestra los archivos CSV encontrados en el directorio.
     * 
     * @param directorio Directorio a examinar
     */
    private void mostrarArchivosEncontrados(Path directorio) {
        try {
            long cantidadArchivos = Files.list(directorio)
                    .filter(Files::isRegularFile)
                    .filter(archivo -> archivo.toString().toLowerCase().endsWith(".csv"))
                    .filter(archivo -> !archivo.getFileName().toString().equalsIgnoreCase("entidad_rec.csv"))
                    .count();
            
            System.out.printf("Se encontraron %d archivos CSV en el directorio.%n", cantidadArchivos);
        } catch (IOException e) {
            System.out.println("Error al contar archivos en el directorio.");
        }
    }
    
    /**
     * Obtiene el directorio donde se esta ejecutando la aplicacion.
     * 
     * @return Path del directorio de ejecucion actual
     */
    private Path obtenerDirectorioEjecucion() {
        return Paths.get(System.getProperty("user.dir"));
    }
    
    /**
     * Solicita al usuario que ingrese un directorio valido.
     * 
     * @return Path del directorio valido ingresado por el usuario
     */
    private Path solicitarDirectorioUsuario() {
        Path directorio = null;
        
        System.out.println("INGRESO MANUAL DE DIRECTORIO:");
        
        while (directorio == null) {
            System.out.print("Ingrese la ruta del directorio con archivos CSV: ");
            String rutaIngresada = scanner.nextLine().trim();
            
            if (rutaIngresada.isEmpty()) {
                System.out.println("ERROR: La ruta no puede estar vacia. Intente nuevamente.");
                continue;
            }
            
            Path rutaCandidata = Paths.get(rutaIngresada);
            
            if (validarDirectorioConArchivos(rutaCandidata)) {
                System.out.println("DIRECTORIO VALIDADO: " + rutaCandidata.toAbsolutePath());
                mostrarArchivosEncontrados(rutaCandidata);
                directorio = rutaCandidata;
            } else {
                System.out.println("ERROR: El directorio no existe, no es valido o no contiene archivos CSV.");
                System.out.println("Ruta verificada: " + rutaCandidata.toAbsolutePath());
                System.out.println("Intente nuevamente o verifique la ruta.\n");
            }
        }
        
        return directorio;
    }
    
    /**
     * Valida si un directorio existe y contiene archivos CSV.
     * 
     * @param directorio Directorio a validar
     * @return true si el directorio es valido y contiene archivos CSV
     */
    private boolean validarDirectorioConArchivos(Path directorio) {
        if (!Files.exists(directorio) || !Files.isDirectory(directorio)) {
            return false;
        }
        
        try {
            return Files.list(directorio)
                    .anyMatch(archivo -> Files.isRegularFile(archivo) && 
                             archivo.toString().toLowerCase().endsWith(".csv") &&
                             !archivo.getFileName().toString().equalsIgnoreCase("entidad_rec.csv"));
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * Cierra los recursos utilizados por el gestor.
     */
    public void cerrar() {
        scanner.close();
    }
}