package com.example.taxreports.controller.authorization;

import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.bean.RegisterBean;
import com.example.taxreports.bean.UserBean;
import com.example.taxreports.util.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class LoginServletTest {
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
    void testDoPost() throws ServletException, IOException, SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        LoginServlet loginServlet = new LoginServlet();
        RegisterBean registerBean = mock(RegisterBean.class);
        HttpSession session = mock(HttpSession.class);
        UserBean userBean = mock(UserBean.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(userBean.getRole()).thenReturn("admin");
        when(userBean.getId()).thenReturn(9);
        when(req.getParameter("username")).thenReturn("admin");
        when(req.getParameter("password")).thenReturn("123456");

        ResultSet rs = mock(ResultSet.class);

        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getString("salt"))
                .thenReturn("332952873");

        PreparedStatement stmt = mock(PreparedStatement.class);
        when(stmt.executeQuery())
                .thenReturn(rs);

        Connection con = mock(Connection.class);

        when(con.prepareStatement(anyString())).thenReturn(stmt);
        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con);
        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);

        String SELECT_USERS = "select id, login,password, role from users";

        ResultSet rs1 = mock(ResultSet.class);

        when(rs1.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs1.getString("role"))
                .thenReturn("adm");
        when(rs1.getString("login"))
                .thenReturn("admin");
        when(rs1.getString("password"))
                .thenReturn("e972bf40cc59aaf06abe6ac9017ca7dbbf7e2e2dbba7f02fe8fa8269de278261");
        when(rs1.getInt("id"))
                .thenReturn(9);
        Statement stmt1 = mock(Statement.class);
        when(stmt1.executeQuery(SELECT_USERS))
                .thenReturn(rs1);

        when(registerBean.getLogin()).thenReturn("admin");
        when(registerBean.getPassword()).thenReturn("e972bf40cc59aaf06abe6ac9017ca7dbbf7e2e2dbba7f02fe8fa8269de278261");
        Connection connection = mock(Connection.class);
        when(con.createStatement())
                .thenReturn(stmt1);

        ConnectionPool connectionPool = mock(ConnectionPool.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);



        UserDAO userDAO = mock(UserDAO.class);
        when(userDAO.getSalByLogin("admin")).thenReturn("4242432");
        when(userDAO.authenticateUser(registerBean)).thenReturn(userBean);
        when(req.getSession()).thenReturn(session);
        when(req.getRequestDispatcher("/listIns")).thenReturn(requestDispatcher);
        loginServlet.doPost(req,resp);

       assertNotNull(session);
       assertNotNull(requestDispatcher);

    }
}