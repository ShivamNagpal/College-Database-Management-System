package com.nagpal.shivam.dbms;

public class Log {

    public static void v(String className, String message) {
        System.out.println(className + ": " + message);
    }

    public static void e(String className, String message) {
        System.err.println(className + ": " + message);
    }
}
