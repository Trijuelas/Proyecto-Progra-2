package proyecto2.LogicaNegocio;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import proyecto2.Entidades.Empleado;
import proyecto2.Entidades.Nomina;

public class GeneradorPDF {

    public Path generarPdf(Empleado empleado, Nomina nomina) throws Exception {
        Path directorio = RutasProyecto.resolver("documentos_pdf");
        Files.createDirectories(directorio);

        String nombreArchivo = String.format(
                "nomina_%s_%s.pdf",
                limpiarArchivo(empleado.getCedula()),
                limpiarArchivo(empleado.getNombre())
        );

        Path rutaPdf = directorio.resolve(nombreArchivo);
        Document document = new Document();

        try (OutputStream salida = Files.newOutputStream(rutaPdf)) {
            PdfWriter writer = PdfWriter.getInstance(document, salida);
            writer.setCloseStream(false);
            document.open();

            Font titulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font subtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normal = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph encabezado = new Paragraph("COMPROBANTE DE NOMINA", titulo);
            encabezado.setAlignment(Element.ALIGN_CENTER);
            document.add(encabezado);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Datos del empleado", subtitulo));
            document.add(new Paragraph("Nombre: " + empleado.getNombre(), normal));
            document.add(new Paragraph("Cedula: " + empleado.getCedula(), normal));
            document.add(new Paragraph("Correo: " + empleado.getCorreo(), normal));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Detalle de nomina", subtitulo));
            document.add(new Paragraph(String.format("Salario bruto: %.2f", nomina.getSalarioBruto()), normal));
            document.add(new Paragraph(String.format("Deducciones: %.2f", nomina.getDeducciones()), normal));
            document.add(new Paragraph(String.format("Salario neto: %.2f", nomina.getSalarioNeto()), normal));
            if (document.isOpen()) {
                document.close();
            }
        } catch (IOException ex) {
            throw new IOException("No se pudo escribir el PDF en disco.", ex);
        }

        return rutaPdf.toAbsolutePath();
    }

    private String limpiarArchivo(String valor) {
        return valor == null ? "sin_dato" : valor.trim().replaceAll("[^a-zA-Z0-9-_]", "_");
    }
}
