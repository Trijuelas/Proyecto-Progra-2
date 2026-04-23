package proyecto2.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private static final String USUARIO_ADMIN = "admin";
    private static final String CLAVE_ADMIN = "1234";

    private static final Color BACKGROUND = new Color(243, 246, 251);
    private static final Color PANEL = new Color(255, 255, 255);
    private static final Color PRIMARY = new Color(26, 115, 232);
    private static final Color PRIMARY_DARK = new Color(14, 84, 170);
    private static final Color PRIMARY_SOFT = new Color(227, 238, 255);
    private static final Color TEXT = new Color(33, 37, 41);
    private static final Color MUTED = new Color(108, 117, 125);
    private static final Color ERROR = new Color(198, 40, 40);
    private static final Color SUCCESS = new Color(46, 125, 50);
    private static final Color WARNING = new Color(239, 108, 0);

    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JLabel lblEstado;
    private JLabel lblAyudaUsuario;
    private JLabel lblAyudaClave;
    private JCheckBox chkMostrarClave;
    private JButton btnIngresar;
    private JButton btnLimpiar;

    public FrmLogin() {
        initComponents();
        configurarVentana();
        configurarValidacionesVisuales();
    }

    private void configurarVentana() {
        setTitle("Sistema de Nomina - Login");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(920, 580));
        setSize(980, 620);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                txtUsuario.requestFocusInWindow();
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
        panel.setPreferredSize(new Dimension(340, 0));
        panel.setBackground(PRIMARY_DARK);
        panel.setBorder(new EmptyBorder(36, 30, 36, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel badge = new JLabel("ACCESO ADMIN");
        badge.setOpaque(true);
        badge.setBackground(new Color(255, 255, 255, 40));
        badge.setForeground(Color.WHITE);
        badge.setFont(new Font("SansSerif", Font.BOLD, 12));
        badge.setBorder(new EmptyBorder(8, 12, 8, 12));
        badge.setAlignmentX(LEFT_ALIGNMENT);

        JLabel title = new JLabel("<html>Bienvenido al<br>sistema de nomina</html>");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setAlignmentX(LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("<html>Ingresa con el usuario administrador para abrir las dos interfaces principales del sistema.</html>");
        subtitle.setForeground(new Color(224, 231, 255));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitle.setAlignmentX(LEFT_ALIGNMENT);

        panel.add(badge);
        panel.add(Box.createVerticalStrut(28));
        panel.add(title);
        panel.add(Box.createVerticalStrut(18));
        panel.add(subtitle);
        panel.add(Box.createVerticalGlue());
        panel.add(createInfoCard("Usuario", USUARIO_ADMIN));
        panel.add(Box.createVerticalStrut(12));
        panel.add(createInfoCard("Contrasena", CLAVE_ADMIN));
        panel.add(Box.createVerticalStrut(12));
        panel.add(createInfoCard("Acceso", "Empleado y Patrono"));

        return panel;
    }

    private JPanel createInfoCard(String titulo, String detalle) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setOpaque(true);
        card.setBackground(new Color(255, 255, 255, 28));
        card.setBorder(new EmptyBorder(14, 16, 14, 16));
        card.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel lblDetalle = new JLabel(detalle);
        lblDetalle.setForeground(new Color(224, 231, 255));
        lblDetalle.setFont(new Font("SansSerif", Font.PLAIN, 13));

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblDetalle, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildLoginPanel() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(PANEL);
        container.setBorder(new EmptyBorder(36, 42, 36, 42));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        JLabel welcome = new JLabel("Iniciar sesion");
        welcome.setFont(new Font("SansSerif", Font.BOLD, 28));
        welcome.setForeground(TEXT);
        container.add(welcome, gbc);

        gbc.gridy++;
        JLabel description = new JLabel("Usa el acceso rapido para entrar al sistema.");
        description.setFont(new Font("SansSerif", Font.PLAIN, 15));
        description.setForeground(MUTED);
        container.add(description, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(22, 0, 18, 0);
        container.add(buildHighlightPanel(), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 6, 0);
        container.add(createFieldLabel("Usuario"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 10, 0);
        txtUsuario = createTextField();
        container.add(txtUsuario, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        lblAyudaUsuario = createHelperLabel("Escribe el usuario admin.");
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
        lblAyudaClave = createHelperLabel("Ingresa la contrasena 1234.");
        container.add(lblAyudaClave, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(2, 0, 10, 0);
        container.add(buildOptionsRow(), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(12, 0, 10, 0);
        btnIngresar = createPrimaryButton("Entrar");
        btnIngresar.addActionListener(evt -> autenticar());
        container.add(btnIngresar, gbc);
        getRootPane().setDefaultButton(btnIngresar);

        gbc.gridy++;
        btnLimpiar = createSecondaryButton("Limpiar");
        btnLimpiar.addActionListener(evt -> limpiarCampos());
        container.add(btnLimpiar, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(16, 0, 0, 0);
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

        JLabel title = new JLabel("Acceso configurado");
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(title, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 0, 0);
        JLabel detail = new JLabel("<html>Este login es quemado y solo usa admin / 1234 para entrar.</html>");
        detail.setForeground(MUTED);
        detail.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panel.add(detail, gbc);
        return panel;
    }

    private JPanel buildOptionsRow() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        chkMostrarClave = new JCheckBox("Mostrar contrasena");
        chkMostrarClave.setOpaque(false);
        chkMostrarClave.setForeground(MUTED);
        chkMostrarClave.setFont(new Font("SansSerif", Font.PLAIN, 13));
        chkMostrarClave.addActionListener(evt -> txtClave.setEchoChar(chkMostrarClave.isSelected() ? (char) 0 : '\u2022'));

        JLabel link = new JLabel("Login de demostracion");
        link.setForeground(PRIMARY);
        link.setFont(new Font("SansSerif", Font.BOLD, 13));
        link.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(chkMostrarClave, BorderLayout.WEST);
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
        field.setEchoChar('\u2022');
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
            restaurarCampo(txtUsuario, lblAyudaUsuario, "Escribe el usuario admin.");
            return false;
        }

        if (usuario.length() < 3) {
            marcarCampo(txtUsuario, lblAyudaUsuario, "Debe tener al menos 3 caracteres.", WARNING);
            return false;
        }

        marcarCampo(txtUsuario, lblAyudaUsuario, "Usuario capturado correctamente.", SUCCESS);
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
                restaurarCampo(txtClave, lblAyudaClave, "Ingresa la contrasena 1234.");
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
            actualizarEstado("Corrige los campos para continuar.", true);
            JOptionPane.showMessageDialog(this, "Revisa los campos marcados.", "Validacion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword()).trim();

        if (!USUARIO_ADMIN.equals(usuario) || !CLAVE_ADMIN.equals(clave)) {
            marcarCampo(txtUsuario, lblAyudaUsuario, "Usuario no valido para este acceso.", ERROR);
            marcarCampo(txtClave, lblAyudaClave, "Contrasena incorrecta.", ERROR);
            actualizarEstado("Credenciales invalidas.", true);
            JOptionPane.showMessageDialog(this, "Usa las credenciales admin / 1234.", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        abrirSiguienteVentana();
    }

    private void abrirSiguienteVentana() {
        try {
            setEstadoFormulario(false, "Ingresando al sistema...");
            FrmMenuPrincipal menu = new FrmMenuPrincipal();
            menu.setVisible(true);
            dispose();
        } catch (Exception ex) {
            setEstadoFormulario(true, "No se pudo abrir la siguiente ventana.");
            JOptionPane.showMessageDialog(
                    this,
                    "El acceso fue correcto, pero no se pudo abrir el menu principal.",
                    "Error al continuar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limpiarCampos() {
        txtUsuario.setText("");
        txtClave.setText("");
        chkMostrarClave.setSelected(false);
        txtClave.setEchoChar('\u2022');
        restaurarCampo(txtUsuario, lblAyudaUsuario, "Escribe el usuario admin.");
        restaurarCampo(txtClave, lblAyudaClave, "Ingresa la contrasena 1234.");
        actualizarEstado("Campos limpiados.", false);
        txtUsuario.requestFocusInWindow();
    }

    private void setEstadoFormulario(boolean habilitado, String mensaje) {
        txtUsuario.setEnabled(habilitado);
        txtClave.setEnabled(habilitado);
        chkMostrarClave.setEnabled(habilitado);
        btnIngresar.setEnabled(habilitado);
        btnLimpiar.setEnabled(habilitado);
        actualizarEstado(mensaje, false);
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
