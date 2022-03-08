package com.example.taxreports.bean;

public class EntytiBean {
    String company;
    String okpo;
    int userId;

    public EntytiBean() {
        //default constructor
    }


    public EntytiBean(String company, String okpo, int userId) {
        this.company = company;
        this.okpo = okpo;
        this.userId = userId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOkpo() {
        return okpo;
    }

    public void setOkpo(String okpo) {
        this.okpo = okpo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "EntytiBean{" +
                "company='" + company + '\'' +
                ", okpo='" + okpo + '\'' +
                ", userId=" + userId +
                '}';
    }
}
