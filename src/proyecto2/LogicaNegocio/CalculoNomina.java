package proyecto2.LogicaNegocio;

import proyecto2.Entidades.Empleado;
import proyecto2.Entidades.Nomina;

public class CalculoNomina extends logicaBase implements ICalculable {

    @Override
    public Nomina calcular(Empleado empleado) {

        double bruto = empleado.getSalarioBase();
        double sem = calcularSEM(bruto);
        double ivm = calcularIVM(bruto);
        double bancoPopular = calcularBancoPopular(bruto);
        double renta = calcularRenta(bruto);
        double deducciones = sem + ivm + bancoPopular + renta;
        double neto = bruto - deducciones;

        return new Nomina(bruto, sem, ivm, bancoPopular, renta, neto);
    }
}
