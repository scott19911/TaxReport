package com.example.taxreports.bean;

import java.io.Serializable;

public class UserBean implements Serializable {

    private String role;
    private String locale;
    private int id;

    public UserBean() {//default
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "role='" + role + '\'' +
                ", id=" + id +
                '}';
    }
}