package proyecto2.LogicaNegocio;

import java.util.regex.Pattern;

public class Correo {

    private static final Pattern PATRON_CORREO =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);

    public String limpiar(String correo) {
        if (correo == null) {
            return "";
        }
        return correo.trim().toLowerCase();
    }

    public boolean esValido(String correo) {
        String correoLimpio = limpiar(correo);
        return !correoLimpio.isEmpty() && PATRON_CORREO.matcher(correoLimpio).matches();
    }
}
