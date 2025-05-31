/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.dao;

import com.app.config.ConnectionDB;

import com.app.config.ConnectionDB;
import com.app.model.ModelBarang;
import com.app.model.ModelSupplier;
import com.app.model.ModelUser;
import com.app.service.ServiceBarang;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author queen
 */
public class BarangDAO implements ServiceBarang {

    private Connection conn;

    public BarangDAO() {
        conn = ConnectionDB.getConnection();
    }

    @Override
    public List<ModelBarang> tampilData() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List list = new ArrayList();
        String sql = "SELECT tbl_master_barang.id_barang, tbl_supplier.nama_supplier, tbl_master_barang.nama_barang, "
                + "tbl_master_barang.barcode, tbl_master_barang.harga, ref_satuan.nama_satuan, tbl_master_barang.stok FROM "
                + "tbl_master_barang LEFT JOIN tbl_supplier ON tbl_master_barang.id_supplier = tbl_supplier.id_supplier LEFT JOIN ref_satuan ON tbl_master_barang.id_satuan = ref_satuan.id_satuan";

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                ModelBarang model = new ModelBarang();
                ModelSupplier supl = new ModelSupplier();
                model.setIdBarang(rs.getString("id_barang"));
                String namaSupplier = rs.getString("nama_supplier");
                if (namaSupplier != null) { 
                    supl.setNama(namaSupplier);
                }
                model.setModelSupplier(supl);
                model.setNama_barang(rs.getString("nama_barang"));
                model.setBarcode(rs.getString("barcode"));
                model.setHarga(rs.getBigDecimal("harga"));
                model.setSatuan(rs.getString("nama_satuan"));
                model.setStok(rs.getDouble("stok"));
                list.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void tambahData(ModelBarang model) {
        try {
            System.out.println(model.getSatuan());
            String sql = "INSERT INTO tbl_master_barang (id_barang,id_supplier, nama_barang, barcode, harga, id_satuan, stok) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, model.getIdBarang());
            stmt.setString(2, model.getModelSupplier().getNama());
            stmt.setString(3, model.getNama_barang());
            stmt.setString(4, model.getBarcode());
            stmt.setBigDecimal(5, model.getHarga());
            stmt.setString(6, model.getSatuan());
            stmt.setDouble(7, model.getStok());

            stmt.executeUpdate();
            stmt.close();

            System.out.println("Data berhasil ditambahkan.");
        } catch (SQLException e) {
            System.err.println("Gagal menambahkan data: " + e.getMessage());
        }
    }

    public void perbaruiData(ModelBarang model) {
        PreparedStatement st = null;
        try {
            String sql = "UPDATE tbl_master_barang SET id_supplier = ?, nama_barang = ?, barcode = ?, harga = ?, id_satuan = ?, stok = ? WHERE id_barang = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, model.getModelSupplier().getNama());
            st.setString(2, model.getNama_barang());
            st.setString(3, model.getBarcode());
            st.setBigDecimal(4, model.getHarga());
            st.setString(5, model.getSatuan());
            st.setDouble(6, model.getStok());
            st.setString(7, model.getIdBarang());

            st.executeUpdate();
            st.close();

            System.out.println("Data berhasil diperbarui.");
        } catch (SQLException e) {
            System.err.println("Gagal memperbarui data: " + e.getMessage());
        }
    }

    @Override
    public void hapusData(ModelBarang model) {
        PreparedStatement st = null;
        String sql = "DELETE FROM tbl_master_barang WHERE id_barang=?";
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, model.getIdBarang());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override

    public List<ModelBarang> pencarianData(String keyword) {
        List<ModelBarang> listObat = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;

        // Buat query dasar
        StringBuilder sql = new StringBuilder("SELECT * FROM tbl_master_barang WHERE ");
        List<Object> parameters = new ArrayList<>();

        // Tambah kondisi string
        sql.append("(nama_barang LIKE ? OR barcode LIKE ? OR id_barang LIKE ?)");
        parameters.add("%" + keyword + "%");
        parameters.add("%" + keyword + "%");
        parameters.add("%" + keyword + "%");

        // Cek apakah keyword adalah angka (stok dan harga)
        boolean isNumber = false;
        try {
            int stok = Integer.parseInt(keyword);
            sql.append(" OR stok = ?");
            parameters.add(stok);
            isNumber = true;
        } catch (NumberFormatException e) {
            // Bukan angka
        }

        try {
            long harga = Long.parseLong(keyword);
            sql.append(" OR harga = ?");
            parameters.add(harga);
            isNumber = true;
        } catch (NumberFormatException e) {
            // Bukan angka
        }

        try {
            st = conn.prepareStatement(sql.toString());
            // Set parameter sesuai urutan
            for (int i = 0; i < parameters.size(); i++) {
                Object param = parameters.get(i);
                if (param instanceof String) {
                    st.setString(i + 1, (String) param);
                } else if (param instanceof Integer) {
                    st.setInt(i + 1, (Integer) param);
                } else if (param instanceof Long) {
                    st.setLong(i + 1, (Long) param);
                }
            }

            rs = st.executeQuery();
            while (rs.next()) {
                ModelBarang obat = new ModelBarang();
                obat.setIdBarang(rs.getString("id_barang"));
                obat.setNama_barang(rs.getString("nama_barang"));
                obat.setHarga(rs.getBigDecimal("harga"));
                obat.setStok(rs.getDouble("stok"));
                obat.setSatuan(rs.getString("satuan"));
                obat.setBarcode(rs.getString("barcode"));
                listObat.add(obat);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari data: " + e);
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (st != null) try {
                st.close();
            } catch (SQLException e) {
            }
        }

        return listObat;
    }

    @Override
    public List<ModelBarang> searchByBarcode(String barcode) {
        List<ModelBarang> list = new ArrayList<>();
        String query = "SELECT * FROM tbl_master_barang WHERE barcode = ?";

        try (PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, barcode);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ModelBarang barang = new ModelBarang();
                    barang.setIdBarang(rs.getString("id_barang"));
                    barang.setNama_barang(rs.getString("nama_barang"));
                    barang.setBarcode(rs.getString("barcode"));
                    barang.setHarga(rs.getBigDecimal("harga"));
                    barang.setSatuan(rs.getString("satuan"));
                    barang.setStok(rs.getDouble("stok"));

                    list.add(barang);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
