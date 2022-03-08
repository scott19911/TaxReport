package com.example.taxreports.DAO;

import com.example.taxreports.bean.ReportBean;
import com.example.taxreports.util.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReportsDAOTest {
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
    void getUserReport() throws SQLException, ParseException {
        String SELECT_USER_REPORTS ="select r.id, r.date,r.status_id, r.comments,r.xml,r.description,CONCAT(i.FName,' ',i.LName) as inspec "+
                "from report as r left join inspectors as i " +
                "on i.user_id= r.inspector_id "+
                "where r.creater = ?";
        DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
        List<ReportBean> expected =new ArrayList<>();
        expected.add(new ReportBean(1, 1, "Admin", null,DATE_FORMAT.parse("2018-07-25 07:42:36"),
                "C:\\file.xml", ""));
        expected.add(new ReportBean(8, 5, "Admin", "wrong format", DATE_FORMAT.parse("2018-08-25 17:42:36"),
                "C:\\user\\file.xml", "my file"));
        ResultSet rs = mock(ResultSet.class);

        when(rs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getInt("status_id"))
                .thenReturn(1)
                .thenReturn(5);
        when(rs.getInt("id"))
                .thenReturn(1)
                .thenReturn(8);
        when(rs.getString("date"))
                .thenReturn("2018-07-25 07:42:36")
                .thenReturn("2018-08-25 17:42:36");

        when(rs.getString("comments"))
                .thenReturn(null)
                .thenReturn("wrong format");

        when(rs.getString("XML"))
                .thenReturn("C:\\file.xml")
                .thenReturn("C:\\user\\file.xml");

        when(rs.getString("description"))
                .thenReturn("")
                .thenReturn("my file");

        when(rs.getString("inspec"))
                .thenReturn("Admin")
                .thenReturn("Admin");

        PreparedStatement stmt = mock(PreparedStatement.class);
        when(stmt.executeQuery())
                .thenReturn(rs);

        Connection con = mock(Connection.class);

        when(con.prepareStatement(SELECT_USER_REPORTS)).thenReturn(stmt);
        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con);


        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);
        ReportsDAO reportsDAO = new ReportsDAO();
        List<ReportBean> actual =reportsDAO.getUserReport(1);

        assertEquals(expected.toString(),actual.toString());

    }

    @Test
    void getAvelaibleReport() throws SQLException, ParseException {
        String SELECT_AVELAIBLE_REPORT ="select r.creater,CONCAT(ind.LName,' ',ind.FName,' ',ind.sName) as user,e.company," +
                " r.id, r.date,r.status_id, r.comments,r.xml,r.description,CONCAT(i.FName,' ',i.LName) as inspec"+
                " from report as r left join inspectors as i "+
                "on i.user_id= r.inspector_id left join individual as ind " +
                "on ind.user_id= r.creater left join entytis as e on e.user_id= r.creater" +
                " where (r.status_id = ? or r.status_id = ? or r.status_id = ? ) and (r.inspector_id is null or r.inspector_id = ?) ";

        DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
        List<ReportBean> expected =new ArrayList<>();
        expected.add(new ReportBean(1, 1, "3", "Admin", null,DATE_FORMAT.parse("2018-07-25 07:42:36"),
                "C:\\file.xml", "Petrov" , ""));
        expected.add(new ReportBean(8, 5, "4", "Admin", "wrong format", DATE_FORMAT.parse("2018-08-25 17:42:36"),
                "C:\\user\\file.xml", "Ivanov" , "my file"));
        ResultSet rs = mock(ResultSet.class);

        when(rs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getInt("status_id"))
                .thenReturn(1)
                .thenReturn(5);
        when(rs.getInt("id"))
                .thenReturn(1)
                .thenReturn(8);
        when(rs.getString("date"))
                .thenReturn("2018-07-25 07:42:36")
                .thenReturn("2018-08-25 17:42:36");

        when(rs.getString("comments"))
                .thenReturn(null)
                .thenReturn("wrong format");

        when(rs.getString("XML"))
                .thenReturn("C:\\file.xml")
                .thenReturn("C:\\user\\file.xml");

        when(rs.getString("creater"))
                .thenReturn("3")
                .thenReturn("4");

        when(rs.getString("description"))
                .thenReturn("")
                .thenReturn("my file");

        when(rs.getString("inspec"))
                .thenReturn("Admin")
                .thenReturn("Admin");
        when(rs.getString("user"))
                .thenReturn("Petrov")
                .thenReturn("Ivanov");

        when(rs.getString("company"))
                .thenReturn(null)
                .thenReturn(null);
        PreparedStatement stmt = mock(PreparedStatement.class);
        when(stmt.executeQuery())
                .thenReturn(rs);

        Connection con = mock(Connection.class);

        when(con.prepareStatement(SELECT_AVELAIBLE_REPORT)).thenReturn(stmt);
        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con);



        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);
        ReportsDAO reportsDAO = new ReportsDAO();
        List<ReportBean> actual =reportsDAO.getAvelaibleReport(1);

        assertEquals(expected.toString(),actual.toString());

    }


    @Test
    void getArchive() throws SQLException, ParseException {
        String SELECT_ARCHIV_REPORT="select r.creater,CONCAT(ind.LName,' ',ind.FName,' ',ind.sName) as user,e.company," +
                " r.id, r.date,r.status_id, r.comments,r.xml,r.description,CONCAT(i.FName,' ',i.LName) as inspec"+
                " from report as r left join inspectors as i "+
                "on i.user_id= r.inspector_id left join individual as ind " +
                "on ind.user_id= r.creater left join entytis as e on e.user_id= r.creater " +
                "where r.status_id = ? or r.status_id = ?";
        DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
        List<ReportBean> expected =new ArrayList<>();
        expected.add(new ReportBean(1, 2, "3", "Admin", null,DATE_FORMAT.parse("2018-07-25 07:42:36"),
                "C:\\file.xml", "Petrov" , ""));
        expected.add(new ReportBean(8, 3, "4", "Admin", "wrong format", DATE_FORMAT.parse("2018-08-25 17:42:36"),
                "C:\\user\\file.xml", "Ivanov" , "my file"));
        ResultSet rs = mock(ResultSet.class);

        when(rs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getInt("status_id"))
                .thenReturn(2)
                .thenReturn(3);
        when(rs.getInt("id"))
                .thenReturn(1)
                .thenReturn(8);
        when(rs.getString("date"))
                .thenReturn("2018-07-25 07:42:36")
                .thenReturn("2018-08-25 17:42:36");

        when(rs.getString("comments"))
                .thenReturn(null)
                .thenReturn("wrong format");

        when(rs.getString("XML"))
                .thenReturn("C:\\file.xml")
                .thenReturn("C:\\user\\file.xml");

        when(rs.getString("creater"))
                .thenReturn("3")
                .thenReturn("4");

        when(rs.getString("description"))
                .thenReturn("")
                .thenReturn("my file");

        when(rs.getString("inspec"))
                .thenReturn("Admin")
                .thenReturn("Admin");
        when(rs.getString("user"))
                .thenReturn(null)
                .thenReturn("Ivanov");

        when(rs.getString("company"))
                .thenReturn("Petrov")
                .thenReturn(null);
        PreparedStatement stmt = mock(PreparedStatement.class);
        when(stmt.executeQuery())
                .thenReturn(rs);

        Connection con = mock(Connection.class);

        when(con.prepareStatement(SELECT_ARCHIV_REPORT)).thenReturn(stmt);
        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con);


        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);
        ReportsDAO reportsDAO = new ReportsDAO();
        List<ReportBean> actual =reportsDAO.getArchive();

        assertEquals(expected.toString(),actual.toString());

    }

    @Test
    void getReportStatus() throws SQLException {
        String SELECT_REPORT_STATUS = "select status_id from report where id = ?";
        int expected = 4;
        ResultSet rs = mock(ResultSet.class);

        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getInt("status_id"))
                .thenReturn(4);

        PreparedStatement stmt = mock(PreparedStatement.class);
        when(stmt.executeQuery())
                .thenReturn(rs);

        Connection con = mock(Connection.class);

        when(con.prepareStatement(SELECT_REPORT_STATUS)).thenReturn(stmt);
        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con);

        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);
        ReportsDAO reportsDAO = new ReportsDAO();
        int actual =reportsDAO.getReportStatus(1);

        assertEquals(expected,actual);

    }

    @Test
    void getInspector() throws SQLException {
        String SELECT_REPORT_INSPECTOR = "select inspector_id from report where id = ?";
        int expected = 4;
        ResultSet rs = mock(ResultSet.class);

        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getInt("inspector_id"))
                .thenReturn(4);

        PreparedStatement stmt = mock(PreparedStatement.class);
        when(stmt.executeQuery())
                .thenReturn(rs);

        Connection con = mock(Connection.class);

        when(con.prepareStatement(SELECT_REPORT_INSPECTOR)).thenReturn(stmt);
        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con);


        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);
        ReportsDAO reportsDAO = new ReportsDAO();
        int actual =reportsDAO.getInspector(1);

        assertEquals(expected,actual);


    }

}