package com.example.taxreports.DAO;

import com.example.taxreports.bean.EntytiBean;
import com.example.taxreports.util.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EntytiDAOTest {

    static MockedStatic<ConnectionPool> mocked;

    @BeforeAll
    public static void init() {
        mocked =
                mockStatic(ConnectionPool.class);
    }

    @AfterAll
    public static void close() {
        mocked.close();
    }

    @Test
    void userInfo() throws SQLException {
        String SELECT_ENTYTI = "select Company,OKPO from entytis where user_id =?";
        EntytiBean expected = new EntytiBean("Base","123456789012",1);


        ResultSet rs = mock(ResultSet.class);

        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getString("Company"))
                .thenReturn("Base");

        when(rs.getString("OKPO"))
                .thenReturn("123456789012");


        PreparedStatement stmt = mock(PreparedStatement.class);
        when(stmt.executeQuery())
                .thenReturn(rs);

        Connection con = mock(Connection.class);

        when(con.prepareStatement(SELECT_ENTYTI)).thenReturn(stmt);
        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con);

        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);

        EntytiBean users = EntytiDAO.userInfo(1);


        assertEquals(expected.toString(),users.toString());
        mocked.close();

    }
}