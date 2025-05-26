package com.app.dao;



import com.app.config.ConnectionDB;
import com.app.model.ModelBarang;
import com.app.model.ModelPembelianRinci;
import com.app.model.ModelPembelianSmt;
import com.app.service.ServicePembelianSmt;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PembelianSmtDAO implements ServicePembelianSmt{

    private Connection conn;

    public PembelianSmtDAO() {
        conn = ConnectionDB.getConnection();
    }
@Override
    public void tambahData(ModelPembelianSmt model) {
        
        String sql = "INSERT INTO tbl_pembeliansmt (id_barang, barcode, nama, harga, qty, satuan, subtotal) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, model.getModelBarang().getIdBarang());  // Kode Obat
            stmt.setString(2, model.getModelBarang().getBarcode());  // Kode Obat
            stmt.setString(3, model.getModelBarang().getNama_barang()); // Nama Obat
            stmt.setBigDecimal(4, model.getModelBarang().getHarga());   // Harga Obat
            stmt.setDouble(5, model.getModelPembRinci().getQty()); 
            stmt.setString(6, model.getModelBarang().getSatuan());// Kuantitas
            
            
            BigDecimal harga = model.getModelBarang().getHarga();
            double qty = model.getModelPembRinci().getQty();
            BigDecimal subtotal = harga.multiply(BigDecimal.valueOf(qty));
            stmt.setBigDecimal(7, subtotal);  // Subtotal (Harga * Qty)
            
            stmt.executeUpdate();
//            updateStok(model.getModelBarang().getIdBarang(), model.getModelPembRinci().getQty());
        } catch (SQLException e) {
            System.out.println("Error while adding data: " + e.getMessage());
        }
    }
    
     public void updateStok(String idObat, int qty) {
        String sql = "UPDATE tbl_masterbarang SET Stok = Stok - ? WHERE id_barang = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setDouble(1, qty);
            st.setString(2, idObat);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal memperbarui stok: " + e.getMessage());
        }
    }
    
    public boolean cekStok(String idObat, int qty) {
        String sql = "SELECT Stok FROM tbl_masterbarang WHERE id_barang = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, idObat);

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
    public void updateData(ModelPembelianSmt model) {
        String sql = "UPDATE tbl_pembeliansmt SET nama = ?, harga = ?, qty = ?, subtotal = ? WHERE id_barang = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, model.getModelBarang().getNama_barang()); // Nama Obat
            stmt.setBigDecimal(2, model.getModelBarang().getHarga()); // Harga Obat
            stmt.setDouble(3, model.getModelPembRinci().getQty());  // Kuantitas
            stmt.setBigDecimal(4, model.getModelPembRinci().getNilai()); // Subtotal
            stmt.setString(5, model.getModelBarang().getIdBarang());  // Kode Obat
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating data: " + e.getMessage());
        }
    }

    // Menghapus data penjualan sementara
    @Override
    public void hapusData(ModelPembelianSmt model) {
        String sql = "DELETE FROM tbl_transaksismt WHERE id_barang = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, model.getModelBarang().getIdBarang());  // Kode Obat
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting data: " + e.getMessage());
        }
    }

    // Menampilkan data penjualan sementara
    @Override
    public List<ModelPembelianSmt> tampilData() {
        List<ModelPembelianSmt> list = new ArrayList<>();
        String sql = "SELECT * FROM tbl_pembeliansmt";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ModelPembelianSmt model = new ModelPembelianSmt();
                ModelPembelianRinci detail = new ModelPembelianRinci();
                ModelBarang barang = new ModelBarang();
                
                barang.setIdBarang(rs.getString("id_barang"));
                barang.setBarcode(rs.getString("barcode"));
                barang.setNama_barang(rs.getString("nama"));
                barang.setHarga(rs.getBigDecimal("harga"));
                barang.setSatuan(rs.getString("satuan"));
                
                detail.setQty(rs.getInt("qty"));
                detail.setNilai(rs.getBigDecimal("subtotal"));
                
                
                model.setModelBarang(barang);
                model.setModelPembRinci(detail);
                
                list.add(model);
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching data: " + e.getMessage());
        }
        
        return list;
    }
    
    @Override
    public void updateQty(String idObat, int qtyBaru) {
        String getQtySql = "SELECT Qty FROM tbl_transaksismt WHERE id_barang = ?";
        String updateQtySql =
                "UPDATE tbl_transaksismt SET Stok = ?, Qty = ? WHERE id_barang = ?";
        String updateStokSql = "UPDATE tbl_masterbarang SET Stok = ? WHERE id_barang = ?";
        String getStok = "SELECT Stok FROM tbl_masterbarang WHERE id_barang = ?";

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
