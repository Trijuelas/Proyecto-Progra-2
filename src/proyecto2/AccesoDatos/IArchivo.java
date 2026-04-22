package proyecto2.AccesoDatos;

import java.io.IOException;
import java.util.List;

public interface IArchivo {

    /**
     * Agrega un nuevo dato al final del archivo.
     *
     * @param dato contenido que se desea almacenar
     * @throws IOException si ocurre un error al escribir el archivo
     */
    void guardar(String dato) throws IOException;

    /**
     * Reemplaza el contenido actual del archivo por un nuevo valor.
     *
     * @param dato contenido que se desea dejar en el archivo
     * @throws IOException si ocurre un error al escribir el archivo
     */
    void sobrescribir(String dato) throws IOException;

    /**
     * Lee el contenido completo del archivo.
     *
     * @return texto almacenado o cadena vacia si el archivo no existe
     * @throws IOException si ocurre un error al leer el archivo
     */
    String leer() throws IOException;

    /**
     * Lee el archivo linea por linea.
     *
     * @return lista de lineas almacenadas o lista vacia si el archivo no existe
     * @throws IOException si ocurre un error al leer el archivo
     */
    List<String> leerLineas() throws IOException;

    /**
     * Indica si el archivo ya existe en disco.
     *
     * @return {@code true} si el archivo existe
     */
    boolean existe();
}
