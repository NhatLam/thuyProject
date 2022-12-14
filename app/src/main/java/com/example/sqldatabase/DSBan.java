package com.example.sqldatabase;

public class DSBan {

    private int IdBan;
    private String TenBan;

    public DSBan(int idBan, String tenBan) {
        IdBan = idBan;
        TenBan = tenBan;
    }

    public int getIdBan() {
        return IdBan;
    }

    public void setIdBan(int idBan) {
        IdBan = idBan;
    }

    public String getTenBan() {
        return TenBan;
    }

    public void setTenBan(String tenBan) {
        TenBan = tenBan;
    }
}
