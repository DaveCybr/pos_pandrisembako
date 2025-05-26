/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.dao;

import com.app.config.ConnectionDB;
import com.app.model.ModelPembelianRinci;
import com.app.service.ServicePembelianRinci;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author queen
 */
public class PembelianRinciDAO implements ServicePembelianRinci {

    private Connection conn;

    public PembelianRinciDAO() {
        conn = ConnectionDB.getConnection();
    }
    
    public void updateStok(String idBarang, double qty) {
        String selectSql = "SELECT stok FROM tbl_master_barang WHERE id_barang = ?";
        String updateSql = "UPDATE tbl_master_barang SET stok = stok + ? WHERE id_barang = ?";

        try (PreparedStatement selectSt = conn.prepareStatement(selectSql)) {
            selectSt.setString(1, idBarang);
            ResultSet rs = selectSt.executeQuery();

            if (rs.next()) {
                double stokSaatIni = rs.getDouble("stok");
                double stokBaru = stokSaatIni + qty;

                if (stokBaru < 0) {
                    System.out.println("Update stok dibatalkan: stok tidak boleh negatif.");
                    return;
                }

                try (PreparedStatement updateSt = conn.prepareStatement(updateSql)) {
                    updateSt.setDouble(1, qty);
                    updateSt.setString(2, idBarang);
                    int affectedRows = updateSt.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.println("Stok berhasil diperbarui.");
                    } else {
                        System.out.println("Gagal memperbarui stok: ID barang tidak ditemukan.");
                    }
                }
            } else {
                System.out.println("ID barang tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.err.println("Terjadi kesalahan saat memperbarui stok:");
            e.printStackTrace();
        }
    }


     
    @Override
    public void tambah_detail_P(ModelPembelianRinci model) {
        String sql =
                "INSERT INTO tbl_pembelianrinci (id_pembelian, id_barang, nama_barang, qty, harga, satuan, nilai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        System.out.println(model.getModelBarang().getSatuan());
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, model.getModelPemb().getIdPembelian());
            st.setString(2, model.getModelBarang().getIdBarang());
            st.setString(3, model.getModelBarang().getNama_barang());
            st.setDouble(4, model.getQty());
            st.setBigDecimal(5, model.getModelBarang().getHarga());
            st.setString(6, model.getModelBarang().getSatuan());
            st.setBigDecimal(7, model.getNilai());
            st.executeUpdate();

            // Update stok setelah menambahkan detail penjualan
            updateStok(model.getModelBarang().getIdBarang(), model.getQty());
        } catch (SQLException e) {
            System.out.println("Gagal menambahkan detail penjualan: " + e.getMessage());
        }
    }
    


    @Override
    public void sumTotal(ModelPembelianRinci model) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "SELECT SUM(subtotal) FROM tbl_pembeliansmt";
        try{
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            if(rs.next()){
                model.setNilai(rs.getBigDecimal(1));
            }  
        } catch (SQLException e){
            e.printStackTrace();
        }
    }



    @Override
    public void hapusDataSmt() {
        String sql = "DELETE FROM tbl_pembeliansmt";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menghapus data: " + e.getMessage());
        }
    }
    @Override
    public List<ModelPembelianRinci> tampil_detail_P(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ModelPembelianRinci> search(String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
