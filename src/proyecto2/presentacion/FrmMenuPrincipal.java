package proyecto2.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FrmMenuPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Color BACKGROUND = new Color(243, 246, 251);
    private static final Color PANEL = new Color(255, 255, 255);
    private static final Color PRIMARY = new Color(26, 115, 232);
    private static final Color PRIMARY_DARK = new Color(14, 84, 170);
    private static final Color TEXT = new Color(33, 37, 41);
    private static final Color MUTED = new Color(108, 117, 125);

    public FrmMenuPrincipal() {
        initComponents();
        configurarVentana();
    }

    private void configurarVentana() {
        setTitle("Sistema de Nomina - Menu Principal");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1180, 760));
        setSize(1320, 860);
        setLocationRelativeTo(null);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void initComponents() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(24, 24, 24, 24));

        JPanel card = new JPanel(new BorderLayout(24, 0));
        card.setBackground(PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 226, 234)),
                new EmptyBorder(28, 28, 28, 28)
        ));

        card.add(buildSidePanel(), BorderLayout.WEST);
        card.add(buildOptionsPanel(), BorderLayout.CENTER);

        content.add(card, BorderLayout.CENTER);
        setContentPane(content);
    }

    private JPanel buildSidePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(320, 0));
        panel.setBackground(PRIMARY_DARK);
        panel.setBorder(new EmptyBorder(32, 28, 32, 28));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel badge = new JLabel("PANEL SIMPLE");
        badge.setOpaque(true);
        badge.setBackground(new Color(255, 255, 255, 40));
        badge.setForeground(Color.WHITE);
        badge.setFont(new Font("SansSerif", Font.BOLD, 12));
        badge.setBorder(new EmptyBorder(8, 12, 8, 12));
        badge.setAlignmentX(LEFT_ALIGNMENT);

        JLabel title = new JLabel("<html>Selecciona la<br>interfaz que<br>vas a usar</html>");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setAlignmentX(LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("<html>El sistema queda dividido en dos partes para que trabajes mas rapido.</html>");
        subtitle.setForeground(new Color(224, 231, 255));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitle.setAlignmentX(LEFT_ALIGNMENT);

        panel.add(badge);
        panel.add(Box.createVerticalStrut(28));
        panel.add(title);
        panel.add(Box.createVerticalStrut(18));
        panel.add(subtitle);
        panel.add(Box.createVerticalGlue());
        panel.add(createPoint("Empleado", "Usa la ventana de nomina y correo ya existente."));
        panel.add(Box.createVerticalStrut(12));
        panel.add(createPoint("Patrono", "Guarda informacion basica del patrono."));
        panel.add(Box.createVerticalStrut(12));
        panel.add(createPoint("Login", "Puedes cerrar sesion y volver al acceso."));

        return panel;
    }

    private JPanel createPoint(String title, String detail) {
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

    private JPanel buildOptionsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        panel.add(createOptionCard(
                "Empleado y Correo",
                "Abre la interfaz actual para capturar datos del empleado, generar el PDF y enviarlo por correo.",
                "Abrir empleado",
                () -> {
                    new FrmNomina().setVisible(true);
                    dispose();
                }
        ), gbc);

        gbc.gridx = 1;
        panel.add(createOptionCard(
                "Patrono",
                "Abre una ventana simple para registrar nombre, cedula juridica, telefono y correo del patrono.",
                "Abrir patrono",
                () -> {
                    new FrmPatrono().setVisible(true);
                    dispose();
                }
        ), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0.0;
        panel.add(createFooterActions(), gbc);

        return panel;
    }

    private JPanel createOptionCard(String title, String detail, String buttonText, Runnable action) {
        JPanel card = new JPanel(new BorderLayout(0, 18));
        card.setBackground(PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 226, 234)),
                new EmptyBorder(26, 26, 26, 26)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(TEXT);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));

        JLabel lblDetail = new JLabel("<html>" + detail + "</html>");
        lblDetail.setForeground(MUTED);
        lblDetail.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JButton button = new JButton(buttonText);
        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(12, 18, 12, 18));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(evt -> action.run());

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(lblTitle);
        top.add(Box.createVerticalStrut(12));
        top.add(lblDetail);

        card.add(top, BorderLayout.CENTER);
        card.add(button, BorderLayout.SOUTH);
        return card;
    }

    private JPanel createFooterActions() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel info = new JLabel("Primero inicia sesion y luego abre la ventana que necesites.");
        info.setForeground(MUTED);
        info.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JButton btnCerrarSesion = new JButton("Cerrar sesion");
        btnCerrarSesion.setBackground(new Color(237, 242, 247));
        btnCerrarSesion.setForeground(TEXT);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnCerrarSesion.setBorder(new EmptyBorder(10, 16, 10, 16));
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrarSesion.addActionListener(evt -> {
            new FrmLogin().setVisible(true);
            dispose();
        });

        panel.add(info, BorderLayout.WEST);
        panel.add(btnCerrarSesion, BorderLayout.EAST);
        return panel;
    }
}
