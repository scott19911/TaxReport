package com.example.taxreports.controller.reports;

import com.example.taxreports.DAO.ReportsDAO;
import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.TableColums;
import com.example.taxreports.util.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.taxreports.Queris.*;
import static com.example.taxreports.TableColums.REPORT_CREATER_ID;
import static org.mockito.Mockito.*;

class InspectionsTest {

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
    void testDoPostInappropriateStatus() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletContext servletContext = mock(ServletContext.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        ReportsDAO reportsDAO = mock(ReportsDAO.class);
        String UPDATE_REPORT= "Update report Set status_id=?, inspector_id = ? where id = ?";
        String SELECT_REPORT_STATUS = "select status_id from report where id = ?";

        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(false);
        PreparedStatement stm = mock(PreparedStatement.class);
        when(stm.executeQuery())
                .thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.prepareStatement(SELECT_REPORT_STATUS))
                .thenReturn(stm);

        PreparedStatement stmt = mock(PreparedStatement.class);


        when(con.prepareStatement(UPDATE_REPORT))
                .thenReturn(stmt);

        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con);

        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);


        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(servletContext);
        when(reportsDAO.getReportStatus(2)).thenReturn(0);
        when(request.getParameter("works")).thenReturn("2");
        when(request.getParameter("userId")).thenReturn("2");
        when(request.getParameter("id")).thenReturn("2");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);


        Inspections inspections = new Inspections();
        inspections.doPost(request,response);
        verify(requestDispatcher,times(1)).forward(request,response);
        verify(request,times(1)).setAttribute("errMessage","Sorry,no such file.");
    }
    @Test
    void testDoPostSuccess() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletContext servletContext = mock(ServletContext.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        ReportsDAO reportsDAO = mock(ReportsDAO.class);
        UserDAO userDAO = mock(UserDAO.class);
        String UPDATE_REPORT= "Update report Set status_id=?, inspector_id = ? where id = ?";
        String SELECT_REPORT_STATUS = "select status_id from report where id = ?";
        String SELECT_REPORT_INSPECTOR = "select inspector_id from report where id = ?";


        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt("status_id")).thenReturn(1);
        PreparedStatement stm = mock(PreparedStatement.class);
        when(stm.executeQuery())
                .thenReturn(rs);


        Connection con = mock(Connection.class);
        when(con.prepareStatement(SELECT_REPORT_STATUS))
                .thenReturn(stm);

        ResultSet rs2 = mock(ResultSet.class);
        when(rs2.next()).thenReturn(true).thenReturn(false);
        when(rs2.getInt(REPORT_CREATER_ID)).thenReturn(1);
        PreparedStatement stm2 = mock(PreparedStatement.class);
        when(stm2.executeQuery())
                .thenReturn(rs2);
        when(con.prepareStatement(SELECT_CREATER_ID))
                .thenReturn(stm2);

        when(reportsDAO.getIdCreaterReport(anyInt())).thenReturn(1);

        ResultSet rs3 = mock(ResultSet.class);
        when(rs3.next()).thenReturn(true).thenReturn(false);
        when(rs3.getString(TableColums.EMAIL)).thenReturn("exm@exm.com");
        PreparedStatement stm3 = mock(PreparedStatement.class);
        when(stm3.executeQuery())
                .thenReturn(rs3);
        when(con.prepareStatement(SELECT_EMAIL_USER_BY_ID))
                .thenReturn(stm3);

        ResultSet rs4 = mock(ResultSet.class);
        when(rs4.next()).thenReturn(true).thenReturn(false);
        when(rs4.getString(TableColums.LOCALE)).thenReturn("en");
        PreparedStatement stm4 = mock(PreparedStatement.class);
        when(stm4.executeQuery())
                .thenReturn(rs4);
        when(con.prepareStatement(SELECT_LOCALE_USER_BY_ID))
                .thenReturn(stm4);

        PreparedStatement stmt = mock(PreparedStatement.class);


        when(con.prepareStatement(UPDATE_REPORT))
                .thenReturn(stmt);

        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con);

        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);


        ResultSet rs1 = mock(ResultSet.class);
        when(rs1.next()).thenReturn(true).thenReturn(false);
        when(rs1.getInt("inspector_id")).thenReturn(2);

        PreparedStatement stmt1 = mock(PreparedStatement.class);
        when(stmt1.executeQuery()).thenReturn(rs1);
        when(con.prepareStatement(SELECT_REPORT_INSPECTOR))
                .thenReturn(stmt1);

        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(servletContext);
        when(reportsDAO.getReportStatus(2)).thenReturn(1);
        when(request.getParameter("works")).thenReturn("2");
        when(request.getParameter("userId")).thenReturn("2");
        when(request.getParameter("id")).thenReturn("2");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);


        Inspections inspections = new Inspections();
        inspections.doPost(request,response);

        verify(response,times(1)).sendRedirect("/reportList");

    }
}