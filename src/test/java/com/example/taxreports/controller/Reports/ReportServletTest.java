package com.example.taxreports.controller.Reports;

import com.example.taxreports.bean.UserBean;
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
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ReportServletTest {
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
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        ServletContext servletContext = mock(ServletContext.class);
        UserBean userBean = mock(UserBean.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        String content = "form-data; name=\"file\"; filename=\"C:\\file1.xml\"";
        String INSERT_REPORT = "Insert into report(status_id,creater,date,XML, description) "
                + " values (?,?,?,?,?) ";
        Part part = mock(Part.class);
        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRealPath("")).thenReturn(anyString());
        when(session.getAttribute("user")).thenReturn(userBean);
        when(userBean.getId()).thenReturn(3);
        when(request.getParameter("description")).thenReturn("anyString()");
        when(request.getPart("file")).thenReturn(part);
        when(part.getHeader("content-disposition")).thenReturn(content);
        when(servletContext.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        PreparedStatement stmt = mock(PreparedStatement.class);

        Connection con = mock(Connection.class);
        when(con.prepareStatement(INSERT_REPORT))
                .thenReturn(stmt);

        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con);

        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);

        ReportServlet reportServlet = new ReportServlet();
        reportServlet.doPost(request,response);

        verify(part,times(1)).write(anyString());
        verify(response, times(1)).sendRedirect(anyString());
    }

    @Test
    void testDoGet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletContext servletContext = mock(ServletContext.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher("/UploadReport.jsp")).thenReturn(requestDispatcher);
        when(request.getParameter("id")).thenReturn("1");

        ReportServlet reportServlet = new ReportServlet();
        reportServlet.doGet(request,response);
        verify(requestDispatcher,times(1)).forward(request,response);
    }
    @Test
    void testDoGetWithNumberFormatException() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletContext servletContext = mock(ServletContext.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher("/UploadReport.jsp")).thenReturn(requestDispatcher);
        when(request.getParameter("id")).thenReturn("a");

        ReportServlet reportServlet = new ReportServlet();

        NumberFormatException thrown = assertThrows(NumberFormatException.class,()->{
            reportServlet.doGet(request,response);
        }, "a");
        assertTrue(thrown.getMessage().contains("a"));
    }
}