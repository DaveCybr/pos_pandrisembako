package com.app.tablemodel;

import com.app.model.ModelPembelianSmt;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class TableModelPembelianSmt extends AbstractTableModel {

    private final List<ModelPembelianSmt> list = new ArrayList<>();
    private final String[] columnNames = {"NO", "BARCODE", "ID BARANG", "NAMA BARANG", "HARGA", "QTY", "SATUAN","SUBTOTAL"};
    
    public void insertData(ModelPembelianSmt model) {
        list.add(model);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }
    
    public void updateData(int row, ModelPembelianSmt model){
        list.set(row, model);
        fireTableDataChanged();
    }
    
    public void deleteData(int row){
        list.remove(row);
        fireTableRowsDeleted(row, row);
    }
    
    public ModelPembelianSmt getData(int index){
        return list.get(index);
    }
    
    public void clear(){
        list.clear();
        fireTableDataChanged();
    }
    
    public void setData(List<ModelPembelianSmt> list){
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
        ModelPembelianSmt model = list.get(rowIndex);
        DecimalFormat df1 = new DecimalFormat("#,##0");
        String qty = df1.format(model.getModelPembRinci().getQty());
        String harga = df1.format(model.getModelBarang().getHarga());
        String nilai = df1.format(model.getModelPembRinci().getNilai());
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
                return harga;
            case 5:
                return qty;
            case 6:
                return model.getModelBarang().getSatuan();
            case 7: 
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
