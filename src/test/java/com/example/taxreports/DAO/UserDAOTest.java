package com.example.taxreports.DAO;

import com.example.taxreports.bean.RegisterBean;
import com.example.taxreports.bean.UserBean;
import com.example.taxreports.util.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserDAOTest {
    static MockedStatic<ConnectionPool> mocked;

    @BeforeAll
    public static void init() {
        mocked = mockStatic(ConnectionPool.class);
    }

    @AfterAll
    public static void close() {
        mocked.close();
    }

    @Test
    void authenticateUser() throws SQLException {
        String SELECT_USERS = "select id, login,password, role from users";
        UserBean expected = new UserBean();
        expected.setRole("adm");
        expected.setId(1);

        ResultSet rs = mock(ResultSet.class);

        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getString("role"))
                .thenReturn("adm");
        when(rs.getString("login"))
                .thenReturn("admin");
        when(rs.getString("password"))
                .thenReturn("adminpass");
        when(rs.getInt("id"))
                .thenReturn(1);
        Statement stmt = mock(Statement.class);
        when(stmt.executeQuery(SELECT_USERS))
                .thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.createStatement())
                .thenReturn(stmt);

        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con);



        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);
        RegisterBean registerBean = mock(RegisterBean.class);
        when(registerBean.getLogin()).thenReturn("admin");
        when(registerBean.getPassword()).thenReturn("adminpass");

        UserDAO userDAO = new UserDAO();
       UserBean actual =userDAO.authenticateUser(registerBean);

        assertEquals(expected.toString(),actual.toString());

    }


    @Test
    void getUserRoleByID() throws SQLException {
        String SELECT_ROLE_FROM_USERS_WHERE_ID = "select role from users where id = ?";
        String expected = "adm";
        ResultSet rs = mock(ResultSet.class);

        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getString("role"))
                .thenReturn("adm");

        PreparedStatement stmt = mock(PreparedStatement.class);
        when(stmt.executeQuery())
                .thenReturn(rs);

        Connection con = mock(Connection.class);

        when(con.prepareStatement(SELECT_ROLE_FROM_USERS_WHERE_ID)).thenReturn(stmt);
        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con);



        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);
        UserDAO userDAO = mock(UserDAO.class);
        String actual =userDAO.getUserRoleByID(1);

        assertEquals(expected,actual);

    }

    @Test
    void getSalByLogin() throws SQLException {
        String SELECT_SALT_FROM_USERS_WHERE_LOGIN = "select salt from users where login = ?";
        String expected = "4242432";
        ResultSet rs = mock(ResultSet.class);

        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getString("salt"))
                .thenReturn("4242432");

        PreparedStatement stmt = mock(PreparedStatement.class);
        when(stmt.executeQuery())
                .thenReturn(rs);

        Connection con = mock(Connection.class);

        when(con.prepareStatement(SELECT_SALT_FROM_USERS_WHERE_LOGIN)).thenReturn(stmt);
        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con);



        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);
        UserDAO userDAO = new UserDAO();
        String actual =userDAO.getSalByLogin("adm");

        assertEquals(expected,actual);

    }

    @Test
    void getIdByLogin() throws SQLException {
        String SELECT_ID_USER_BY_LOGIN = "select id from users where login = ?";
        int expected = 4;
        ResultSet rs = mock(ResultSet.class);

        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getInt("id"))
                .thenReturn(4);

        PreparedStatement stmt = mock(PreparedStatement.class);
        when(stmt.executeQuery())
                .thenReturn(rs);

        Connection con = mock(Connection.class);

        when(con.prepareStatement(SELECT_ID_USER_BY_LOGIN)).thenReturn(stmt);
        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con);



        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);
        UserDAO userDAO = new UserDAO();
        int actual =userDAO.getIdByLogin("adm");

        assertEquals(expected,actual);

    }
}