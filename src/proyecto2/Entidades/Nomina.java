package proyecto2.Entidades;

public class Nomina {

    private final double salarioBruto;
    private final double seguroEnfermedadMaternidadObrero;
    private final double invalidezVejezMuerteObrero;
    private final double bancoPopularObrero;
    private final double impuestoRenta;
    private final double deducciones;
    private final double salarioNeto;
    private final double seguroEnfermedadMaternidadPatronal;
    private final double invalidezVejezMuertePatronal;
    private final double asignacionesFamiliares;
    private final double imas;
    private final double ina;
    private final double bancoPopularPatronal;
    private final double fondoCapitalizacionLaboral;
    private final double operadoraPensionesComplementarias;
    private final double ins;
    private final double cargaPatronalTotal;
    private final double costoTotalEmpresa;
    private final boolean aplicaBaseMinimaSem;
    private final boolean aplicaBaseMinimaIvm;

    public Nomina(
            double salarioBruto,
            double seguroEnfermedadMaternidadObrero,
            double invalidezVejezMuerteObrero,
            double bancoPopularObrero,
            double impuestoRenta,
            double deducciones,
            double salarioNeto,
            double seguroEnfermedadMaternidadPatronal,
            double invalidezVejezMuertePatronal,
            double asignacionesFamiliares,
            double imas,
            double ina,
            double bancoPopularPatronal,
            double fondoCapitalizacionLaboral,
            double operadoraPensionesComplementarias,
            double ins,
            double cargaPatronalTotal,
            double costoTotalEmpresa,
            boolean aplicaBaseMinimaSem,
            boolean aplicaBaseMinimaIvm) {
        this.salarioBruto = salarioBruto;
        this.seguroEnfermedadMaternidadObrero = seguroEnfermedadMaternidadObrero;
        this.invalidezVejezMuerteObrero = invalidezVejezMuerteObrero;
        this.bancoPopularObrero = bancoPopularObrero;
        this.impuestoRenta = impuestoRenta;
        this.deducciones = deducciones;
        this.salarioNeto = salarioNeto;
        this.seguroEnfermedadMaternidadPatronal = seguroEnfermedadMaternidadPatronal;
        this.invalidezVejezMuertePatronal = invalidezVejezMuertePatronal;
        this.asignacionesFamiliares = asignacionesFamiliares;
        this.imas = imas;
        this.ina = ina;
        this.bancoPopularPatronal = bancoPopularPatronal;
        this.fondoCapitalizacionLaboral = fondoCapitalizacionLaboral;
        this.operadoraPensionesComplementarias = operadoraPensionesComplementarias;
        this.ins = ins;
        this.cargaPatronalTotal = cargaPatronalTotal;
        this.costoTotalEmpresa = costoTotalEmpresa;
        this.aplicaBaseMinimaSem = aplicaBaseMinimaSem;
        this.aplicaBaseMinimaIvm = aplicaBaseMinimaIvm;
    }

    public double getSalarioBruto() {
        return salarioBruto;
    }

    public double getSeguroEnfermedadMaternidadObrero() {
        return seguroEnfermedadMaternidadObrero;
    }

    public double getInvalidezVejezMuerteObrero() {
        return invalidezVejezMuerteObrero;
    }

    public double getBancoPopularObrero() {
        return bancoPopularObrero;
    }

    public double getImpuestoRenta() {
        return impuestoRenta;
    }

    public double getDeducciones() {
        return deducciones;
    }

    public double getSalarioNeto() {
        return salarioNeto;
    }

    public double getSeguroEnfermedadMaternidadPatronal() {
        return seguroEnfermedadMaternidadPatronal;
    }

    public double getInvalidezVejezMuertePatronal() {
        return invalidezVejezMuertePatronal;
    }

    public double getAsignacionesFamiliares() {
        return asignacionesFamiliares;
    }

    public double getImas() {
        return imas;
    }

    public double getIna() {
        return ina;
    }

    public double getBancoPopularPatronal() {
        return bancoPopularPatronal;
    }

    public double getFondoCapitalizacionLaboral() {
        return fondoCapitalizacionLaboral;
    }

    public double getOperadoraPensionesComplementarias() {
        return operadoraPensionesComplementarias;
    }

    public double getIns() {
        return ins;
    }

    public double getCargaPatronalTotal() {
        return cargaPatronalTotal;
    }

    public double getCostoTotalEmpresa() {
        return costoTotalEmpresa;
    }

    public boolean isAplicaBaseMinimaSem() {
        return aplicaBaseMinimaSem;
    }

    public boolean isAplicaBaseMinimaIvm() {
        return aplicaBaseMinimaIvm;
    }

    public double getTotalObreroSinRenta() {
        return seguroEnfermedadMaternidadObrero + invalidezVejezMuerteObrero + bancoPopularObrero;
    }
}
