/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.dao;

import com.app.config.ConnectionDB;
import com.app.model.ModelUser;
import com.app.service.ServiceUser;
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
public class UserDAO implements ServiceUser {

    private Connection conn;

    public UserDAO() {
        conn = ConnectionDB.getConnection();
    }

    @Override
    public void tambahData(ModelUser model) {
        PreparedStatement st = null;
        try {
            String sql = "INSERT INTO tbl_user(nama, rfid_uid, username, password, no_telepon, alamat, role) VALUES (?,?,?,?,?,?,?)";

            st = conn.prepareStatement(sql);
            st.setString(1, model.getNama());
            st.setString(2, model.getRfidUid());
            st.setString(3, model.getUsername());
            st.setString(4, generateSHA256(model.getPassword()));
            st.setString(5, model.getNo_telepon());
            st.setString(6, model.getAlamat());
            st.setString(7, model.getRole());

            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void perbaruiData(ModelUser model) {
        PreparedStatement st = null;
        try {
            String sql = "UPDATE tbl_user SET nama=?, rfid_uid=?, username=?, no_telepon=?, alamat=?, role=? WHERE id_user=?";

            st = conn.prepareStatement(sql);
            st.setString(1, model.getNama());
            st.setString(2, model.getRfidUid());
            st.setString(3, model.getUsername());
            st.setString(4, model.getNo_telepon());
            st.setString(5, model.getAlamat());
            st.setString(6, model.getRole());
            st.setInt(7, model.getIdUser());

            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hapusData(ModelUser model) {
        PreparedStatement st = null;
        String sql = "DELETE FROM tbl_user WHERE id_user=?";
        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, model.getIdUser());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ModelUser> tampilData() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List list = new ArrayList();
        String sql = "SELECT * FROM tbl_user";

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                ModelUser model = new ModelUser();
                model.setIdUser(rs.getInt("id_user"));
                model.setRfidUid(rs.getString("rfid_uid"));
                model.setNama(rs.getString("nama"));
                model.setUsername(rs.getString("username"));
                model.setRole(rs.getString("role"));
                model.setNo_telepon(rs.getString("no_telepon"));
                model.setAlamat(rs.getString("alamat"));
                model.setCreatedAt(rs.getTimestamp("created_at"));
                model.setUpdatedAt(rs.getTimestamp("updated_at"));

                list.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ModelUser> pencarianData(String id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<ModelUser> list = new ArrayList<>();
        String sql = "SELECT * FROM tbl_user WHERE id_user LIKE ? "
                + "OR rfid_uid LIKE ? "
                + "OR nama LIKE ? "
                + "OR username LIKE ? "
                + "OR alamat LIKE ? "
                + "OR role LIKE ? "
                + "OR created_at LIKE ? "
                + "OR updated_at LIKE ?";
        try {
            st = conn.prepareStatement(sql);
            for (int i = 1; i <= 8; i++) {
                st.setString(i, "%" + id + "%");
            }

            rs = st.executeQuery();
            while (rs.next()) {
                ModelUser model = new ModelUser();
                model.setIdUser(rs.getInt("id_user"));
                model.setRfidUid(rs.getString("rfid_uid"));
                model.setNama(rs.getString("nama"));
                model.setUsername(rs.getString("username"));
                model.setRole(rs.getString("role"));
                model.setNo_telepon(rs.getString("no_telepon"));
                model.setAlamat(rs.getString("alamat"));
                model.setCreatedAt(rs.getTimestamp("created_at"));
                model.setUpdatedAt(rs.getTimestamp("updated_at"));

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
    public String generateSHA256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodehash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder(2 * encodehash.length);

            for (byte b : encodehash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ModelUser prosesLogin(ModelUser model) {
        PreparedStatement st = null;
        ResultSet rs = null;
        ModelUser modelUs = null;
        String sql = "SELECT * FROM tbl_user WHERE username = ? AND password = ?";

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, model.getUsername());
            st.setString(2, generateSHA256(model.getPassword()));
            rs = st.executeQuery();

            if (rs.next()) {
                modelUs = new ModelUser();
                modelUs.setIdUser(rs.getInt("id_user"));
                modelUs.setNama(rs.getString("nama"));
                modelUs.setUsername(rs.getString("username"));
                modelUs.setRole(rs.getString("role"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return modelUs;
    }

    @Override
    public boolean validasiPasswordLama(String username, String passwordLama) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String enkripsiPasswordLama = generateSHA256(passwordLama);
        String sql = "SELECT * FROM tbl_user WHERE username = ? AND password = ?";
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, enkripsiPasswordLama);
            rs = st.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean pergantianPassword(String username, String passwordLama, String passwordBaru) {
        PreparedStatement st = null;
        PreparedStatement stUpdate = null;
        ResultSet rs = null;
        String enkripsiPasswordLama = generateSHA256(passwordLama);
        String enkripsiPasswordBaru = generateSHA256(passwordBaru);
        String sql = "SELECT * FROM tbl_user WHERE username = ? AND password = ?";

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, enkripsiPasswordLama);
            rs = st.executeQuery();

            if (rs.next()) {
                String sqlUpdate = "UPDATE tbl_user SET password = ? WHERE username = ?";
                stUpdate = conn.prepareStatement(sqlUpdate);
                stUpdate.setString(1, enkripsiPasswordBaru);
                stUpdate.setString(2, username);
                int result = stUpdate.executeUpdate();
                return result > 0;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getLastUserId() {
        int lastId = 0;
        String sql = "SELECT MAX(id_user) FROM tbl_user";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                lastId = rs.getInt(1); // Jika tabel kosong, lastId tetap 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastId;
    }

    public String generateRFID() {
        int lastId = getLastUserId(); // Ambil ID terakhir dari database
        int nextId = lastId + 1; // ID berikutnya
        String rfid = String.format("RF%04d", nextId); // Format RFID dengan 4 digit angka
        return rfid;
    }

    public List<ModelUser> detailuser() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List list = new ArrayList();
        String sql = "SELECT * FROM tbl_user";

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                ModelUser model = new ModelUser();
//                model.setIdUser(rs.getInt("id_user"));
                model.setRfidUid(rs.getString("rfid_uid"));
                model.setNama(rs.getString("nama"));
                model.setUsername(rs.getString("username"));
                model.setRole(rs.getString("role"));
                model.setNo_telepon(rs.getString("no_telepon"));
                model.setAlamat(rs.getString("alamat"));
                model.setCreatedAt(rs.getTimestamp("created_at"));
                model.setUpdatedAt(rs.getTimestamp("updated_at"));

                list.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
