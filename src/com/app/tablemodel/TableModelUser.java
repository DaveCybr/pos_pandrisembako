/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.tablemodel;

import com.app.model.ModelUser;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Rohim
 */
public class TableModelUser extends AbstractTableModel {

    private List<ModelUser> list = new ArrayList<>();

    public ModelUser getData(int index) {
        return list.get(index);
    }

    public void clear() {
        list.clear();
        fireTableDataChanged();
    }

    public void setData(List<ModelUser> list) {
        clear();
        this.list.addAll(list);
        fireTableDataChanged();
    }

    public void insertData(ModelUser model) {
        list.add(model);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
        JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan");
    }

    public void updateData(int row, ModelUser model) {
        list.set(row, model);
        fireTableDataChanged();
        JOptionPane.showMessageDialog(null, "Data Berhasil Diperbarui");
    }

    public void deleteData(int row) {
        list.remove(row);
        fireTableRowsDeleted(row, row);
        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
    }

    private final String[] columnNames = {"ID", "Nama", "Username", "Alamat", "No Telepon", "Role"};

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
        ModelUser model = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return "    " + model.getIdUser();
            case 1:
                return model.getNama();
            case 2:
                return model.getUsername();
            case 3:
                return model.getAlamat();
            case 4:
                return model.getNo_telepon();
            case 5:
                return model.getRole();
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
