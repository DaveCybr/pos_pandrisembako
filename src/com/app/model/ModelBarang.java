/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.model;

import java.sql.Timestamp;

/**
 *
 * @author queen
 */
public class ModelBarang {
    private char idBarang;
    private String nama_barang;
    private String satuan;
    private double stok;

    public char getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(char idBarang) {
        this.idBarang = idBarang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public double getStok() {
        return stok;
    }

    public void setStok(double stok) {
        this.stok = stok;
    }
}
