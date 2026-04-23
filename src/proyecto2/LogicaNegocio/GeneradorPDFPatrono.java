package proyecto2.LogicaNegocio;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import proyecto2.Entidades.Empleado;
import proyecto2.Entidades.Nomina;

public class GeneradorPDFPatrono {

    private static final Locale LOCALE_CR = new Locale("es", "CR");
    private static final DateTimeFormatter FORMATO_ARCHIVO = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private final CalculoNomina calculoNomina = new CalculoNomina();

    public Path generarPdf(String nombrePatrono, String cedulaJuridica, String telefono, String correo, List<Empleado> empleados) throws Exception {
        if (empleados == null || empleados.isEmpty()) {
            throw new IllegalArgumentException("Debes agregar al menos un empleado al patrono.");
        }

        Path directorio = RutasProyecto.resolver("documentos_pdf");
        Files.createDirectories(directorio);

        String nombreArchivo = String.format(
                "patrono_%s_%s.pdf",
                limpiarArchivo(cedulaJuridica),
                LocalDateTime.now().format(FORMATO_ARCHIVO)
        );
        Path rutaPdf = directorio.resolve(nombreArchivo);
        NumberFormat moneda = NumberFormat.getCurrencyInstance(LOCALE_CR);

        double totalBruto = 0.0;
        double totalNeto = 0.0;
        double totalPatronal = 0.0;
        double totalEmpresa = 0.0;

        Document document = new Document();
        try (OutputStream salida = Files.newOutputStream(rutaPdf)) {
            PdfWriter writer = PdfWriter.getInstance(document, salida);
            writer.setCloseStream(false);
            document.open();

            Font titulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font subtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normal = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font destacado = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);

            Paragraph encabezado = new Paragraph("REGISTRO DE EMPLEADOS DEL PATRONO", titulo);
            encabezado.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(encabezado);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Datos del patrono", subtitulo));
            document.add(new Paragraph("Nombre: " + valor(nombrePatrono), normal));
            document.add(new Paragraph("Cedula juridica: " + valor(cedulaJuridica), normal));
            document.add(new Paragraph("Telefono: " + valor(telefono), normal));
            document.add(new Paragraph("Correo: " + valor(correo), normal));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Empleados asignados", subtitulo));
            for (Empleado empleado : empleados) {
                Nomina nomina = calculoNomina.calcular(empleado);
                totalBruto += nomina.getSalarioBruto();
                totalNeto += nomina.getSalarioNeto();
                totalPatronal += nomina.getCargaPatronalTotal();
                totalEmpresa += nomina.getCostoTotalEmpresa();

                document.add(new Paragraph("Empleado: " + empleado.getNombre(), destacado));
                document.add(new Paragraph("Cedula: " + empleado.getCedula(), normal));
                document.add(new Paragraph("Correo: " + empleado.getCorreo(), normal));
                document.add(new Paragraph("Salario bruto: " + moneda.format(nomina.getSalarioBruto()), normal));
                document.add(new Paragraph("Deducciones: " + moneda.format(nomina.getDeducciones()), normal));
                document.add(new Paragraph("Salario neto: " + moneda.format(nomina.getSalarioNeto()), normal));
                document.add(new Paragraph("Carga patronal: " + moneda.format(nomina.getCargaPatronalTotal()), normal));
                document.add(new Paragraph("Costo total empresa: " + moneda.format(nomina.getCostoTotalEmpresa()), normal));
                document.add(new Paragraph(" "));
            }

            document.add(new Paragraph("Resumen general", subtitulo));
            document.add(new Paragraph("Cantidad de empleados: " + empleados.size(), normal));
            document.add(new Paragraph("Total salarios brutos: " + moneda.format(totalBruto), normal));
            document.add(new Paragraph("Total salarios netos: " + moneda.format(totalNeto), normal));
            document.add(new Paragraph("Total carga patronal: " + moneda.format(totalPatronal), normal));
            document.add(new Paragraph("Costo total para el patrono: " + moneda.format(totalEmpresa), destacado));
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }

        return rutaPdf.toAbsolutePath().normalize();
    }

    private String valor(String texto) {
        return texto == null || texto.trim().isEmpty() ? "No indicado" : texto.trim();
    }

    private String limpiarArchivo(String valor) {
        String limpio = valor == null ? "patrono" : valor.trim().replaceAll("[^A-Za-z0-9_-]", "_");
        return limpio.isEmpty() ? "patrono" : limpio;
    }
}
