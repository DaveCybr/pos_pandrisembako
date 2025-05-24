package com.app.tablemodel;

import com.app.model.ModelPembelian;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class TableModelPembelian extends AbstractTableModel {

    private final List<ModelPembelian> list = new ArrayList<>();
    private final String[] columnNames = {"NO FAKTUR", "TANGGAL", "SUPPLIER", "URAIAN", "NILAI", "DIBAYAR", "STATUS"};
    
    public void insertData(ModelPembelian model) {
        list.add(model);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
    }
    
    public void updateData(int row, ModelPembelian model){
        list.set(row, model);
        fireTableDataChanged();
        JOptionPane.showMessageDialog(null, "Data berhasil diupdate!");
    }
    
    public void deleteData(int row){
        list.remove(row);
        fireTableRowsDeleted(row, row);
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
    }
    
    public ModelPembelian getData(int index){
        return list.get(index);
    }
    
    public void clear(){
        list.clear();
        fireTableDataChanged();
    }
    
    public void setData(List<ModelPembelian> list){
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
        ModelPembelian model = list.get(rowIndex);
        DecimalFormat df1 = new DecimalFormat("#,##0");
        String total = df1.format(model.getNilai());
        String bayar = df1.format(model.getBayar());
        switch (columnIndex) {
        case 0:
            return model.getIdPembelian();
        case 1:
            return model.getTglPembelian();
        case 2:
            return model.getSupplier().getNama();
        case 3:
            return model.getUraian();
        case 4:
            return total;
        case 5:
            return bayar;
        case 6:
            return model.getStatusPembayaran();
        default:
            return null;
    }
    }
}
