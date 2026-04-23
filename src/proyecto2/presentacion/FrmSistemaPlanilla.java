package proyecto2.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import proyecto2.AccesoDatos.ArchivoTexto;
import proyecto2.Entidades.Empleado;
import proyecto2.Entidades.Nomina;
import proyecto2.LogicaNegocio.CalculoNomina;
import proyecto2.LogicaNegocio.Correo;
import proyecto2.LogicaNegocio.GeneradorPDF;
import proyecto2.LogicaNegocio.MailService;
import proyecto2.LogicaNegocio.RutasProyecto;

public class FrmSistemaPlanilla extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Locale LOCALE_CR = new Locale("es", "CR");
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final Color BACKGROUND = new Color(246, 242, 235);
    private static final Color PANEL = new Color(255, 252, 247);
    private static final Color PRIMARY = new Color(15, 76, 92);
    private static final Color SECONDARY = new Color(185, 124, 64);
    private static final Color ACCENT = new Color(225, 239, 233);
    private static final Color TEXT = new Color(44, 44, 44);
    private static final Color MUTED = new Color(108, 106, 101);
    private static final Color BORDER = new Color(220, 214, 205);
    private static final Color SUCCESS = new Color(46, 125, 50);
    private static final Color ERROR = new Color(183, 28, 28);

    private final Correo correoHelper = new Correo();
    private final CalculoNomina calculoNomina = new CalculoNomina();
    private final GeneradorPDF generadorPDF = new GeneradorPDF();
    private final MailService mailService = new MailService();
    private final NumberFormat moneda = NumberFormat.getCurrencyInstance(LOCALE_CR);
    private final Path rutaRegistro = RutasProyecto.resolver("data", "nominas_registradas.txt");

    private JTabbedPane tabs;
    private JTextField txtUsuario;
    private JPasswordField txtClaveAcceso;
    private JLabel lblEstadoLogin;

    private JTextField txtRemitente;
    private JPasswordField txtClaveCorreo;
    private JCheckBox chkMostrarClaveCorreo;
    private JTextField txtNombre;
    private JTextField txtCedula;
    private JTextField txtSalario;
    private JTextField txtCorreoDestino;
    private JLabel lblEstadoGestion;
    private JLabel lblSalarioBruto;
    private JLabel lblDeduccionObrera;
    private JLabel lblRenta;
    private JLabel lblSalarioNeto;
    private JLabel lblCargaPatronal;
    private JLabel lblCostoEmpresa;
    private JLabel lblObservacionLegal;
    private JTextArea txtResumen;
    private JTextArea txtCierre;
    private DefaultTableModel modeloTabla;

    private boolean sesionActiva;
    private Empleado ultimoEmpleado;
    private Nomina ultimaNomina;
    private Path ultimoPdf;

    public FrmSistemaPlanilla() {
        initComponents();
        cargarRegistrosExistentes();
        actualizarResumenCierre();
    }

    private void initComponents() {
        setTitle("Sistema de Planilla Costa Rica");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 560));
        setSize(1100, 700);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(18, 18));
        root.setBackground(BACKGROUND);
        root.setBorder(new EmptyBorder(14, 14, 14, 14));
        setContentPane(root);

        root.add(buildHeader(), BorderLayout.NORTH);

        tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.BOLD, 14));
        tabs.addTab("Login", buildLoginTab());
        tabs.addTab("Gestion y registro", buildGestionTab());
        tabs.addTab("Cierre del proceso", buildCierreTab());
        tabs.setEnabledAt(1, false);
        tabs.setEnabledAt(2, false);
        root.add(tabs, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(PRIMARY);
        panel.setBorder(new EmptyBorder(18, 22, 18, 22));

        JLabel titulo = new JLabel("Planilla patronal y registro de empleados");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));

        JLabel subtitulo = new JLabel(
                "<html>Interfaz con flujo de login, gestion de nomina y salida. "
                + "La vista usa referencias 2026 de CCSS y Hacienda para mostrar cargas sociales, renta y costo patronal.</html>"
        );
        subtitulo.setForeground(new Color(224, 241, 236));
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.add(titulo);
        textos.add(Box.createVerticalStrut(6));
        textos.add(subtitulo);

        JLabel sello = new JLabel("Costa Rica");
        sello.setOpaque(true);
        sello.setBackground(SECONDARY);
        sello.setForeground(Color.WHITE);
        sello.setHorizontalAlignment(SwingConstants.CENTER);
        sello.setBorder(new EmptyBorder(12, 20, 12, 20));
        sello.setFont(new Font("SansSerif", Font.BOLD, 16));

        JButton btnVolver = createSecondaryButton("Volver al menu");
        btnVolver.addActionListener(evt -> volverAlMenu());

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        acciones.setOpaque(false);
        acciones.add(sello);
        acciones.add(btnVolver);

        panel.add(textos, BorderLayout.CENTER);
        panel.add(acciones, BorderLayout.EAST);
        return panel;
    }

    private JPanel buildLoginTab() {
        JPanel wrapper = createPanel(new BorderLayout(18, 18), 24);

        JPanel hero = createSoftPanel(new BorderLayout(10, 10));
        JLabel titulo = new JLabel("Acceso al modulo de presentacion");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(TEXT);

        JTextArea ayuda = createReadOnlyText(
                "Ingresa un usuario y contrasena para habilitar las pestañas de gestion y cierre.\n\n"
                + "Este acceso es una validacion local para la presentacion del proyecto en NetBeans."
        );

        hero.add(titulo, BorderLayout.NORTH);
        hero.add(ayuda, BorderLayout.CENTER);

        JPanel formulario = createPanel(new GridLayout(0, 1, 0, 10), 24);
        formulario.setBackground(PANEL);
        formulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(24, 24, 24, 24)
        ));

        formulario.add(createFieldLabel("Usuario"));
        txtUsuario = createTextField();
        formulario.add(txtUsuario);

        formulario.add(createFieldLabel("Contrasena"));
        txtClaveAcceso = createPasswordField();
        formulario.add(txtClaveAcceso);

        JButton btnIngresar = createPrimaryButton("Iniciar sesion");
        btnIngresar.addActionListener(evt -> autenticar());
        formulario.add(btnIngresar);
        getRootPane().setDefaultButton(btnIngresar);

        JButton btnLimpiar = createSecondaryButton("Limpiar");
        btnLimpiar.addActionListener(evt -> {
            txtUsuario.setText("");
            txtClaveAcceso.setText("");
            actualizarEstadoLogin("Campos reiniciados.", false);
        });
        formulario.add(btnLimpiar);

        lblEstadoLogin = new JLabel("Acceso pendiente.");
        lblEstadoLogin.setForeground(MUTED);
        lblEstadoLogin.setFont(new Font("SansSerif", Font.PLAIN, 13));
        formulario.add(lblEstadoLogin);

        wrapper.add(hero, BorderLayout.CENTER);
        wrapper.add(formulario, BorderLayout.EAST);
        return wrapper;
    }

    private JPanel buildGestionTab() {
        JPanel wrapper = createPanel(new BorderLayout(18, 18), 18);

        JScrollPane scrollFormulario = new JScrollPane(buildFormularioGestion());
        scrollFormulario.setBorder(null);
        scrollFormulario.getVerticalScrollBar().setUnitIncrement(16);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollFormulario, buildPanelDerecho());
        split.setResizeWeight(0.55);
        split.setBorder(null);
        split.setContinuousLayout(true);
        wrapper.add(split, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel buildFormularioGestion() {
        JPanel panel = createPanel(new BorderLayout(14, 14), 18);

        JPanel form = createPanel(new GridLayout(0, 1, 0, 8), 18);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(18, 18, 18, 18)
        ));

        form.add(createSectionTitle("Datos de envio"));
        form.add(createFieldLabel("Correo remitente de Gmail"));
        txtRemitente = createTextField();
        form.add(txtRemitente);

        form.add(createFieldLabel("Contrasena de aplicacion"));
        txtClaveCorreo = createPasswordField();
        form.add(txtClaveCorreo);

        chkMostrarClaveCorreo = new JCheckBox("Mostrar contrasena");
        chkMostrarClaveCorreo.setOpaque(false);
        chkMostrarClaveCorreo.setForeground(MUTED);
        chkMostrarClaveCorreo.addActionListener(evt -> txtClaveCorreo.setEchoChar(chkMostrarClaveCorreo.isSelected() ? (char) 0 : '\u2022'));
        form.add(chkMostrarClaveCorreo);

        form.add(createSectionTitle("Registro del empleado"));
        form.add(createFieldLabel("Nombre completo"));
        txtNombre = createTextField();
        form.add(txtNombre);

        form.add(createFieldLabel("Cedula"));
        txtCedula = createTextField();
        form.add(txtCedula);

        form.add(createFieldLabel("Salario base mensual (CRC)"));
        txtSalario = createTextField();
        form.add(txtSalario);

        form.add(createFieldLabel("Correo del empleado"));
        txtCorreoDestino = createTextField();
        form.add(txtCorreoDestino);

        JPanel botones = new JPanel(new GridLayout(1, 4, 10, 0));
        botones.setOpaque(false);
        JButton btnRegistrar = createPrimaryButton("Calcular y registrar");
        btnRegistrar.addActionListener(evt -> procesarNomina(false));
        botones.add(btnRegistrar);

        JButton btnEnviar = createSecondaryButton("Generar PDF y enviar");
        btnEnviar.addActionListener(evt -> procesarNomina(true));
        botones.add(btnEnviar);

        JButton btnLimpiar = createSecondaryButton("Limpiar");
        btnLimpiar.addActionListener(evt -> limpiarGestion());
        botones.add(btnLimpiar);

        JButton btnVolver = createSecondaryButton("Volver al login");
        btnVolver.addActionListener(evt -> tabs.setSelectedIndex(0));
        botones.add(btnVolver);
        form.add(botones);

        lblEstadoGestion = new JLabel("Listo para registrar una nomina.");
        lblEstadoGestion.setForeground(MUTED);
        form.add(lblEstadoGestion);

        panel.add(form, BorderLayout.NORTH);
        panel.add(buildLegalInfoPanel(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildPanelDerecho() {
        JPanel panel = createPanel(new BorderLayout(14, 14), 0);
        panel.add(buildMetricPanel(), BorderLayout.NORTH);
        panel.add(buildRegistroPanel(), BorderLayout.CENTER);
        panel.add(buildResumenPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildMetricPanel() {
        JPanel panel = createPanel(new GridLayout(2, 3, 12, 12), 0);
        lblSalarioBruto = createMetricCard(panel, "Salario bruto");
        lblDeduccionObrera = createMetricCard(panel, "Deduccion obrera");
        lblRenta = createMetricCard(panel, "Renta salarial");
        lblSalarioNeto = createMetricCard(panel, "Salario neto");
        lblCargaPatronal = createMetricCard(panel, "Carga patronal");
        lblCostoEmpresa = createMetricCard(panel, "Costo empresa");
        actualizarMetricas(null);
        return panel;
    }

    private JLabel createMetricCard(JPanel contenedor, String titulo) {
        JPanel card = createSoftPanel(new BorderLayout(0, 8));
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(MUTED);
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel lblValor = new JLabel(moneda.format(0));
        lblValor.setForeground(TEXT);
        lblValor.setFont(new Font("SansSerif", Font.BOLD, 22));

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);
        contenedor.add(card);
        return lblValor;
    }

    private JPanel buildRegistroPanel() {
        JPanel panel = createPanel(new BorderLayout(10, 10), 16);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(16, 16, 16, 16)
        ));

        JLabel titulo = createSectionTitle("Registro de empleados");
        panel.add(titulo, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
                new Object[]{"Fecha", "Estado", "Empleado", "Cedula", "Neto", "Patronal", "PDF"},
                0
        ) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(24);
        tabla.getTableHeader().setReorderingAllowed(false);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildResumenPanel() {
        JPanel panel = createPanel(new BorderLayout(10, 10), 16);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(16, 16, 16, 16)
        ));

        panel.add(createSectionTitle("Resumen legal y operativo"), BorderLayout.NORTH);

        txtResumen = createReadOnlyText("Aqui veras el desglose de la ultima nomina calculada.");
        txtResumen.setRows(10);
        panel.add(new JScrollPane(txtResumen), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildLegalInfoPanel() {
        JPanel panel = createSoftPanel(new BorderLayout(10, 10));

        panel.add(createSectionTitle("Referencias graficas de Costa Rica"), BorderLayout.NORTH);

        String texto = "CCSS trabajador: SEM 5.50%, IVM 4.33%, Banco Popular 1.00%.\n"
                + "CCSS y cargas patronales: SEM 9.25%, IVM 5.58%, Asignaciones 5.00%, IMAS 0.50%, INA 1.50%, "
                + "Banco Popular 0.25%, FCL 1.50%, OPC 2.00%, INS 1.00%.\n"
                + "Impuesto sobre la renta 2026: exento hasta CRC 918,000 mensuales y luego tramos progresivos.\n"
                + "Nota visual: aguinaldo, vacaciones, horas extra e incapacidades deben liquidarse segun el caso concreto y no se automatizan en esta pantalla.";
        panel.add(createReadOnlyText(texto), BorderLayout.CENTER);

        lblObservacionLegal = new JLabel("Sin observaciones de base minima contributiva.");
        lblObservacionLegal.setForeground(MUTED);
        lblObservacionLegal.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panel.add(lblObservacionLegal, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildCierreTab() {
        JPanel panel = createPanel(new BorderLayout(18, 18), 24);

        JPanel resumen = createSoftPanel(new BorderLayout(10, 10));
        resumen.add(createSectionTitle("Cierre del proceso"), BorderLayout.NORTH);
        txtCierre = createReadOnlyText("");
        txtCierre.setRows(14);
        resumen.add(new JScrollPane(txtCierre), BorderLayout.CENTER);

        JPanel acciones = createPanel(new GridLayout(0, 1, 0, 12), 18);
        acciones.setPreferredSize(new Dimension(320, 0));
        acciones.add(createSoftPanel(new BorderLayout(0, 8)));

        JButton btnFinalizar = createPrimaryButton("Finalizar proceso");
        btnFinalizar.addActionListener(evt -> {
            actualizarResumenCierre();
            JOptionPane.showMessageDialog(
                    this,
                    "Proceso revisado. Puedes cerrar la interfaz cuando lo desees.",
                    "Cierre listo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        JButton btnSalir = createSecondaryButton("Salir de la interfaz");
        btnSalir.addActionListener(evt -> salir());

        JButton btnVolver = createSecondaryButton("Volver al menu");
        btnVolver.addActionListener(evt -> volverAlMenu());

        JPanel bloque = createSoftPanel(new GridLayout(0, 1, 0, 12));
        bloque.add(createReadOnlyText(
                "Usa esta pestana para dejar documentado el ultimo empleado procesado, el PDF generado y el recordatorio de validar la planilla en Oficina Virtual CCSS."
        ));
        bloque.add(btnFinalizar);
        bloque.add(btnVolver);
        bloque.add(btnSalir);
        acciones.removeAll();
        acciones.add(bloque);

        panel.add(resumen, BorderLayout.CENTER);
        panel.add(acciones, BorderLayout.EAST);
        return panel;
    }

    private JPanel createPanel(BorderLayout layout, int padding) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(BACKGROUND);
        if (padding > 0) {
            panel.setBorder(new EmptyBorder(padding, padding, padding, padding));
        }
        return panel;
    }

    private JPanel createPanel(GridLayout layout, int padding) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(BACKGROUND);
        if (padding > 0) {
            panel.setBorder(new EmptyBorder(padding, padding, padding, padding));
        }
        return panel;
    }

    private JPanel createSoftPanel(BorderLayout layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(ACCENT);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(16, 16, 16, 16)
        ));
        return panel;
    }

    private JPanel createSoftPanel(GridLayout layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(ACCENT);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(16, 16, 16, 16)
        ));
        return panel;
    }

    private JLabel createFieldLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(TEXT);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        return label;
    }

    private JLabel createSectionTitle(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(TEXT);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(0, 38));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(9, 10, 9, 10)
        ));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(0, 38));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(9, 10, 9, 10)
        ));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setEchoChar('\u2022');
        return field;
    }

    private JTextArea createReadOnlyText(String texto) {
        JTextArea area = new JTextArea(texto);
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        area.setEditable(false);
        area.setOpaque(false);
        area.setForeground(TEXT);
        area.setFont(new Font("SansSerif", Font.PLAIN, 13));
        area.setBorder(null);
        return area;
    }

    private JButton createPrimaryButton(String texto) {
        JButton button = new JButton(texto);
        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(12, 14, 12, 14));
        return button;
    }

    private JButton createSecondaryButton(String texto) {
        JButton button = new JButton(texto);
        button.setBackground(new Color(232, 224, 214));
        button.setForeground(TEXT);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(12, 14, 12, 14));
        return button;
    }

    private void autenticar() {
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClaveAcceso.getPassword()).trim();

        if (usuario.isEmpty() || clave.isEmpty()) {
            actualizarEstadoLogin("Debes completar usuario y contrasena.", true);
            return;
        }

        sesionActiva = true;
        tabs.setEnabledAt(1, true);
        tabs.setEnabledAt(2, true);
        tabs.setSelectedIndex(1);
        actualizarEstadoLogin("Sesion iniciada para " + usuario + ".", false);
    }

    private void actualizarEstadoLogin(String mensaje, boolean error) {
        lblEstadoLogin.setText(mensaje);
        lblEstadoLogin.setForeground(error ? ERROR : SUCCESS);
    }

    private void procesarNomina(boolean enviarCorreo) {
        if (!sesionActiva) {
            mostrarError("Primero debes iniciar sesion en la pestana Login.");
            tabs.setSelectedIndex(0);
            return;
        }

        Empleado empleado = construirEmpleado();
        if (empleado == null) {
            return;
        }

        if (enviarCorreo) {
            String remitente = correoHelper.limpiar(txtRemitente.getText());
            String claveCorreo = new String(txtClaveCorreo.getPassword()).trim();

            if (!correoHelper.esValido(remitente) || !remitente.endsWith("@gmail.com")) {
                mostrarError("Para el envio debes indicar un Gmail valido como remitente.");
                return;
            }

            if (claveCorreo.isEmpty()) {
                mostrarError("Debes ingresar la contrasena de aplicacion de Gmail.");
                return;
            }
        }

        Nomina nomina = calculoNomina.calcular(empleado);
        Path rutaPdf = null;

        try {
            if (enviarCorreo) {
                rutaPdf = generadorPDF.generarPdf(empleado, nomina);
                mailService.enviarCorreo(
                        correoHelper.limpiar(txtRemitente.getText()),
                        new String(txtClaveCorreo.getPassword()).trim(),
                        empleado,
                        nomina,
                        rutaPdf
                );
            }

            String estado = enviarCorreo ? "Comprobante enviado" : "Registro interno";
            registrarNomina(empleado, nomina, rutaPdf, estado);
            actualizarResumenOperacion(empleado, nomina, rutaPdf, estado);
            actualizarMetricas(nomina);
            ultimoEmpleado = empleado;
            ultimaNomina = nomina;
            ultimoPdf = rutaPdf;
            actualizarResumenCierre();
            lblEstadoGestion.setText("Proceso completado: " + estado + ".");
            lblEstadoGestion.setForeground(SUCCESS);

            JOptionPane.showMessageDialog(
                    this,
                    enviarCorreo ? "Nomina enviada y registrada correctamente." : "Nomina registrada correctamente.",
                    "Operacion exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception ex) {
            lblEstadoGestion.setText("Ocurrio un error durante el proceso.");
            lblEstadoGestion.setForeground(ERROR);
            mostrarError(obtenerMensajeError(ex));
        }
    }

    private Empleado construirEmpleado() {
        String nombre = txtNombre.getText().trim();
        String cedula = txtCedula.getText().trim();
        String salarioTexto = txtSalario.getText().trim();
        String correo = correoHelper.limpiar(txtCorreoDestino.getText());

        if (nombre.isEmpty() || cedula.isEmpty() || salarioTexto.isEmpty() || correo.isEmpty()) {
            mostrarError("Completa nombre, cedula, salario y correo del empleado.");
            return null;
        }

        if (!cedula.matches("[0-9\\-]{9,20}")) {
            mostrarError("La cedula debe contener solo digitos o guiones.");
            return null;
        }

        if (!correoHelper.esValido(correo)) {
            mostrarError("El correo del empleado no es valido.");
            return null;
        }

        double salario;
        try {
            salario = Double.parseDouble(normalizarMonto(salarioTexto));
        } catch (NumberFormatException ex) {
            mostrarError("El salario base debe ser numerico.");
            return null;
        }

        if (salario <= 0) {
            mostrarError("El salario base debe ser mayor que cero.");
            return null;
        }

        return new Empleado(nombre, cedula, salario, correo);
    }

    private void registrarNomina(Empleado empleado, Nomina nomina, Path rutaPdf, String estado) throws IOException {
        String fecha = FORMATO_FECHA.format(LocalDateTime.now());
        String ruta = rutaPdf == null ? "Pendiente" : rutaPdf.toString();
        ArchivoTexto archivo = new ArchivoTexto(rutaRegistro);
        archivo.guardar(String.join(" | ",
                fecha,
                estado,
                empleado.getNombre(),
                empleado.getCedula(),
                formatearPlano(nomina.getSalarioNeto()),
                formatearPlano(nomina.getCargaPatronalTotal()),
                ruta
        ));

        modeloTabla.addRow(new Object[]{
            fecha,
            estado,
            empleado.getNombre(),
            empleado.getCedula(),
            moneda.format(nomina.getSalarioNeto()),
            moneda.format(nomina.getCargaPatronalTotal()),
            ruta
        });
    }

    private void cargarRegistrosExistentes() {
        try {
            ArchivoTexto archivo = new ArchivoTexto(rutaRegistro);
            List<String> lineas = archivo.leerLineas();

            for (String linea : lineas) {
                if (linea == null || linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split("\\s\\|\\s");
                if (partes.length >= 7) {
                    modeloTabla.addRow(new Object[]{
                        partes[0],
                        partes[1],
                        partes[2],
                        partes[3],
                        moneda.format(parsearSeguro(partes[4])),
                        moneda.format(parsearSeguro(partes[5])),
                        partes[6]
                    });
                } else if (partes.length >= 6) {
                    modeloTabla.addRow(new Object[]{
                        "Anterior",
                        "Historico",
                        partes[0],
                        partes[1],
                        moneda.format(parsearSeguro(partes[4])),
                        moneda.format(0),
                        partes[5]
                    });
                }
            }
        } catch (IOException ex) {
            if (lblEstadoGestion != null) {
                lblEstadoGestion.setText("No se pudo leer el historial guardado.");
                lblEstadoGestion.setForeground(ERROR);
            }
        }
    }

    private double parsearSeguro(String valor) {
        try {
            return Double.parseDouble(valor.replace(",", "").trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private void actualizarMetricas(Nomina nomina) {
        if (nomina == null) {
            lblSalarioBruto.setText(moneda.format(0));
            lblDeduccionObrera.setText(moneda.format(0));
            lblRenta.setText(moneda.format(0));
            lblSalarioNeto.setText(moneda.format(0));
            lblCargaPatronal.setText(moneda.format(0));
            lblCostoEmpresa.setText(moneda.format(0));
            lblObservacionLegal.setText("Sin observaciones de base minima contributiva.");
            return;
        }

        lblSalarioBruto.setText(moneda.format(nomina.getSalarioBruto()));
        lblDeduccionObrera.setText(moneda.format(nomina.getTotalObreroSinRenta()));
        lblRenta.setText(moneda.format(nomina.getImpuestoRenta()));
        lblSalarioNeto.setText(moneda.format(nomina.getSalarioNeto()));
        lblCargaPatronal.setText(moneda.format(nomina.getCargaPatronalTotal()));
        lblCostoEmpresa.setText(moneda.format(nomina.getCostoTotalEmpresa()));

        if (nomina.isAplicaBaseMinimaSem() || nomina.isAplicaBaseMinimaIvm()) {
            lblObservacionLegal.setText(
                    "Advertencia: el salario activa base minima contributiva 2026 para SEM/IVM y la cuota efectiva puede subir."
            );
            lblObservacionLegal.setForeground(SECONDARY);
        } else {
            lblObservacionLegal.setText("Aplicacion normal de cargas sociales sobre salario reportado.");
            lblObservacionLegal.setForeground(MUTED);
        }
    }

    private void actualizarResumenOperacion(Empleado empleado, Nomina nomina, Path rutaPdf, String estado) {
        StringBuilder sb = new StringBuilder();
        sb.append("Estado: ").append(estado).append('\n');
        sb.append("Empleado: ").append(empleado.getNombre()).append('\n');
        sb.append("Cedula: ").append(empleado.getCedula()).append('\n');
        sb.append("Correo: ").append(empleado.getCorreo()).append('\n');
        sb.append('\n');
        sb.append("Salario bruto: ").append(moneda.format(nomina.getSalarioBruto())).append('\n');
        sb.append("SEM obrero: ").append(moneda.format(nomina.getSeguroEnfermedadMaternidadObrero())).append('\n');
        sb.append("IVM obrero: ").append(moneda.format(nomina.getInvalidezVejezMuerteObrero())).append('\n');
        sb.append("Banco Popular obrero: ").append(moneda.format(nomina.getBancoPopularObrero())).append('\n');
        sb.append("Renta salarial: ").append(moneda.format(nomina.getImpuestoRenta())).append('\n');
        sb.append("Deducciones totales: ").append(moneda.format(nomina.getDeducciones())).append('\n');
        sb.append("Salario neto: ").append(moneda.format(nomina.getSalarioNeto())).append('\n');
        sb.append('\n');
        sb.append("Carga patronal total: ").append(moneda.format(nomina.getCargaPatronalTotal())).append('\n');
        sb.append("Costo total empresa: ").append(moneda.format(nomina.getCostoTotalEmpresa())).append('\n');
        sb.append('\n');
        sb.append("Observacion legal: ");
        sb.append((nomina.isAplicaBaseMinimaSem() || nomina.isAplicaBaseMinimaIvm())
                ? "se aplico referencia de base minima contributiva 2026 para SEM/IVM."
                : "no fue necesario ajustar por base minima contributiva.");
        sb.append('\n');
        sb.append("PDF: ").append(rutaPdf == null ? "No generado en esta operacion." : rutaPdf.toString());
        txtResumen.setText(sb.toString());
    }

    private void actualizarResumenCierre() {
        String empleado = ultimoEmpleado == null ? "Sin empleado procesado aun." : ultimoEmpleado.getNombre() + " - " + ultimoEmpleado.getCedula();
        String neto = ultimaNomina == null ? moneda.format(0) : moneda.format(ultimaNomina.getSalarioNeto());
        String costoEmpresa = ultimaNomina == null ? moneda.format(0) : moneda.format(ultimaNomina.getCostoTotalEmpresa());
        String pdf = ultimoPdf == null ? "Sin comprobante generado en la ultima operacion." : ultimoPdf.toString();

        txtCierre.setText(
                "Ultimo empleado procesado: " + empleado + "\n"
                + "Salario neto mostrado: " + neto + "\n"
                + "Costo total empresa: " + costoEmpresa + "\n"
                + "Comprobante PDF: " + pdf + "\n\n"
                + "Checklist sugerido antes de salir:\n"
                + "1. Revisar que la planilla y las cargas coincidan con el periodo real.\n"
                + "2. Validar en Oficina Virtual CCSS cualquier ajuste patronal o incapacidad.\n"
                + "3. Confirmar tablas vigentes de renta si el periodo fiscal cambia.\n"
                + "4. Cerrar la interfaz solo cuando el registro del empleado quede correcto."
        );
    }

    private String obtenerMensajeError(Throwable error) {
        Throwable actual = error;
        while (actual != null) {
            if (actual.getMessage() != null && !actual.getMessage().trim().isEmpty()) {
                return actual.getMessage();
            }
            actual = actual.getCause();
        }
        return "Ocurrio un error no identificado.";
    }

    private String formatearPlano(double valor) {
        return String.format(Locale.US, "%.2f", valor);
    }

    private String normalizarMonto(String valor) {
        String limpio = valor.trim().replace(" ", "");

        if (limpio.contains(",") && limpio.contains(".")) {
            limpio = limpio.replace(".", "").replace(",", ".");
        } else if (limpio.contains(",")) {
            limpio = limpio.replace(",", ".");
        }

        return limpio;
    }

    private void limpiarGestion() {
        txtRemitente.setText("");
        txtClaveCorreo.setText("");
        txtNombre.setText("");
        txtCedula.setText("");
        txtSalario.setText("");
        txtCorreoDestino.setText("");
        chkMostrarClaveCorreo.setSelected(false);
        txtClaveCorreo.setEchoChar('\u2022');
        lblEstadoGestion.setText("Formulario limpio.");
        lblEstadoGestion.setForeground(MUTED);
        txtResumen.setText("Aqui veras el desglose de la ultima nomina calculada.");
        actualizarMetricas(null);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Validacion", JOptionPane.ERROR_MESSAGE);
    }

    private void salir() {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "Deseas salir de la interfaz grafica?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    private void volverAlMenu() {
        new FrmMenuPrincipal().setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FrmSistemaPlanilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new FrmSistemaPlanilla().setVisible(true));
    }
}
