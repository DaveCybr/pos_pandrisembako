/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.service;

import com.app.model.ModelSupplier;
import java.util.List;

/**
 *
 * @author Rohim
 */
public interface ServiceSupplier {
    void tambahData (ModelSupplier model);
    void perbaruiData (ModelSupplier model);
    void hapusData (ModelSupplier model);
    
    List<ModelSupplier> tampilData();
    List<ModelSupplier> pencarianData(String id);
    
    boolean validasiNamaSupplier(ModelSupplier model);
    
}
