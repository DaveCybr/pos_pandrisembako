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
public class ModelPembelianRinci {
    private ModelBarang modelBarang;
    private ModelPembelian modelPemb;
    private double qty;
    private BigDecimal nilai;
    
     public ModelPembelianRinci() {
        this.modelBarang = new ModelBarang(); // Pastikan ini ada
        this.modelPemb = new ModelPembelian();
    }
     
    public ModelBarang getModelBarang() {
        return modelBarang;
    }

    public void setModelBarang(ModelBarang modelBarang) {
        this.modelBarang = modelBarang;
    }

    public ModelPembelian getModelPemb() {
        return modelPemb;
    }

    public void setModelPemb(ModelPembelian modelPemb) {
        this.modelPemb = modelPemb;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public BigDecimal getNilai() {
        return nilai;
    }

    public void setNilai(BigDecimal nilai) {
        this.nilai = nilai;
    }
}
