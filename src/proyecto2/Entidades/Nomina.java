/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2.Entidades;

/**
 *
 * @author gabriel
 */
public class Nomina {private double salarioBruto;
    private double deducciones;
    private double salarioNeto;

    public Nomina(double salarioBruto, double deducciones, double salarioNeto) {
        this.salarioBruto = salarioBruto;
        this.deducciones = deducciones;
        this.salarioNeto = salarioNeto;
    }

    public double getSalarioBruto() { return salarioBruto; }
    public double getDeducciones() { return deducciones; }
    public double getSalarioNeto() { return salarioNeto; }
}
