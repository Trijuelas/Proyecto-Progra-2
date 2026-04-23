package proyecto2.LogicaNegocio;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import proyecto2.Entidades.Empleado;
import proyecto2.Entidades.Nomina;

public class GeneradorPDF {

    private static final BaseColor COLOR_PRINCIPAL = new BaseColor(31, 63, 96);
    private static final BaseColor COLOR_SECUNDARIO = new BaseColor(47, 117, 181);
    private static final BaseColor COLOR_FONDO = new BaseColor(244, 247, 251);
    private static final BaseColor COLOR_BORDE = new BaseColor(214, 222, 232);
    private static final BaseColor COLOR_TEXTO = new BaseColor(45, 55, 72);
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Path generarPdf(Empleado empleado, Nomina nomina) throws Exception {
        Path directorio = RutasProyecto.resolver("documentos_pdf");
        Files.createDirectories(directorio);

        String nombreArchivo = String.format(
                "nomina_%s_%s.pdf",
                limpiarArchivo(empleado.getCedula()),
                limpiarArchivo(empleado.getNombre())
        );

        Path rutaPdf = directorio.resolve(nombreArchivo);
        Document document = new Document(PageSize.LETTER, 46, 46, 42, 42);

        try (OutputStream salida = Files.newOutputStream(rutaPdf)) {
            PdfWriter writer = PdfWriter.getInstance(document, salida);
            writer.setCloseStream(false);
            document.open();

            document.add(crearEncabezado());
            document.add(espacio(14));
            document.add(crearSeccionEmpleado(empleado));
            document.add(espacio(12));
            document.add(crearDetalleNomina(nomina));
            document.add(espacio(12));
            document.add(crearTotal(nomina));
            document.add(espacio(16));
            document.add(crearNotaFinal());

            if (document.isOpen()) {
                document.close();
            }
        } catch (IOException ex) {
            throw new IOException("No se pudo escribir el PDF en disco.", ex);
        }

        return rutaPdf.toAbsolutePath();
    }

    private PdfPTable crearEncabezado() throws Exception {
        PdfPTable tabla = new PdfPTable(new float[]{3.2f, 1.5f});
        tabla.setWidthPercentage(100);

        PdfPCell titulo = crearCeldaSinBorde();
        titulo.setPadding(18);
        titulo.setBackgroundColor(COLOR_PRINCIPAL);
        titulo.addElement(crearParrafo("COMPROBANTE DE NOMINA", 22, Font.BOLD, BaseColor.WHITE, Element.ALIGN_LEFT));
        titulo.addElement(crearParrafo("Documento generado automaticamente para envio por correo electronico.", 10, Font.NORMAL, new BaseColor(218, 230, 242), Element.ALIGN_LEFT));
        tabla.addCell(titulo);

        PdfPCell fecha = crearCeldaSinBorde();
        fecha.setPadding(18);
        fecha.setBackgroundColor(COLOR_SECUNDARIO);
        fecha.addElement(crearParrafo("FECHA DE EMISION", 9, Font.BOLD, BaseColor.WHITE, Element.ALIGN_CENTER));
        fecha.addElement(crearParrafo(LocalDateTime.now().format(FORMATO_FECHA), 13, Font.BOLD, BaseColor.WHITE, Element.ALIGN_CENTER));
        tabla.addCell(fecha);

        return tabla;
    }

    private PdfPTable crearSeccionEmpleado(Empleado empleado) throws Exception {
        PdfPTable tarjeta = crearTarjeta();
        tarjeta.addCell(crearTituloSeccion("Datos del empleado"));

        PdfPTable datos = new PdfPTable(new float[]{1.1f, 2.4f});
        datos.setWidthPercentage(100);
        agregarFilaDato(datos, "Nombre", empleado.getNombre());
        agregarFilaDato(datos, "Cedula", empleado.getCedula());
        agregarFilaDato(datos, "Correo", empleado.getCorreo());
        agregarFilaDato(datos, "Salario base", formatearMonto(empleado.getSalarioBase()));

        PdfPCell contenido = crearCeldaSinBorde();
        contenido.setPadding(14);
        contenido.addElement(datos);
        tarjeta.addCell(contenido);
        return tarjeta;
    }

    private PdfPTable crearDetalleNomina(Nomina nomina) throws Exception {
        PdfPTable tarjeta = crearTarjeta();
        tarjeta.addCell(crearTituloSeccion("Detalle de nomina"));

        PdfPTable detalle = new PdfPTable(new float[]{2.2f, 1.2f, 1.2f});
        detalle.setWidthPercentage(100);
        agregarEncabezadoTabla(detalle, "Concepto");
        agregarEncabezadoTabla(detalle, "Base / tasa");
        agregarEncabezadoTabla(detalle, "Monto");
        agregarFilaMonto(detalle, "Salario bruto", "Ingreso mensual", nomina.getSalarioBruto(), false);
        agregarFilaMonto(detalle, "CCSS - Seguro Enfermedad y Maternidad (SEM)", "5.50%", nomina.getDeduccionSEM(), true);
        agregarFilaMonto(detalle, "CCSS - Invalidez, Vejez y Muerte (IVM)", "4.33%", nomina.getDeduccionIVM(), true);
        agregarFilaMonto(detalle, "Banco Popular - aporte trabajador", "1.00%", nomina.getDeduccionBancoPopular(), true);
        agregarFilaMonto(detalle, "Impuesto sobre la renta salarial", "Tramos 2026", nomina.getImpuestoRenta(), true);
        agregarFilaMonto(detalle, "Total deducciones", "SEM + IVM + BP + renta", nomina.getDeducciones(), true);
        agregarFilaMonto(detalle, "Salario neto", "Bruto - deducciones", nomina.getSalarioNeto(), false);

        PdfPCell contenido = crearCeldaSinBorde();
        contenido.setPadding(14);
        contenido.addElement(detalle);
        tarjeta.addCell(contenido);
        return tarjeta;
    }

    private PdfPTable crearTotal(Nomina nomina) throws Exception {
        PdfPTable total = new PdfPTable(new float[]{2.4f, 1.2f});
        total.setWidthPercentage(100);

        PdfPCell etiqueta = crearCeldaSinBorde();
        etiqueta.setPadding(16);
        etiqueta.setBackgroundColor(COLOR_PRINCIPAL);
        etiqueta.addElement(crearParrafo("TOTAL A RECIBIR", 12, Font.BOLD, BaseColor.WHITE, Element.ALIGN_LEFT));
        etiqueta.addElement(crearParrafo("Salario neto despues de cargas sociales e impuesto de renta.", 9, Font.NORMAL, new BaseColor(218, 230, 242), Element.ALIGN_LEFT));
        total.addCell(etiqueta);

        PdfPCell monto = crearCeldaSinBorde();
        monto.setPadding(16);
        monto.setBackgroundColor(COLOR_SECUNDARIO);
        monto.addElement(crearParrafo(formatearMonto(nomina.getSalarioNeto()), 18, Font.BOLD, BaseColor.WHITE, Element.ALIGN_RIGHT));
        total.addCell(monto);

        return total;
    }

    private Paragraph crearNotaFinal() {
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.ITALIC, new BaseColor(105, 117, 134));
        Paragraph nota = new Paragraph("Calculo estimado con cargas obreras de Costa Rica y tramos de renta salarial 2026. No incluye creditos fiscales por hijos o conyuge.", fuente);
        nota.setAlignment(Element.ALIGN_CENTER);
        nota.setSpacingBefore(8);
        return nota;
    }

    private PdfPTable crearTarjeta() throws Exception {
        PdfPTable tarjeta = new PdfPTable(1);
        tarjeta.setWidthPercentage(100);
        tarjeta.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        return tarjeta;
    }

    private PdfPCell crearTituloSeccion(String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE)));
        celda.setBorder(Rectangle.NO_BORDER);
        celda.setPadding(10);
        celda.setBackgroundColor(COLOR_PRINCIPAL);
        return celda;
    }

    private void agregarFilaDato(PdfPTable tabla, String etiqueta, String valor) {
        PdfPCell celdaEtiqueta = crearCeldaTabla(etiqueta, Font.BOLD, COLOR_PRINCIPAL, COLOR_FONDO);
        PdfPCell celdaValor = crearCeldaTabla(valor, Font.NORMAL, COLOR_TEXTO, BaseColor.WHITE);
        tabla.addCell(celdaEtiqueta);
        tabla.addCell(celdaValor);
    }

    private void agregarEncabezadoTabla(PdfPTable tabla, String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE)));
        celda.setPadding(9);
        celda.setBorderColor(COLOR_PRINCIPAL);
        celda.setBackgroundColor(COLOR_PRINCIPAL);
        tabla.addCell(celda);
    }

    private void agregarFilaMonto(PdfPTable tabla, String concepto, String base, double monto, boolean deduccion) {
        tabla.addCell(crearCeldaTabla(concepto, Font.NORMAL, COLOR_TEXTO, BaseColor.WHITE));
        PdfPCell celdaBase = crearCeldaTabla(base, Font.NORMAL, COLOR_TEXTO, BaseColor.WHITE);
        celdaBase.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celdaBase);
        PdfPCell celdaMonto = crearCeldaTabla((deduccion ? "- " : "") + formatearMonto(monto), Font.BOLD, deduccion ? new BaseColor(170, 54, 54) : COLOR_TEXTO, BaseColor.WHITE);
        celdaMonto.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(celdaMonto);
    }

    private PdfPCell crearCeldaTabla(String texto, int estilo, BaseColor colorTexto, BaseColor fondo) {
        PdfPCell celda = new PdfPCell(new Phrase(texto == null || texto.trim().isEmpty() ? "No indicado" : texto, FontFactory.getFont(FontFactory.HELVETICA, 10, estilo, colorTexto)));
        celda.setPadding(9);
        celda.setBorderColor(COLOR_BORDE);
        celda.setBackgroundColor(fondo);
        return celda;
    }

    private PdfPCell crearCeldaSinBorde() {
        PdfPCell celda = new PdfPCell();
        celda.setBorder(Rectangle.NO_BORDER);
        return celda;
    }

    private Paragraph crearParrafo(String texto, float tamano, int estilo, BaseColor color, int alineacion) {
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA, tamano, estilo, color);
        Paragraph parrafo = new Paragraph(texto, fuente);
        parrafo.setAlignment(alineacion);
        parrafo.setLeading(tamano + 4);
        return parrafo;
    }

    private Paragraph espacio(float alto) {
        Paragraph espacio = new Paragraph(new Chunk(" "));
        espacio.setLeading(alto);
        return espacio;
    }

    private String formatearMonto(double monto) {
        return String.format("CRC %,.2f", monto);
    }

    private String limpiarArchivo(String valor) {
        return valor == null ? "sin_dato" : valor.trim().replaceAll("[^a-zA-Z0-9-_]", "_");
    }
}
