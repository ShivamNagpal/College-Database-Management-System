package com.nagpal.shivam.dbms.data;

public class DatabaseContract {

    public class Professor {
        public static final String TABLE_NAME = "Professor";
        public static final String NAME = "NAME";
        public static final String PROFESSOR_ID = "PROFESSOR_ID";
        public static final String DATE_OF_BIRTH = "DATE_OF_BIRTH";
        public static final String ADDRESS = "ADDRESS";
        public static final String PHONE = "PHONE";
        public static final String EMAIL = "EMAIL";
        public static final String DEPARTMENT_ID = "DEPARTMENT_ID";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                NAME + " TEXT NOT NULL, " +
                PROFESSOR_ID + " TEXT PRIMARY KEY, " +
                DATE_OF_BIRTH + " TEXT, " +
                ADDRESS + " TEXT, " +
                PHONE + " TEXT, " +
                EMAIL + " TEXT, " +
                DEPARTMENT_ID + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + DEPARTMENT_ID + ") REFERENCES " + Department.TABLE_NAME + "(" + Department.DEPARTMENT_ID + "));";
    }

    public class Department {
        public static final String TABLE_NAME = "DEPARTMENT";
        public static final String NAME = "NAME";
        public static final String DEPARTMENT_ID = "DEPARTMENT_ID";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                NAME + " TEXT NOT NULL UNIQUE, " +
                DEPARTMENT_ID + " TEXT PRIMARY KEY);";
    }

    public class Student {
        public static final String TABLE_NAME = "STUDENT";
        public static final String NAME = "NAME";
        public static final String STUDENT_ID = "STUDENT_ID";
        public static final String DATE_OF_BIRTH = "DATE_OF_BIRTH";
        public static final String ADDRESS = "ADDRESS";
        public static final String PHONE = "PHONE";
        public static final String EMAIL = "EMAIL";
        public static final String DEPARTMENT_ID = "DEPARTMENT_ID";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                NAME + " TEXT NOT NULL, " +
                STUDENT_ID + " TEXT PRIMARY KEY, " +
                DATE_OF_BIRTH + " TEXT, " +
                ADDRESS + " TEXT, " +
                PHONE + " TEXT, " +
                EMAIL + " TEXT, " +
                DEPARTMENT_ID + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + DEPARTMENT_ID + ") REFERENCES " + Department.TABLE_NAME + "(" + Department.DEPARTMENT_ID + "));";
    }

}
