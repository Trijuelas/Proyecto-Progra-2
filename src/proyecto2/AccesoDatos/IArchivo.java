package proyecto2.AccesoDatos;

import java.io.IOException;

public interface IArchivo {

    /**
     * Agrega un nuevo dato al final del archivo.
     *
     * @param dato contenido que se desea almacenar
     * @throws IOException si ocurre un error al escribir el archivo
     */
    void guardar(String dato) throws IOException;

    /**
     * Lee el contenido completo del archivo.
     *
     * @return texto almacenado o cadena vacia si el archivo no existe
     * @throws IOException si ocurre un error al leer el archivo
     */
    String leer() throws IOException;
}
