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
    private double deduccionSEM;
    private double deduccionIVM;
    private double deduccionBancoPopular;
    private double impuestoRenta;
    private double deducciones;
    private double salarioNeto;

    public Nomina(double salarioBruto, double deducciones, double salarioNeto) {
        this(salarioBruto, 0, 0, 0, deducciones, salarioNeto);
    }

    public Nomina(double salarioBruto, double deduccionSEM, double deduccionIVM, double deduccionBancoPopular, double impuestoRenta, double salarioNeto) {
        this.salarioBruto = salarioBruto;
        this.deduccionSEM = deduccionSEM;
        this.deduccionIVM = deduccionIVM;
        this.deduccionBancoPopular = deduccionBancoPopular;
        this.impuestoRenta = impuestoRenta;
        this.deducciones = deduccionSEM + deduccionIVM + deduccionBancoPopular + impuestoRenta;
        this.salarioNeto = salarioNeto;
    }

    public double getSalarioBruto() { return salarioBruto; }
    public double getDeduccionSEM() { return deduccionSEM; }
    public double getDeduccionIVM() { return deduccionIVM; }
    public double getDeduccionBancoPopular() { return deduccionBancoPopular; }
    public double getImpuestoRenta() { return impuestoRenta; }
    public double getDeducciones() { return deducciones; }
    public double getSalarioNeto() { return salarioNeto; }
}
