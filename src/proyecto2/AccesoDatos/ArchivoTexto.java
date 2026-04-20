package proyecto2.AccesoDatos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ArchivoTexto implements IArchivo {

    private final Path ruta;

    public ArchivoTexto(String ruta) {
        this.ruta = Paths.get(ruta);
    }

    @Override
    public void guardar(String dato) throws IOException {
        if (ruta.getParent() != null) {
            Files.createDirectories(ruta.getParent());
        }

        Files.writeString(
                ruta,
                dato + System.lineSeparator(),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    @Override
    public String leer() throws IOException {
        if (!Files.exists(ruta)) {
            return "";
        }

        return Files.readString(ruta, StandardCharsets.UTF_8);
    }
}
