package com.example.taxreports.DAO;

import com.example.taxreports.bean.InspectorsBean;
import com.example.taxreports.util.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminDaoTest {

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
    void testGetInspectorsList() throws SQLException {
        String SELECT_ALL_INSPECTORS ="select user_id, FName,LName from inspectors";
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

            when(rs.getInt("user_id"))
                    .thenReturn(1)
                    .thenReturn(2)
                    .thenReturn(3);

            when(rs.getString("LName"))
                    .thenReturn("admin")
                    .thenReturn("Иванов")
                    .thenReturn("Петров");

            when(rs.getString("FName"))
                     .thenReturn("admin")
                     .thenReturn("Иван")
                     .thenReturn("Петр");

        Statement stmt = mock(Statement.class);
            when(stmt.executeQuery(SELECT_ALL_INSPECTORS))
                    .thenReturn(rs);

            Connection con = mock(Connection.class);
            when(con.createStatement())
                    .thenReturn(stmt);

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