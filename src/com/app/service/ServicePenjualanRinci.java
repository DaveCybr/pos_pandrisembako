/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this
 * template
 */
package com.app.service;

import com.app.model.ModelPenjualanRinci;
import java.util.List;

/**
 *
 * @author queen
 */
public interface ServicePenjualanRinci {
    void tambah_detail_P(ModelPenjualanRinci model);

    void sumTotal(ModelPenjualanRinci model);

    void hapusDataSmt();

    List<ModelPenjualanRinci> tampil_detail_P(String id);

    List<ModelPenjualanRinci> search(String keyword);
}
