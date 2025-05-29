/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.dao;

import com.app.config.ConnectionDB;
import com.app.model.ModelSatuan;
import com.app.service.ServiceSatuan;
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
public class SatuanDAO implements ServiceSatuan {

    private Connection conn;

    public SatuanDAO() {
        conn = ConnectionDB.getConnection();
    }

    private int getIdSatuan() {
        int idSatuan = 0;
        try {
            String q = "SELECT id_satuan FROM ref_satuan ORDER BY id_satuan DESC LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idSatuan = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idSatuan;
    }

    @Override
    public void tambahData(ModelSatuan model) {
        PreparedStatement st = null;
        try {
            String sql = "INSERT INTO ref_satuan (id_satuan,nama_satuan) VALUES (?,?)";

            st = conn.prepareStatement(sql);
            st.setInt(1, getIdSatuan());
            st.setString(2, model.getNamaSatuan());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
   public void perbaruiData(ModelSatuan model) {
    PreparedStatement st = null;
    try {
        String sql = "UPDATE ref_satuan SET nama_satuan=? WHERE id_satuan=?";

        st = conn.prepareStatement(sql);
        st.setString(1, model.getNamaSatuan());
        st.setInt(2, model.getIdSatuan()); // Pastikan method getIdSatuan() mengembalikan ID satuan yang benar

        st.executeUpdate();
        st.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    @Override
    public void hapusData(ModelSatuan model) {
        PreparedStatement st = null;
        String sql = "DELETE FROM ref_satuan WHERE id_satuan=?";
        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, model.getIdSatuan());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ModelSatuan> tampilData() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List list = new ArrayList();
        String sql = "SELECT * FROM ref_satuan";

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                ModelSatuan model = new ModelSatuan();
                model.setIdSatuan(rs.getInt("id_satuan"));
                model.setNamaSatuan(rs.getString("nama_satuan"));

                list.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ModelSatuan> pencarianData(String id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List list = new ArrayList();
        String sql = "SELECT * FROM ref_satuan WHERE id_satuan LIKE '%" + id + "%' "
                + "OR nama_satuan LIKE '%" + id + "%' ";
        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                ModelSatuan model = new ModelSatuan();
                model.setIdSatuan(rs.getInt("id_satuan"));
                model.setNamaSatuan(rs.getString("nama_satuan"));

                list.add(model);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

//    @Override
//    public boolean validasiNamaSupplier(ModelSatuan mode) {
//        PreparedStatement st = null;
//        ResultSet rs = null;
//        String sql = "SELECT * FROM tbl_supplier "
//                + "WHERE nama = ? AND id_supplier != ?";
//        try {
//            ModelSatuan model = new ModelSatuan();
//            st = conn.prepareStatement(sql);
//            st.setString(1, model.getNama ());
//            st.setInt(2, model.getId_supplier());
//            rs = st.executeQuery();
//            
//            return rs.next();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
