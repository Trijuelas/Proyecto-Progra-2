package proyecto2.LogicaNegocio;

import proyecto2.Entidades.Empleado;
import proyecto2.Entidades.Nomina;

public interface ICalculable {
    Nomina calcular(Empleado empleado);
}
