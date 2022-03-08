package com.example.taxreports.bean;

import java.io.Serializable;

public class RegisterBean implements Serializable {

    private int id;
    String role;

    private String login;
    private String password;
    private String salt;

    public RegisterBean() {
        // default constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}