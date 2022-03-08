package com.example.taxreports.bean;


import java.util.Date;

public class ReportBean {

    int id;
    public static final int STATUS_FILED = 1;
    public static final int STATUS_ACCEPTED = 2;
    public static final int STATUS_REJECT = 3;
    public static final int STATUS_EDIT = 4;
    public static final int STATUS_UPDATE = 5;
    public static final int STATUS_IN_PROCESSING = 6;
    int status;
    String creater;
    String inspector;
    String comments;
    Date date;
    String filePath;
    String createrName;

    public ReportBean() {//default

    }

    public ReportBean(int id, int status, String inspector, String comments, Date date, String filePath, String description) {
        this.status = status;
        this.id= id;
        this.inspector = inspector;
        this.comments = comments;
        this.date = date;
        this.filePath = filePath;
        this.description = description;
    }

    public ReportBean(int id, int status, String creater, String inspector,
                      String comments, Date date, String filePath, String createrName , String description) {
        this.id = id;
        this.status = status;
        this.creater = creater;
        this.inspector = inspector;
        this.comments = comments;
        this.date = date;
        this.filePath = filePath;
        this.createrName =createrName;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    @Override
    public String toString() {
        return "ReportBean{" +
                "id=" + id +
                ", status=" + status +
                ", creater='" + creater + '\'' +
                ", inspector='" + inspector + '\'' +
                ", comments='" + comments + '\'' +
                ", date=" + date +
                ", filePath='" + filePath + '\'' +
                ", createrName='" + createrName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
