/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.tablemodel;

import com.app.model.ModelBarang;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;

/**
 *
 * @author Rohim
 */
public class TableModelBarang extends AbstractTableModel {

    private List<ModelBarang> list = new ArrayList<>();

    public ModelBarang getData(int index) {
        return list.get(index);
    }

    public void clear() {
        list.clear();
        fireTableDataChanged();
    }

    public void setData(List<ModelBarang> list) {
        clear();
        this.list.addAll(list);
        fireTableDataChanged();
    }

    public void insertData(ModelBarang model) {
        list.add(model);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
        JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan");
    }

    public void updateData(int row, ModelBarang model) {
        list.set(row, model);
        fireTableDataChanged();
        JOptionPane.showMessageDialog(null, "Data Berhasil Diperbarui");
    }

    public void deleteData(int row) {
        list.remove(row);
        fireTableRowsDeleted(row, row);
        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
    }

    private final String[] columnNames = {"ID", "Nama Barang", "Satuan", "Stok"};

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ModelBarang model = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return model.getNama_barang();
            case 3:
                return model.getStok();
            case 2:
                return model.getSatuan();
            default:
                return null;
        }
    }

    public String getFormattedDetail(int index) {
        ModelBarang model = list.get(index);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        StringBuilder detail = new StringBuilder();
        detail.append("ID Barang   : ").append(model.getIdBarang()).append("\n");
        detail.append("Nama Barang : ").append(model.getNama_barang()).append("\n");
        detail.append("Satuan      : ").append(model.getSatuan()).append("\n");
        detail.append("Stok        : ").append(model.getStok()).append("\n");
        
        return detail.toString();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
