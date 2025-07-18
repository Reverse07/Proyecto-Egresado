package Vista;

import Dao.EgresadoDAO;
import Modelo.Egresado;
import com.formdev.flatlaf.FlatLightLaf;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ReporteEgresadosForm extends javax.swing.JPanel {

    private JLabel lblTitulo;
    private JTable tablaEgresados;
    private DefaultTableModel modeloTabla;
    private JButton btnExportarPDF, btnExportarExcel, btnActualizar;
    private JPanel panelGrafico;

    private EgresadoDAO egresadoDAO = new EgresadoDAO();

    public ReporteEgresadosForm() {
        // 🌟 Aplicar FlatLaf
        FlatLightLaf.setup();

        initComponents2();
        cargarDatosTabla();
        mostrarGrafico();
    }

    private void initComponents2() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Fondo claro

        // 🌟 Panel superior con título
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(33, 150, 243)); // Azul FlatLaf
        panelSuperior.setBorder(new EmptyBorder(15, 20, 15, 20));

        lblTitulo = new JLabel("📊 Dashboard de Egresados");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);

        panelSuperior.add(lblTitulo, BorderLayout.WEST);

        // 🌟 Panel central con tabla estilizada
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "DNI", "Correo", "Carrera", "Fecha Egreso"}, 0
        );
        tablaEgresados = new JTable(modeloTabla);
        tablaEgresados.setRowHeight(28);
        tablaEgresados.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaEgresados.setShowGrid(false);
        tablaEgresados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        tablaEgresados.getTableHeader().setBackground(new Color(3, 169, 244));
        tablaEgresados.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tablaEgresados);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 🌟 Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBotones.setBackground(new Color(250, 250, 250));

        btnActualizar = crearBoton("🔄 Actualizar", new Color(76, 175, 80));
        btnExportarPDF = crearBoton("📄 Exportar PDF", new Color(244, 67, 54));
        btnExportarExcel = crearBoton("📊 Exportar Excel", new Color(33, 150, 243));

        panelBotones.add(btnActualizar);
        panelBotones.add(btnExportarPDF);
        panelBotones.add(btnExportarExcel);

        // 🌟 Panel derecho para gráficos
        panelGrafico = new JPanel(new BorderLayout());
        panelGrafico.setPreferredSize(new Dimension(400, 0));
        panelGrafico.setBackground(Color.WHITE);

        // Eventos
        btnActualizar.addActionListener(this::actualizarTabla);
        btnExportarPDF.addActionListener(this::exportarPDF);
        btnExportarExcel.addActionListener(this::exportarExcel);

        // Agregar componentes al panel principal
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        add(panelGrafico, BorderLayout.EAST);
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de cargar nuevos datos
        List<Egresado> lista = egresadoDAO.obtenerTodosEgresados();

        for (Egresado e : lista) {
            String carreraNombre = (e.getCarrera() != null) ? e.getCarrera().getNombre() : "Sin carrera";

            modeloTabla.addRow(new Object[]{
                e.getId(),
                e.getNombres() + " " + e.getApellidos(),
                e.getDni(),
                e.getCorreo(),
                carreraNombre,
                e.getFechaEgreso()
            });
        }
    }

    private void mostrarGrafico() {
        panelGrafico.removeAll();

        // Traer datos de egresados por carrera
        Map<String, Integer> datosCarrera = egresadoDAO.contarEgresadosPorCarrera();

        // Crear dataset para el gráfico
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : datosCarrera.entrySet()) {
            dataset.addValue(entry.getValue(), "Egresados", entry.getKey());
        }

        // Crear gráfico de barras
        JFreeChart chart = ChartFactory.createBarChart(
                "Egresados por Carrera", // Título
                "Carrera", // Etiqueta X
                "Cantidad", // Etiqueta Y
                dataset, // Datos
                PlotOrientation.VERTICAL,
                false, true, false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        panelGrafico.setLayout(new BorderLayout());
        panelGrafico.add(chartPanel, BorderLayout.CENTER);
        panelGrafico.validate();
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.putClientProperty("JButton.buttonType", "roundRect"); // FlatLaf estilo
        return boton;
    }

    private void actualizarTabla(ActionEvent e) {
        cargarDatosTabla();
        mostrarGrafico();
        JOptionPane.showMessageDialog(this, "✅ Datos y gráficos actualizados.");
    }

    private void exportarPDF(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte PDF");
        fileChooser.setSelectedFile(new File("ReporteEgresados.pdf"));
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try {
                // 📄 Crear documento PDF
                com.itextpdf.text.Document document = new com.itextpdf.text.Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                document.open();

                // 📝 Título del reporte
                com.itextpdf.text.Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE);
                Paragraph titulo = new Paragraph("📊 Reporte de Egresados\n\n", fontTitulo);
                titulo.setAlignment(Element.ALIGN_CENTER);
                document.add(titulo);

                // 📋 Crear tabla con el mismo número de columnas que JTable
                PdfPTable pdfTable = new PdfPTable(modeloTabla.getColumnCount());
                pdfTable.setWidthPercentage(100);
                pdfTable.setSpacingBefore(10f);
                pdfTable.setSpacingAfter(10f);

                // ✅ Estilos para encabezados
                com.itextpdf.text.Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
                PdfPCell headerCell;

                for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                    headerCell = new PdfPCell(new Phrase(modeloTabla.getColumnName(i), headerFont));
                    headerCell.setBackgroundColor(new BaseColor(52, 152, 219)); // Azul bonito
                    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    headerCell.setPadding(5);
                    pdfTable.addCell(headerCell);
                }

                // ✅ Agregar filas de datos
                com.itextpdf.text.Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);
                for (int row = 0; row < modeloTabla.getRowCount(); row++) {
                    for (int col = 0; col < modeloTabla.getColumnCount(); col++) {
                        String cellValue = String.valueOf(modeloTabla.getValueAt(row, col));
                        PdfPCell dataCell = new PdfPCell(new Phrase(cellValue, cellFont));
                        dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        dataCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        dataCell.setPadding(4);
                        pdfTable.addCell(dataCell);
                    }
                }

                // 📥 Agregar tabla al documento
                document.add(pdfTable);

                // ✅ Cerrar documento
                document.close();

                JOptionPane.showMessageDialog(this, "✅ PDF exportado exitosamente a:\n" + fileToSave.getAbsolutePath());

                // 🚀 Abrir el PDF automáticamente (opcional)
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(fileToSave);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "❌ Error al exportar PDF:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportarExcel(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte Excel");
        fileChooser.setSelectedFile(new File("ReporteEgresados.xlsx"));
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Egresados");

                // Crear encabezados
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(modeloTabla.getColumnName(i));
                }

                // Agregar datos
                for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                    Row dataRow = sheet.createRow(i + 1);
                    for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                        dataRow.createCell(j).setCellValue(modeloTabla.getValueAt(i, j).toString());
                    }
                }

                // Ajustar tamaño de columnas
                for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                    sheet.autoSizeColumn(i);
                }

                // Guardar archivo
                try (FileOutputStream fileOut = new FileOutputStream(fileToSave)) {
                    workbook.write(fileOut);
                }

                JOptionPane.showMessageDialog(this, "✅ Excel exportado exitosamente a:\n" + fileToSave.getAbsolutePath());

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "❌ Error al exportar Excel:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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
