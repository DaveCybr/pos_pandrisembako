/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author queen
 */
public class ModelPembelian {

    private String idPembelian;
    private String idSupplier;
    private String uraian; // huruf kecil di awal
    private LocalDate tglPembelian; // pakai LocalDate lebih baik daripada String
    private BigDecimal nilai; // ganti double untuk data uang
    private String statusPembayaran;
    private BigDecimal bayar; // ganti double untuk data uang
    private ModelSupplier supplier;

    public String getIdPembelian() {
        return idPembelian;
    }

    public void setIdPembelian(String idPembelian) {
        this.idPembelian = idPembelian;
    }

    public String getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(String idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getUraian() {
        return uraian;
    }

    public void setUraian(String uraian) {
        this.uraian = uraian;
    }

    public LocalDate getTglPembelian() {
        return tglPembelian;
    }

    public void setTglPembelian(LocalDate tglPembelian) {
        this.tglPembelian = tglPembelian;
    }

    public BigDecimal getNilai() {
        return nilai;
    }

    public void setNilai(BigDecimal nilai) {
        this.nilai = nilai;
    }

    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public BigDecimal getBayar() {
        return bayar;
    }

    public void setBayar(BigDecimal bayar) {
        this.bayar = bayar;
    }

    public ModelSupplier getSupplier() {
        return supplier;
    }

    public void setSupplier(ModelSupplier supplier) {
        this.supplier = supplier;
    }

}