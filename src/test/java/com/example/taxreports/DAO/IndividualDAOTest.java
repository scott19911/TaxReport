package com.example.taxreports.DAO;

import com.example.taxreports.TableColums;
import com.example.taxreports.bean.IndividualBean;
import com.example.taxreports.util.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.taxreports.Queris.SELECT_INDIVIDUAL_BY_USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class IndividualDAOTest {
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
    void userInfo() throws SQLException {

        IndividualBean expected = new IndividualBean(1,"Ivan","Иванович","Иванов","1234567890");
        ResultSet rs = mock(ResultSet.class);

        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getString(TableColums.FNAME))
                .thenReturn("Ivan");

        when(rs.getString(TableColums.TIN))
                .thenReturn("1234567890");

        when(rs.getString(TableColums.S_NAME))
                .thenReturn("Иванович");
        when(rs.getString(TableColums.LNAME))
                .thenReturn("Иванов");

        PreparedStatement stmt = mock(PreparedStatement.class);
        when(stmt.executeQuery())
                .thenReturn(rs);

        Connection con = mock(Connection.class);

        when(con.prepareStatement(SELECT_INDIVIDUAL_BY_USER_ID)).thenReturn(stmt);
        ConnectionPool dbUtils = mock(ConnectionPool.class);
        when(dbUtils.getConnection())
                .thenReturn(con);

        mocked.when(ConnectionPool::getInstance)
                .thenReturn(dbUtils);

        IndividualBean users = IndividualDAO.userInfo(1);

        assertEquals(expected.toString(),users.toString());

    }
}