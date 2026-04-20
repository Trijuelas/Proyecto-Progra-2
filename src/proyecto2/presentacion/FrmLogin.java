package proyecto2.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FrmLogin extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color BACKGROUND = new Color(243, 246, 251);
    private static final Color PANEL = new Color(255, 255, 255);
    private static final Color PRIMARY = new Color(26, 115, 232);
    private static final Color PRIMARY_DARK = new Color(14, 84, 170);
    private static final Color PRIMARY_SOFT = new Color(227, 238, 255);
    private static final Color TEXT = new Color(33, 37, 41);
    private static final Color MUTED = new Color(108, 117, 125);
    private static final Color BORDER = new Color(220, 226, 234);
    private static final Color SUCCESS = new Color(46, 125, 50);
    private static final Color ERROR = new Color(198, 40, 40);
    private static final Color WARNING = new Color(239, 108, 0);

    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JLabel lblEstado;
    private JLabel lblAyudaUsuario;
    private JLabel lblAyudaClave;

    public FrmLogin() {
        initComponents();
        configurarVentana();
        configurarValidacionesVisuales();
    }

    private void configurarVentana() {
        setTitle("Sistema de Nomina - Acceso");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 620));
        setSize(1024, 640);
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
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(0, 0, 0, 0)
        ));

        card.add(buildBrandPanel(), BorderLayout.WEST);
        card.add(buildLoginPanel(), BorderLayout.CENTER);

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

        JLabel title = new JLabel("<html>Gestiona tu<br>nomina con orden<br>y claridad</html>");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setAlignmentX(LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("<html>Una pantalla de acceso mas limpia para iniciar sesion en el sistema y preparar la gestion de empleados.</html>");
        subtitle.setForeground(new Color(224, 231, 255));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitle.setAlignmentX(LEFT_ALIGNMENT);

        panel.add(badge);
        panel.add(Box.createVerticalStrut(32));
        panel.add(title);
        panel.add(Box.createVerticalStrut(18));
        panel.add(subtitle);
        panel.add(Box.createVerticalGlue());

        panel.add(createFeature("Seguridad", "Acceso centralizado para el sistema."));
        panel.add(Box.createVerticalStrut(14));
        panel.add(createFeature("Agilidad", "Preparado para flujo rapido de trabajo."));
        panel.add(Box.createVerticalStrut(14));
        panel.add(createFeature("Presentacion", "Interfaz moderna y mas profesional."));
        panel.add(Box.createVerticalStrut(26));
        panel.add(createStatsPanel());

        return panel;
    }

    private JPanel createFeature(String title, String detail) {
        JPanel wrapper = new JPanel(new BorderLayout(10, 0));
        wrapper.setOpaque(false);
        wrapper.setAlignmentX(LEFT_ALIGNMENT);

        JLabel dot = new JLabel("●");
        dot.setForeground(new Color(144, 202, 249));
        dot.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel text = new JLabel("<html><b>" + title + "</b><br>" + detail + "</html>");
        text.setForeground(Color.WHITE);
        text.setFont(new Font("SansSerif", Font.PLAIN, 14));

        wrapper.add(dot, BorderLayout.WEST);
        wrapper.add(text, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setAlignmentX(LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 12, 0);
        panel.add(createStatCard("Acceso rapido", "Diseno limpio para iniciar sin distracciones."), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(createStatCard("Interfaz clara", "Jerarquia visual que facilita el siguiente paso."), gbc);
        return panel;
    }

    private JPanel createStatCard(String title, String detail) {
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

    private JPanel buildLoginPanel() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(PANEL);
        container.setBorder(new EmptyBorder(34, 42, 34, 42));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        JLabel welcome = new JLabel("Bienvenido");
        welcome.setFont(new Font("SansSerif", Font.BOLD, 28));
        welcome.setForeground(TEXT);
        container.add(welcome, gbc);

        gbc.gridy++;
        JLabel description = new JLabel("Ingresa tus credenciales para acceder al sistema.");
        description.setFont(new Font("SansSerif", Font.PLAIN, 15));
        description.setForeground(MUTED);
        container.add(description, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(22, 0, 18, 0);
        container.add(buildHighlightPanel(), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(24, 0, 6, 0);
        container.add(createFieldLabel("Usuario"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtUsuario = createTextField();
        container.add(txtUsuario, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        lblAyudaUsuario = createHelperLabel("Escribe tu usuario institucional.");
        container.add(lblAyudaUsuario, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 6, 0);
        container.add(createFieldLabel("Contrasena"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtClave = createPasswordField();
        container.add(txtClave, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        lblAyudaClave = createHelperLabel("Ingresa tu contrasena para continuar.");
        container.add(lblAyudaClave, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(2, 0, 10, 0);
        container.add(buildOptionsRow(), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(12, 0, 10, 0);
        JButton btnIngresar = createPrimaryButton("Iniciar sesion");
        btnIngresar.addActionListener(evt -> autenticar());
        container.add(btnIngresar, gbc);

        gbc.gridy++;
        JButton btnLimpiar = createSecondaryButton("Limpiar campos");
        btnLimpiar.addActionListener(evt -> limpiarCampos());
        container.add(btnLimpiar, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(18, 0, 0, 0);
        lblEstado = new JLabel("Listo para iniciar sesion.");
        lblEstado.setForeground(MUTED);
        lblEstado.setFont(new Font("SansSerif", Font.PLAIN, 13));
        container.add(lblEstado, gbc);

        gbc.gridy++;
        gbc.weighty = 1.0;
        container.add(new JPanel(), gbc);

        return container;
    }

    private JPanel buildHighlightPanel() {
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

        JLabel title = new JLabel("Antes de ingresar");
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(title, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 0, 0);
        JLabel detail = new JLabel("<html>Usa tus credenciales del sistema y verifica que el usuario este escrito correctamente.</html>");
        detail.setForeground(MUTED);
        detail.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panel.add(detail, gbc);
        return panel;
    }

    private JPanel buildOptionsRow() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JCheckBox check = new JCheckBox("Recordar usuario");
        check.setOpaque(false);
        check.setForeground(MUTED);
        check.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel link = new JLabel("Necesitas ayuda?");
        link.setForeground(PRIMARY);
        link.setFont(new Font("SansSerif", Font.BOLD, 13));
        link.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(check, BorderLayout.WEST);
        panel.add(link, BorderLayout.EAST);
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
        instalarValidacionEnVivo(txtUsuario, () -> validarUsuario(false));
        instalarValidacionEnVivo(txtClave, () -> validarClave(false));
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

    private boolean validarUsuario(boolean mostrarVacioComoError) {
        String usuario = txtUsuario.getText().trim();

        if (usuario.isEmpty()) {
            if (mostrarVacioComoError) {
                marcarCampo(txtUsuario, lblAyudaUsuario, "El usuario es obligatorio.", ERROR);
                return false;
            }
            restaurarCampo(txtUsuario, lblAyudaUsuario, "Escribe tu usuario institucional.");
            return false;
        }

        if (usuario.length() < 3) {
            marcarCampo(txtUsuario, lblAyudaUsuario, "Debe tener al menos 3 caracteres.", WARNING);
            return false;
        }

        marcarCampo(txtUsuario, lblAyudaUsuario, "Usuario con formato valido.", SUCCESS);
        return true;
    }

    private boolean validarClave(boolean mostrarVacioComoError) {
        char[] clave = txtClave.getPassword();
        String valor = new String(clave).trim();

        try {
            if (valor.isEmpty()) {
                if (mostrarVacioComoError) {
                    marcarCampo(txtClave, lblAyudaClave, "La contrasena es obligatoria.", ERROR);
                    return false;
                }
                restaurarCampo(txtClave, lblAyudaClave, "Ingresa tu contrasena para continuar.");
                return false;
            }

            if (valor.length() < 4) {
                marcarCampo(txtClave, lblAyudaClave, "Debe tener al menos 4 caracteres.", WARNING);
                return false;
            }

            marcarCampo(txtClave, lblAyudaClave, "Contrasena capturada correctamente.", SUCCESS);
            return true;
        } finally {
            Arrays.fill(clave, '\0');
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

    private void autenticar() {
        boolean usuarioValido = validarUsuario(true);
        boolean claveValida = validarClave(true);

        if (!usuarioValido || !claveValida) {
            actualizarEstado("Corrige los campos marcados antes de continuar.", true);
            JOptionPane.showMessageDialog(this, "Revisa los campos resaltados para continuar.", "Validacion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String usuario = txtUsuario.getText().trim();
        actualizarEstado("Credenciales capturadas correctamente.", false);
        JOptionPane.showMessageDialog(
                this,
                "Inicio de sesion simulado con exito para: " + usuario,
                "Acceso concedido",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void limpiarCampos() {
        txtUsuario.setText("");
        txtClave.setText("");
        restaurarCampo(txtUsuario, lblAyudaUsuario, "Escribe tu usuario institucional.");
        restaurarCampo(txtClave, lblAyudaClave, "Ingresa tu contrasena para continuar.");
        actualizarEstado("Campos limpiados.", false);
    }

    private void actualizarEstado(String mensaje, boolean error) {
        lblEstado.setText(mensaje);
        lblEstado.setForeground(error ? ERROR : MUTED);
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new FrmLogin().setVisible(true));
    }
}
