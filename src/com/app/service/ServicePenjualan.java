/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.service;

import com.app.model.ModelPenjualan;
import java.util.List;

/**
 *
 * @author queen
 */
public interface ServicePenjualan {
    
    void tambahPenjualan (ModelPenjualan model);
    void hapusPenjualan (ModelPenjualan model);
    
    List<ModelPenjualan> tampilPenjualan(String id);
    List<ModelPenjualan> cariData(String keyword);
    
    String noTransaksi();
    void printNota (String id);
}
