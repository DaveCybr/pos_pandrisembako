package com.app.dao;

import com.app.config.ConnectionDB;
import com.app.main.FormMenuUtama;
import com.app.model.ModelPembelian;
import com.app.service.ServicePembelian;
import java.awt.Dialog;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author queen
 */
public class PembelianDAO  implements ServicePembelian {
    
    private Connection conn;

    public PembelianDAO() {
        conn = ConnectionDB.getConnection();
    }

    @Override
    public void tambahData(ModelPembelian model) {
        String sql =
                "INSERT INTO tbl_pembelian (id_pembelian, tgl_pembelian, nilai) VALUES (?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, model.getIdPembelian());
            st.setDate(2, java.sql.Date.valueOf(model.getTglPembelian()));
            st.setBigDecimal(3, model.getNilai());
//            st.setDouble(4, model.getTotal());
//            st.setDouble(5, model.getBayar());
//            st.setDouble(6, model.getKembalian());
//            st.setDouble(7, model.getDiskon());
//            st.setDouble(8, model.getIdUser());
            st.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Transaksi berhasil disimpan!");
        } catch (SQLException e) {
            System.out.println("Gagal menambahkan data: " + e.getMessage());
        }
    }

    @Override
    public void perbaruiData(ModelPembelian pembelian) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void hapusData(String idPembelian) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ModelPembelian> getAllData() {
    List<ModelPembelian> list = new ArrayList<>();
    String query = "SELECT * FROM tbl_pembelian";

    try (PreparedStatement st = conn.prepareStatement(query);
         ResultSet rs = st.executeQuery()) {

        while (rs.next()) {
            ModelPembelian pembelian = new ModelPembelian();

            pembelian.setIdPembelian(rs.getString("id_pembelian")); // dari int ke String   // dari int ke String

            // Ambil java.sql.Date dari DB, konversi ke LocalDate
            pembelian.setTglPembelian(rs.getDate("tgl_pembelian").toLocalDate());

            // Gunakan BigDecimal untuk nilai uang
            pembelian.setNilai(rs.getBigDecimal("nilai"));

            list.add(pembelian);
        }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



    @Override
    public ModelPembelian getById(String idPembelian) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ModelPembelian> cariData(String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String noFakturPembelian() {
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
            Class.forName("com.mysql.jdbc.Driver");
            File report = new File("src/com/app/form/pos/ASW.jrxml");
            param.put("Ref", nofak);
            jasdes = JRXmlLoader.load(report);
            jasRep = JasperCompileManager.compileReport(jasdes);
            jasPri = JasperFillManager.fillReport(jasRep,param, conn);
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
}
