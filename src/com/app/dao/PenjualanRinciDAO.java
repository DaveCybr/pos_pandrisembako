/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.dao;

import com.app.config.ConnectionDB;
import com.app.model.ModelBarang;
import com.app.model.ModelPenjualan;
import com.app.model.ModelPenjualanRinci;
import com.app.service.ServicePenjualanRinci;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author queen
 */
public class PenjualanRinciDAO implements ServicePenjualanRinci {

    private final Connection conn;

    public PenjualanRinciDAO() {
        conn = ConnectionDB.getConnection();
    }

    @Override
    public void tambah_detail_P(ModelPenjualanRinci model) {
        
        String sql =
                "INSERT INTO tbl_penjualanrinci (id_penjualanrinci, id_penjualan, id_barang, barcode, nama_barang, qty, satuan, harga, nilai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            String id_pr = "RINC" + (int) (Math.random() * 900 + 100);
            st.setString(1, id_pr);
            st.setString(2, model.getModelPenjualan().getIdPenjualan());
            st.setString(3, model.getModelBarang().getIdBarang());
            st.setString(4, model.getModelBarang().getBarcode());
            st.setString(5, model.getModelBarang().getNama_barang());
            st.setBigDecimal(6, model.getQty());
            st.setString(7, model.getModelBarang().getSatuan());
            st.setBigDecimal(8, model.getModelBarang().getHarga());
            st.setBigDecimal(9, model.getNilai());
            st.executeUpdate();

            // Update stok setelah menambahkan detail penjualan
            updateStok(model.getModelBarang().getIdBarang(), model.getQty());
        } catch (SQLException e) {
            System.out.println("Gagal menambahkan detail penjualan: " + e.getMessage());
        }
    }


    @Override
    public void sumTotal(ModelPenjualanRinci model) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "SELECT SUM(subtotal) FROM tbl_penjualansmt";
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
    public List<ModelPenjualanRinci> tampil_detail_P(String id) {
        List<ModelPenjualanRinci> details = new ArrayList<>();

        // SQL untuk mengambil detail penjualan berdasarkan id transaksi
        String sql = "SELECT td.*, m.* "
                + "FROM tbl_penjualanrinci td "
                + "JOIN tbl_master_barang m ON td.id_barang = m.id_barang " + "WHERE td.id_penjualan = ?"; 

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id); // Set id transaksi sebagai parameter

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    // Set ModelBarang
                    ModelBarang obat = new ModelBarang();
                    obat.setIdBarang(rs.getString("id_barang"));
                    obat.setNama_barang(rs.getString("nama_barang"));
                    obat.setBarcode(rs.getString("barcode"));
                    obat.setSatuan(rs.getString("satuan"));
                    obat.setHarga(rs.getBigDecimal("harga"));

                    // Set ModelPenjualan (asumsi sudah ada, Anda bisa menyesuaikan)
                    ModelPenjualan penjualan = new ModelPenjualan();
                    penjualan.setIdPenjualan(id); // ID transaksi di sini bisa disesuaikan

                    // Set qty dan nilai
                    ModelPenjualanRinci detail = new ModelPenjualanRinci();
                    detail.setModelBarang(obat);
                    detail.setModelPenjualan(penjualan);
                    detail.setQty(rs.getBigDecimal("qty"));
                    detail.setNilai(rs.getBigDecimal("nilai"));

                    // Tambahkan detail ke list
                    details.add(detail);
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data detail penjualan: " + e.getMessage());
        }

        return details;
    }


    @Override
    public List<ModelPenjualanRinci> search(String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void updateStok(String idObat, BigDecimal qty) {
        String sql = "UPDATE tbl_master_barang SET stok = stok - ? WHERE id_barang = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setBigDecimal(1, qty);
            st.setString(2, idObat);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal memperbarui stok: " + e.getMessage());
        }
    }

    public boolean cekStok(String idObat, BigDecimal qty) {
        String sql = "SELECT stok FROM tbl_master_barang WHERE id_barang = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, idObat);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    BigDecimal stok = rs.getBigDecimal("stok");
                    return stok.compareTo(qty) >= 0; // True jika stok mencukupi
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengecek stok: " + e.getMessage());
        }
        return false; // False jika stok tidak mencukupi atau ada kesalahan
    }

    public void updateQty(String idPenjualanrinci, String idObat, double qtyBaru) {
        String getQtySql = "SELECT Qty FROM tbl_transaksidetail WHERE Kd_Trx = ? AND Kd_Obat = ?";
        String updateQtySql =
                "UPDATE tbl_transaksidetail SET Qty = ? WHERE Kd_Trx = ? AND Kd_Obat = ?";
        String updateStokSql = "UPDATE tbl_masterobat SET Stok = ? WHERE Kd_Obat = ?";
        String getStok = "SELECT Stok FROM tbl_masterobat WHERE Kd_Obat = ?";

        try {
            double qtyLama = 0;
            try (PreparedStatement stGet = conn.prepareStatement(getQtySql)) {
                stGet.setString(1, idPenjualanrinci);
                stGet.setString(2, idObat);

                try (ResultSet rs = stGet.executeQuery()) {
                    if (rs.next()) {
                        qtyLama = rs.getDouble("Qty");
                    }
                }
            }
            double stok = 0;
            try (PreparedStatement stStok = conn.prepareStatement(getStok)) {
                stStok.setString(1, idObat);

                try (ResultSet rs = stStok.executeQuery()) {
                    if (rs.next()) {
                        stok = rs.getDouble("Stok");
                    }
                }
            }


            double stokTotal = stok + qtyLama;
            double stokReal;
            if (stokTotal < qtyBaru) {
                System.out.println("Gagal memperbarui stok");
                return;
            } else {
                stokReal = stokTotal - qtyLama;
            }

            try (PreparedStatement stUpdateQty = conn.prepareStatement(updateQtySql)) {
                stUpdateQty.setDouble(1, qtyBaru);
                stUpdateQty.setString(2, idPenjualanrinci);
                stUpdateQty.setString(3, idObat);
                stUpdateQty.executeUpdate();
            }


            try (PreparedStatement stUpdateStok = conn.prepareStatement(updateStokSql)) {
                stUpdateStok.setDouble(1, stokReal);
                stUpdateStok.setString(2, idObat);
                stUpdateStok.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("Gagal memperbarui qty: " + e.getMessage());
        }
    }

    @Override
    public void hapusDataSmt() {
        String sql = "DELETE FROM tbl_penjualansmt";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menghapus data: " + e.getMessage());
        }
    }
}
