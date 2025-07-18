
package Vista;

import Dao.UsuarioDAO;
import Modelo.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class BusquedaUsuarioForm extends javax.swing.JPanel {

 private JTextField txtBuscar;
    private JButton btnBuscar, btnLimpiar;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private UsuarioDAO usuarioDAO;

    public BusquedaUsuarioForm() {
        this.usuarioDAO = new UsuarioDAO();
        initComponents2();
    }

    private void initComponents2() {
        // 🌟 Panel principal con fondo (Glassmorphism)
        this.setLayout(new BorderLayout());
        JPanel panelFondo = new JPanel(new BorderLayout()) {
            private final Image backgroundImage = new ImageIcon(getClass().getResource("/img/fondo2.jpg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panelFondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 📝 Panel superior con barra de búsqueda
        JPanel panelBusqueda = new JPanel(new BorderLayout(10, 10));
        panelBusqueda.setBackground(new Color(255, 255, 255, 180)); // Semi-transparente
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtBuscar = new JTextField();
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtBuscar.setBorder(BorderFactory.createTitledBorder("🔍 Buscar usuario por nombre o username"));

        btnBuscar = crearBotonAzul("🔍 Buscar");
        btnLimpiar = crearBotonRojo("🧹 Limpiar");

        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);

        panelBusqueda.add(panelBotones, BorderLayout.EAST);

        // 📋 Tabla de resultados
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Username", "Rol"}, 0
        );
        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaResultados.setRowHeight(25);
        tablaResultados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        tablaResultados.setGridColor(new Color(189, 195, 199));

        JScrollPane scrollTabla = new JScrollPane(tablaResultados);
        scrollTabla.setOpaque(false);
        scrollTabla.getViewport().setOpaque(false);

        // 🎯 Eventos
        btnBuscar.addActionListener(e -> buscarUsuarios());
        btnLimpiar.addActionListener(e -> limpiarBusqueda());
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarUsuarios(); // 🔥 Búsqueda en tiempo real
            }
        });

        // ➕ Agregar al panel principal
        panelFondo.add(panelBusqueda, BorderLayout.NORTH);
        panelFondo.add(scrollTabla, BorderLayout.CENTER);

        this.add(panelFondo, BorderLayout.CENTER);
    }

    private void buscarUsuarios() {
        String criterio = txtBuscar.getText().trim();
        modeloTabla.setRowCount(0); // Limpiar tabla

        List<Usuario> resultados = usuarioDAO.buscarUsuariosPorNombreOUsername(criterio);
        for (Usuario u : resultados) {
            modeloTabla.addRow(new Object[]{
                u.getId(),
                u.getNombreUsuario(),
                u.getRol()
            });
        }
    }

    private void limpiarBusqueda() {
        txtBuscar.setText("");
        modeloTabla.setRowCount(0);
    }

    private JButton crearBotonAzul(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(41, 128, 185));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(31, 97, 141));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(41, 128, 185));
            }
        });
        return boton;
    }

    private JButton crearBotonRojo(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(231, 76, 60));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(192, 57, 43));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(231, 76, 60));
            }
        });
        return boton;
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
