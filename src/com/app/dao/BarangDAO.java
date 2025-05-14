/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.dao;

import com.app.config.ConnectionDB;

import com.app.config.ConnectionDB;
import com.app.model.ModelBarang;
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
                model.setIdBarang((char) rs.getInt("id_barang"));
                model.setNama_barang(rs.getString("nama_barang"));
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
            String sql = "INSERT INTO tbl_master_barang (nama_barang, satuan, stok) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, model.getNama_barang());
            stmt.setString(2, model.getSatuan());
            stmt.setDouble(3, model.getStok());

            stmt.executeUpdate();
            stmt.close();

            System.out.println("Data berhasil ditambahkan.");
        } catch (SQLException e) {
            System.err.println("Gagal menambahkan data: " + e.getMessage());
        }
    }



    @Override
    public void perbaruiData(ModelBarang model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void hapusData(ModelBarang model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ModelBarang> pencarianData(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
