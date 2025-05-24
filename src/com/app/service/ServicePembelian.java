package com.app.service;

import com.app.model.ModelPembelian;
import java.util.List;

public interface ServicePembelian {
    void tambahData(ModelPembelian pembelian);
    void perbaruiData(ModelPembelian pembelian);
    void hapusData(String idPembelian);
    
    List<ModelPembelian> getAllData();
    ModelPembelian getById(String idPembelian);
    List<ModelPembelian> cariData(String keyword);
}
