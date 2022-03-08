package com.example.taxreports.bean;

import java.io.Serializable;


public class CommentsBean implements Serializable {
    int idReport;
    int idInsp;
    int act;

    public int getAct() {
        return act;
    }

    public void setAct(int act) {
        this.act = act;
    }

    public CommentsBean() {
    }
    public int getIdReport() {
        return idReport;
    }

    public void setIdReport(int idReport) {
        this.idReport = idReport;
    }

    public int getIdInsp() {
        return idInsp;
    }

    public void setIdInsp(int idInsp) {
        this.idInsp = idInsp;
    }
}
