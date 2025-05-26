/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.service;

import com.app.model.ModelPembelianSmt;
import java.util.List;

/**
 *
 * @author queen
 */
public interface ServicePembelianSmt {
    
    void tambahData (ModelPembelianSmt model);
    void updateData (ModelPembelianSmt model);
    void hapusData (ModelPembelianSmt model);
    void updateQty (String idObat, int Qty);
    List<ModelPembelianSmt> tampilData();
}
