package proyecto2.LogicaNegocio;

public abstract class logicaBase {

    protected static final double BMC_SEM_2026 = 346789.00;
    protected static final double BMC_IVM_2026 = 324590.00;

    protected static final double SEM_OBRERO = 0.0550;
    protected static final double IVM_OBRERO = 0.0433;
    protected static final double BPOP_OBRERO = 0.0100;

    protected static final double SEM_PATRONAL = 0.0925;
    protected static final double IVM_PATRONAL = 0.0558;
    protected static final double ASIGNACIONES_FAMILIARES = 0.0500;
    protected static final double IMAS = 0.0050;
    protected static final double INA = 0.0150;
    protected static final double BPOP_PATRONAL = 0.0025;
    protected static final double FCL = 0.0150;
    protected static final double OPC = 0.0200;
    protected static final double INS = 0.0100;

    protected double calcularSemObrero(double salario) {
        return Math.max(salario, BMC_SEM_2026) * SEM_OBRERO;
    }

    protected double calcularIvmObrero(double salario) {
        return Math.max(salario, BMC_IVM_2026) * IVM_OBRERO;
    }

    protected double calcularBancoPopularObrero(double salario) {
        return salario * BPOP_OBRERO;
    }

    protected double calcularSemPatronal(double salario) {
        return Math.max(salario, BMC_SEM_2026) * SEM_PATRONAL;
    }

    protected double calcularIvmPatronal(double salario) {
        return Math.max(salario, BMC_IVM_2026) * IVM_PATRONAL;
    }

    protected double calcularAsignacionesFamiliares(double salario) {
        return salario * ASIGNACIONES_FAMILIARES;
    }

    protected double calcularImas(double salario) {
        return salario * IMAS;
    }

    protected double calcularIna(double salario) {
        return salario * INA;
    }

    protected double calcularBancoPopularPatronal(double salario) {
        return salario * BPOP_PATRONAL;
    }

    protected double calcularFcl(double salario) {
        return salario * FCL;
    }

    protected double calcularOpc(double salario) {
        return salario * OPC;
    }

    protected double calcularIns(double salario) {
        return salario * INS;
    }

    protected double calcularRenta(double salarioBruto) {
        double impuesto = 0.0;
        double[] limites = {918000.0, 1347000.0, 2364000.0, 4727000.0};
        double[] tasas = {0.10, 0.15, 0.20, 0.25};

        if (salarioBruto > limites[0]) {
            impuesto += calcularTramo(salarioBruto, limites[0], limites[1], tasas[0]);
        }

        if (salarioBruto > limites[1]) {
            impuesto += calcularTramo(salarioBruto, limites[1], limites[2], tasas[1]);
        }

        if (salarioBruto > limites[2]) {
            impuesto += calcularTramo(salarioBruto, limites[2], limites[3], tasas[2]);
        }

        if (salarioBruto > limites[3]) {
            impuesto += (salarioBruto - limites[3]) * tasas[3];
        }

        return impuesto;
    }

    private double calcularTramo(double salario, double inicio, double fin, double tasa) {
        return Math.max(0.0, Math.min(salario, fin) - inicio) * tasa;
    }
}
