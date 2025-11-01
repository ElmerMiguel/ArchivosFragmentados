package archivosfragmentados.service;

import archivosfragmentados.model.Entidad;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Servicio principal para procesar archivos fragmentados de manera modular.
 */
public class ProcesadorDatos {
    
    private final AnalizadorDatos analizador;
    private final ManejadorCSV manejadorCSV;
    private final VisualizadorResultados visualizador;
    
    public ProcesadorDatos() {
        this.analizador = new AnalizadorDatos();
        this.manejadorCSV = new ManejadorCSV();
        this.visualizador = new VisualizadorResultados();
    }
    
    /**
     * Procesa archivos fragmentados de manera interactiva y modular.
     */
    public void procesarArchivosFragmentados(GestorRutas gestorRutas, LectorArchivos lector, 
                                           EscritorArchivos escritor) {
        try {
            visualizador.mostrarEncabezado();
            
            Path directorioEntrada = gestorRutas.obtenerDirectorioEntrada();
            
            SelectorArchivos selector = new SelectorArchivos(gestorRutas.getScanner());
            List<Path> archivosSeleccionados = selector.seleccionarArchivos(directorioEntrada);
            
            if (archivosSeleccionados.isEmpty()) {
                System.out.println("No se seleccionaron archivos para procesar.");
                return;
            }
            
            visualizador.mostrarSeparador();
            System.out.println("PROCESANDO ARCHIVOS SELECCIONADOS...");
            
            // Las cabeceras ya fueron omitidas en el LectorArchivos
            Map<String, Entidad> entidades = lector.leerArchivosSeleccionados(archivosSeleccionados);
            
            if (entidades.isEmpty()) {
                System.out.println("No se pudieron procesar los archivos seleccionados.");
                return;
            }
            
            // Unificar datos (ya sin cabeceras)
            List<String> datosUnificados = manejadorCSV.procesarDatos(entidades);
            
            visualizador.mostrarResumenDetallado(entidades);
            
            AnalizadorDatos.ResultadoAnalisis resultado = analizador.analizarYEliminarDuplicados(datosUnificados);
            
            visualizador.mostrarAnalisisDuplicados(resultado);
            
            // Preparar archivo final CON cabecera
            List<String> archivoFinal = manejadorCSV.prepararArchivoFinal(resultado.getDatosSinDuplicados());
            
            Path archivoSalida = gestorRutas.obtenerArchivoSalida();
            escritor.escribirArchivo(archivoSalida, archivoFinal);
            
            // Mostrar estadísticas correctas (sin contar cabeceras)
            visualizador.mostrarResultadoFinal(archivoSalida, datosUnificados.size(), 
                                             resultado.getDatosSinDuplicados().size(), 
                                             resultado.getDuplicados().size());
            
            if (gestorRutas.mostrarContenidoFinal()) {
                visualizador.mostrarContenidoArchivo(archivoFinal); // Mostrar con cabecera
            }
            
        } catch (IOException e) {
            System.err.println("ERROR DURANTE EL PROCESAMIENTO: " + e.getMessage());
        } finally {
            gestorRutas.cerrar();
        }
    }
}