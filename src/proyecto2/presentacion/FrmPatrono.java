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
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import proyecto2.AccesoDatos.ArchivoTexto;
import proyecto2.LogicaNegocio.RutasProyecto;

public class FrmPatrono extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Color BACKGROUND = new Color(243, 246, 251);
    private static final Color PANEL = new Color(255, 255, 255);
    private static final Color PRIMARY = new Color(26, 115, 232);
    private static final Color PRIMARY_DARK = new Color(14, 84, 170);
    private static final Color TEXT = new Color(33, 37, 41);
    private static final Color MUTED = new Color(108, 117, 125);

    private JTextField txtNombre;
    private JTextField txtCedulaJuridica;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JTextArea txtResumen;
    private JLabel lblEstado;

    public FrmPatrono() {
        initComponents();
        configurarVentana();
    }

    private void configurarVentana() {
        setTitle("Sistema de Nomina - Patrono");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(900, 560));
        setSize(1024, 680);
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

        JLabel title = new JLabel("<html>Datos del<br>patrono</html>");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setAlignmentX(LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("<html>Una ventana simple para registrar la informacion principal del patrono y dejarla guardada en texto.</html>");
        subtitle.setForeground(new Color(224, 231, 255));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitle.setAlignmentX(LEFT_ALIGNMENT);

        panel.add(badge);
        panel.add(Box.createVerticalStrut(24));
        panel.add(title);
        panel.add(Box.createVerticalStrut(14));
        panel.add(subtitle);
        panel.add(Box.createVerticalGlue());
        panel.add(createItem("Nombre del patrono"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createItem("Cedula juridica"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createItem("Telefono y correo"));

        return panel;
    }

    private JLabel createItem(String text) {
        JLabel label = new JLabel("• " + text);
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
        gbc.insets = new Insets(14, 0, 4, 0);
        container.add(createFieldLabel("Nombre del patrono"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(4, 0, 7, 0);
        txtNombre = createTextField();
        container.add(txtNombre, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(7, 0, 4, 0);
        container.add(createFieldLabel("Cedula juridica"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(4, 0, 7, 0);
        txtCedulaJuridica = createTextField();
        container.add(txtCedulaJuridica, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(7, 0, 4, 0);
        container.add(createFieldLabel("Telefono"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(4, 0, 7, 0);
        txtTelefono = createTextField();
        container.add(txtTelefono, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(7, 0, 4, 0);
        container.add(createFieldLabel("Correo"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(4, 0, 7, 0);
        txtCorreo = createTextField();
        container.add(txtCorreo, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(9, 0, 7, 0);
        JButton btnGuardar = createPrimaryButton("Guardar patrono");
        btnGuardar.addActionListener(evt -> guardarPatrono());
        container.add(btnGuardar, gbc);

        gbc.gridy++;
        JButton btnLimpiar = createSecondaryButton("Limpiar");
        btnLimpiar.addActionListener(evt -> limpiarFormulario());
        container.add(btnLimpiar, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(12, 0, 6, 0);
        lblEstado = new JLabel("Listo para guardar informacion del patrono.");
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
        txtResumen.setText("Todavia no hay informacion guardada.");
        container.add(txtResumen, gbc);

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

        JLabel description = new JLabel("Completa la informacion y guardala en el archivo local.");
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

    private void guardarPatrono() {
        String nombre = txtNombre.getText().trim();
        String cedula = txtCedulaJuridica.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty() || cedula.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            lblEstado.setText("Completa todos los campos.");
            lblEstado.setForeground(new Color(198, 40, 40));
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Validacion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String contenido = String.format(
                "Nombre del patrono: %s%nCedula juridica: %s%nTelefono: %s%nCorreo: %s%n",
                nombre,
                cedula,
                telefono,
                correo
        );

        try {
            ArchivoTexto archivo = new ArchivoTexto(RutasProyecto.resolver("data", "patrono_registrado.txt").toString());
            archivo.sobrescribir(contenido);
            txtResumen.setText(contenido + "\nInformacion guardada en data/patrono_registrado.txt");
            lblEstado.setText("Patrono guardado correctamente.");
            lblEstado.setForeground(MUTED);
            JOptionPane.showMessageDialog(this, "Los datos del patrono fueron guardados.", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            lblEstado.setText("No se pudo guardar la informacion.");
            lblEstado.setForeground(new Color(198, 40, 40));
            JOptionPane.showMessageDialog(this, "Ocurrio un error al guardar los datos del patrono.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtCedulaJuridica.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtResumen.setText("Todavia no hay informacion guardada.");
        lblEstado.setText("Campos limpiados.");
        lblEstado.setForeground(MUTED);
        txtNombre.requestFocusInWindow();
    }

    private void volverAlMenu() {
        new FrmMenuPrincipal().setVisible(true);
        dispose();
    }
}
