package com.example.taxreports.DAO;

import com.example.taxreports.Queris;
import com.example.taxreports.TableColums;
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



    @Test
    void userInfo() throws SQLException {

        EntytiBean expected = new EntytiBean("Base","123456789012",1);


        ResultSet rs = mock(ResultSet.class);

        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getString(TableColums.ENTYTI_COMPANY))
                .thenReturn("Base");

        when(rs.getString(TableColums.ENTYTI_OKPO))
                .thenReturn("123456789012");


        PreparedStatement stmt = mock(PreparedStatement.class);
        when(stmt.executeQuery())
                .thenReturn(rs);

        Connection con = mock(Connection.class);

        when(con.prepareStatement(Queris.SELECT_ENTYTI_BY_ID)).thenReturn(stmt);
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