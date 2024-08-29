package com.ohgiraffers.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {

    private static Connection con;

        public static Connection getConnection() {
            Connection con = null;
            Properties prop = new Properties();

            try {
                prop.load(new FileReader("src/main/resources/config/connection-info.properties"));
                String url = prop.getProperty("url");
                String user = prop.getProperty("user");
                String password = prop.getProperty("password");

                con = DriverManager.getConnection(url,user,password);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(String.valueOf(e));
            }
                return con;
        }

        public static void close(Connection con) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public static void close(Statement stmt) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public static void close(ResultSet rset) {
            try {
                rset.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
}
