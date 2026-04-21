package proyecto2.LogicaNegocio;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class RutasProyecto {

    private RutasProyecto() {
    }

    public static Path resolver(String primeraParte, String... partesRestantes) {
        return directorioBase().resolve(Paths.get(primeraParte, partesRestantes)).normalize();
    }

    private static Path directorioBase() {
        try {
            Path ubicacion = Paths.get(RutasProyecto.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toAbsolutePath().normalize();

            // Cuando la aplicacion corre desde un JAR, se intenta volver a la raiz del proyecto.
            if (Files.isRegularFile(ubicacion)) {
                Path padre = ubicacion.getParent();
                if (padre != null && "dist".equalsIgnoreCase(padre.getFileName().toString())) {
                    return padre.getParent() != null ? padre.getParent() : padre;
                }
                return padre != null ? padre : ubicacion.getParent();
            }

            Path nombre = ubicacion.getFileName();
            // En ejecuciones desde NetBeans/Ant normalmente el classpath apunta a build/classes.
            if (nombre != null && "classes".equalsIgnoreCase(nombre.toString())) {
                Path build = ubicacion.getParent();
                if (build != null && build.getFileName() != null
                        && "build".equalsIgnoreCase(build.getFileName().toString())
                        && build.getParent() != null) {
                    return build.getParent();
                }
            }
        } catch (URISyntaxException | SecurityException ex) {
            // Si no se puede resolver desde el classpath, se usa el directorio actual.
        }

        return Paths.get("").toAbsolutePath().normalize();
    }
}
