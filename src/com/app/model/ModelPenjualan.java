/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.model;

import java.math.BigDecimal;

/**
 *
 * @author queen
 */
public class ModelPenjualan {

    private String idPenjualan;
    private BigDecimal bayar;
    private BigDecimal nilai;
    private BigDecimal kembalian;
    private int diskon;
    private String tanggalPenjualan;

    public ModelPenjualan() {
    }

    public ModelPenjualan(String idPenjualan, BigDecimal bayar, BigDecimal nilai, String tanggalPenjualan) {
        this.idPenjualan = idPenjualan;
        this.bayar = bayar;
        this.nilai = nilai;
        this.tanggalPenjualan = tanggalPenjualan;
    }

    public BigDecimal getKembalian() {
        return kembalian;
    }

    public void setKembalian(BigDecimal kembalian) {
        this.kembalian = kembalian;
    }

    public int getDiskon() {
        return diskon;
    }

    public void setDiskon(int diskon) {
        this.diskon = diskon;
    }

    // Getter dan Setter
    public String getIdPenjualan() {
        return idPenjualan;
    }

    public void setIdPenjualan(String idPenjualan) {
        this.idPenjualan = idPenjualan;
    }

    public BigDecimal getBayar() {
        return bayar;
    }

    public void setBayar(BigDecimal bayar) {
        this.bayar = bayar;
    }

    public BigDecimal getNilai() {
        return nilai;
    }

    public void setNilai(BigDecimal nilai) {
        this.nilai = nilai;
    }

    public String getTanggalPenjualan() {
        return tanggalPenjualan;
    }

    public void setTanggalPenjualan(String tanggalPenjualan) {
        this.tanggalPenjualan = tanggalPenjualan;
    }

    @Override
    public String toString() {
        return "ModelPenjualan{"
                + "idPenjualan='" + idPenjualan + '\''
                + ", bayar=" + bayar
                + ", nilai=" + nilai
                + ", tanggalPenjualan=" + tanggalPenjualan
                + '}';
    }
}
