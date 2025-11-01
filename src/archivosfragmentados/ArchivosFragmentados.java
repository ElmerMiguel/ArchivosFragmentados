package archivosfragmentados;

import archivosfragmentados.service.LectorArchivos;
import archivosfragmentados.service.ProcesadorDatos;
import archivosfragmentados.service.EscritorArchivos;
import archivosfragmentados.service.GestorRutas;

/**
 * Programa principal para reconstruir archivos fragmentados de entidades.
 * 
 * @author elmer
 */
public class ArchivosFragmentados {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GestorRutas gestorRutas = new GestorRutas();
        LectorArchivos lector = new LectorArchivos();
        ProcesadorDatos procesador = new ProcesadorDatos();
        EscritorArchivos escritor = new EscritorArchivos();
        
        procesador.procesarArchivosFragmentados(gestorRutas, lector, escritor);
    }
}
