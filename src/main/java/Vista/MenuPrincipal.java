package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MenuPrincipal extends javax.swing.JFrame {

  private JPanel panelLateral, panelContenido;
    private JButton btnEgresados, btnReportes, btnUsuarios, btnSalir;
    private JLabel lblTitulo;

    public MenuPrincipal() {
        initComponents2();
        setSize(1000, 700); // 🖥️ Tamaño inicial más amplio
        setLocationRelativeTo(null); // Centrar ventana
        setResizable(true); // ✅ Ahora se puede redimensionar
        setTitle("Sistema de Gestión de Egresados");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents2() {
        // Layout base
        getContentPane().setLayout(new BorderLayout());

        // Panel lateral
        panelLateral = new JPanel();
        panelLateral.setBackground(new Color(33, 47, 61));
        panelLateral.setPreferredSize(new Dimension(220, getHeight()));
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));

        // Botones del menú lateral
        btnEgresados = crearBoton("📋 Egresados");
        btnReportes = crearBoton("📊 Reportes");
        btnUsuarios = crearBoton("👤 Usuarios");
        btnSalir = crearBoton("🚪 Salir");

        panelLateral.add(Box.createVerticalStrut(40));
        panelLateral.add(btnEgresados);
        panelLateral.add(Box.createVerticalStrut(15));
        panelLateral.add(btnReportes);
        panelLateral.add(Box.createVerticalStrut(15));
        panelLateral.add(btnUsuarios);
        panelLateral.add(Box.createVerticalGlue());
        panelLateral.add(btnSalir);
        panelLateral.add(Box.createVerticalStrut(20));

        // Panel de contenido (fluido)
        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(new Color(236, 240, 241));

        // Título de bienvenida
        lblTitulo = new JLabel("Bienvenido al Sistema de Egresados", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(52, 73, 94));
        panelContenido.add(lblTitulo, BorderLayout.CENTER);

        // Agregar paneles al frame
        getContentPane().add(panelLateral, BorderLayout.WEST);
        getContentPane().add(panelContenido, BorderLayout.CENTER);

        // Eventos de botones
        btnEgresados.addActionListener(e -> mostrarSubMenuEgresados());
        btnUsuarios.addActionListener(e -> mostrarSubMenuUsuarios());
        btnReportes.addActionListener(e -> mostrarSubMenuReportes());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(200, 45));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setBackground(new Color(41, 128, 185)); // Azul elegante
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(31, 97, 141)); // Azul más oscuro
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(41, 128, 185)); // Color original
            }
        });

        return boton;
    }

    private void mostrarSubMenuEgresados() {
        // Menú con dos opciones: registrar y buscar
        Object[] opciones = {"➕ Registrar Egresado", "🔎 Buscar Egresados"};
        int seleccion = JOptionPane.showOptionDialog(
                this, "Elige una opción para egresados:",
                "📋 Egresados",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);

        if (seleccion == 0) {
            cargarFormulario(new RegistroEgresadoForm());
        } else if (seleccion == 1) {
            cargarFormulario(new BusquedaEgresadoForm());
        }
    }

    private void mostrarSubMenuUsuarios() {
        // Menú con dos opciones: registrar y buscar
        Object[] opciones = {"➕ Registrar Usuario", "🔎 Buscar Usuarios"};
        int seleccion = JOptionPane.showOptionDialog(
                this, "Elige una opción para usuarios:",
                "👤 Usuarios",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);

        if (seleccion == 0) {
            cargarFormulario(new RegistroUsuarioForm());
        } else if (seleccion == 1) {
            cargarFormulario(new BusquedaUsuarioForm());
        }
    }

    private void mostrarSubMenuReportes() {
        // Menú con dos opciones: reportes de egresados y usuarios
        Object[] opciones = {"📊 Reportes Egresados", "📊 Reportes Usuarios"};
        int seleccion = JOptionPane.showOptionDialog(
                this, "Elige un tipo de reporte:",
                "📊 Reportes",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);

        if (seleccion == 0) {
            cargarFormulario(new ReporteEgresadosForm());
        } else if (seleccion == 1) {
            cargarFormulario(new ReporteUsuariosForm());
        }
    }

    private void cargarFormulario(JPanel formulario) {
        panelContenido.removeAll();
        JScrollPane scrollPane = new JScrollPane(formulario);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Desplazamiento suave
        panelContenido.add(scrollPane, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

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
