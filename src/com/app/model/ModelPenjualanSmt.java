/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.model;

/**
 *
 * @author queen
 */
public class ModelPenjualanSmt {
    private ModelPenjualanRinci modelPenjualanRinci;
    private ModelBarang modelBarang;
    
    public ModelPenjualanSmt() {
        this.modelBarang = new ModelBarang(); // Pastikan ini ada
        this.modelPenjualanRinci = new ModelPenjualanRinci();
    }

    public ModelPenjualanRinci getModelPenjualanRinci() {
        return modelPenjualanRinci;
    }

    public void setModelPenjualanRinci(ModelPenjualanRinci modelPenjualanRinci) {
        this.modelPenjualanRinci = modelPenjualanRinci;
    }

    public ModelBarang getModelBarang() {
        return modelBarang;
    }

    public void setModelBarang(ModelBarang modelBarang) {
        this.modelBarang = modelBarang;
    }
}
