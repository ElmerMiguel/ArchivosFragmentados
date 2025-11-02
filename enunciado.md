# **ARCHIVOS FRAGMENTADOS**

## **Objetivo General**

Desarrollar un programa que lea múltiples archivos de texto fragmentados, los combine en un solo archivo maestro y procese su contenido eliminando duplicados y ordenando los datos.

## **Competencias a Desarrollar**

* Lectura y escritura de archivos secuenciales.  
* Manejo de listas y estructuras de datos.  
* Uso de rutas y directorios.  
* Limpieza de información.

## **Descripción del problema**

Una empresa pierde todos sus archivos de registro principales, pero aún conserva varias copias parciales de los mismos, cada copia empieza con el nombre de la entidad que guarda, seguido de un guión bajo y luego un número que indica la secuencia en la que va, por ejemplo: entidad\_1.csv, entidad\_2.csv. Cada archivo contiene líneas con los datos de esa entidad, su tarea es reconstruir el archivo completo y eliminar duplicados.

## **Requerimientos del programa:**

* Lectura de archivos: El programa debe leer todos los archivos .csv del directorio especificado.  
* Lista de entidades: El programa debe listar todas las entidades presentes en ese directorio.  
* Unificación de datos: Combinar todo el contenido en una lista única.  
* Eliminación de duplicados: No deben repetirse las líneas en el archivo final.  
* Archivo de salida: Guardar el resultado en un archivo “entidad\_rec.csv”.

# 