/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.model;

import java.sql.Timestamp;

/**
 *
 * @author Rohim
 */
public class ModelUser {

    private int idUser;
    private String rfid_uid;
    private String nama;
    private String username;
    private String password;
    private String role;
    private String alamat;
    private String no_telepon;
    private Timestamp created_at;
    private Timestamp updated_at;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getRfidUid() {
        return rfid_uid;
    }

    public void setRfidUid(String rfid_uid) {
        this.rfid_uid = rfid_uid;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_telepon() {
        return no_telepon;
    }

    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.created_at = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updated_at = updatedAt;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public Timestamp getUpdatedAt() {
        return updated_at;
    }

}
