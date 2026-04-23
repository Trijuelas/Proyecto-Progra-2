package proyecto2.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import proyecto2.AccesoDatos.ArchivoTexto;
import proyecto2.Entidades.Empleado;
import proyecto2.Entidades.Nomina;
import proyecto2.LogicaNegocio.CalculoNomina;
import proyecto2.LogicaNegocio.Correo;
import proyecto2.LogicaNegocio.GeneradorPDF;
import proyecto2.LogicaNegocio.MailService;
import proyecto2.LogicaNegocio.RutasProyecto;

public class FrmNomina extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color BACKGROUND = new Color(243, 246, 251);
    private static final Color PANEL = new Color(255, 255, 255);
    private static final Color PRIMARY = new Color(26, 115, 232);
    private static final Color PRIMARY_DARK = new Color(14, 84, 170);
    private static final Color PRIMARY_SOFT = new Color(231, 240, 255);
    private static final Color TEXT = new Color(33, 37, 41);
    private static final Color MUTED = new Color(108, 117, 125);
    private static final Color BORDER = new Color(220, 226, 234);
    private static final Color SUCCESS = new Color(46, 125, 50);
    private static final Color ERROR = new Color(198, 40, 40);
    private static final Color WARNING = new Color(239, 108, 0);

    private JTextField txtRemitente;
    private JPasswordField txtClaveCorreo;
    private JTextField txtNombre;
    private JTextField txtCedula;
    private JTextField txtSalario;
    private JTextField txtCorreoDestino;
    private JTextArea txtResumen;
    private JLabel lblEstado;
    private JLabel lblAyudaRemitente;
    private JLabel lblAyudaClaveCorreo;
    private JLabel lblAyudaNombre;
    private JLabel lblAyudaCedula;
    private JLabel lblAyudaSalario;
    private JLabel lblAyudaCorreoDestino;
    private JButton btnEnviar;
    private JButton btnLimpiar;
    private JCheckBox chkMostrarClaveCorreo;

    private final Correo correoHelper = new Correo();
    private final CalculoNomina calculoNomina = new CalculoNomina();
    private final GeneradorPDF generadorPDF = new GeneradorPDF();
    private final MailService mailService = new MailService();

    public FrmNomina() {
        initComponents();
        configurarVentana();
        configurarValidacionesVisuales();
    }

    private void configurarVentana() {
        setTitle("Sistema de Nomina - Windows");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 620));
        setSize(1160, 720);
        setLocationRelativeTo(null);
        setResizable(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                txtRemitente.requestFocusInWindow();
            }
        });
    }

    private void initComponents() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(24, 24, 24, 24));

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(0, 0, 0, 0)
        ));

        card.add(buildBrandPanel(), BorderLayout.WEST);
        card.add(buildFormPanel(), BorderLayout.CENTER);

        content.add(card, BorderLayout.CENTER);
        setContentPane(content);
    }

    private JPanel buildBrandPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(360, 0));
        panel.setBackground(PRIMARY_DARK);
        panel.setBorder(new EmptyBorder(40, 34, 40, 34));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel badge = new JLabel("PROYECTO 2");
        badge.setOpaque(true);
        badge.setBackground(new Color(255, 255, 255, 45));
        badge.setForeground(Color.WHITE);
        badge.setFont(new Font("SansSerif", Font.BOLD, 12));
        badge.setBorder(new EmptyBorder(8, 12, 8, 12));
        badge.setAlignmentX(LEFT_ALIGNMENT);

        JLabel title = new JLabel("<html>Genera PDF y<br>envia comprobantes<br>por correo</html>");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setAlignmentX(LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("<html>Genera comprobantes de nomina y envialos por correo desde una sola pantalla.</html>");
        subtitle.setForeground(new Color(224, 231, 255));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitle.setAlignmentX(LEFT_ALIGNMENT);

        panel.add(badge);
        panel.add(Box.createVerticalStrut(32));
        panel.add(title);
        panel.add(Box.createVerticalStrut(18));
        panel.add(subtitle);
        panel.add(Box.createVerticalGlue());
        panel.add(createFeature("Nomina", "Calcula deducciones y salario neto del empleado."));
        panel.add(Box.createVerticalStrut(14));
        panel.add(createFeature("PDF", "Genera comprobantes en la carpeta documentos_pdf."));
        panel.add(Box.createVerticalStrut(14));
        panel.add(createFeature("Correo", "Adjunta el PDF y lo envia al destinatario."));
        panel.add(Box.createVerticalStrut(26));
        panel.add(createProgressPanel());

        return panel;
    }

    private JPanel createFeature(String title, String detail) {
        JPanel wrapper = new JPanel(new BorderLayout(10, 0));
        wrapper.setOpaque(false);
        wrapper.setAlignmentX(LEFT_ALIGNMENT);

        JLabel dot = new JLabel("*");
        dot.setForeground(new Color(144, 202, 249));
        dot.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel text = new JLabel("<html><b>" + title + "</b><br>" + detail + "</html>");
        text.setForeground(Color.WHITE);
        text.setFont(new Font("SansSerif", Font.PLAIN, 14));

        wrapper.add(dot, BorderLayout.WEST);
        wrapper.add(text, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createProgressPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setAlignmentX(LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(createMilestoneCard("1. Captura", "Completa remitente, empleado y salario."), gbc);

        gbc.gridy++;
        panel.add(createMilestoneCard("2. Generacion", "Se crea el PDF automaticamente."), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(createMilestoneCard("3. Envio", "El comprobante se adjunta y se envia por correo."), gbc);
        return panel;
    }

    private JPanel createMilestoneCard(String title, String detail) {
        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setOpaque(true);
        card.setBackground(new Color(255, 255, 255, 30));
        card.setBorder(new EmptyBorder(14, 16, 14, 16));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel lblDetail = new JLabel("<html>" + detail + "</html>");
        lblDetail.setForeground(new Color(224, 231, 255));
        lblDetail.setFont(new Font("SansSerif", Font.PLAIN, 13));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblDetail, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildFormPanel() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(PANEL);
        container.setBorder(new EmptyBorder(34, 42, 34, 42));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        JLabel welcome = new JLabel("Datos de envio y nomina");
        welcome.setFont(new Font("SansSerif", Font.BOLD, 28));
        welcome.setForeground(TEXT);
        container.add(welcome, gbc);

        gbc.gridy++;
        JLabel description = new JLabel("Completa el remitente Gmail, los datos del empleado y envia el comprobante.");
        description.setFont(new Font("SansSerif", Font.PLAIN, 15));
        description.setForeground(MUTED);
        container.add(description, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(22, 0, 18, 0);
        container.add(buildSummaryBanner(), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(24, 0, 6, 0);
        container.add(createFieldLabel("Correo remitente de Gmail"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtRemitente = createTextField();
        container.add(txtRemitente, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        lblAyudaRemitente = createHelperLabel("Debe ser un Gmail valido para el envio.");
        container.add(lblAyudaRemitente, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 6, 0);
        container.add(createFieldLabel("Contrasena de aplicacion"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtClaveCorreo = createPasswordField();
        container.add(txtClaveCorreo, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        lblAyudaClaveCorreo = createHelperLabel("Usa la contrasena de aplicacion de Gmail.");
        container.add(lblAyudaClaveCorreo, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(2, 0, 10, 0);
        chkMostrarClaveCorreo = new JCheckBox("Mostrar contrasena de aplicacion");
        chkMostrarClaveCorreo.setOpaque(false);
        chkMostrarClaveCorreo.setForeground(MUTED);
        chkMostrarClaveCorreo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        chkMostrarClaveCorreo.addActionListener(evt -> txtClaveCorreo.setEchoChar(chkMostrarClaveCorreo.isSelected() ? (char) 0 : '\u2022'));
        container.add(chkMostrarClaveCorreo, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 6, 0);
        container.add(createFieldLabel("Nombre del empleado"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtNombre = createTextField();
        container.add(txtNombre, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        lblAyudaNombre = createHelperLabel("Ingresa el nombre completo del empleado.");
        container.add(lblAyudaNombre, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 6, 0);
        container.add(createFieldLabel("Cedula"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtCedula = createTextField();
        container.add(txtCedula, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        lblAyudaCedula = createHelperLabel("Solo digitos y un formato reconocible.");
        container.add(lblAyudaCedula, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 6, 0);
        container.add(createFieldLabel("Salario base"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtSalario = createTextField();
        container.add(txtSalario, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        lblAyudaSalario = createHelperLabel("Ingresa un monto numerico mayor que cero.");
        container.add(lblAyudaSalario, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 6, 0);
        container.add(createFieldLabel("Correo del empleado"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtCorreoDestino = createTextField();
        container.add(txtCorreoDestino, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        lblAyudaCorreoDestino = createHelperLabel("Debe ser un correo valido para recibir el PDF.");
        container.add(lblAyudaCorreoDestino, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(12, 0, 10, 0);
        btnEnviar = createPrimaryButton("Generar PDF y enviar");
        btnEnviar.addActionListener(evt -> procesarNomina());
        container.add(btnEnviar, gbc);
        getRootPane().setDefaultButton(btnEnviar);

        gbc.gridy++;
        btnLimpiar = createSecondaryButton("Limpiar formulario");
        btnLimpiar.addActionListener(evt -> limpiarCampos());
        container.add(btnLimpiar, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(18, 0, 8, 0);
        lblEstado = new JLabel("Listo para procesar una nomina.");
        lblEstado.setForeground(MUTED);
        lblEstado.setFont(new Font("SansSerif", Font.PLAIN, 13));
        container.add(lblEstado, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(8, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        txtResumen = new JTextArea(8, 20);
        txtResumen.setEditable(false);
        txtResumen.setLineWrap(true);
        txtResumen.setWrapStyleWord(true);
        txtResumen.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtResumen.setBackground(new Color(252, 253, 255));
        txtResumen.setBorder(new EmptyBorder(14, 14, 14, 14));
        txtResumen.setText("");
        JScrollPane scroll = new JScrollPane(txtResumen);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(0, 0, 0, 0)
        ));
        container.add(scroll, gbc);

        return container;
    }

    private JPanel buildSummaryBanner() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(PRIMARY_SOFT);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(198, 218, 255)),
                new EmptyBorder(16, 18, 16, 18)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Flujo recomendado");
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(title, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 0, 0);
        JLabel detail = new JLabel("<html>Primero valida los datos del remitente, luego captura salario y correo del empleado antes de generar el comprobante.</html>");
        detail.setForeground(MUTED);
        detail.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panel.add(detail, gbc);
        return panel;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        return label;
    }

    private JLabel createHelperLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(MUTED);
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(0, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                new EmptyBorder(10, 12, 10, 12)
        ));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBackground(new Color(252, 253, 255));
        field.setToolTipText("Completa este campo antes de enviar el comprobante.");
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(0, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                new EmptyBorder(10, 12, 10, 12)
        ));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBackground(new Color(252, 253, 255));
        field.setEchoChar('\u2022');
        field.setToolTipText("Usa la contrasena de aplicacion asociada al remitente.");
        return field;
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(12, 16, 12, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(237, 242, 247));
        button.setForeground(TEXT);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(12, 16, 12, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void configurarValidacionesVisuales() {
        instalarValidacionEnVivo(txtRemitente, () -> validarRemitente(false));
        instalarValidacionEnVivo(txtClaveCorreo, () -> validarClaveCorreo(false));
        instalarValidacionEnVivo(txtNombre, () -> validarNombre(false));
        instalarValidacionEnVivo(txtCedula, () -> validarCedula(false));
        instalarValidacionEnVivo(txtSalario, () -> validarSalario(false));
        instalarValidacionEnVivo(txtCorreoDestino, () -> validarCorreoDestino(false));
        txtSalario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                normalizarSalario();
            }
        });
    }

    private void instalarValidacionEnVivo(JTextField field, Runnable accion) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                accion.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                accion.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                accion.run();
            }
        });
    }

    private boolean validarRemitente(boolean mostrarVacioComoError) {
        return validarCorreo(txtRemitente, lblAyudaRemitente, "Debe ser un Gmail valido para el envio.", mostrarVacioComoError);
    }

    private boolean validarCorreoDestino(boolean mostrarVacioComoError) {
        return validarCorreo(txtCorreoDestino, lblAyudaCorreoDestino, "Debe ser un correo valido para recibir el PDF.", mostrarVacioComoError);
    }

    private boolean validarCorreo(JTextField field, JLabel ayuda, String mensajeBase, boolean mostrarVacioComoError) {
        String valor = field.getText().trim();

        if (valor.isEmpty()) {
            if (mostrarVacioComoError) {
                marcarCampo(field, ayuda, "Este campo es obligatorio.", ERROR);
                return false;
            }
            restaurarCampo(field, ayuda, mensajeBase);
            return false;
        }

        if (!correoHelper.esValido(valor)) {
            marcarCampo(field, ayuda, "El formato del correo no es valido.", WARNING);
            return false;
        }

        marcarCampo(field, ayuda, "Correo valido.", SUCCESS);
        return true;
    }

    private boolean validarClaveCorreo(boolean mostrarVacioComoError) {
        char[] clave = txtClaveCorreo.getPassword();
        String valor = new String(clave).trim();

        try {
            if (valor.isEmpty()) {
                if (mostrarVacioComoError) {
                    marcarCampo(txtClaveCorreo, lblAyudaClaveCorreo, "La contrasena es obligatoria.", ERROR);
                    return false;
                }
                restaurarCampo(txtClaveCorreo, lblAyudaClaveCorreo, "Usa la contrasena de aplicacion de Gmail.");
                return false;
            }

            if (valor.length() < 8) {
                marcarCampo(txtClaveCorreo, lblAyudaClaveCorreo, "La contrasena parece demasiado corta.", WARNING);
                return false;
            }

            marcarCampo(txtClaveCorreo, lblAyudaClaveCorreo, "Contrasena capturada.", SUCCESS);
            return true;
        } finally {
            Arrays.fill(clave, '\0');
        }
    }

    private boolean validarNombre(boolean mostrarVacioComoError) {
        String valor = txtNombre.getText().trim();

        if (valor.isEmpty()) {
            if (mostrarVacioComoError) {
                marcarCampo(txtNombre, lblAyudaNombre, "El nombre es obligatorio.", ERROR);
                return false;
            }
            restaurarCampo(txtNombre, lblAyudaNombre, "Ingresa el nombre completo del empleado.");
            return false;
        }

        if (valor.length() < 3) {
            marcarCampo(txtNombre, lblAyudaNombre, "Debe tener al menos 3 caracteres.", WARNING);
            return false;
        }

        marcarCampo(txtNombre, lblAyudaNombre, "Nombre valido.", SUCCESS);
        return true;
    }

    private boolean validarCedula(boolean mostrarVacioComoError) {
        String valor = txtCedula.getText().trim();

        if (valor.isEmpty()) {
            if (mostrarVacioComoError) {
                marcarCampo(txtCedula, lblAyudaCedula, "La cedula es obligatoria.", ERROR);
                return false;
            }
            restaurarCampo(txtCedula, lblAyudaCedula, "Solo digitos y un formato reconocible.");
            return false;
        }

        if (!valor.matches("[0-9-]{6,20}")) {
            marcarCampo(txtCedula, lblAyudaCedula, "Usa solo numeros y guiones.", WARNING);
            return false;
        }

        marcarCampo(txtCedula, lblAyudaCedula, "Cedula valida.", SUCCESS);
        return true;
    }

    private boolean validarSalario(boolean mostrarVacioComoError) {
        String valor = txtSalario.getText().trim().replace(',', '.');

        if (valor.isEmpty()) {
            if (mostrarVacioComoError) {
                marcarCampo(txtSalario, lblAyudaSalario, "El salario es obligatorio.", ERROR);
                return false;
            }
            restaurarCampo(txtSalario, lblAyudaSalario, "Ingresa un monto numerico mayor que cero.");
            return false;
        }

        try {
            double salario = Double.parseDouble(valor);
            if (salario <= 0) {
                marcarCampo(txtSalario, lblAyudaSalario, "El salario debe ser mayor que cero.", WARNING);
                return false;
            }
        } catch (NumberFormatException ex) {
            marcarCampo(txtSalario, lblAyudaSalario, "Escribe un numero valido.", WARNING);
            return false;
        }

        marcarCampo(txtSalario, lblAyudaSalario, "Salario valido.", SUCCESS);
        return true;
    }

    private void normalizarSalario() {
        String valor = txtSalario.getText().trim().replace(',', '.');

        if (valor.isEmpty()) {
            return;
        }

        try {
            double salario = Double.parseDouble(valor);
            txtSalario.setText(String.format(java.util.Locale.US, "%.2f", salario));
        } catch (NumberFormatException ex) {
            // La validacion visual ya informa el error al usuario.
        }
    }

    private void marcarCampo(JTextField field, JLabel helpLabel, String message, Color color) {
        field.setBorder(createFieldBorder(color));
        helpLabel.setForeground(color);
        helpLabel.setText(message);
    }

    private void restaurarCampo(JTextField field, JLabel helpLabel, String message) {
        field.setBorder(createFieldBorder(new Color(206, 212, 218)));
        helpLabel.setForeground(MUTED);
        helpLabel.setText(message);
    }

    private Border createFieldBorder(Color color) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                new EmptyBorder(9, 11, 9, 11)
        );
    }

    private void procesarNomina() {
        String remitente = txtRemitente.getText().trim();
        String claveCorreo = new String(txtClaveCorreo.getPassword()).trim();
        String nombre = txtNombre.getText().trim();
        String cedula = txtCedula.getText().trim();
        String salarioTexto = txtSalario.getText().trim().replace(',', '.');
        String correoDestino = txtCorreoDestino.getText().trim();

        boolean formularioValido = validarRemitente(true)
                & validarClaveCorreo(true)
                & validarNombre(true)
                & validarCedula(true)
                & validarSalario(true)
                & validarCorreoDestino(true);

        if (!formularioValido) {
            mostrarError("Corrige los campos marcados antes de continuar.");
            return;
        }

        double salarioBase;
        try {
            salarioBase = Double.parseDouble(salarioTexto);
        } catch (NumberFormatException ex) {
            mostrarError("El salario base debe ser numerico.");
            return;
        }

        if (salarioBase <= 0) {
            mostrarError("El salario base debe ser mayor que cero.");
            return;
        }

        Empleado empleado = new Empleado(nombre, cedula, salarioBase, correoHelper.limpiar(correoDestino));
        Nomina nomina = calculoNomina.calcular(empleado);

        try {
            setEstadoFormulario(false, "Procesando nomina y preparando envio...");
            Path rutaPdf = generadorPDF.generarPdf(empleado, nomina);
            registrarNomina(empleado, nomina, rutaPdf);
            mailService.enviarCorreo(correoHelper.limpiar(remitente), claveCorreo, empleado, nomina, rutaPdf);

            txtResumen.setText(construirResumen(empleado, nomina, rutaPdf));
            actualizarEstado("PDF generado y correo enviado correctamente.", false);
            JOptionPane.showMessageDialog(
                    this,
                    "¡Proceso completado exitosamente!\n\n" +
                    "✓ PDF generado: " + rutaPdf.getFileName() + "\n" +
                    "✓ Correo enviado a: " + empleado.getCorreo() + "\n" +
                    "✓ Salario neto: " + String.format("%.2f", nomina.getSalarioNeto()) + "\n\n" +
                    "Revisa la carpeta 'documentos_pdf' para el archivo generado.",
                    "Envío Exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception ex) {
            registrarErrorTecnico(ex);
            String mensajeError = obtenerMensajeErrorAmigable(ex);
            actualizarEstado("Error en el envío: " + mensajeError, true);
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo completar el envío del comprobante.\n\n" +
                    "Error: " + mensajeError + "\n\n" +
                    "Revisa los datos e intenta nuevamente. Si el problema persiste, " +
                    "consulta el archivo 'data/ultimo_error_envio.txt' para más detalles.",
                    "Error en el Envío",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            setEstadoFormulario(true, lblEstado.getText());
        }
    }

    private void limpiarCampos() {
        txtRemitente.setText("");
        txtClaveCorreo.setText("");
        txtNombre.setText("");
        txtCedula.setText("");
        txtSalario.setText("");
        txtCorreoDestino.setText("");
        chkMostrarClaveCorreo.setSelected(false);
        txtClaveCorreo.setEchoChar('\u2022');
        restaurarCampo(txtRemitente, lblAyudaRemitente, "Debe ser un Gmail valido para el envio.");
        restaurarCampo(txtClaveCorreo, lblAyudaClaveCorreo, "Usa la contrasena de aplicacion de Gmail.");
        restaurarCampo(txtNombre, lblAyudaNombre, "Ingresa el nombre completo del empleado.");
        restaurarCampo(txtCedula, lblAyudaCedula, "Solo digitos y un formato reconocible.");
        restaurarCampo(txtSalario, lblAyudaSalario, "Ingresa un monto numerico mayor que cero.");
        restaurarCampo(txtCorreoDestino, lblAyudaCorreoDestino, "Debe ser un correo valido para recibir el PDF.");
        txtResumen.setText("");
        actualizarEstado("Campos limpiados.", false);
        txtRemitente.requestFocusInWindow();
    }

    private void registrarNomina(Empleado empleado, Nomina nomina, Path rutaPdf) throws IOException {
        ArchivoTexto archivo = new ArchivoTexto(RutasProyecto.resolver("data", "nominas_registradas.txt").toString());
        archivo.guardar(String.format(
                "%s | %s | %.2f | %.2f | %.2f | %s",
                empleado.getNombre(),
                empleado.getCedula(),
                nomina.getSalarioBruto(),
                nomina.getDeducciones(),
                nomina.getSalarioNeto(),
                rutaPdf
        ));
    }

    private String construirResumen(Empleado empleado, Nomina nomina, Path rutaPdf) {
        return String.format(
                "Empleado: %s%nCedula: %s%nCorreo: %s%n%nSalario bruto: %.2f%nDeducciones: %.2f%nSalario neto: %.2f%n%nPDF generado en:%n%s",
                empleado.getNombre(),
                empleado.getCedula(),
                empleado.getCorreo(),
                nomina.getSalarioBruto(),
                nomina.getDeducciones(),
                nomina.getSalarioNeto(),
                rutaPdf
        );
    }

    private void mostrarError(String mensaje) {
        actualizarEstado(mensaje, true);
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private String obtenerMensajeErrorAmigable(Throwable error) {
        String mensajeOriginal = error.getMessage();
        if (mensajeOriginal == null) mensajeOriginal = "";

        // Errores específicos de autenticación
        if (mensajeOriginal.contains("Google rechazo la autenticacion") ||
            mensajeOriginal.contains("AuthenticationFailedException")) {
            return "Error de autenticación con Gmail. Verifica que uses una contraseña de aplicación válida y que tu cuenta esté configurada correctamente.";
        }

        // Errores de envío
        if (mensajeOriginal.contains("No se pudo entregar el correo") ||
            mensajeOriginal.contains("SendFailedException")) {
            return "No se pudo enviar el correo. Verifica la dirección de email del destinatario.";
        }

        // Errores de conexión
        if (mensajeOriginal.contains("Fallo la conexion segura") ||
            mensajeOriginal.contains("SSLException") ||
            mensajeOriginal.contains("UnknownHostException")) {
            return "Error de conexión con el servidor de Gmail. Verifica tu conexión a internet.";
        }

        // Errores de librerías faltantes
        if (mensajeOriginal.contains("Falta una libreria requerida") ||
            mensajeOriginal.contains("NoClassDefFoundError")) {
            return "Faltan librerías necesarias. Contacta al administrador del sistema.";
        }

        // Errores de archivo
        if (error instanceof java.io.FileNotFoundException) {
            return "No se encontró el archivo PDF generado. Verifica los permisos de escritura.";
        }

        // Errores de formato
        if (error instanceof IllegalArgumentException) {
            return "Datos inválidos: " + mensajeOriginal;
        }

        // Error genérico
        return "Error inesperado: " + obtenerMensajeError(error);
    }

    private String obtenerMensajeError(Throwable error) {
        Throwable actual = error;

        while (actual != null) {
            String mensaje = actual.getMessage();
            if (mensaje != null && !mensaje.trim().isEmpty()) {
                return mensaje;
            }
            actual = actual.getCause();
        }

        return error == null ? "Error desconocido." : error.getClass().getSimpleName();
    }

    private void registrarErrorTecnico(Throwable error) {
        StringWriter buffer = new StringWriter();
        error.printStackTrace(new PrintWriter(buffer));

        try {
            ArchivoTexto archivo = new ArchivoTexto(RutasProyecto.resolver("data", "ultimo_error_envio.txt").toString());
            archivo.guardar("========== NUEVO ERROR ==========");
            archivo.guardar(buffer.toString());
        } catch (IOException ex) {
            txtResumen.setText("No se pudo guardar el log del error.\n" + buffer);
            return;
        }

        txtResumen.setText(
                "Se produjo un error tecnico.\n\n"
                + "Revisa el archivo:\n"
                + "data/ultimo_error_envio.txt\n\n"
                + "Resumen:\n"
                + obtenerMensajeError(error)
        );
    }

    private void actualizarEstado(String mensaje, boolean error) {
        lblEstado.setText(mensaje);
        lblEstado.setForeground(error ? ERROR : MUTED);
    }

    private void setEstadoFormulario(boolean habilitado, String mensaje) {
        txtRemitente.setEnabled(habilitado);
        txtClaveCorreo.setEnabled(habilitado);
        txtNombre.setEnabled(habilitado);
        txtCedula.setEnabled(habilitado);
        txtSalario.setEnabled(habilitado);
        txtCorreoDestino.setEnabled(habilitado);
        chkMostrarClaveCorreo.setEnabled(habilitado);
        btnEnviar.setEnabled(habilitado);
        btnLimpiar.setEnabled(habilitado);
        actualizarEstado(mensaje, false);
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
            java.util.logging.Logger.getLogger(FrmNomina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new FrmNomina().setVisible(true));
    }
}
