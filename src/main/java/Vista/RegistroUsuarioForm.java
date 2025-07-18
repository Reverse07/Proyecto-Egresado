
package Vista;

import Dao.UsuarioDAO;
import Modelo.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegistroUsuarioForm extends javax.swing.JPanel {
  
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRol;
    private JButton btnRegistrar, btnLimpiar;

    public RegistroUsuarioForm() {
        initComponents2();
    }

    private void initComponents2() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 🌟 Panel título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(52, 152, 219));
        JLabel lblTitulo = new JLabel("👤 Registro de Usuarios");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        // 🌟 Panel formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panelFormulario.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("👤 Usuario:"), gbc);
        txtUsername = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtUsername, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(new JLabel("🔑 Contraseña:"), gbc);
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtPassword, gbc);

        // Rol
        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(new JLabel("🎯 Rol:"), gbc);
        cmbRol = new JComboBox<>(new String[]{"admin", "egresado"});
        gbc.gridx = 1;
        panelFormulario.add(cmbRol, gbc);

        // Botones
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.WHITE);
        btnRegistrar = crearBoton("✅ Registrar");
        btnLimpiar = crearBoton("🧹 Limpiar");
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnLimpiar);
        panelFormulario.add(panelBotones, gbc);

        // Eventos
        btnRegistrar.addActionListener(this::registrarUsuario);
        btnLimpiar.addActionListener(e -> limpiarCampos());

        // Agregar paneles al principal
        add(panelTitulo, BorderLayout.NORTH);
        add(panelFormulario, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setBackground(new Color(46, 204, 113));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(39, 174, 96));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(46, 204, 113));
            }
        });

        return boton;
    }


     private void registrarUsuario(ActionEvent e) {
    String username = txtUsername.getText().trim();
    String password = new String(txtPassword.getPassword()).trim();
    String rol = cmbRol.getSelectedItem().toString();

    if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "⚠️ Todos los campos son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
        return;
    }

    Usuario nuevoUsuario = new Usuario();
    nuevoUsuario.setNombreUsuario(username);
    nuevoUsuario.setPassword(password); // Encriptar en el DAO
    nuevoUsuario.setRol(rol);

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    boolean exito = usuarioDAO.insertarUsuario(nuevoUsuario);

    if (exito) {
        JOptionPane.showMessageDialog(this, "✅ Usuario registrado correctamente:\nUsuario: " + username + "\nRol: " + rol);
        limpiarCampos();
    } else {
        JOptionPane.showMessageDialog(this, "❌ Error al registrar el usuario. Verifique la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    private void limpiarCampos() {
        txtUsername.setText("");
        txtPassword.setText("");
        cmbRol.setSelectedIndex(0);
    }


 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
