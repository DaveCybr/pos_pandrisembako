/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this
 * template
 */
package com.app.service;

import com.app.model.ModelPembelianRinci;
import java.util.List;

/**
 *
 * @author queen
 */
public interface ServicePembelianRinci {

    void tambah_detail_P(ModelPembelianRinci model);

    void sumTotal(ModelPembelianRinci model);

    void hapusDataSmt();

    List<ModelPembelianRinci> tampil_detail_P(String id);

    List<ModelPembelianRinci> search(String keyword);
}
