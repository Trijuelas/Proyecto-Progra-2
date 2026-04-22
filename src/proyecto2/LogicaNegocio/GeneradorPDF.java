package proyecto2.LogicaNegocio;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
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

            // Fuentes
            Font titulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE);
            Font subtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY);
            Font normal = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font negrita = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            // Encabezado
            Paragraph encabezado = new Paragraph("EMPRESA XYZ - SISTEMA DE NÓMINA", titulo);
            encabezado.setAlignment(Element.ALIGN_CENTER);
            document.add(encabezado);
            document.add(new Paragraph(" "));

            // Fecha
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechaActual = sdf.format(new Date());
            Paragraph fecha = new Paragraph("Fecha de emisión: " + fechaActual, normal);
            fecha.setAlignment(Element.ALIGN_RIGHT);
            document.add(fecha);
            document.add(new Paragraph(" "));

            // Título del comprobante
            Paragraph tituloComprobante = new Paragraph("COMPROBANTE DE NÓMINA", subtitulo);
            tituloComprobante.setAlignment(Element.ALIGN_CENTER);
            document.add(tituloComprobante);
            document.add(new Paragraph(" "));

            // Tabla de datos del empleado
            PdfPTable tablaEmpleado = new PdfPTable(2);
            tablaEmpleado.setWidthPercentage(100);
            tablaEmpleado.setSpacingBefore(10f);
            tablaEmpleado.setSpacingAfter(10f);

            // Encabezados de tabla
            PdfPCell cell = new PdfPCell(new Phrase("Información del Empleado", negrita));
            cell.setColspan(2);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaEmpleado.addCell(cell);

            tablaEmpleado.addCell(new PdfPCell(new Phrase("Nombre:", negrita)));
            tablaEmpleado.addCell(empleado.getNombre());

            tablaEmpleado.addCell(new PdfPCell(new Phrase("Cédula:", negrita)));
            tablaEmpleado.addCell(empleado.getCedula());

            tablaEmpleado.addCell(new PdfPCell(new Phrase("Correo:", negrita)));
            tablaEmpleado.addCell(empleado.getCorreo());

            document.add(tablaEmpleado);
            document.add(new Paragraph(" "));

            // Tabla de detalle de nómina
            PdfPTable tablaNomina = new PdfPTable(2);
            tablaNomina.setWidthPercentage(100);
            tablaNomina.setSpacingBefore(10f);
            tablaNomina.setSpacingAfter(10f);

            cell = new PdfPCell(new Phrase("Detalle de Nómina", negrita));
            cell.setColspan(2);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaNomina.addCell(cell);

            tablaNomina.addCell(new PdfPCell(new Phrase("Salario Bruto:", negrita)));
            tablaNomina.addCell(String.format("%.2f", nomina.getSalarioBruto()));

            tablaNomina.addCell(new PdfPCell(new Phrase("Deducciones:", negrita)));
            tablaNomina.addCell(String.format("%.2f", nomina.getDeducciones()));

            PdfPCell cellNeto = new PdfPCell(new Phrase("Salario Neto:", negrita));
            cellNeto.setBackgroundColor(BaseColor.GREEN);
            tablaNomina.addCell(cellNeto);
            PdfPCell cellValorNeto = new PdfPCell(String.format("%.2f", nomina.getSalarioNeto()));
            cellValorNeto.setBackgroundColor(BaseColor.GREEN);
            tablaNomina.addCell(cellValorNeto);

            document.add(tablaNomina);
            document.add(new Paragraph(" "));

            // Pie de página
            Paragraph pie = new Paragraph("Este documento es generado automáticamente por el Sistema de Nómina.", normal);
            pie.setAlignment(Element.ALIGN_CENTER);
            document.add(pie);

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
