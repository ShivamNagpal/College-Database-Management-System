package com.nagpal.shivam.dbms.data;

import com.nagpal.shivam.dbms.Log;
import org.sqlite.SQLiteConfig;

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
                SQLiteConfig sqLiteConfig = new SQLiteConfig();
                sqLiteConfig.enforceForeignKeys(true);
                sConnection = DriverManager.getConnection(url, sqLiteConfig.toProperties());
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
