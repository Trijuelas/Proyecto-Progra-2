package proyecto2.LogicaNegocio;

import proyecto2.Entidades.Empleado;
import proyecto2.Entidades.Nomina;

public class CalculoNomina extends logicaBase implements ICalculable {

    @Override
    public Nomina calcular(Empleado empleado) {
        double bruto = empleado.getSalarioBase();

        double semObrero = calcularSemObrero(bruto);
        double ivmObrero = calcularIvmObrero(bruto);
        double bancoPopularObrero = calcularBancoPopularObrero(bruto);
        double renta = calcularRenta(bruto);

        double semPatronal = calcularSemPatronal(bruto);
        double ivmPatronal = calcularIvmPatronal(bruto);
        double asignacionesFamiliares = calcularAsignacionesFamiliares(bruto);
        double imas = calcularImas(bruto);
        double ina = calcularIna(bruto);
        double bancoPopularPatronal = calcularBancoPopularPatronal(bruto);
        double fcl = calcularFcl(bruto);
        double opc = calcularOpc(bruto);
        double ins = calcularIns(bruto);

        double deducciones = semObrero + ivmObrero + bancoPopularObrero + renta;
        double neto = bruto - deducciones;
        double cargaPatronalTotal = semPatronal + ivmPatronal + asignacionesFamiliares + imas
                + ina + bancoPopularPatronal + fcl + opc + ins;
        double costoEmpresa = bruto + cargaPatronalTotal;

        return new Nomina(
                bruto,
                semObrero,
                ivmObrero,
                bancoPopularObrero,
                renta,
                deducciones,
                neto,
                semPatronal,
                ivmPatronal,
                asignacionesFamiliares,
                imas,
                ina,
                bancoPopularPatronal,
                fcl,
                opc,
                ins,
                cargaPatronalTotal,
                costoEmpresa,
                bruto < BMC_SEM_2026,
                bruto < BMC_IVM_2026
        );
    }
}
