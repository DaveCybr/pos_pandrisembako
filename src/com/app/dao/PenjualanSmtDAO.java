package com.app.dao;

import com.app.config.ConnectionDB;
import com.app.model.ModelBarang;
import com.app.model.ModelPenjualanRinci;
import com.app.model.ModelPenjualanSmt;
import com.app.service.ServicePenjualanSmt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PenjualanSmtDAO implements ServicePenjualanSmt {
    
    private final Connection conn;
    
    public PenjualanSmtDAO() {
        conn = ConnectionDB.getConnection();
    }

    // Menambahkan data penjualan sementara
    @Override
    public void tambahData(ModelPenjualanSmt model) {
        System.out.println(model.getModelPenjualanRinci().getQty().intValue());
        if (!cekStok(model.getModelBarang().getIdBarang(), model.getModelPenjualanRinci().getQty().intValue())) {
            JOptionPane.showMessageDialog(null, "Stok tidak mencukupi untuk Barang ID: " + model.getModelBarang().getIdBarang());
            throw new IllegalArgumentException("Stok tidak mencukupi untuk Barang ID: " + model.getModelBarang().getIdBarang());
        }
        
        String sql = "INSERT INTO tbl_penjualansmt (id_barang, barcode, nama, harga, qty, satuan, subtotal, stok) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, model.getModelBarang().getIdBarang());  // Kode Obat
            stmt.setString(2, model.getModelBarang().getBarcode());  // Kode Obat
            stmt.setString(3, model.getModelBarang().getNama_barang()); // Nama Obat
            stmt.setBigDecimal(4, model.getModelBarang().getHarga());  // Harga Obat
            stmt.setBigDecimal(5, model.getModelPenjualanRinci().getQty());  // Kuantitas
            stmt.setString(6, model.getModelBarang().getSatuan());  // Kuantitas
            stmt.setBigDecimal(7, model.getModelPenjualanRinci().getNilai()); // Subtotal (Harga * Qty)
            stmt.setBigDecimal(8, model.getModelBarang().getStok());
            stmt.executeUpdate();
//            updateStok(model.getModelBarang().getIdObat(), model.getModelPenjualanRinci().getQty());
        } catch (SQLException e) {
            System.out.println("Error while adding data: " + e.getMessage());
        }
    }
    
     public void updateStok(String idObat, int qty) {
        String sql = "UPDATE tbl_masterobat SET Stok = Stok - ? WHERE Kd_Obat = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setDouble(1, qty);
            st.setString(2, idObat);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal memperbarui stok: " + e.getMessage());
        }
    }
    
    public boolean cekStok(String idBarang, int qty) {
        String sql = "SELECT stok FROM tbl_master_barang WHERE id_barang = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, idBarang);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    double stok = rs.getDouble("Stok");
                    return stok >= qty; // True jika stok mencukupi
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengecek stok: " + e.getMessage());
        }
        return false; // False jika stok tidak mencukupi atau ada kesalahan
    }

    // Mengupdate data penjualan sementara
    @Override
    public void updateData(ModelPenjualanSmt model) {
        String sql = "UPDATE tbl_penjualansmt SET qty = ?, subtotal = ? WHERE id_barang = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, model.getModelPenjualanRinci().getQty());  // Kuantitas
            stmt.setBigDecimal(2, model.getModelPenjualanRinci().getNilai()); // Subtotal
            stmt.setString(3, model.getModelBarang().getIdBarang());  // Kode Obat
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating data: " + e.getMessage());
        }
    }

    // Menghapus data penjualan sementara
    @Override
    public void hapusData(ModelPenjualanSmt model) {
        String sql = "DELETE FROM tbl_penjualansmt WHERE id_barang = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, model.getModelBarang().getIdBarang());  // Kode Obat
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting data: " + e.getMessage());
        }
    }

    // Menampilkan data penjualan sementara
    @Override
    public List<ModelPenjualanSmt> tampilData() {
        List<ModelPenjualanSmt> list = new ArrayList<>();
        String sql = "SELECT * FROM tbl_penjualansmt";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ModelPenjualanSmt model = new ModelPenjualanSmt();
                ModelPenjualanRinci detail = new ModelPenjualanRinci();
                ModelBarang obat = new ModelBarang();
                
                obat.setIdBarang(rs.getString("id_barang"));
                obat.setBarcode(rs.getString("barcode"));
                obat.setNama_barang(rs.getString("nama"));
                obat.setSatuan(rs.getString("satuan"));
                obat.setHarga(rs.getBigDecimal("harga"));
                obat.setStok(rs.getBigDecimal("stok"));
                
                detail.setQty(rs.getBigDecimal("qty"));
                detail.setNilai(rs.getBigDecimal("subtotal"));
                
                
                model.setModelBarang(obat);
                model.setModelPenjualanRinci(detail);
                
                list.add(model);
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching data: " + e.getMessage());
        }
        
        return list;
    }
    
    @Override
    public void updateQty(String idObat, int qtyBaru) {
        String getQtySql = "SELECT Qty FROM tbl_transaksismt WHERE Kd_Obat = ?";
        String updateQtySql =
                "UPDATE tbl_transaksismt SET Stok = ?, Qty = ? WHERE Kd_Obat = ?";
        String updateStokSql = "UPDATE tbl_masterobat SET Stok = ? WHERE Kd_Obat = ?";
        String getStok = "SELECT Stok FROM tbl_masterobat WHERE Kd_Obat = ?";

        try {
            double qtyLama = 0;
            try (PreparedStatement stGet = conn.prepareStatement(getQtySql)) {
                stGet.setString(1, idObat);

                try (ResultSet rs = stGet.executeQuery()) {
                    if (rs.next()) {
                        qtyLama = rs.getInt("Qty");
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
                stokReal = stokTotal - qtyBaru;
            }

            try (PreparedStatement stUpdateQty = conn.prepareStatement(updateQtySql)) {
                stUpdateQty.setDouble(1, stokReal);
                stUpdateQty.setDouble(2, qtyBaru);
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

}
