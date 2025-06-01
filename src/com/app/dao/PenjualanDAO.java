/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.dao;

import com.app.config.ConnectionDB;
import com.app.main.FormMenuUtama;
import com.app.model.ModelPenjualan;
import com.app.model.ModelUser;
import com.app.service.ServicePenjualan;
import java.awt.Dialog;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author queen
 */
public class PenjualanDAO implements ServicePenjualan {

    private final Connection conn;

    public PenjualanDAO() {
        conn = ConnectionDB.getConnection();
    }

    @Override
    public void tambahPenjualan(ModelPenjualan model) {
        String sql
                = "INSERT INTO tbl_penjualan (id_penjualan, bayar, kembalian, diskon, nilai, tgl_penjualan) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, model.getIdPenjualan());
            st.setBigDecimal(2, model.getBayar());
            st.setBigDecimal(3, model.getKembalian());
            st.setInt(4, model.getDiskon());
            st.setBigDecimal(5, model.getNilai());
            st.setString(6, model.getTanggalPenjualan());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menambahkan data: " + e.getMessage());
        }
    }

    @Override
    public List<ModelPenjualan> tampilPenjualan(String id_user) {
        List<ModelPenjualan> listPenjualan = new ArrayList<>();

        String sql = "SELECT id_penjualan, DATE_FORMAT(tgl_penjualan,'%d-%m-%Y') AS tanggal, nilai, bayar, kembalian, diskon "
                + "FROM tbl_penjualan";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ModelPenjualan model = new ModelPenjualan();
                model.setIdPenjualan(rs.getString("id_penjualan"));
                model.setTanggalPenjualan(rs.getString("tanggal"));
                model.setNilai(rs.getBigDecimal("nilai"));
                model.setBayar(rs.getBigDecimal("bayar"));
                model.setKembalian(rs.getBigDecimal("kembalian"));
                model.setDiskon(rs.getInt("diskon"));

                listPenjualan.add(model);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data: " + e.getMessage());
        }

        return listPenjualan;
    }

    @Override
    public List<ModelPenjualan> cariData(String keyword) {
        List<ModelPenjualan> listObat = new ArrayList<>();
        String sql
                = "SELECT id_penjualan, DATE_FORMAT(tgl_penjualan,'%d-%m-%Y') AS tanggal, nilai, bayar, kembalian, diskon "
                + "FROM tbl_penjualan "
                + "WHERE id_penjualan LIKE ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            // Mengisi parameter dengan keyword untuk pencarian
            st.setString(1, "%" + keyword + "%");
            st.setString(2, "%" + keyword + "%");

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ModelPenjualan model = new ModelPenjualan();
                    model.setIdPenjualan(rs.getString("Ref"));
                    model.setTanggalPenjualan(rs.getString("Tanggal"));
                    model.setNilai(rs.getBigDecimal("Total"));
                    model.setBayar(rs.getBigDecimal("Bayar"));
                    model.setKembalian(rs.getBigDecimal("Kembalian"));
                    model.setDiskon(rs.getInt("Diskon"));

                    listObat.add(model);
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari data: " + e.getMessage());
        }

        return listObat;
    }

    @Override
    public String noTransaksi() {
        String prefix = "REF" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        String randomDigits = String.format("%04d", (int) (Math.random() * 10000));

        return prefix + randomDigits;
    }

    @Override
    public void printNota(String nofak) {
        JasperReport jasRep;
        JasperPrint jasPri;
        JasperDesign jasdes;
        Map<String, Object> param = new HashMap<String, Object>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            File report = new File("src/com/app/form/laporan/StrukPenjualan.jrxml");
            param.put("id_penjualan", nofak);
            jasdes = JRXmlLoader.load(report);
            jasRep = JasperCompileManager.compileReport(jasdes);
            jasPri = JasperFillManager.fillReport(jasRep, param, conn);
            //         JasperViewer.viewReport(jasPri,false);
            JasperViewer jasperViewer = new JasperViewer(jasPri, false);
            JDialog dialog = new JDialog();//the owner
            dialog.setContentPane(jasperViewer.getContentPane());
            dialog.setSize(jasperViewer.getSize());
            dialog.setTitle("NOTA");
            dialog.setAlwaysOnTop(true);
            dialog.setModalityType(Dialog.ModalityType.MODELESS);
            dialog.setModal(true);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FormMenuUtama.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(FormMenuUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void hapusPenjualan( ModelPenjualan model) {
        String idPenjualan = model.getIdPenjualan();
        if (idPenjualan == null || idPenjualan.isEmpty()) {
            System.out.println("ID pembelian tidak boleh kosong.");
        }

        String query = "{CALL hapus_penjualan(?)}"; // Nama stored procedure

        try (com.mysql.cj.jdbc.CallableStatement stmt = (com.mysql.cj.jdbc.CallableStatement) conn.prepareCall(query)) {
            // Set parameter untuk stored procedure
            stmt.setString(1, idPenjualan);

            // Eksekusi stored procedure
            stmt.execute();

            System.out.println("Pembelian dengan ID " + idPenjualan + " berhasil dihapus.");
            

        } catch (SQLException e) {
            System.out.println("Gagal menghapus pembelian: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
