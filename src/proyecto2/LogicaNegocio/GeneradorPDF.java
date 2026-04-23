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
import java.text.NumberFormat;
import java.util.Locale;
import proyecto2.Entidades.Empleado;
import proyecto2.Entidades.Nomina;

public class GeneradorPDF {

    private static final Locale LOCALE_CR = new Locale("es", "CR");

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
        NumberFormat moneda = NumberFormat.getCurrencyInstance(LOCALE_CR);

        try (OutputStream salida = Files.newOutputStream(rutaPdf)) {
            PdfWriter writer = PdfWriter.getInstance(document, salida);
            writer.setCloseStream(false);
            document.open();

            Font titulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font subtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normal = FontFactory.getFont(FontFactory.HELVETICA, 11);

            Paragraph encabezado = new Paragraph("COMPROBANTE DE NOMINA", titulo);
            encabezado.setAlignment(Element.ALIGN_CENTER);
            document.add(encabezado);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Datos del empleado", subtitulo));
            document.add(new Paragraph("Nombre: " + empleado.getNombre(), normal));
            document.add(new Paragraph("Cedula: " + empleado.getCedula(), normal));
            document.add(new Paragraph("Correo: " + empleado.getCorreo(), normal));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Desglose obrero", subtitulo));
            document.add(new Paragraph("SEM 5.50%: " + moneda.format(nomina.getSeguroEnfermedadMaternidadObrero()), normal));
            document.add(new Paragraph("IVM 4.33%: " + moneda.format(nomina.getInvalidezVejezMuerteObrero()), normal));
            document.add(new Paragraph("Banco Popular 1.00%: " + moneda.format(nomina.getBancoPopularObrero()), normal));
            document.add(new Paragraph("Impuesto sobre la renta: " + moneda.format(nomina.getImpuestoRenta()), normal));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Desglose patronal", subtitulo));
            document.add(new Paragraph("SEM patronal 9.25%: " + moneda.format(nomina.getSeguroEnfermedadMaternidadPatronal()), normal));
            document.add(new Paragraph("IVM patronal 5.58%: " + moneda.format(nomina.getInvalidezVejezMuertePatronal()), normal));
            document.add(new Paragraph("Asignaciones familiares 5.00%: " + moneda.format(nomina.getAsignacionesFamiliares()), normal));
            document.add(new Paragraph("IMAS 0.50%: " + moneda.format(nomina.getImas()), normal));
            document.add(new Paragraph("INA 1.50%: " + moneda.format(nomina.getIna()), normal));
            document.add(new Paragraph("Banco Popular patronal 0.25%: " + moneda.format(nomina.getBancoPopularPatronal()), normal));
            document.add(new Paragraph("FCL 1.50%: " + moneda.format(nomina.getFondoCapitalizacionLaboral()), normal));
            document.add(new Paragraph("OPC 2.00%: " + moneda.format(nomina.getOperadoraPensionesComplementarias()), normal));
            document.add(new Paragraph("INS 1.00%: " + moneda.format(nomina.getIns()), normal));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Totales", subtitulo));
            document.add(new Paragraph("Salario bruto: " + moneda.format(nomina.getSalarioBruto()), normal));
            document.add(new Paragraph("Deducciones totales: " + moneda.format(nomina.getDeducciones()), normal));
            document.add(new Paragraph("Salario neto: " + moneda.format(nomina.getSalarioNeto()), normal));
            document.add(new Paragraph("Carga patronal total: " + moneda.format(nomina.getCargaPatronalTotal()), normal));
            document.add(new Paragraph("Costo total empresa: " + moneda.format(nomina.getCostoTotalEmpresa()), normal));

            if (nomina.isAplicaBaseMinimaSem() || nomina.isAplicaBaseMinimaIvm()) {
                document.add(new Paragraph(" "));
                document.add(new Paragraph(
                        "Nota: el salario reportado activa la base minima contributiva 2026 de la CCSS para SEM/IVM.",
                        normal
                ));
            }

            if (document.isOpen()) {
                document.close();
            }
        } catch (IOException ex) {
            throw new IOException("No se pudo escribir el PDF en disco.", ex);
        }

        return rutaPdf.toAbsolutePath().normalize();
    }

    private String limpiarArchivo(String valor) {
        return valor == null ? "sin_dato" : valor.trim().replaceAll("[^a-zA-Z0-9-_]", "_");
    }
}
