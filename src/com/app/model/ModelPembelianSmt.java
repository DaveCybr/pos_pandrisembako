/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.model;

/**
 *
 * @author queen
 */
public class ModelPembelianSmt {
    private ModelPembelianRinci modelPembRinci;
    private ModelBarang modelBarang;
    
     public ModelPembelianSmt() {
        this.modelBarang = new ModelBarang(); // Pastikan ini ada
        this.modelPembRinci = new ModelPembelianRinci();
    }

    public ModelPembelianRinci getModelPembRinci() {
        return modelPembRinci;
    }

    public void setModelPembRinci(ModelPembelianRinci modelPembRinci) {
        this.modelPembRinci = modelPembRinci;
    }

    public ModelBarang getModelBarang() {
        return modelBarang;
    }

    public void setModelBarang(ModelBarang modelBarang) {
        this.modelBarang = modelBarang;
    }

}
