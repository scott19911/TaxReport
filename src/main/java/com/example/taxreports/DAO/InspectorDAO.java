package com.example.taxreports.DAO;


import com.example.taxreports.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.taxreports.Queris.*;

public class InspectorDAO {

    private static final Logger log = Logger.getLogger(InspectorDAO.class);


    public void updateReportStatus(int statusId,int idReport, int idInspector){

    try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(UPDATE_REPORT_STATUS)){
       stm.setInt(1, statusId);
       stm.setInt(2,idInspector);
       stm.setInt(3,idReport);
       stm.executeUpdate();
    } catch (SQLException e) {
        log.error(e);
        throw new RuntimeException("Sorry, cannot update status");
    }
    }

    public void updateReportWithComm(int statusId,int idReport, int idInspector, String comm){

        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(UPDATE_REPORT_STATUS_WITH_COMMENT)){
            stm.setInt(1, statusId);
            stm.setInt(2,idInspector);
            stm.setString(3,comm);
            stm.setInt(4,idReport);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot update status");
        }
    }
}
