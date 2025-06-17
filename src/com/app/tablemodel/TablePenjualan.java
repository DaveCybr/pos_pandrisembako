package com.app.tablemodel;

import com.app.model.ModelPenjualan;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class TablePenjualan extends AbstractTableModel {

    private final List<ModelPenjualan> list = new ArrayList<>();
    private final String[] columnNames = {"REF", "TANGGAL", "TOTAL", "BAYAR", "KEMBALIAN"};
    
    public void insertData(ModelPenjualan model) {
        list.add(model);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
    }
    
    public void updateData(int row, ModelPenjualan model){
        list.set(row, model);
        fireTableDataChanged();
        JOptionPane.showMessageDialog(null, "Data berhasil diupdate!");
    }
    
    public void deleteData(int row){
        list.remove(row);
        fireTableRowsDeleted(row, row);
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
    }
    
    public ModelPenjualan getData(int index){
        return list.get(index);
    }
    
    public void clear(){
        list.clear();
        fireTableDataChanged();
    }
    
    public void setData(List<ModelPenjualan> list){
        clear();
        this.list.addAll(list);
        fireTableDataChanged();
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ModelPenjualan model = list.get(rowIndex);
        DecimalFormat df1 = new DecimalFormat("#,##0");
        String total = df1.format(model.getNilai());
        String bayar = df1.format(model.getBayar());
        String kembalian = df1.format(model.getKembalian());
        switch (columnIndex) {
        case 0:
            return model.getIdPenjualan();
        case 1:
            return model.getTanggalPenjualan();
        case 2:
            return total;
        case 3:
            return bayar;
        case 4:
            return kembalian;
        default:
            return null;
    }
    }
}
