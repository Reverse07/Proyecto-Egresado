package Vista;

import Dao.UsuarioDAO;
import Modelo.Conexion;
import Modelo.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginForm extends javax.swing.JFrame {
   private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private JButton btnIngresar;
    private JComboBox<String> cmbRol; // ✅ ComboBox para rol

    private UsuarioDAO usuariodao;
    public static Usuario usuarioLogueado; // 🔑 Usuario actual logueado

    public LoginForm() {
        // Inicializa conexión y DAO
        Connection conexion = Conexion.conectar();
        usuariodao = new UsuarioDAO();

        initComponents2();
        setTitle("Inicio de Sesión - Sistema de Egresados");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void initComponents2() {
        // Panel principal con fondo
        JPanel panelPrincipal = new JPanel(new BorderLayout()) {
            private Image backgroundImage = new ImageIcon(getClass().getResource("/img/fondo_login.jpg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panelPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Panel transparente para el formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false);

        // Título
        JLabel lblTitulo = new JLabel("Iniciar Sesión", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelFormulario.add(lblTitulo, gbc);

        // Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblUsuario.setForeground(Color.WHITE);

        txtUsuario = new JTextField(20);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        gbc.gridy++;
        gbc.gridwidth = 1;
        panelFormulario.add(lblUsuario, gbc);

        gbc.gridx = 1;
        panelFormulario.add(txtUsuario, gbc);

        // Contraseña
        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblContraseña.setForeground(Color.WHITE);

        txtContraseña = new JPasswordField(20);
        txtContraseña.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy++;
        panelFormulario.add(lblContraseña, gbc);

        gbc.gridx = 1;
        panelFormulario.add(txtContraseña, gbc);

        // Rol
        JLabel lblRol = new JLabel("Rol:");
        lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblRol.setForeground(Color.WHITE);

        cmbRol = new JComboBox<>(new String[]{"admin", "egresado"});
        cmbRol.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy++;
        panelFormulario.add(lblRol, gbc);

        gbc.gridx = 1;
        panelFormulario.add(cmbRol, gbc);

        // Botón ingresar
        btnIngresar = crearBoton("Ingresar", new Color(46, 204, 113), Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(btnIngresar, gbc);

        // Agregar formulario al panel principal
        panelPrincipal.add(panelFormulario);

        setContentPane(panelPrincipal);

        // Evento del botón ingresar
        btnIngresar.addActionListener(e -> autenticarUsuario());
    }

    private JButton crearBoton(String texto, Color bgColor, Color fgColor) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setBackground(bgColor);
        boton.setForeground(fgColor);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(bgColor);
            }
        });
        return boton;
    }

    private void autenticarUsuario() {
        String username = txtUsuario.getText().trim();
        String password = String.valueOf(txtContraseña.getPassword()).trim();
        String rolSeleccionado = (String) cmbRol.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Por favor, completa todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario usuario = usuariodao.autenticar(username, password);
            if (usuario != null && usuario.getRol().equalsIgnoreCase(rolSeleccionado)) {
                // ✅ Guardar usuario logueado para uso global
                usuarioLogueado = usuario;

                JOptionPane.showMessageDialog(this, "✅ Bienvenido " + usuario.getNombreUsuario() + " (" + usuario.getRol() + ")");
                abrirPanelPorRol(usuario.getRol());
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Usuario, contraseña o rol incorrectos.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error al autenticar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void abrirPanelPorRol(String rol) {
        if (rol.equalsIgnoreCase("admin")) {
            new MenuPrincipal().setVisible(true);
        } else if (rol.equalsIgnoreCase("egresado")) {
            new PanelEgresado().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Rol no reconocido: " + rol);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
