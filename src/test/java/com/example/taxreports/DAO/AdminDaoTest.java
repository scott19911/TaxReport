package com.example.taxreports.DAO;

import com.example.taxreports.TableColums;
import com.example.taxreports.bean.InspectorsBean;
import com.example.taxreports.util.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.taxreports.Queris.SELECT_ALL_INSPECTORS;
import static com.example.taxreports.Queris.SELECT_EMAIL_USER_BY_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminDaoTest {

    static MockedStatic<ConnectionPool> mocked;

    @BeforeAll
    public static void init() {
        mocked = mockStatic(ConnectionPool.class);
    }



    @Test
    void testGetInspectorsList() throws SQLException {

        List<InspectorsBean> expected = new ArrayList<>();
        expected.add(new InspectorsBean(1,"admin","admin"));
        expected.add(new InspectorsBean(2,"Иван","Иванов"));
        expected.add(new InspectorsBean(3,"Петр","Петров"));
            ResultSet rs = mock(ResultSet.class);

            when(rs.next())
                    .thenReturn(true)
                    .thenReturn(true)
                    .thenReturn(true)
                    .thenReturn(false);

            when(rs.getInt(TableColums.USER_ID))
                    .thenReturn(1)
                    .thenReturn(2)
                    .thenReturn(3);

            when(rs.getString(TableColums.LNAME))
                    .thenReturn("admin")
                    .thenReturn("Иванов")
                    .thenReturn("Петров");

            when(rs.getString(TableColums.FNAME))
                     .thenReturn("admin")
                     .thenReturn("Иван")
                     .thenReturn("Петр");

        Statement stmt = mock(Statement.class);
            when(stmt.executeQuery(SELECT_ALL_INSPECTORS))
                    .thenReturn(rs);

            Connection con = mock(Connection.class);
            when(con.createStatement())
                    .thenReturn(stmt);

        ResultSet rs3 = mock(ResultSet.class);
        when(rs3.next()).thenReturn(true).thenReturn(false);
        when(rs3.getString(TableColums.EMAIL)).thenReturn("exm@exm.com");
        PreparedStatement stm3 = mock(PreparedStatement.class);
        when(stm3.executeQuery())
                .thenReturn(rs3);
        when(con.prepareStatement(SELECT_EMAIL_USER_BY_ID))
                .thenReturn(stm3);


        ConnectionPool dbUtils = mock(ConnectionPool.class);
            when(dbUtils.getConnection())
                    .thenReturn(con);

            mocked.when(ConnectionPool::getInstance)
                    .thenReturn(dbUtils);

            List<InspectorsBean> users = AdminDao.getInspectorsList();

            assertEquals(expected.toString(),users.toString());
            mocked.close();

    }
}