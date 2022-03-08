package com.example.taxreports.bean;

public class IndividualBean {
    int userId;
    String fName;
    String sName;
    String lName;
    String tin;

    public IndividualBean(int userId, String fName, String sName, String lName, String tin) {
        this.userId = userId;
        this.fName = fName;
        this.sName = sName;
        this.lName = lName;
        this.tin = tin;
    }

    public IndividualBean() {
        //default constructor
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    @Override
    public String toString() {
        return "IndividualBean{" +
                "userId=" + userId +
                ", fName='" + fName + '\'' +
                ", sName='" + sName + '\'' +
                ", lName='" + lName + '\'' +
                ", tin='" + tin + '\'' +
                '}';
    }
}
