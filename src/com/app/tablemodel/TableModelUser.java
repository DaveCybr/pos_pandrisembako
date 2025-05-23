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
import java.text.SimpleDateFormat;

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

    private final String[] columnNames = {"ID", "Nama", "Username", "Role"};

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        switch (columnIndex) {
            case 0:
                return "    " + model.getIdUser();
            case 1:
                return model.getNama();
            case 2:
                return model.getUsername();
            case 3:
                return model.getRole();
            case 4:
                return model.getRfidUid();
            case 5:
                return model.getNo_telepon();
            case 6:
                return model.getAlamat();
            case 7:
                return model.getCreatedAt() != null ? sdf.format(model.getCreatedAt()) : "";
            case 8:
                return model.getUpdatedAt() != null ? sdf.format(model.getUpdatedAt()) : "";
            default:
                return null;
        }
    }

    public String getFormattedDetail(int index) {
        ModelUser model = list.get(index);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        StringBuilder detail = new StringBuilder();
        detail.append("Nama        : ").append(model.getNama()).append("\n");
        detail.append("RFID        : ").append(model.getRfidUid()).append("\n");
        detail.append("Username    : ").append(model.getUsername()).append("\n");
        detail.append("Role        : ").append(model.getRole()).append("\n");
        detail.append("No Telepon  : ").append(model.getNo_telepon()).append("\n");
        detail.append("Alamat      : ").append(model.getAlamat()).append("\n");
        detail.append("Created At  : ").append(model.getCreatedAt() != null ? sdf.format(model.getCreatedAt()) : "").append("\n");
        detail.append("Updated At  : ").append(model.getUpdatedAt() != null ? sdf.format(model.getUpdatedAt()) : "").append("\n");

        return detail.toString();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
