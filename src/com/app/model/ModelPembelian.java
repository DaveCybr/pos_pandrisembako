/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.model;

/**
 *
 * @author queen
 */
public class ModelPembelian {

    private char idPembelian;
    private char idSupplier;
    private String Uraian;
    private String tglPembelian;
    private double Nilai;
    private String statusPembayaran;
    private double Bayar;
    private ModelSupplier supplier;
    
    public char getIdPembelian() {
        return idPembelian;
    }

    public void setIdPembelian(char idPembelian) {
        this.idPembelian = idPembelian;
    }

    public char getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(char idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getUraian() {
        return Uraian;
    }

    public void setUraian(String Uraian) {
        this.Uraian = Uraian;
    }

    public String getTglPembelian() {
        return tglPembelian;
    }

    public void setTglPembelian(String tglPembelian) {
        this.tglPembelian = tglPembelian;
    }

    public double getNilai() {
        return Nilai;
    }

    public void setNilai(double Nilai) {
        this.Nilai = Nilai;
    }

    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public double getBayar() {
        return Bayar;
    }

    public void setBayar(double Bayar) {
        this.Bayar = Bayar;
    }

    public ModelSupplier getSupplier() {
        return supplier;
    }

    public void setSupplier(ModelSupplier supplier) {
        this.supplier = supplier;
    }
}
