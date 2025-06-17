/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author queen
 */
public class ModelBarang {

    private String idBarang;
//    private String nama_supplier;
    private String nama_barang;
    private String barcode;
    private BigDecimal harga;
    private String satuan;
    private String id_satuan;
    private BigDecimal stok;

    public String getId_satuan() {
        return id_satuan;
    }

    public void setId_satuan(String id_satuan) {
        this.id_satuan = id_satuan;
    }

    public ModelSupplier getModelSupplier() {
        return modelSupplier;
    }

    public void setModelSupplier(ModelSupplier modelSupplier) {
        this.modelSupplier = modelSupplier;
    }
    
    private ModelSupplier modelSupplier;

    
    
    public String getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(String idBarang) {
        this.idBarang = idBarang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }
    
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public BigDecimal getStok() {
        return stok;
    }

    public void setStok(BigDecimal stok) {
        this.stok = stok;
    }
}
