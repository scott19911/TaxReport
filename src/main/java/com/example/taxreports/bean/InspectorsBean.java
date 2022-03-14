package com.example.taxreports.bean;

public class InspectorsBean {
    int userId;
    String fName;
    String lName;
    String email;

    public InspectorsBean() {
        //default
    }

    public InspectorsBean(int userId, String fName, String lName, String email) {
        this.email = email;
        this.userId = userId;
        this.fName = fName;
        this.lName = lName;
    }
    public InspectorsBean(int userId, String fName, String lName) {
        this.userId = userId;
        this.fName = fName;
        this.lName = lName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getlName() {
        return lName;
    }
    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "InspectorsBean{" +
                "userId=" + userId +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                '}';
    }
}
