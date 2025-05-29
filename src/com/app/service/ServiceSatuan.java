/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.service;

import com.app.model.ModelSatuan;
import java.util.List;

/**
 *
 * @author queen
 */
public interface ServiceSatuan {
    void tambahData (ModelSatuan model);
    void perbaruiData (ModelSatuan model);
    void hapusData (ModelSatuan model);
    
    List<ModelSatuan> tampilData();
    
    List<ModelSatuan> pencarianData(String id);
    
//    List<ModelMasterObat> ambilNamaObat();
//    String ambilNamaObatId(int id);

//    public List<ModelSatuan> searchByBarcode(String id);
    
}
