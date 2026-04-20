/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2.LogicaNegocio;

import proyecto2.Entidades.Empleado;
import proyecto2.Entidades.Nomina;

public class CalculoNomina extends logicaBase implements ICalculable {

    @Override
    public Nomina calcular(Empleado empleado) {

        double bruto = empleado.getSalarioBase();
        double deducciones = calcularCCSS(bruto) + calcularRenta(bruto);
        double neto = bruto - deducciones;

        return new Nomina(bruto, deducciones, neto);
    }
}
