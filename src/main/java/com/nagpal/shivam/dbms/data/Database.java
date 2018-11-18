package com.nagpal.shivam.dbms.data;

import com.nagpal.shivam.dbms.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "college";
    private static String CLASS_NAME = Database.class.getSimpleName();
    private static Connection sConnection;

    public static Connection getConnection() {
        if (sConnection == null) {
            try {
                Connection connection = DriverManager.getConnection(URL, "shivam", "shivam");
                Statement statement = connection.createStatement();
                statement.execute("CREATE SCHEMA IF NOT EXISTS " + DATABASE_NAME);
                sConnection = DriverManager.getConnection(URL + DATABASE_NAME, "shivam", "shivam");
                Log.v(CLASS_NAME, "Database Connected");
            } catch (SQLException e) {
                Log.e(CLASS_NAME, e.getMessage());
            }
        }
        return sConnection;
    }

    public static void closeConnection() {
        if (sConnection != null) {
            try {
                sConnection.close();
                Log.v(CLASS_NAME, "Database Closed");
            } catch (SQLException e) {
                Log.e(CLASS_NAME, e.getMessage());
            } finally {
                sConnection = null;
            }
        }
    }

    public static void closeSqlComponents(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
                Log.v(CLASS_NAME, "SQL Component " + closeable.getClass().getSimpleName() + " Closed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
