package com.example.taxreports.DAO;

import com.example.taxreports.TableColums;
import com.example.taxreports.bean.RegisterBean;
import com.example.taxreports.bean.UserBean;
import com.example.taxreports.util.ConnectionPool;
import com.example.taxreports.util.SecurityPassword;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

import static com.example.taxreports.Queris.*;
import static com.example.taxreports.TableColums.AVALAIBLE;
import static com.example.taxreports.TableColums.TIME;

public class UserDAO {
   private static final Logger log = Logger.getLogger(UserDAO.class);
   private final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
    public static final long TIME_VERIFI = 900000L;
    public UserBean authenticateUser(RegisterBean registerBean)
    {
        String login = registerBean.getLogin(); //Assign user entered values to temporary variables.
        String password = registerBean.getPassword();
        ResultSet resultSet ;
        String roleDB;
        int idDB;
        String loginDB ;
        String passwordDB;

        try(Connection con = ConnectionPool.getInstance().getConnection();Statement statement = con.createStatement() )
        {
            resultSet = statement.executeQuery(SELECT_ALL_USERS); //the table name is users and userName,password are columns. Fetching all the records and storing in a resultSet.

            while(resultSet.next()) // Until next row is present otherwise it return false
            {
                loginDB = resultSet.getString(TableColums.USER_LOGIN); //fetch the values present in database
                passwordDB = resultSet.getString(TableColums.PASSWORD);
                roleDB = resultSet.getString(TableColums.USER_ROLE);
                idDB = resultSet.getInt(TableColums.ID);

                if(login.equals(loginDB) && password.equals(passwordDB)) {
                    UserBean user = new UserBean();
                    user.setRole(roleDB);
                    user.setId(idDB);
                    return user; ////If the user entered values are already present in the database, which means user has already registered so return a SUCCESS message.
                }
            }

            return null; // Return appropriate message in case of failure
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, try again");
        }

    }

    public int registerUser(RegisterBean registerBean)
    {
        String role = registerBean.getRole();
        String login = registerBean.getLogin();
        String password = registerBean.getPassword();
        String salt = registerBean.getSalt();
        String email = registerBean.getEmail();
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);
            preparedStatement.setString(4, salt);
            preparedStatement.setString(5, email);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return   rs.getInt(1);
            }

        }
        catch(SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot register such user");
        }
        return 0;
    }

    public static String getUserRoleByID (int id){
        String role = null;

        try (Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(SELECT_ROLE_FROM_USERS_WHERE_ID)){

            pstm.setInt(1,id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()){
                role = rs.getString(TableColums.USER_ROLE);
            }

        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot find user role");
        }
        return role;
    }

    public void deleteUser(int id){
        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(DELETE_FROM_USERS_WHERE_ID)){

            stm.setInt(1,id);
            stm.executeUpdate();

        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot find such user");
        }

    }
    public static void updatePassword (int id, String password){
        SecurityPassword securityPassword = new SecurityPassword();
        String salt = securityPassword.getSalt();
        String newPassword = securityPassword.getHashPassword(password + salt);
        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(UPDATE_USERS_SET_PASSWORD_SALT_WHERE_ID)){
            stm.setString(1,newPassword);
            stm.setString(2, salt);
            stm.setInt(3, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry cannot update password");
        }
    }

    public void updateEmail (int id, String email){
        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(UPDATE_USERS_SET_EMAIL_WHERE_ID)){
            stm.setString(1,email);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry cannot update email");
        }
    }

    public String getSalByLogin (String login){
        String salt;
        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_SALT_FROM_USERS_WHERE_LOGIN)){
            stm.setString(1,login);

            ResultSet rs = stm.executeQuery();
            if (rs.next()){
                salt = rs.getString(TableColums.USER_SALT);
            } else {
                salt = null;
            }
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry cannot find information");
        }
        return salt;
    }

    public  int getIdByLogin (String login){
       int id = 0;
        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_ID_USER_BY_LOGIN)){
            stm.setString(1,login);
            ResultSet rs = stm.executeQuery();
            if (rs.next()){
                id = rs.getInt(TableColums.ID);
            }

        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry user missing");
        }
        return id;
    }
    public  String getLocaleById (int id){
        String locale = null;
        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_LOCALE_USER_BY_ID)){
            stm.setInt(1,id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()){
                locale = rs.getString(TableColums.LOCALE);
            }

        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry user missing");
        }
        return locale;
    }
    public void setLocaleById (int id, String locale){
        
        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(UPDATE_LOCALE_USER_BY_ID)){
            stm.setString(1,locale);
            stm.setInt(2,id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry user missing");
        }
    }
    public  String getEmailById (int id){
        String email = null;
        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_EMAIL_USER_BY_ID)){
            stm.setInt(1,id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()){
                email = rs.getString(TableColums.EMAIL);
            }

        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry user missing");
        }
        return email;
    }
    public int getIdByEmail (String email){
        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_ID_BY_EMAIL)){
            stm.setString(1,email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()){
               return rs.getInt(TableColums.ID);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry user missing");
        }
        return 0;
    }
    public void addRestorePassword (int id, String key){
       Date date = new Date();
        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(INSERT_NEW_REQUEST_RESTOR_PASSWORD)){
            stm.setInt(1,id);
            stm.setString(2,key);
            stm.setTimestamp(3, Timestamp.valueOf(DATE_FORMAT.format(date)));
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry cannot creat request");
        }
    }
    public boolean verifiRestorePassword (int id, String key, Date date){
        Date createDate;

        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_REQUEST_RESTOR_PASSWORD)){
            stm.setInt(1,id);
            stm.setString(2,key);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
             createDate = DATE_FORMAT.parse(rs.getString(TIME));
             if(createDate.getTime() - date.getTime() < TIME_VERIFI && rs.getBoolean(AVALAIBLE)){
                 return true;
             }
            }

        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("Sorry cannot restore password, try again");
        }
        return false;
    }
    public boolean disableRestorePassword (int id, String key){
        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(UPDATE_AVALAIBLE_RESTOR_PASSWORD)){
            stm.setInt(1,id);
            stm.setString(2,key);
            stm.executeUpdate();
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("Sorry cannot restore password, try again");
        }
        return false;
    }
}
