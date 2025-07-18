
package Vista;

import Dao.UsuarioDAO;
import Modelo.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;


public class ReporteUsuariosForm extends javax.swing.JPanel {

   private JLabel lblTitulo;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar, btnExportarPDF, btnExportarExcel;
    private JPanel panelGrafico;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public ReporteUsuariosForm() {
        initComponents2();
        cargarDatosTabla();
        mostrarGrafico();
    }

    private void initComponents2() {
        setLayout(new BorderLayout());

        // 🌟 Panel superior (título)
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(41, 128, 185));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        lblTitulo = new JLabel("📋 Reporte de Usuarios");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);

        panelSuperior.add(lblTitulo, BorderLayout.WEST);

        // 🌟 Panel central (tabla)
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Usuario", "Rol"}, 0
        );
        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);

        // 🌟 Panel inferior (botones)
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(236, 240, 241));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnActualizar = crearBoton("🔄 Actualizar");
        btnExportarPDF = crearBoton("📄 Exportar PDF");
        btnExportarExcel = crearBoton("📊 Exportar Excel");

        panelBotones.add(btnActualizar);
        panelBotones.add(btnExportarPDF);
        panelBotones.add(btnExportarExcel);

        // 🌟 Panel derecho (gráfico)
        panelGrafico = new JPanel();
        panelGrafico.setPreferredSize(new Dimension(350, 0));
        panelGrafico.setLayout(new BorderLayout());

        // 🎯 Eventos
        btnActualizar.addActionListener(this::actualizarTabla);
        btnExportarPDF.addActionListener(this::exportarPDF);
        btnExportarExcel.addActionListener(this::exportarExcel);

        // Agregar todo al panel principal
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        add(panelGrafico, BorderLayout.EAST);
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        List<Usuario> lista = usuarioDAO.obtenerTodosUsuarios();

        for (Usuario u : lista) {
            modeloTabla.addRow(new Object[]{
                    u.getId(),
                    u.getNombreUsuario(),
                    u.getRol()
            });
        }
    }

    private void mostrarGrafico() {
        panelGrafico.removeAll();

        Map<String, Integer> usuariosPorRol = usuarioDAO.contarUsuariosPorRol();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> entry : usuariosPorRol.entrySet()) {
            dataset.addValue(entry.getValue(), "Usuarios", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Usuarios por Rol",
                "Rol",
                "Cantidad",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        panelGrafico.add(chartPanel, BorderLayout.CENTER);
        panelGrafico.revalidate();
        panelGrafico.repaint();
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setBackground(new Color(52, 152, 219));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(52, 152, 219));
            }
        });
        return boton;
    }

    private void actualizarTabla(ActionEvent e) {
        cargarDatosTabla();
        mostrarGrafico();
        JOptionPane.showMessageDialog(this, "✅ Datos actualizados.");
    }

    private void exportarPDF(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "📄 Exportación a PDF (simulada).");
        // Aquí puedes usar iText para exportar
    }

    private void exportarExcel(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "📊 Exportación a Excel (simulada).");
        // Aquí puedes usar Apache POI para exportar
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
