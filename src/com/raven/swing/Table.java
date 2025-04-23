package com.raven.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import javax.swing.border.LineBorder;

public class Table extends JTable {

    public Table() {
        setTableHeader(createCustomTableHeader());
        setRowHeight(25); // Mengatur tinggi baris

        // Renderer untuk isi tabel agar teks rata tengah
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        cellRenderer.setBorder(new LineBorder(Color.GRAY)); // Border di setiap sel
        setDefaultRenderer(Object.class, cellRenderer);

        // Efek hover
//        setSelectionBackground(new Color(200, 230, 255)); // Warna hover
//        setSelectionForeground(Color.BLACK);
//        
        getTableHeader().setReorderingAllowed(false);
    }

    private JTableHeader createCustomTableHeader() {
        JTableHeader header = super.getTableHeader();
        header.setDefaultRenderer(new CustomHeaderRenderer());
        return header;
    }

    private static class CustomHeaderRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            JLabel label = new JLabel(value.toString());
            label.setOpaque(true);
            label.setBackground(new Color(36, 104, 155)); // Warna biru
            label.setForeground(Color.WHITE); // Teks putih
            label.setFont(new Font("SansSerif", Font.BOLD, 14)); // Font header
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(new LineBorder(Color.GRAY)); // Border header
            return label;
        }
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);

        // Striping rows (warna alternatif)
        if (!isRowSelected(row)) {
            c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
        }

        // Border setiap sel
        if (c instanceof JComponent) {
            ((JComponent) c).setBorder(new LineBorder(Color.GRAY));
        }

        return c;
    }

    // Menambahkan panel dengan border radius
    public JPanel withRoundedCorners(int cornerRadius) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE); // Latar belakang putih
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
                g2.setColor(Color.GRAY); // Border abu-abu
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);
        panel.add(new JScrollPane(this), BorderLayout.CENTER);
        return panel;
    }

    // Menjadikan tabel non-editable
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
