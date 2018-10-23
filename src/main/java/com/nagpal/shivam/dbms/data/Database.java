package com.nagpal.shivam.dbms.data;

import com.nagpal.shivam.dbms.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static String CLASS_NAME = Database.class.getSimpleName();
    private static Connection sConnection;
    private static String url = "jdbc:sqlite:database.db";

    public static Connection getConnection() {
        if (sConnection == null) {
            try {
                sConnection = DriverManager.getConnection(url);
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
            } catch (SQLException e) {
                Log.e(CLASS_NAME, e.getMessage());
            } finally {
                sConnection = null;
            }
        }
    }
}
