/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2.LogicaNegocio;

public abstract class logicaBase {

    protected double calcularCCSS(double salario) {
        return salario * 0.10; // ejemplo Costa Rica
    }

    protected double calcularRenta(double salario) {
        return salario * 0.05;
    }
}
