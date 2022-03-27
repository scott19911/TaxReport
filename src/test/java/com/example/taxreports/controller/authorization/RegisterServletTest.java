package com.example.taxreports.controller.authorization;

import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.TableColums;
import com.example.taxreports.bean.RegisterBean;
import com.example.taxreports.bean.UserBean;
import com.example.taxreports.util.ConnectionPool;
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

import static com.example.taxreports.Queris.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class RegisterServletTest {

    HttpServletRequest req;
    HttpServletResponse resp;
    LoginServlet loginServlet;
    RegisterBean registerBean;
    HttpSession session;
    UserBean userBean;
    UserDAO userDAO;
    RequestDispatcher requestDispatcher;
    static MockedStatic<ConnectionPool> mocked;

    @BeforeAll
    public static void init() {
        mocked = mockStatic(ConnectionPool.class);
    }



    @Test
    void testDoPost() throws ServletException, IOException, SQLException {
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        loginServlet = new LoginServlet();
        registerBean = mock(RegisterBean.class);
        session = mock(HttpSession.class);
        userBean = mock(UserBean.class);
        userDAO = mock(UserDAO.class);

        requestDispatcher = mock(RequestDispatcher.class);
        when(req.getParameter("role")).thenReturn("indi");
        when(req.getParameter("login")).thenReturn("user");
        when(req.getParameter("password")).thenReturn("123456");
        when(req.getParameter("email")).thenReturn("ex@ex.com");
        registerBean.setLogin("user");
        registerBean.setEmail("ex@ex.com");
        registerBean.setPassword("e972bf40cc59aaf06abe6ac9017ca7dbbf7e2e2dbba7f02fe8fa8269de278261");
        when(registerBean.getLogin()).thenReturn("user");
        when(registerBean.getRole()).thenReturn("indi");
        when(registerBean.getPassword()).thenReturn("e972bf40cc59aaf06abe6ac9017ca7dbbf7e2e2dbba7f02fe8fa8269de278261");
        when(registerBean.getSalt()).thenReturn("12313123");


        ResultSet rs = mock(ResultSet.class);

        when(rs.next()).thenReturn(false);

        when(rs.getInt(TableColums.ID))
                .thenReturn(0);

        PreparedStatement stmt = mock(PreparedStatement.class);
        when(stmt.executeQuery())
                .thenReturn(rs);

        Connection con1 = mock(Connection.class);

        when(con1.prepareStatement(SELECT_ID_USER_BY_LOGIN)).thenReturn(stmt);
        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con1);

        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);



        ResultSet rs1 = mock(ResultSet.class);
        when(rs1.next()).thenReturn(true).thenReturn(false);
        when(rs1.getInt(1)).thenReturn(2);

        PreparedStatement stmt1 = mock(PreparedStatement.class);
        when(stmt1.getGeneratedKeys())
                .thenReturn(rs1);


        when(con1.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)).thenReturn(stmt1);

        when(dbUtils.getConnection())
                .thenReturn(con1);

        PreparedStatement stmt2 = mock(PreparedStatement.class);
        when(con1.prepareStatement(UPDATE_LOCALE_USER_BY_ID)).thenReturn(stmt2);
        when(dbUtils.getConnection())
                .thenReturn(con1);
        RegisterServlet registerServlet = new RegisterServlet();
        when(userDAO.getIdByLogin("user")).thenReturn(0);
        when(userDAO.registerUser(registerBean)).thenReturn(2);
        when(req.getSession()).thenReturn(session);

        when(req.getRequestDispatcher("/Register.jsp")).thenReturn(requestDispatcher);

        registerServlet.doPost(req,resp);
        assertNotNull(session);
        assertNotNull(requestDispatcher);
        mocked.close();
    }

}