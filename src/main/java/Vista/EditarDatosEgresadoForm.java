package Vista;

import Dao.EgresadoDAO;
import Modelo.Carrera;
import Modelo.Egresado;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditarDatosEgresadoForm extends javax.swing.JDialog {

    private JTextField txtNombres, txtApellidos, txtCorreo, txtTelefono, txtDireccion;
    private JComboBox<Carrera> cmbCarrera;
    private JButton btnGuardar, btnCancelar;

    private Egresado egresadoActual;
    private EgresadoDAO egresadoDAO;

    public EditarDatosEgresadoForm(JFrame parent, Egresado egresado) {
        super(parent, "✏️ Editar Datos del Egresado", true);
        this.egresadoActual = egresado;
        this.egresadoDAO = new EgresadoDAO();

        initComponents2();
        cargarDatosEgresado();
        setSize(500, 550); // Tamaño mejorado
        setLocationRelativeTo(parent);
    }

    private void initComponents2() {
        // 🌟 Panel principal con fondo
        JPanel panelFondo = new JPanel(new GridBagLayout()) {
            private final Image backgroundImage = new ImageIcon(getClass().getResource("/img/fondo2.jpg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 📝 Panel contenedor semi-transparente
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(255, 255, 255, 200)); // Semi-transparente
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Nombres
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(crearEtiqueta("👤 Nombres:"), gbc);
        txtNombres = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtNombres, gbc);

        // Apellidos
        gbc.gridx = 0;
        gbc.gridy++;
        panelFormulario.add(crearEtiqueta("👤 Apellidos:"), gbc);
        txtApellidos = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtApellidos, gbc);

        // Correo
        gbc.gridx = 0;
        gbc.gridy++;
        panelFormulario.add(crearEtiqueta("📧 Correo:"), gbc);
        txtCorreo = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtCorreo, gbc);

        // Teléfono
        gbc.gridx = 0;
        gbc.gridy++;
        panelFormulario.add(crearEtiqueta("📱 Teléfono:"), gbc);
        txtTelefono = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtTelefono, gbc);

        // Dirección
        gbc.gridx = 0;
        gbc.gridy++;
        panelFormulario.add(crearEtiqueta("🏠 Dirección:"), gbc);
        txtDireccion = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtDireccion, gbc);

        // Carrera
        gbc.gridx = 0;
        gbc.gridy++;
        panelFormulario.add(crearEtiqueta("🎓 Carrera:"), gbc);
        cmbCarrera = new JComboBox<>();
        gbc.gridx = 1;
        panelFormulario.add(cmbCarrera, gbc);

        // 🔘 Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBotones.setOpaque(false);
        btnGuardar = crearBotonVerde("💾 Guardar Cambios");
        btnCancelar = crearBotonRojo("❌ Cancelar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        // Agregar panel formulario sobre fondo
        panelFondo.add(panelFormulario);
        setContentPane(panelFondo);

        // 🎯 Eventos
        btnGuardar.addActionListener(e -> guardarCambios());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void cargarDatosEgresado() {
        txtNombres.setText(egresadoActual.getNombres());
        txtApellidos.setText(egresadoActual.getApellidos());
        txtCorreo.setText(egresadoActual.getCorreo());
        txtTelefono.setText(egresadoActual.getTelefono());
        txtDireccion.setText(egresadoActual.getDireccion());

        List<Carrera> carreras = egresadoDAO.obtenerTodasLasCarreras();
        for (Carrera carrera : carreras) {
            cmbCarrera.addItem(carrera);
        }
        cmbCarrera.setSelectedItem(egresadoActual.getCarrera());
    }

    private void guardarCambios() {
        egresadoActual.setNombres(txtNombres.getText().trim());
        egresadoActual.setApellidos(txtApellidos.getText().trim());
        egresadoActual.setCorreo(txtCorreo.getText().trim());
        egresadoActual.setTelefono(txtTelefono.getText().trim());
        egresadoActual.setDireccion(txtDireccion.getText().trim());
        egresadoActual.setCarrera((Carrera) cmbCarrera.getSelectedItem());

        boolean actualizado = egresadoDAO.actualizarEgresado(egresadoActual);
        if (actualizado) {
            JOptionPane.showMessageDialog(this, "✅ Datos actualizados correctamente.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "❌ No se pudieron actualizar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(new Color(44, 62, 80)); // Azul oscuro
        return label;
    }

    private JButton crearBotonVerde(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(46, 204, 113));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        return boton;
    }

    private JButton crearBotonRojo(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(231, 76, 60));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        return boton;
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
