package com.nagpal.shivam.dbms.data;

import com.nagpal.shivam.dbms.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static String sUrl;
    private static String sSchema;
    private static String sUsername;
    private static String sPassword;
    private static String CLASS_NAME = Database.class.getSimpleName();
    private static Connection sConnection;
    private static String lastError;

    public static Connection getConnection() {
        if (sConnection == null) {
            try {
                if (sUrl.charAt(sUrl.length() - 1) != '/') {
                    sUrl += '/';
                }
                sConnection = DriverManager.getConnection(sUrl + sSchema, sUsername, sPassword);
                Log.v(CLASS_NAME, "Database Connected");
            } catch (SQLException e) {
                lastError = e.getMessage();
                Log.e(CLASS_NAME, lastError);
            }
        }
        return sConnection;
    }

    public static void putParameters(String url, String schema, String username, String password) {
        sUrl = url;
        sSchema = schema;
        sUsername = username;
        sPassword = password;
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

    public static String getLastError() {
        return lastError;
    }
}
