package proyecto2.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import proyecto2.AccesoDatos.ArchivoTexto;
import proyecto2.Entidades.Empleado;
import proyecto2.Entidades.Nomina;
import proyecto2.LogicaNegocio.CalculoNomina;
import proyecto2.LogicaNegocio.Correo;
import proyecto2.LogicaNegocio.GeneradorPDFPatrono;
import proyecto2.LogicaNegocio.MailService;
import proyecto2.LogicaNegocio.RutasProyecto;

public class FrmPatrono extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Color BACKGROUND = new Color(243, 246, 251);
    private static final Color PANEL = new Color(255, 255, 255);
    private static final Color PRIMARY = new Color(26, 115, 232);
    private static final Color PRIMARY_DARK = new Color(14, 84, 170);
    private static final Color TEXT = new Color(33, 37, 41);
    private static final Color MUTED = new Color(108, 117, 125);
    private static final Color ERROR = new Color(198, 40, 40);
    private static final Locale LOCALE_CR = new Locale("es", "CR");

    private JTextField txtNombre;
    private JTextField txtCedulaJuridica;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JTextField txtRemitente;
    private JPasswordField txtClaveCorreo;
    private JTextField txtEmpleadoNombre;
    private JTextField txtEmpleadoCedula;
    private JTextField txtEmpleadoSalario;
    private JTextField txtEmpleadoCorreo;
    private JTextArea txtResumen;
    private JLabel lblEstado;
    private DefaultTableModel modeloEmpleados;
    private JTable tablaEmpleados;

    private final List<Empleado> empleados = new ArrayList<>();
    private final Correo correoHelper = new Correo();
    private final CalculoNomina calculoNomina = new CalculoNomina();
    private final GeneradorPDFPatrono generadorPDFPatrono = new GeneradorPDFPatrono();
    private final MailService mailService = new MailService();
    private final NumberFormat moneda = NumberFormat.getCurrencyInstance(LOCALE_CR);

    public FrmPatrono() {
        initComponents();
        configurarVentana();
        cargarRegistroGuardado();
    }

    private void configurarVentana() {
        setTitle("Sistema de Nomina - Patrono");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(980, 620));
        setSize(1160, 740);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void initComponents() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(16, 16, 16, 16));

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 226, 234)),
                new EmptyBorder(0, 0, 0, 0)
        ));

        card.add(buildBrandPanel(), BorderLayout.WEST);
        JScrollPane formScroll = new JScrollPane(buildFormPanel());
        formScroll.setBorder(null);
        formScroll.getVerticalScrollBar().setUnitIncrement(16);
        card.add(formScroll, BorderLayout.CENTER);

        content.add(card, BorderLayout.CENTER);
        setContentPane(content);
    }

    private JPanel buildBrandPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 0));
        panel.setBackground(PRIMARY_DARK);
        panel.setBorder(new EmptyBorder(28, 26, 28, 26));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel badge = new JLabel("PATRONO");
        badge.setOpaque(true);
        badge.setBackground(new Color(255, 255, 255, 40));
        badge.setForeground(Color.WHITE);
        badge.setFont(new Font("SansSerif", Font.BOLD, 12));
        badge.setBorder(new EmptyBorder(8, 12, 8, 12));
        badge.setAlignmentX(LEFT_ALIGNMENT);

        JLabel title = new JLabel("<html>Registro de<br>empleados</html>");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setAlignmentX(LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("<html>Asigna empleados al patrono, guarda el registro y envia un PDF resumen al correo del patrono.</html>");
        subtitle.setForeground(new Color(224, 231, 255));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitle.setAlignmentX(LEFT_ALIGNMENT);

        panel.add(badge);
        panel.add(Box.createVerticalStrut(24));
        panel.add(title);
        panel.add(Box.createVerticalStrut(14));
        panel.add(subtitle);
        panel.add(Box.createVerticalGlue());
        panel.add(createItem("Datos del patrono"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createItem("Empleados asignados"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createItem("PDF resumen por correo"));

        return panel;
    }

    private JLabel createItem(String text) {
        JLabel label = new JLabel("* " + text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setAlignmentX(LEFT_ALIGNMENT);
        return label;
    }

    private JPanel buildFormPanel() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(PANEL);
        container.setBorder(new EmptyBorder(24, 34, 24, 34));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        container.add(buildHeaderPanel(), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(14, 0, 8, 0);
        container.add(buildPatronoPanel(), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(8, 0, 8, 0);
        container.add(buildEmpleadoPanel(), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        container.add(buildRegistroPanel(), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(8, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0.0;
        container.add(buildAccionesPanel(), gbc);

        gbc.gridy++;
        lblEstado = new JLabel("Listo para registrar patrono y empleados.");
        lblEstado.setForeground(MUTED);
        lblEstado.setFont(new Font("SansSerif", Font.PLAIN, 13));
        container.add(lblEstado, gbc);

        return container;
    }

    private JPanel buildHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setOpaque(false);

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Registro de patrono");
        welcome.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcome.setForeground(TEXT);
        welcome.setAlignmentX(LEFT_ALIGNMENT);

        JLabel description = new JLabel("Agrega empleados, guarda el registro y envia el PDF al patrono.");
        description.setFont(new Font("SansSerif", Font.PLAIN, 14));
        description.setForeground(MUTED);
        description.setAlignmentX(LEFT_ALIGNMENT);

        textos.add(welcome);
        textos.add(Box.createVerticalStrut(4));
        textos.add(description);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        acciones.setOpaque(false);
        JButton btnVolver = createSecondaryButton("Volver al menu");
        btnVolver.addActionListener(evt -> volverAlMenu());
        acciones.add(btnVolver);

        header.add(textos, BorderLayout.CENTER);
        header.add(acciones, BorderLayout.EAST);
        return header;
    }

    private JPanel buildPatronoPanel() {
        JPanel panel = createSectionPanel("Datos del patrono y envio");
        JPanel grid = new JPanel(new GridLayout(0, 2, 12, 8));
        grid.setOpaque(false);

        txtNombre = createTextField();
        txtCedulaJuridica = createTextField();
        txtTelefono = createTextField();
        txtCorreo = createTextField();
        txtRemitente = createTextField();
        txtClaveCorreo = createPasswordField();

        addField(grid, "Nombre del patrono", txtNombre);
        addField(grid, "Cedula juridica", txtCedulaJuridica);
        addField(grid, "Telefono", txtTelefono);
        addField(grid, "Correo del patrono", txtCorreo);
        addField(grid, "Gmail remitente", txtRemitente);
        addField(grid, "Contrasena de aplicacion", txtClaveCorreo);

        panel.add(grid, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildEmpleadoPanel() {
        JPanel panel = createSectionPanel("Asignar empleado al patrono");
        JPanel grid = new JPanel(new GridLayout(0, 2, 12, 8));
        grid.setOpaque(false);

        txtEmpleadoNombre = createTextField();
        txtEmpleadoCedula = createTextField();
        txtEmpleadoSalario = createTextField();
        txtEmpleadoCorreo = createTextField();

        addField(grid, "Nombre del empleado", txtEmpleadoNombre);
        addField(grid, "Cedula", txtEmpleadoCedula);
        addField(grid, "Salario base", txtEmpleadoSalario);
        addField(grid, "Correo del empleado", txtEmpleadoCorreo);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botones.setOpaque(false);
        JButton btnAgregar = createPrimaryButton("Agregar empleado");
        btnAgregar.addActionListener(evt -> agregarEmpleado());
        JButton btnQuitar = createSecondaryButton("Quitar seleccionado");
        btnQuitar.addActionListener(evt -> quitarEmpleadoSeleccionado());
        botones.add(btnQuitar);
        botones.add(btnAgregar);

        panel.add(grid, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildRegistroPanel() {
        JPanel panel = createSectionPanel("Empleados asignados");
        modeloEmpleados = new DefaultTableModel(
                new Object[]{"Nombre", "Cedula", "Salario bruto", "Correo", "Carga patronal"},
                0
        ) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEmpleados = new JTable(modeloEmpleados);
        tablaEmpleados.setRowHeight(24);
        tablaEmpleados.getTableHeader().setReorderingAllowed(false);
        tablaEmpleados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(tablaEmpleados), BorderLayout.CENTER);

        txtResumen = new JTextArea(5, 20);
        txtResumen.setEditable(false);
        txtResumen.setLineWrap(true);
        txtResumen.setWrapStyleWord(true);
        txtResumen.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtResumen.setBackground(new Color(252, 253, 255));
        txtResumen.setBorder(new EmptyBorder(10, 10, 10, 10));
        txtResumen.setText("Todavia no hay empleados asignados.");
        panel.add(new JScrollPane(txtResumen), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildAccionesPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panel.setOpaque(false);

        JButton btnGuardar = createSecondaryButton("Guardar registro");
        btnGuardar.addActionListener(evt -> guardarRegistro());

        JButton btnLimpiar = createSecondaryButton("Limpiar todo");
        btnLimpiar.addActionListener(evt -> limpiarFormulario());

        JButton btnEnviar = createPrimaryButton("Enviar PDF al patrono");
        btnEnviar.addActionListener(evt -> enviarPdfPatrono());

        panel.add(btnLimpiar);
        panel.add(btnGuardar);
        panel.add(btnEnviar);
        return panel;
    }

    private JPanel createSectionPanel(String titulo) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 226, 234)),
                new EmptyBorder(14, 14, 14, 14)
        ));
        JLabel label = new JLabel(titulo);
        label.setForeground(TEXT);
        label.setFont(new Font("SansSerif", Font.BOLD, 15));
        panel.add(label, BorderLayout.NORTH);
        return panel;
    }

    private void addField(JPanel panel, String label, JTextField field) {
        JPanel wrapper = new JPanel(new BorderLayout(0, 4));
        wrapper.setOpaque(false);
        wrapper.add(createFieldLabel(label), BorderLayout.NORTH);
        wrapper.add(field, BorderLayout.CENTER);
        panel.add(wrapper);
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(0, 34));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                new EmptyBorder(7, 12, 7, 12)
        ));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBackground(new Color(252, 253, 255));
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(0, 34));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                new EmptyBorder(7, 12, 7, 12)
        ));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBackground(new Color(252, 253, 255));
        field.setEchoChar('\u2022');
        return field;
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(9, 16, 9, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(237, 242, 247));
        button.setForeground(TEXT);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(9, 16, 9, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void agregarEmpleado() {
        Empleado empleado = construirEmpleadoDesdeFormulario();
        if (empleado == null) {
            return;
        }

        empleados.add(empleado);
        agregarEmpleadoATabla(empleado);
        limpiarCamposEmpleado();
        actualizarResumen();
        guardarRegistroSilencioso();
        actualizarEstado("Empleado asignado al patrono.", false);
    }

    private Empleado construirEmpleadoDesdeFormulario() {
        String nombre = txtEmpleadoNombre.getText().trim();
        String cedula = txtEmpleadoCedula.getText().trim();
        String salarioTexto = txtEmpleadoSalario.getText().trim().replace(',', '.');
        String correo = correoHelper.limpiar(txtEmpleadoCorreo.getText());

        if (nombre.isEmpty() || cedula.isEmpty() || salarioTexto.isEmpty() || correo.isEmpty()) {
            mostrarError("Completa los datos del empleado antes de agregarlo.");
            return null;
        }

        if (!correoHelper.esValido(correo)) {
            mostrarError("El correo del empleado no es valido.");
            return null;
        }

        double salario;
        try {
            salario = Double.parseDouble(salarioTexto);
        } catch (NumberFormatException ex) {
            mostrarError("El salario del empleado debe ser numerico.");
            return null;
        }

        if (salario <= 0) {
            mostrarError("El salario del empleado debe ser mayor que cero.");
            return null;
        }

        return new Empleado(nombre, cedula, salario, correo);
    }

    private void quitarEmpleadoSeleccionado() {
        int fila = obtenerFilaSeleccionada();
        if (fila < 0) {
            mostrarError("Selecciona un empleado de la tabla para quitarlo.");
            return;
        }

        empleados.remove(fila);
        modeloEmpleados.removeRow(fila);
        actualizarResumen();
        guardarRegistroSilencioso();
        actualizarEstado("Empleado quitado del registro.", false);
    }

    private int obtenerFilaSeleccionada() {
        if (tablaEmpleados == null) {
            return -1;
        }

        return tablaEmpleados.getSelectedRow();
    }

    private void guardarRegistro() {
        if (!validarPatrono(false)) {
            return;
        }

        try {
            guardarPatrono();
            guardarEmpleadosPatrono();
            actualizarResumen();
            actualizarEstado("Registro del patrono guardado correctamente.", false);
            JOptionPane.showMessageDialog(this, "Registro guardado correctamente.", "Guardado", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            mostrarError("No se pudo guardar el registro: " + ex.getMessage());
        }
    }

    private void guardarRegistroSilencioso() {
        if (!validarPatrono(true)) {
            return;
        }

        try {
            guardarPatrono();
            guardarEmpleadosPatrono();
        } catch (IOException ex) {
            actualizarEstado("No se pudo guardar automaticamente el registro.", true);
        }
    }

    private void enviarPdfPatrono() {
        if (!validarPatrono(false)) {
            return;
        }

        String remitente = correoHelper.limpiar(txtRemitente.getText());
        String clave = new String(txtClaveCorreo.getPassword()).trim();
        String correoPatrono = correoHelper.limpiar(txtCorreo.getText());

        if (!correoHelper.esValido(remitente) || !remitente.endsWith("@gmail.com")) {
            mostrarError("Para enviar el PDF debes indicar un Gmail remitente valido.");
            return;
        }

        if (clave.isEmpty()) {
            mostrarError("Debes ingresar la contrasena de aplicacion del Gmail remitente.");
            return;
        }

        if (empleados.isEmpty()) {
            mostrarError("Agrega al menos un empleado antes de enviar el PDF.");
            return;
        }

        try {
            guardarRegistroSilencioso();
            Path rutaPdf = generadorPDFPatrono.generarPdf(
                    txtNombre.getText().trim(),
                    txtCedulaJuridica.getText().trim(),
                    txtTelefono.getText().trim(),
                    correoPatrono,
                    empleados
            );

            mailService.enviarCorreoConAdjunto(
                    remitente,
                    clave,
                    correoPatrono,
                    "Resumen de empleados asignados - " + txtNombre.getText().trim(),
                    "Hola,\n\nAdjunto encontraras el PDF con el registro de empleados asignados al patrono "
                    + txtNombre.getText().trim() + ".\n\nSaludos.",
                    rutaPdf
            );

            actualizarEstado("PDF generado y enviado al patrono.", false);
            JOptionPane.showMessageDialog(this, "PDF enviado al patrono.\nArchivo: " + rutaPdf, "Envio exitoso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            mostrarError("No se pudo enviar el PDF al patrono: " + obtenerMensajeError(ex));
        }
    }

    private boolean validarPatrono(boolean silencioso) {
        String nombre = txtNombre.getText().trim();
        String cedula = txtCedulaJuridica.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = correoHelper.limpiar(txtCorreo.getText());

        if (nombre.isEmpty() || cedula.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            if (!silencioso) {
                mostrarError("Completa los datos del patrono.");
            }
            return false;
        }

        if (!correoHelper.esValido(correo)) {
            if (!silencioso) {
                mostrarError("El correo del patrono no es valido.");
            }
            return false;
        }

        return true;
    }

    private void guardarPatrono() throws IOException {
        String contenido = String.format(
                "Nombre del patrono: %s%nCedula juridica: %s%nTelefono: %s%nCorreo: %s%n",
                txtNombre.getText().trim(),
                txtCedulaJuridica.getText().trim(),
                txtTelefono.getText().trim(),
                correoHelper.limpiar(txtCorreo.getText())
        );

        ArchivoTexto archivo = new ArchivoTexto(RutasProyecto.resolver("data", "patrono_registrado.txt"));
        archivo.sobrescribir(contenido);
    }

    private void guardarEmpleadosPatrono() throws IOException {
        StringBuilder contenido = new StringBuilder();
        String nombrePatrono = txtNombre.getText().trim();
        String cedulaPatrono = txtCedulaJuridica.getText().trim();

        for (Empleado empleado : empleados) {
            contenido.append(cedulaPatrono).append(" | ")
                    .append(nombrePatrono).append(" | ")
                    .append(empleado.getCedula()).append(" | ")
                    .append(empleado.getNombre()).append(" | ")
                    .append(String.format(Locale.US, "%.2f", empleado.getSalarioBase())).append(" | ")
                    .append(empleado.getCorreo())
                    .append(System.lineSeparator());
        }

        ArchivoTexto archivo = new ArchivoTexto(RutasProyecto.resolver("data", "empleados_patrono.txt"));
        archivo.sobrescribir(contenido.toString());
    }

    private void cargarRegistroGuardado() {
        cargarPatronoGuardado();
        cargarEmpleadosGuardados();
        actualizarResumen();
    }

    private void cargarPatronoGuardado() {
        try {
            ArchivoTexto archivo = new ArchivoTexto(RutasProyecto.resolver("data", "patrono_registrado.txt"));
            for (String linea : archivo.leerLineas()) {
                if (linea.startsWith("Nombre del patrono:")) {
                    txtNombre.setText(linea.replace("Nombre del patrono:", "").trim());
                } else if (linea.startsWith("Cedula juridica:")) {
                    txtCedulaJuridica.setText(linea.replace("Cedula juridica:", "").trim());
                } else if (linea.startsWith("Telefono:")) {
                    txtTelefono.setText(linea.replace("Telefono:", "").trim());
                } else if (linea.startsWith("Correo:")) {
                    txtCorreo.setText(linea.replace("Correo:", "").trim());
                }
            }
        } catch (IOException ex) {
            actualizarEstado("No se pudo cargar el patrono guardado.", true);
        }
    }

    private void cargarEmpleadosGuardados() {
        try {
            ArchivoTexto archivo = new ArchivoTexto(RutasProyecto.resolver("data", "empleados_patrono.txt"));
            for (String linea : archivo.leerLineas()) {
                String[] partes = linea.split("\\s\\|\\s");
                if (partes.length < 6) {
                    continue;
                }

                double salario = Double.parseDouble(partes[4]);
                Empleado empleado = new Empleado(partes[3], partes[2], salario, partes[5]);
                empleados.add(empleado);
                agregarEmpleadoATabla(empleado);
            }
        } catch (IOException | NumberFormatException ex) {
            actualizarEstado("No se pudo cargar el registro de empleados guardado.", true);
        }
    }

    private void agregarEmpleadoATabla(Empleado empleado) {
        Nomina nomina = calculoNomina.calcular(empleado);
        modeloEmpleados.addRow(new Object[]{
            empleado.getNombre(),
            empleado.getCedula(),
            moneda.format(empleado.getSalarioBase()),
            empleado.getCorreo(),
            moneda.format(nomina.getCargaPatronalTotal())
        });
    }

    private void actualizarResumen() {
        if (empleados.isEmpty()) {
            txtResumen.setText("Todavia no hay empleados asignados.");
            return;
        }

        double totalBruto = 0.0;
        double totalNeto = 0.0;
        double totalPatronal = 0.0;
        double totalEmpresa = 0.0;

        for (Empleado empleado : empleados) {
            Nomina nomina = calculoNomina.calcular(empleado);
            totalBruto += nomina.getSalarioBruto();
            totalNeto += nomina.getSalarioNeto();
            totalPatronal += nomina.getCargaPatronalTotal();
            totalEmpresa += nomina.getCostoTotalEmpresa();
        }

        txtResumen.setText(
                "Empleados asignados: " + empleados.size() + "\n"
                + "Total salarios brutos: " + moneda.format(totalBruto) + "\n"
                + "Total salarios netos: " + moneda.format(totalNeto) + "\n"
                + "Total carga patronal: " + moneda.format(totalPatronal) + "\n"
                + "Costo total para el patrono: " + moneda.format(totalEmpresa)
        );
    }

    private void limpiarCamposEmpleado() {
        txtEmpleadoNombre.setText("");
        txtEmpleadoCedula.setText("");
        txtEmpleadoSalario.setText("");
        txtEmpleadoCorreo.setText("");
        txtEmpleadoNombre.requestFocusInWindow();
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtCedulaJuridica.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtRemitente.setText("");
        txtClaveCorreo.setText("");
        limpiarCamposEmpleado();
        empleados.clear();
        modeloEmpleados.setRowCount(0);
        actualizarResumen();
        actualizarEstado("Formulario limpiado.", false);
    }

    private void mostrarError(String mensaje) {
        actualizarEstado(mensaje, true);
        JOptionPane.showMessageDialog(this, mensaje, "Validacion", JOptionPane.WARNING_MESSAGE);
    }

    private String obtenerMensajeError(Throwable error) {
        Throwable actual = error;
        while (actual != null) {
            if (actual.getMessage() != null && !actual.getMessage().trim().isEmpty()) {
                return actual.getMessage();
            }
            actual = actual.getCause();
        }
        return "Error desconocido.";
    }

    private void actualizarEstado(String mensaje, boolean error) {
        if (lblEstado != null) {
            lblEstado.setText(mensaje);
            lblEstado.setForeground(error ? ERROR : MUTED);
        }
    }

    private void volverAlMenu() {
        new FrmMenuPrincipal().setVisible(true);
        dispose();
    }
}
