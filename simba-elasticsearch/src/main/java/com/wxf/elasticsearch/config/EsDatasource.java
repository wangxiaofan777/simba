package com.wxf.elasticsearch.config;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author WangMaoSong
 * @date 2022/5/2 10:20
 */
public class EsDatasource {

    private static Connection CONNECTION = null;

    static {
        Properties properties = new Properties();
        try {
            CONNECTION = DriverManager.getConnection("jdbc:es://10.50.30.177:9500");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public static void main(String[] args) {
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from extract_console_20220426");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(0));
            }

            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
