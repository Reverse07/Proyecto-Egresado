package Vista;

import Dao.EgresadoDAO;
import Modelo.Carrera;
import Modelo.Conexion;
import Modelo.Egresado;
import Modelo.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class RegistroEgresadoForm extends JPanel {

    private JTextField txtNombres, txtApellidos, txtDNI, txtCorreo, txtTelefono, txtDireccion,
            txtUniversidad, txtAnioEgreso, txtEmpresa, txtPuesto, txtSalario, txtFechaNacimiento;
    private JComboBox<String> cmbCarrera;
    private JComboBox<String> cmbSexo; // 🔥 Corregido
    private JButton btnGuardar, btnCancelar;

    private final EgresadoDAO egresadoDAO;

    public RegistroEgresadoForm() {
        // Inicializar DAO
        egresadoDAO = new EgresadoDAO();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // 🌟 Título
        JLabel lblTitulo = new JLabel("📋 Registro de Egresado", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(41, 128, 185));
        lblTitulo.setBorder(new EmptyBorder(20, 10, 20, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // 🌟 Panel del formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(new EmptyBorder(20, 40, 20, 40));

        JScrollPane scrollPane = new JScrollPane(panelFormulario);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 15);
        Color labelColor = new Color(52, 73, 94);

        // 🌟 Labels y campos
        String[] labels = {
            "Nombres:", "Apellidos:", "DNI:", "Correo:", "Teléfono:", "Dirección:",
            "Sexo:", "Carrera:", "Universidad:", "Año de Egreso:", "Empresa:",
            "Puesto:", "Salario:", "Fecha Nacimiento (YYYY-MM-DD):"
        };

        JTextField[] textFields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;

            JLabel label = new JLabel(labels[i]);
            label.setFont(labelFont);
            label.setForeground(labelColor);
            panelFormulario.add(label, gbc);

            gbc.gridx = 1;

            if (labels[i].equals("Sexo:")) {
                // 🔥 ComboBox para Sexo
                cmbSexo = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
                cmbSexo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                cmbSexo.setBackground(Color.WHITE);
                panelFormulario.add(cmbSexo, gbc);
            } else if (labels[i].equals("Carrera:")) {
                // 🔥 ComboBox para Carrera
                cmbCarrera = new JComboBox<>();
                cargarCarreras();
                cmbCarrera.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                cmbCarrera.setBackground(Color.WHITE);
                panelFormulario.add(cmbCarrera, gbc);
            } else {
                // 🔥 Campos de texto
                textFields[i] = new JTextField(30);
                textFields[i].setFont(new Font("Segoe UI", Font.PLAIN, 15));
                textFields[i].setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                        BorderFactory.createEmptyBorder(5, 8, 5, 8)
                ));
                panelFormulario.add(textFields[i], gbc);
            }
        }

        // 🌟 Asignación de campos
        txtNombres = textFields[0];
        txtApellidos = textFields[1];
        txtDNI = textFields[2];
        txtCorreo = textFields[3];
        txtTelefono = textFields[4];
        txtDireccion = textFields[5];
        txtUniversidad = textFields[8];
        txtAnioEgreso = textFields[9];
        txtEmpresa = textFields[10];
        txtPuesto = textFields[11];
        txtSalario = textFields[12];
        txtFechaNacimiento = textFields[13];

        // 🌟 Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(Color.WHITE);

        btnGuardar = crearBotonEstilizado("💾 Guardar", new Color(46, 204, 113), Color.WHITE);
        btnCancelar = crearBotonEstilizado("❌ Cancelar", new Color(231, 76, 60), Color.WHITE);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // 🌟 Eventos
        btnGuardar.addActionListener(this::guardarEgresado);
        btnCancelar.addActionListener(e -> cancelarRegistro());
    }

    private void cargarCarreras() {
        List<Carrera> listaCarreras = egresadoDAO.obtenerTodasLasCarreras();

        cmbCarrera.removeAllItems();
        for (Carrera carrera : listaCarreras) {
            cmbCarrera.addItem(carrera.getNombre());
        }
    }

    private JButton crearBotonEstilizado(String texto, Color bgColor, Color fgColor) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setBackground(bgColor);
        boton.setForeground(fgColor);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

    private void guardarEgresado(ActionEvent evt) {
        try {
            if (txtNombres.getText().trim().isEmpty() || txtApellidos.getText().trim().isEmpty()
                    || txtDNI.getText().trim().isEmpty() || txtCorreo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Completa todos los campos obligatorios.",
                        "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Date fechaNacimiento = Date.valueOf(txtFechaNacimiento.getText().trim());
            Date fechaEgreso = Date.valueOf(txtAnioEgreso.getText().trim() + "-12-31");

            Carrera carreraSeleccionada = egresadoDAO.obtenerCarreraPorNombre((String) cmbCarrera.getSelectedItem());

            if (carreraSeleccionada == null) {
                JOptionPane.showMessageDialog(this, "❌ No se encontró la carrera seleccionada.",
                        "Error Carrera", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario usuarioActual = LoginForm.usuarioLogueado;
            if (usuarioActual == null) {
                JOptionPane.showMessageDialog(this, "❌ No hay un usuario logueado. Inicia sesión nuevamente.",
                        "Error Usuario", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Egresado egresado = new Egresado();
            egresado.setNombres(txtNombres.getText().trim());
            egresado.setApellidos(txtApellidos.getText().trim());
            egresado.setDni(txtDNI.getText().trim());
            egresado.setCorreo(txtCorreo.getText().trim());
            egresado.setTelefono(txtTelefono.getText().trim());
            egresado.setFechaNacimiento(fechaNacimiento);
            // ✅ Obtener sexo seleccionado (solo inicial)
            String sexoSeleccionado = (String) cmbSexo.getSelectedItem();
            egresado.setSexo(sexoSeleccionado.substring(0, 1)); // Solo "M", "F" u "O"

            egresado.setDireccion(txtDireccion.getText().trim());
            egresado.setCarrera(carreraSeleccionada);
            egresado.setFechaEgreso(fechaEgreso);
            egresado.setUsuario(usuarioActual);

            boolean resultado = egresadoDAO.insertarEgresado(egresado);

            if (resultado) {
                JOptionPane.showMessageDialog(this, "✅ Egresado registrado correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo registrar el egresado.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error inesperado: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void cancelarRegistro() {
        JPanel parent = (JPanel) this.getParent();
        parent.removeAll();
        JLabel lblBienvenida = new JLabel("Bienvenido al Sistema de Egresados", JLabel.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblBienvenida.setForeground(new Color(52, 73, 94));
        parent.add(lblBienvenida, BorderLayout.CENTER);
        parent.revalidate();
        parent.repaint();
    }

    private void limpiarCampos() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtDNI.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtUniversidad.setText("");
        txtAnioEgreso.setText("");
        txtEmpresa.setText("");
        txtPuesto.setText("");
        txtSalario.setText("");
        txtFechaNacimiento.setText("");
        cmbCarrera.setSelectedIndex(0);
        cmbSexo.setSelectedIndex(0); // 🔥 Limpia la selección de sexo
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
