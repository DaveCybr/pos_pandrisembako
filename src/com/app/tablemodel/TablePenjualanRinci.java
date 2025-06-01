package com.app.tablemodel;

import com.app.model.ModelPenjualanRinci;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class TablePenjualanRinci extends AbstractTableModel {

    private final List<ModelPenjualanRinci> list = new ArrayList<>();
    private final String[] columnNames = {"NO", "KODE", "NAMA BARANG", "HARGA", "QTY", "SATUAN", "SUBTOTAL"};
    
    public void insertData(ModelPenjualanRinci model) {
        list.add(model);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
    }
    
    public void updateData(int row, ModelPenjualanRinci model){
        list.set(row, model);
        fireTableDataChanged();
        JOptionPane.showMessageDialog(null, "Data berhasil diupdate!");
    }
    
    public void deleteData(int row){
        list.remove(row);
        fireTableRowsDeleted(row, row);
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
    }
    
    public ModelPenjualanRinci getData(int index){
        return list.get(index);
    }
    
    public void clear(){
        list.clear();
        fireTableDataChanged();
    }
    
    public void setData(List<ModelPenjualanRinci> list){
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
//    public Object getValueAt(int rowIndex, int columnIndex) {
////        ModelPenjualanRinci model = list.get(rowIndex);
////        DecimalFormat df1 = new DecimalFormat("#,##0");
////        String total = df1.format(model.getNilai());
////        String qty = df1.format(model.getQty());
////        String harga_obat = df1.format(model.getModelBarang().getHargaObat());
////        if(columnIndex == 0){
////            return (rowIndex +1);
////        } else {
////            return switch (columnIndex) {
////                case 1 -> model.getModelBarang().getIdObat();
////                case 2 -> model.getModelBarang().getNamaObat();
////                case 3 -> harga_obat;
////                case 4 -> qty;
////                case 5 -> total;
////                default -> null;
////            };
////        }
//    }
    
//    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ModelPenjualanRinci model = list.get(rowIndex);
        DecimalFormat df1 = new DecimalFormat("#,##0");
        String total = df1.format(model.getNilai());
        String qty = df1.format(model.getQty());
        String harga_obat = df1.format(model.getModelBarang().getHarga());
        if(columnIndex == 0){
            return (rowIndex +1);
        } else {
            switch (columnIndex) {
            case 1:
                return model.getModelBarang().getIdBarang();
            case 2:
                return model.getModelBarang().getNama_barang();
            case 3:
                return harga_obat;
            case 4:
                return qty;
            case 5:
                return model.getModelBarang().getSatuan();
            case 6:
                return total;
            default:
                return null;
        }
        }
    }
}
