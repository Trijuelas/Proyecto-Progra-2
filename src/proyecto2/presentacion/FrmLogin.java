package proyecto2.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.border.EmptyBorder;

public class FrmLogin extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color BACKGROUND = new Color(243, 246, 251);
    private static final Color PANEL = new Color(255, 255, 255);
    private static final Color PRIMARY = new Color(26, 115, 232);
    private static final Color PRIMARY_DARK = new Color(14, 84, 170);
    private static final Color TEXT = new Color(33, 37, 41);
    private static final Color MUTED = new Color(108, 117, 125);

    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JLabel lblEstado;

    public FrmLogin() {
        initComponents();
        configurarVentana();
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
                BorderFactory.createLineBorder(new Color(220, 226, 234)),
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
        gbc.insets = new Insets(24, 0, 6, 0);
        container.add(createFieldLabel("Usuario"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtUsuario = createTextField();
        container.add(txtUsuario, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 6, 0);
        container.add(createFieldLabel("Contrasena"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtClave = createPasswordField();
        container.add(txtClave, gbc);

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

    private void autenticar() {
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword()).trim();

        if (usuario.isEmpty() || clave.isEmpty()) {
            actualizarEstado("Completa usuario y contrasena.", true);
            JOptionPane.showMessageDialog(this, "Debes completar ambos campos.", "Validacion", JOptionPane.WARNING_MESSAGE);
            return;
        }

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
        actualizarEstado("Campos limpiados.", false);
    }

    private void actualizarEstado(String mensaje, boolean error) {
        lblEstado.setText(mensaje);
        lblEstado.setForeground(error ? new Color(198, 40, 40) : MUTED);
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
