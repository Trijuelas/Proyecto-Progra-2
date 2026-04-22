package proyecto2.AccesoDatos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class ArchivoTexto implements IArchivo {

    private final Path ruta;

    public ArchivoTexto(String ruta) {
        this(validarRuta(ruta));
    }

    public ArchivoTexto(Path ruta) {
        this.ruta = Objects.requireNonNull(ruta, "La ruta del archivo no puede ser nula.")
                .toAbsolutePath()
                .normalize();
    }

    @Override
    public void guardar(String dato) throws IOException {
        try {
            asegurarDirectorioPadre();
            Files.writeString(
                    ruta,
                    String.valueOf(dato) + System.lineSeparator(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException ex) {
            throw new IOException("No se pudo guardar informacion en: " + ruta, ex);
        }
    }

    @Override
    public String leer() throws IOException {
        try {
            if (!Files.exists(ruta)) {
                return "";
            }

            return Files.readString(ruta, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new IOException("No se pudo leer la informacion de: " + ruta, ex);
        }
    }

    public Path getRuta() {
        return ruta;
    }

    private void asegurarDirectorioPadre() throws IOException {
        if (ruta.getParent() != null) {
            Files.createDirectories(ruta.getParent());
        }
    }

    private static Path validarRuta(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta del archivo no puede estar vacia.");
        }

        return Paths.get(ruta.trim());
    }
}
