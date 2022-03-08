package com.example.taxreports.bean;

import java.sql.*;

public class co {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tax_report","root","1991an09");
            Statement statement = connection.createStatement();
            String sqlInsert = "INSERT manufacturers (name) values ('Apple');";
            String sql = "select * from users;";

            ResultSet resultSet1 = statement.executeQuery(sql);
            while (resultSet1.next()) {
                String rez = resultSet1.getString("login");
                System.out.println(rez);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("ok");
    }

}