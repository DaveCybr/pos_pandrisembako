/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.service;

import com.app.model.ModelPenjualanSmt;
import java.util.List;

/**
 *
 * @author queen
 */
public interface ServicePenjualanSmt {
    
    void tambahData (ModelPenjualanSmt model);
    void updateData (ModelPenjualanSmt model);
    void hapusData (ModelPenjualanSmt model);
    void updateQty (String idObat, int Qty);
    List<ModelPenjualanSmt> tampilData();
}
