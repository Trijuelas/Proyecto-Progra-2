package proyecto2.LogicaNegocio;

public abstract class logicaBase {

    protected static final double PORCENTAJE_SEM_TRABAJADOR = 0.0550;
    protected static final double PORCENTAJE_IVM_TRABAJADOR = 0.0433;
    protected static final double PORCENTAJE_BANCO_POPULAR_TRABAJADOR = 0.0100;

    private static final double LIMITE_RENTA_EXENTO = 918000;
    private static final double LIMITE_RENTA_10 = 1347000;
    private static final double LIMITE_RENTA_15 = 2364000;
    private static final double LIMITE_RENTA_20 = 4727000;

    protected double calcularCCSS(double salario) {
        return calcularSEM(salario) + calcularIVM(salario);
    }

    protected double calcularSEM(double salario) {
        return salario * PORCENTAJE_SEM_TRABAJADOR;
    }

    protected double calcularIVM(double salario) {
        return salario * PORCENTAJE_IVM_TRABAJADOR;
    }

    protected double calcularBancoPopular(double salario) {
        return salario * PORCENTAJE_BANCO_POPULAR_TRABAJADOR;
    }

    protected double calcularRenta(double salarioBruto) {
        double impuesto = 0;

        if (salarioBruto > LIMITE_RENTA_EXENTO) {
            impuesto += (Math.min(salarioBruto, LIMITE_RENTA_10) - LIMITE_RENTA_EXENTO) * 0.10;
        }

        if (salarioBruto > LIMITE_RENTA_10) {
            impuesto += (Math.min(salarioBruto, LIMITE_RENTA_15) - LIMITE_RENTA_10) * 0.15;
        }

        if (salarioBruto > LIMITE_RENTA_15) {
            impuesto += (Math.min(salarioBruto, LIMITE_RENTA_20) - LIMITE_RENTA_15) * 0.20;
        }

        if (salarioBruto > LIMITE_RENTA_20) {
            impuesto += (salarioBruto - LIMITE_RENTA_20) * 0.25;
        }

        return impuesto;
    }
}
