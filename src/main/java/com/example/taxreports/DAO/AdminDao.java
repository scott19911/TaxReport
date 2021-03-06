package com.example.taxreports.DAO;

import com.example.taxreports.TableColums;
import com.example.taxreports.bean.InspectorsBean;
import com.example.taxreports.bean.RegisterBean;
import com.example.taxreports.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.taxreports.Queris.*;

/**
 * The class is responsible for making changes to the database of a user with the administrator role
 */
public class AdminDao {

    private static final Logger log = Logger.getLogger(AdminDao.class);

    /**
     * @see AdminDao#editInspector(int, String, String) allows you to edit the name and surname of the inspector by id
     * @param id inspector id
     * @param fName First Name
     * @param sName Surname
     */
    public void editInspector(int id, String fName, String sName){

        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(UPDATE_INSPECTORS_BY_ID)){
            stm.setString(1, fName);
            stm.setString(2,sName);
            stm.setInt(3,id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot edit such user");
        }
    }

    /**
     * @see AdminDao#createInspector(int, String, String) Adding Inspector Information
     * @param userId inspector ID obtained during user registration
     * @see UserDAO#registerUser(RegisterBean)
     * @param fName First Name
     * @param lName Surname
     */
    public void createInspector(int userId, String fName,String lName){

        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(INSERT_INTO_INSPECTORS)){
            stm.setString(1, fName);
            stm.setString(2,lName);
            stm.setInt(3,userId);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot register such user");
        }
    }

    /**
     * allows deleted inspectors by id
     * @param id inspector id
     */
    public void deleteInspector(int id){

        UserDAO userDAO = new UserDAO();
        userDAO.deleteUser(id);

        try (Connection con= ConnectionPool.getInstance().getConnection();
            PreparedStatement  stm = con.prepareStatement(DELETE_FROM_INSPECTORS_WHERE_USER_ID)){

            stm.setInt(1,id);
            stm.executeUpdate();

        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot find such user");
        }

    }

    /**
     *returns avalaible  list of inspectors
     * @return List<InspectorsBean>
     */
    public static List<InspectorsBean> getInspectorsList(){

        List<InspectorsBean> list = new ArrayList<>();
        int userID;
        String fName;
        String lName;
        String email;
        UserDAO userDAO = new UserDAO();
        try(Connection con = ConnectionPool.getInstance().getConnection(); Statement stm = con.createStatement()) {
            con.setAutoCommit(false);
            ResultSet rs = stm.executeQuery(SELECT_ALL_INSPECTORS);
            while (rs.next()){
                userID =rs.getInt(TableColums.USER_ID);
                fName =rs.getString(TableColums.FNAME);
                lName= rs.getString(TableColums.LNAME);
                email = userDAO.getEmailById(userID);
                list.add(new InspectorsBean(userID,fName,lName,email));
            }
            con.setAutoCommit(true);
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot load user. Try again.");
        }
        return list;
    }
}
