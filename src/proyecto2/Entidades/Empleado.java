/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2.Entidades;

/**
 *
 * @author gabriel
 */
public class Empleado {  private String nombre;
    private String cedula;
    private double salarioBase;
    private String correo;

    public Empleado(String nombre, String cedula, double salarioBase, String correo) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.salarioBase = salarioBase;
        this.correo = correo;
    }

    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }
    public double getSalarioBase() { return salarioBase; }
    public String getCorreo() { return correo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }
    public void setCorreo(String correo) { this.correo = correo; }
}
