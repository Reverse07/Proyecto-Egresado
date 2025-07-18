package Vista;

import Dao.EgresadoDAO;
import Modelo.Conexion;
import Modelo.Egresado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.sql.Connection;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class PanelEgresado extends javax.swing.JFrame {

    private JLabel lblTitulo, lblBienvenida;
    private JButton btnVerPerfil, btnEditarDatos, btnCerrarSesion;
    private JPanel panelBotones, panelContenido;

    private EgresadoDAO egresadoDAO;
    private Egresado egresadoActual;

    public PanelEgresado() {
        setTitle("Panel de Egresado");
        setSize(800, 500); // ✅ Tamaño más amplio
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true); // ✅ Ahora es redimensionable

        // 🔥 Obtener datos del egresado logueado
        egresadoDAO = new EgresadoDAO();
        egresadoActual = egresadoDAO.obtenerPorUsuario(LoginForm.usuarioLogueado.getId());

        initComponents2();
    }

    private void initComponents2() {
        // ✅ Panel principal con fondo
        JPanel panelPrincipal = new JPanel(new BorderLayout()) {
            private final Image backgroundImage = new ImageIcon(getClass().getResource("/img/dashboard.jpg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // 🌟 Panel superior con título
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        lblTitulo = new JLabel("🎓 Panel del Egresado", JLabel.LEFT);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);

        lblBienvenida = new JLabel("👋 Bienvenido, " + LoginForm.usuarioLogueado.getNombreUsuario(), JLabel.RIGHT);
        lblBienvenida.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblBienvenida.setForeground(Color.WHITE);

        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(lblBienvenida, BorderLayout.EAST);

        // 🟦 Panel lateral con botones
        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
        panelBotones.setBackground(new Color(255, 255, 255, 180)); // semi-transparente

        btnVerPerfil = crearBoton("👤 Ver Perfil");
        btnEditarDatos = crearBoton("✏️ Editar Datos");
        btnCerrarSesion = crearBoton("🚪 Cerrar Sesión");

        panelBotones.add(btnVerPerfil);
        panelBotones.add(btnEditarDatos);
        panelBotones.add(Box.createVerticalGlue()); // espacio vacío
        panelBotones.add(btnCerrarSesion);

        // 🟣 Panel central dinámico para mostrar contenido
        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setOpaque(false);

        // 🎯 Eventos
        btnVerPerfil.addActionListener(e -> mostrarPerfil());
        btnEditarDatos.addActionListener(e -> {
            if (egresadoActual != null) {
                EditarDatosEgresadoForm editarForm = new EditarDatosEgresadoForm(this, egresadoActual);
                editarForm.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ No se pudieron cargar los datos del egresado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCerrarSesion.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "👋 Sesión cerrada correctamente.");
            dispose();
            new LoginForm().setVisible(true);
        });

        // Agregar componentes al panel principal
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.WEST);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);

        setContentPane(panelPrincipal);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setBackground(new Color(41, 128, 185));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        // Hover effect
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(31, 97, 141)); // Azul más oscuro
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(41, 128, 185)); // Azul original
            }
        });
        return boton;
    }

    /**
     * ✅ Mostrar perfil del egresado (responsive)
     */
    private void mostrarPerfil() {
        panelContenido.removeAll(); // Limpiar el panel central

        if (egresadoActual == null) {
            egresadoActual = egresadoDAO.obtenerPorUsuario(LoginForm.usuarioLogueado.getId());
        }

        if (egresadoActual != null) {
            // 🌟 Panel con fondo
            JPanel panelPerfil = new JPanel(new BorderLayout()) {
                private final Image backgroundImage = new ImageIcon(getClass().getResource("/img/fondo2.jpg")).getImage();

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            };
            panelPerfil.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // 📷 Foto de perfil
            JLabel lblFoto = new JLabel();
            lblFoto.setPreferredSize(new Dimension(150, 150));
            lblFoto.setHorizontalAlignment(JLabel.CENTER);
            lblFoto.setVerticalAlignment(JLabel.CENTER);
            try {
                ImageIcon icono = new ImageIcon(getClass().getResource("/img/perfil.png")); // Ruta de la foto por defecto
                lblFoto.setIcon(redimensionarIcono(icono, 150, 150));
            } catch (Exception ex) {
                lblFoto.setText("👤");
                lblFoto.setFont(new Font("Segoe UI", Font.BOLD, 72));
            }

            // 📝 Datos del egresado con ajuste automático
            JPanel panelDatos = new JPanel();
            panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS)); // Vertical
            panelDatos.setOpaque(false);

            panelDatos.add(crearEtiquetaResponsive("👤 Nombre: " + egresadoActual.getNombres() + " " + egresadoActual.getApellidos()));
            panelDatos.add(crearEtiquetaResponsive("🆔 DNI: " + egresadoActual.getDni()));
            panelDatos.add(crearEtiquetaResponsive("📧 Correo: " + egresadoActual.getCorreo()));
            panelDatos.add(crearEtiquetaResponsive("📱 Teléfono: " + egresadoActual.getTelefono()));
            panelDatos.add(crearEtiquetaResponsive("🏠 Dirección: " + egresadoActual.getDireccion()));
            panelDatos.add(crearEtiquetaResponsive("🎓 Carrera: " + egresadoActual.getCarrera().getNombre()));
            panelDatos.add(crearEtiquetaResponsive("📅 Fecha de egreso: " + egresadoActual.getFechaEgreso()));

            // 🌟 Scroll para datos largos
            JScrollPane scrollPane = new JScrollPane(panelDatos);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);
            scrollPane.setBorder(null); // Sin borde feo

            // 📦 Contenedor para foto y datos
            JPanel panelCentral = new JPanel(new BorderLayout(20, 0));
            panelCentral.setOpaque(false);
            panelCentral.add(lblFoto, BorderLayout.WEST);
            panelCentral.add(scrollPane, BorderLayout.CENTER);

            panelPerfil.add(panelCentral, BorderLayout.CENTER);

            // 👉 Mostrar en panel central
            panelContenido.add(panelPerfil, BorderLayout.CENTER);
            panelContenido.revalidate();
            panelContenido.repaint();

        } else {
            JOptionPane.showMessageDialog(this, "⚠️ No se pudieron cargar los datos del egresado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel crearEtiquetaResponsive(String texto) {
        JLabel label = new JLabel("<html><div style='width: 100%;'>" + texto + "</div></html>");
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(Color.WHITE); // ✅ Texto blanco visible sobre fondo oscuro
        return label;
    }

    private Icon redimensionarIcono(ImageIcon icono, int ancho, int alto) {
        Image img = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
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
