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
        String sql = "SELECT * FROM tbl_master_barang";

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                ModelBarang model = new ModelBarang();
                model.setIdBarang(rs.getString("id_barang"));
                model.setNama_barang(rs.getString("nama_barang"));
                model.setBarcode(rs.getString("barcode"));
                model.setHarga(rs.getInt("harga"));
                model.setSatuan(rs.getString("satuan"));
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
            String sql = "INSERT INTO tbl_master_barang (id_barang, nama_barang, barcode, harga, satuan, stok) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, model.getIdBarang());
            stmt.setString(2, model.getNama_barang());
            stmt.setString(3, model.getBarcode());
            stmt.setInt(4, model.getHarga());
            stmt.setString(5, model.getSatuan());
            stmt.setDouble(6, model.getStok());

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
            String sql = "UPDATE tbl_master_barang SET nama_barang=?, harga=?, satuan=?, WHERE id_barang";

            st = conn.prepareStatement(sql);
            st.setString(1, model.getNama_barang());
            st.setInt(2, model.getHarga());
            st.setString(3, model.getSatuan());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
    public List<ModelBarang> pencarianData(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
