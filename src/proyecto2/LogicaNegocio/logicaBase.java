package proyecto2.LogicaNegocio;

public abstract class logicaBase {

    protected double calcularCCSS(double salario) {
        return salario * 0.10; // ejemplo Costa Rica
    }

    protected double calcularRenta(double salario) {
        return salario * 0.05;
    }
}
