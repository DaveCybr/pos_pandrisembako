/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.service;

import com.app.model.ModelUser;
import java.util.List;

/**
 *
 * @author Rohim
 */
public interface ServiceUser {
    void tambahData (ModelUser model);
    void perbaruiData (ModelUser model);
    void hapusData (ModelUser model);
    
    List<ModelUser> tampilData();
    List<ModelUser> pencarianData(String id);
    
    String generateSHA256(String password);
    ModelUser prosesLogin(ModelUser model);
    String generateRFID();

    
    boolean validasiPasswordLama(String username, String passwordLama);
    boolean pergantianPassword(String username, String passwordLama, String passwordBaru);
}
