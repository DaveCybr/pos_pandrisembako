package com.app.tablemodel;

import com.app.model.ModelPenjualanSmt;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class TablePenjualanSmt extends AbstractTableModel {

    private final List<ModelPenjualanSmt> list = new ArrayList<>();
    private final String[] columnNames = {"NO", "BARCODE", "ID BARANG", "NAMA BARANG", "STOK", "HARGA", "SATUAN", "QTY", "SUBTOTAL"};
    
    public void insertData(ModelPenjualanSmt model) {
        list.add(model);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }
    
    public void updateData(int row, ModelPenjualanSmt model){
        list.set(row, model);
        fireTableDataChanged();
    }
    
    public void deleteData(int row){
        list.remove(row);
        fireTableRowsDeleted(row, row);
    }
    
    public ModelPenjualanSmt getData(int index){
        return list.get(index);
    }
    
    public void clear(){
        list.clear();
        fireTableDataChanged();
    }
    
    public void setData(List<ModelPenjualanSmt> list){
        clear();
        this.list.addAll(list);
        fireTableDataChanged();
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 4 || column == 3; // Mengizinkan edit pada kolom QTY (index 4) dan Harga (index 3)
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        ModelPenjualanSmt model = list.get(rowIndex);
        DecimalFormat df1 = new DecimalFormat("#,##0");
        String stok = df1.format(model.getModelBarang().getStok());
        String qty = df1.format(model.getModelPenjualanRinci().getQty());
        String harga = df1.format(model.getModelBarang().getHarga());
        String nilai = df1.format(model.getModelPenjualanRinci().getNilai());
        switch (columnIndex) {
            case 0:
                return rowIndex+1;
            case 1: 
                return model.getModelBarang().getBarcode();
            case 2: 
                return model.getModelBarang().getIdBarang();
            case 3:
                return model.getModelBarang().getNama_barang();
            case 4:
                return stok;
            case 5:
                return harga;
            case 6:
                return model.getModelBarang().getSatuan();
            case 7:
                return qty;
            case 8: 
                return nilai;
            default:
                return null;
        }  
    }
    
    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }
}
