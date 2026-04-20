/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2.AccesoDatos;

/**
 *
 * @author gabriel
 */
public interface IArchivo {
    void guardar(String dato) throws Exception;
    String leer() throws Exception;
}
