package com.example.taxreports.bean;

import java.io.Serializable;

public class UserBean implements Serializable {

    private String role;
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

    @Override
    public String toString() {
        return "UserBean{" +
                "role='" + role + '\'' +
                ", id=" + id +
                '}';
    }
}