package proyecto2.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.IOException;
import java.nio.file.Path;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import proyecto2.AccesoDatos.ArchivoTexto;
import proyecto2.Entidades.Empleado;
import proyecto2.Entidades.Nomina;
import proyecto2.LogicaNegocio.CalculoNomina;
import proyecto2.LogicaNegocio.Correo;
import proyecto2.LogicaNegocio.GeneradorPDF;
import proyecto2.LogicaNegocio.MailService;

public class FrmNomina extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color BACKGROUND = new Color(243, 246, 251);
    private static final Color PANEL = new Color(255, 255, 255);
    private static final Color PRIMARY = new Color(26, 115, 232);
    private static final Color PRIMARY_DARK = new Color(14, 84, 170);
    private static final Color TEXT = new Color(33, 37, 41);
    private static final Color MUTED = new Color(108, 117, 125);

    private JTextField txtRemitente;
    private JPasswordField txtClaveCorreo;
    private JTextField txtNombre;
    private JTextField txtCedula;
    private JTextField txtSalario;
    private JTextField txtCorreoDestino;
    private JTextArea txtResumen;
    private JLabel lblEstado;

    private final Correo correoHelper = new Correo();
    private final CalculoNomina calculoNomina = new CalculoNomina();
    private final GeneradorPDF generadorPDF = new GeneradorPDF();
    private final MailService mailService = new MailService();

    public FrmNomina() {
        initComponents();
        configurarVentana();
    }

    private void configurarVentana() {
        setTitle("Sistema de Nomina - Windows");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 620));
        setSize(1160, 720);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void initComponents() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(24, 24, 24, 24));

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 226, 234)),
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
        gbc.insets = new Insets(24, 0, 6, 0);
        container.add(createFieldLabel("Correo remitente de Gmail"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtRemitente = createTextField();
        container.add(txtRemitente, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 6, 0);
        container.add(createFieldLabel("Contrasena de aplicacion"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtClaveCorreo = createPasswordField();
        container.add(txtClaveCorreo, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 6, 0);
        container.add(createFieldLabel("Nombre del empleado"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtNombre = createTextField();
        container.add(txtNombre, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 6, 0);
        container.add(createFieldLabel("Cedula"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtCedula = createTextField();
        container.add(txtCedula, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 6, 0);
        container.add(createFieldLabel("Salario base"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtSalario = createTextField();
        container.add(txtSalario, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 6, 0);
        container.add(createFieldLabel("Correo del empleado"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtCorreoDestino = createTextField();
        container.add(txtCorreoDestino, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(12, 0, 10, 0);
        JButton btnEnviar = createPrimaryButton("Generar PDF y enviar");
        btnEnviar.addActionListener(evt -> procesarNomina());
        container.add(btnEnviar, gbc);

        gbc.gridy++;
        JButton btnLimpiar = createSecondaryButton("Limpiar formulario");
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
        txtResumen.setBorder(new EmptyBorder(10, 10, 10, 10));
        txtResumen.setText("Aqui veras el resumen de la nomina generada.");
        container.add(new JScrollPane(txtResumen), gbc);

        return container;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
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

    private void procesarNomina() {
        String remitente = txtRemitente.getText().trim();
        String claveCorreo = new String(txtClaveCorreo.getPassword()).trim();
        String nombre = txtNombre.getText().trim();
        String cedula = txtCedula.getText().trim();
        String salarioTexto = txtSalario.getText().trim();
        String correoDestino = txtCorreoDestino.getText().trim();

        if (remitente.isEmpty() || claveCorreo.isEmpty() || nombre.isEmpty()
                || cedula.isEmpty() || salarioTexto.isEmpty() || correoDestino.isEmpty()) {
            mostrarError("Debes completar todos los campos.");
            return;
        }

        if (!correoHelper.esValido(remitente) || !correoHelper.esValido(correoDestino)) {
            mostrarError("Revisa el correo remitente y el correo del empleado.");
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
            Path rutaPdf = generadorPDF.generarPdf(empleado, nomina);
            registrarNomina(empleado, nomina, rutaPdf);
            mailService.enviarCorreo(correoHelper.limpiar(remitente), claveCorreo, empleado, nomina, rutaPdf);

            txtResumen.setText(construirResumen(empleado, nomina, rutaPdf));
            actualizarEstado("PDF generado y correo enviado correctamente.", false);
            JOptionPane.showMessageDialog(
                    this,
                    "Proceso completado.\nPDF: " + rutaPdf,
                    "Envio exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception ex) {
            registrarErrorTecnico(ex);
            mostrarError("No se pudo completar el envio: " + obtenerMensajeError(ex));
        }
    }

    private void limpiarCampos() {
        txtRemitente.setText("");
        txtClaveCorreo.setText("");
        txtNombre.setText("");
        txtCedula.setText("");
        txtSalario.setText("");
        txtCorreoDestino.setText("");
        txtResumen.setText("Aqui veras el resumen de la nomina generada.");
        actualizarEstado("Campos limpiados.", false);
    }

    private void registrarNomina(Empleado empleado, Nomina nomina, Path rutaPdf) throws IOException {
        ArchivoTexto archivo = new ArchivoTexto("data/nominas_registradas.txt");
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

    private String obtenerMensajeError(Throwable error) {
        Throwable actual = error;

        while (actual != null) {
            String tipo = actual.getClass().getSimpleName();
            String mensaje = actual.getMessage();

            if (mensaje != null && !mensaje.trim().isEmpty()) {
                return tipo + ": " + mensaje;
            }

            actual = actual.getCause();
        }

        return error.getClass().getName()
                + ". Revisa el archivo data/ultimo_error_envio.txt para ver el detalle tecnico.";
    }

    private void registrarErrorTecnico(Throwable error) {
        StringWriter buffer = new StringWriter();
        error.printStackTrace(new PrintWriter(buffer));

        try {
            ArchivoTexto archivo = new ArchivoTexto("data/ultimo_error_envio.txt");
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
        lblEstado.setForeground(error ? new Color(198, 40, 40) : MUTED);
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
