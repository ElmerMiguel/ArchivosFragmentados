# 🗂️ Procesador de Archivos Fragmentados.

Este proyecto permite reconstruir archivos CSV fragmentados, eliminando duplicados y unificando su contenido en un archivo maestro. Es ideal para recuperar registros dispersos en múltiples archivos parciales.

---

## 🚀 Ejecución del Programa

Para ejecutar el programa, abre una terminal y navega al directorio [`01_jar`](./01_jar). Luego ejecuta:

```bash
java -jar ArchivosFragmentados.jar
```

---

## 🧠 Funcionamiento

Al iniciar, el programa busca automáticamente archivos `.csv` dentro de la carpeta `csv/` ubicada junto al `.jar`. Si encuentra archivos, te preguntará si deseas usarlos. Si no hay archivos en esa carpeta, solicitará una ruta alternativa.

### Ejemplo de ejecución:

```
================================================================
           PROCESADOR DE ARCHIVOS FRAGMENTADOS v1.0           
================================================================
Este programa reconstruye archivos fragmentados eliminando
duplicados y unificando el contenido en un archivo maestro.
================================================================
DIRECTORIO ENCONTRADO: /ruta/proyecto/01_jar/csv
Se encontraron 3 archivos CSV en el directorio.
Desea usar este directorio? (S/n): 
```

---

## 📂 Selección de Archivos

Puedes elegir:

- Todos los archivos (presionando Enter)
- Archivos específicos: `1,3`
- Rango de archivos: `1-3`

---

## ⚙️ Proceso de Reconstrucción

1. Se detecta la cabecera común.
2. Se omiten cabeceras duplicadas.
3. Se identifican entidades por nombre de archivo.
4. Se eliminan líneas duplicadas.
5. Se genera un archivo final con los datos unificados.

---

## 📊 Ejemplo de Resumen

```
================================================================
RESUMEN DE ENTIDADES ENCONTRADAS
================================================================
ENTIDAD              ARCHIVOS        LINEAS TOTALES      
----------------------------------------------------------------
entidad              Multiples       9                   
----------------------------------------------------------------

================================================================
ANALISIS DE DUPLICADOS
================================================================
Se encontraron 2 lineas duplicadas:
...
```

---

## 📁 Archivo de Salida

El archivo final se guarda en la raíz del proyecto como `entidad_rec.csv`, a menos que se indique otro nombre.

```
Archivo generado: entidad_rec.csv
Ubicación: 01_jar/entidad_rec.csv
```

---

## ✅ Requisitos

- Java instalado.
- Archivos `.csv` con estructura consistente.

---

## 📌 Notas

- Los archivos deben tener nombres con el formato `entidad_1.csv`, `entidad_2.csv`, etc.
- La cabecera debe ser idéntica en todos los archivos.