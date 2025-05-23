/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.dao;

import com.app.config.ConnectionDB;
import com.app.model.ModelSupplier;
import com.app.service.ServiceSupplier;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rohim
 */
public class SupplierDAO implements ServiceSupplier {

    private Connection conn;

    public SupplierDAO() {
        conn = ConnectionDB.getConnection();
    }

    @Override
    public void tambahData(ModelSupplier model) {
        PreparedStatement st = null;
        try {
            String sql = "INSERT INTO tbl_supplier(nama, alamat, no_hp) VALUES (?,?,?)";

            st = conn.prepareStatement(sql);
            st.setString(1, model.getNama());
            st.setString(2, model.getAlamat());
            st.setString(3, model.getNo_hp());

            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void perbaruiData(ModelSupplier model) {
        PreparedStatement st = null;
        try {
            String sql = "UPDATE tbl_supplier SET nama=?, alamat=?, no_hp=? WHERE id_supplier=?";

            st = conn.prepareStatement(sql);
            st.setString(1, model.getNama());
            st.setString(2, model.getAlamat());
            st.setString(3, model.getNo_hp());
            st.setInt(4, model.getId_supplier());
            
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hapusData(ModelSupplier model) {
        PreparedStatement st = null;
        String sql = "DELETE FROM tbl_supplier WHERE id_supplier=?";
        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, model.getId_supplier());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ModelSupplier> tampilData() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List list = new ArrayList();
        String sql = "SELECT * FROM tbl_supplier";

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                ModelSupplier model = new ModelSupplier();
                model.setId_supplier(rs.getInt("id_supplier"));
                model.setNama(rs.getString("nama"));
                model.setAlamat(rs.getString("alamat"));
                model.setNo_hp(rs.getString("no_hp"));

                list.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ModelSupplier> pencarianData(String id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List list = new ArrayList();
        String sql = "SELECT * FROM tbl_supplier WHERE id_supplier LIKE '%" + id + "%' "
                + "OR nama LIKE '%" + id + "%' "
                + "OR alamat LIKE '%" + id + "%' "
                + "OR no_hp LIKE '%" + id + "%' ";
        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                ModelSupplier model = new ModelSupplier();
                model.setId_supplier(rs.getInt("id_supplier"));
                model.setNama(rs.getString("nama"));
                model.setAlamat(rs.getString("alamat"));
                model.setNo_hp(rs.getString("no_hp"));

                list.add(model);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean validasiNamaSupplier(ModelSupplier mode) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM tbl_supplier "
                + "WHERE nama = ? AND id_supplier != ?";
        try {
            ModelSupplier model = new ModelSupplier();
            st = conn.prepareStatement(sql);
            st.setString(1, model.getNama ());
            st.setInt(2, model.getId_supplier());
            rs = st.executeQuery();
            
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
}

