package com.example.taxreports.DAO;

import com.example.taxreports.TableColums;
import com.example.taxreports.bean.IndividualBean;
import com.example.taxreports.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.taxreports.Queris.*;


public class IndividualDAO {

    private static final Logger log = Logger.getLogger(IndividualDAO.class);

    public void insertInd(int userId,String fName, String sName, String lName, String tin){

        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(INSERT_INTO_INDIVIDUAL)){
            stm.setInt(1, userId);
            stm.setString(2,fName);
            stm.setString(3,sName);
            stm.setString(4,lName);
            stm.setString(5,tin);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot register such users");
        }
    }

    public void updateFnameInd (int id, String fName){

        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(UPDATE_INDIVIDUAL_FNAME)){
            stm.setString(1,fName);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot update First name");
        }
    }
    public void updateSnameInd (int id, String sName){

        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(UPDATE_INDIVIDUAL_SNAME)){
            stm.setString(1,sName);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot update Second name");
        }
    }
    public void updateLnameInd (int id, String lName){

        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(UPDATE_INDIVIDUAL_LNAME)){
            stm.setString(1,lName);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot update Last name");
        }
    }
    public void updateTinInd (int id, String tin){

        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(UPDATE_INDIVIDUAL_TIN)){
            stm.setString(1,tin);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot update TIN. Check your input data");
        }
    }

    public static IndividualBean userInfo(int id){

       IndividualBean infInd = null;
        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(SELECT_INDIVIDUAL_BY_USER_ID)){
            con.setAutoCommit(false);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
           while (rs.next()){
               String fName = rs.getString(TableColums.FNAME);
               String sName = rs.getString(TableColums.S_NAME);
               String lName = rs.getString(TableColums.LNAME);
               String tin = rs.getString(TableColums.TIN);
               infInd =new IndividualBean(id,fName,sName,lName,tin);
           }

        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot load information try again");
        }
        return infInd;
    }
}
