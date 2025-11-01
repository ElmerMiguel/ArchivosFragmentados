package archivosfragmentados.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa una entidad con sus datos fragmentados.
 */
public class Entidad {
    
    private final String nombre;
    private final List<String> lineasDatos;
    
    /**
     * Constructor para crear una entidad.
     * 
     * @param nombre Nombre de la entidad
     */
    public Entidad(String nombre) {
        this.nombre = nombre;
        this.lineasDatos = new ArrayList<>();
    }
    
    /**
     * Agrega una línea de datos a la entidad.
     * 
     * @param lineaDatos Línea de datos a agregar
     */
    public void agregarLineaDatos(String lineaDatos) {
        if (lineaDatos != null && !lineaDatos.trim().isEmpty()) {
            this.lineasDatos.add(lineaDatos.trim());
        }
    }
    
    /**
     * Obtiene el nombre de la entidad.
     * 
     * @return Nombre de la entidad
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene todas las líneas de datos de la entidad.
     * 
     * @return Lista de líneas de datos
     */
    public List<String> getLineasDatos() {
        return new ArrayList<>(lineasDatos);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Entidad entidad = (Entidad) obj;
        return Objects.equals(nombre, entidad.nombre);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
    
    @Override
    public String toString() {
        return String.format("Entidad{nombre='%s', lineas=%d}", nombre, lineasDatos.size());
    }
}