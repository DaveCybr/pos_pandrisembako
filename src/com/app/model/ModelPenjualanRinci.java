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
public class ModelPenjualanRinci {

    private String idPenjualanRinci;
    private String idPenjualan;
    private BigDecimal qty;
    private BigDecimal nilai;
    private ModelBarang modelBarang;
    private ModelPenjualan modelPenjualan;

    // Constructor
    public ModelPenjualanRinci() {
    }

    public ModelPenjualanRinci(String idPenjualanRinci, String idPenjualan, BigDecimal qty, BigDecimal nilai, ModelBarang modelBarang, ModelPenjualan modelPenjualan) {
        this.idPenjualanRinci = idPenjualanRinci;
        this.idPenjualan = idPenjualan;
        this.qty = qty;
        this.nilai = nilai;
        this.modelBarang = modelBarang;
        this.modelPenjualan = modelPenjualan;
    }

    public ModelPenjualan getModelPenjualan() {
        return modelPenjualan;
    }

    public void setModelPenjualan(ModelPenjualan modelPenjualan) {
        this.modelPenjualan = modelPenjualan;
    }

    public ModelBarang getModelBarang() {
        return modelBarang;
    }

    public void setModelBarang(ModelBarang modelBarang) {
        this.modelBarang = modelBarang;
    }

    // Getter dan Setter
    public String getIdPenjualanRinci() {
        return idPenjualanRinci;
    }

    public void setIdPenjualanRinci(String idPenjualanRinci) {
        this.idPenjualanRinci = idPenjualanRinci;
    }

    public String getIdPenjualan() {
        return idPenjualan;
    }

    public void setIdPenjualan(String idPenjualan) {
        this.idPenjualan = idPenjualan;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getNilai() {
        return nilai;
    }

    public void setNilai(BigDecimal nilai) {
        this.nilai = nilai;
    }

    @Override
    public String toString() {
        return "ModelPenjualanRinci{"
                + "idPenjualanRinci='" + idPenjualanRinci + '\''
                + ", idPenjualan='" + idPenjualan + '\''
                + ", qty=" + qty
                + ", nilai=" + nilai
                + '}';
    }
}
