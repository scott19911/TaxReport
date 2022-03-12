package com.example.taxreports.DAO;

import com.example.taxreports.TableColums;
import com.example.taxreports.bean.ReportBean;
import com.example.taxreports.util.ConnectionPool;
import com.example.taxreports.util.S3Util;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.taxreports.Queris.*;
import static com.example.taxreports.TableColums.*;

public class ReportsDAO {

   private static final Logger log = Logger.getLogger(ReportsDAO.class);

   private final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
   private Date date;
   private String comments;
   private int status;
   private String filePath;
   private String description;
   private String insp;


    public void writeToDB( String file, int userId, String description) {
        status = ReportBean.STATUS_FILED;
        date= new Date();

        try(Connection con = ConnectionPool.getInstance().getConnection(); PreparedStatement pstm = con.prepareStatement(INSERT_REPORT)) {

            pstm.setInt(1, status);
            pstm.setInt(2,userId);
            pstm.setTimestamp(3, Timestamp.valueOf(DATE_FORMAT.format(date)));
            pstm.setString(4, file);
            pstm.setString(5,description);
            pstm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot insert file");
        }

    }
    public  List<ReportBean> getUserReport (int userId) {

        List<ReportBean> list = new ArrayList<>();
        int id;

        try(Connection con = ConnectionPool.getInstance().getConnection(); PreparedStatement pstm = con.prepareStatement(SELECT_USER_REPORTS)) {
            con.setAutoCommit(false);
            pstm.setInt(1, userId);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                status =rs.getInt(REPORT_STATUS_ID);
                id =rs.getInt(ID);
                date = DATE_FORMAT.parse(rs.getString(REPORT_DATE));
                comments =rs.getString(REPORT_COMMENTS);
                filePath= rs.getString(REPORT_FILE_PATH);
                description = rs.getString(REPORT_DESCRIPTION);
                insp = rs.getString(REPORT_INSPECTOR_NAME);
                list.add(new ReportBean(id,status,insp,comments,date,filePath,description));

            }
            con.setAutoCommit(true);

        } catch (SQLException | ParseException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot load info");
        }
        return list;
    }

    public void deleteRepo(int id, String path) throws IOException {
        S3Util.deleteFile(path);
            if( getReportStatus(id) == ReportBean.STATUS_FILED) {
                try (Connection con = ConnectionPool.getInstance().getConnection();
                     PreparedStatement stm = con.prepareStatement(DELETE_FROM_REPORT_WHERE_ID)) {
                    stm.setInt(1, id);
                    stm.executeUpdate();
                } catch (SQLException e) {
                    log.error(e);
                    throw new RuntimeException("Sorry, cannot delete report.");

                }
            } else {
                log.warn("cannot delete report");
                throw new RuntimeException("Sorry, cannot delete report check status.");
            }
    }
    public void editReport(int id, String filePath){

        status = ReportBean.STATUS_UPDATE;
        date= new Date();

        try(Connection con = ConnectionPool.getInstance().getConnection(); PreparedStatement pstm = con.prepareStatement(UPDATE_REPORT_SET_STATUS_ID_DATE_XML_WHERE_ID)) {
            pstm.setInt(1, status);
            pstm.setString(3,filePath);
            pstm.setTimestamp(2, Timestamp.valueOf(DATE_FORMAT.format(date)));
            pstm.setInt(4, id);
            pstm.executeUpdate();
        }catch (SQLException e){
            log.error(e);
            throw new RuntimeException("Sorry, cannot load file");
        }

    }

    public List<ReportBean> getAvelaibleReport (int inspetorId) {

        List<ReportBean> list = new ArrayList<>();

        try(Connection con = ConnectionPool.getInstance().getConnection(); PreparedStatement pstm = con.prepareStatement(SELECT_AVELAIBLE_REPORT)) {
            con.setAutoCommit(false);
            pstm.setInt(1, ReportBean.STATUS_FILED);
            pstm.setInt(2, ReportBean.STATUS_IN_PROCESSING);
            pstm.setInt(3, ReportBean.STATUS_UPDATE);
            pstm.setInt(4, inspetorId);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()){

                status = rs.getInt(REPORT_STATUS_ID);
                int id = rs.getInt(ID);
                date = DATE_FORMAT.parse(rs.getString(REPORT_DATE));
                comments =rs.getString(REPORT_COMMENTS);
                filePath= rs.getString(REPORT_FILE_PATH);
                description = rs.getString(REPORT_DESCRIPTION);
                insp = rs.getString(REPORT_INSPECTOR_NAME);
                String creater = rs.getString(REPORT_CREATER_ID);
                String createrName = "";
                String user = rs.getString(USER);
                String company = rs.getString(REPORT_COMPANY_NAME);
                if(user != null){
                    createrName = user;
                } else if (company != null){
                    createrName = company;
                }
                list.add(new ReportBean(id, status, creater, insp, comments, date, filePath, createrName, description));

            }

            con.setAutoCommit(true);

        } catch (SQLException | ParseException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot load available report.");
        }
        return list;
    }

    public List<ReportBean> getArchive () {

        List<ReportBean> list = new ArrayList<>();
        int id;
        String creater;
        String createrName = "";

        try(Connection con = ConnectionPool.getInstance().getConnection(); PreparedStatement pstm = con.prepareStatement(SELECT_ARCHIV_REPORT)) {

            pstm.setInt(1, ReportBean.STATUS_ACCEPTED);
            pstm.setInt(2, ReportBean.STATUS_REJECT);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                status =rs.getInt(REPORT_STATUS_ID);
                id =rs.getInt(ID);
                date = DATE_FORMAT.parse(rs.getString(REPORT_DATE));
                comments =rs.getString(REPORT_COMMENTS);
                filePath= rs.getString(REPORT_FILE_PATH);
                creater = rs.getString(REPORT_CREATER_ID);
                description = rs.getString(REPORT_DESCRIPTION);
                insp = rs.getString(REPORT_INSPECTOR_NAME);
                String user = rs.getString(USER);
                String company = rs.getString(REPORT_COMPANY_NAME);
                if(user != null){
                    createrName = user;
                } else if (company != null){
                    createrName = company;
                }
                list.add(new ReportBean(id, status, creater, insp, comments, date, filePath, createrName , description));
            }

        } catch (SQLException | ParseException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot load archiv.");
        }
        return list;
    }

    public int getReportStatus(int id){
        int statusId = 0;
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(SELECT_REPORT_STATUS_BY_ID)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                statusId = rs.getInt(REPORT_STATUS_ID);
            }

            return statusId;
        }catch (SQLException e){
            log.error(e);
            throw new RuntimeException("Sorry,no such file.");
        }
    }

    public int getInspector(int id){
        int inspectorId = 0;
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(SELECT_REPORT_INSPECTOR)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                inspectorId = rs.getInt(REPORT_INSPECTOR_ID);
            }
            return inspectorId;
        }catch (SQLException e){
            log.error(e);
            throw new RuntimeException("Sorry, cannot find inspector.");
        }
    }
    public String getPath(int id) throws IOException {

            try (Connection con = ConnectionPool.getInstance().getConnection();
                 PreparedStatement stm = con.prepareStatement(SELECT_FILE_PATH_FROM_REPORT_WHERE_ID)) {
                stm.setInt(1, id);
               ResultSet rs = stm.executeQuery();
               while (rs.next()){
                   return rs.getString(REPORT_FILE_PATH);
               }

            } catch (SQLException e) {
                log.error(e);
                throw new RuntimeException("Sorry, cannot finde file.");

            }
            return null;
        }

}
