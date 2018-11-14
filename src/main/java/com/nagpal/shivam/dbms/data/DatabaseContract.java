package com.nagpal.shivam.dbms.data;

public class DatabaseContract {
    public static final String ROW_ID = "ROWID";

    public class Professor {
        public static final String TABLE_NAME = "PROFESSOR";
        public static final String NAME = "NAME";
        public static final String PROFESSOR_ID = "PROFESSOR_ID";
        public static final String DATE_OF_BIRTH = "DATE_OF_BIRTH";
        public static final String ADDRESS = "ADDRESS";
        public static final String PHONE = "PHONE";
        public static final String EMAIL = "EMAIL";
        public static final String DEPARTMENT_ID = "DEPARTMENT_ID";
        public static final String DESIGNATION = "DESIGNATION";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                NAME + " TEXT NOT NULL" +
                ", " +
                PROFESSOR_ID + " TEXT PRIMARY KEY" +
                ", " +
                DATE_OF_BIRTH + " TEXT" +
                ", " +
                ADDRESS + " TEXT" +
                ", " +
                PHONE + " TEXT" +
                ", " +
                EMAIL + " TEXT" +
                ", " +
                DEPARTMENT_ID + " TEXT NOT NULL" +
                ", " +
                DESIGNATION + " TEXT" +
                ", " +
                "FOREIGN KEY (" + DEPARTMENT_ID + ") REFERENCES " + Department.TABLE_NAME + "(" + Department.DEPARTMENT_ID + ")" +
                ");";
    }

    public class Department {
        public static final String TABLE_NAME = "DEPARTMENT";
        public static final String NAME = "NAME";
        public static final String DEPARTMENT_ID = "DEPARTMENT_ID";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                NAME + " TEXT NOT NULL UNIQUE" +
                ", " +
                DEPARTMENT_ID + " TEXT PRIMARY KEY" +
                ");";
    }

    public class Student {
        @Ignored
        public static final String TABLE_NAME = "STUDENT";
        public static final String NAME = "NAME";
        public static final String STUDENT_ID = "STUDENT_ID";
        public static final String DATE_OF_BIRTH = "DATE_OF_BIRTH";
        public static final String ADDRESS = "ADDRESS";
        public static final String PHONE = "PHONE";
        public static final String EMAIL = "EMAIL";
        public static final String DEPARTMENT_ID = "DEPARTMENT_ID";

        @Ignored
        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                NAME + " TEXT NOT NULL" +
                ", " +
                STUDENT_ID + " TEXT PRIMARY KEY" +
                ", " +
                DATE_OF_BIRTH + " TEXT" +
                ", " +
                ADDRESS + " TEXT" +
                ", " +
                PHONE + " TEXT" +
                ", " +
                EMAIL + " TEXT" +
                ", " +
                DEPARTMENT_ID + " TEXT NOT NULL" +
                ", " +
                "FOREIGN KEY (" + DEPARTMENT_ID + ") REFERENCES " + Department.TABLE_NAME + "(" + Department.DEPARTMENT_ID + ")" +
                ");";
    }

    public class Subject {
        public static final String TABLE_NAME = "SUBJECT";
        public static final String NAME = "NAME";
        public static final String SUBJECT_ID = "SUBJECT_ID";
        public static final String SCHEME = "SCHEME";
        public static final String SEMESTER = "SEMESTER";
        public static final String CREDITS = "CREDITS";
        public static final String DEPARTMENT_ID = "DEPARTMENT_ID";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                NAME + " TEXT NOT NULL" +
                ", " +
                SUBJECT_ID + " TEXT PRIMARY KEY" +
                ", " +
                DEPARTMENT_ID + " TEXT NOT NULL" +
                ", " +
                SCHEME + " TEXT" +
                ", " +
                SEMESTER + " INTEGER" +
                ", " +
                CREDITS + " INTEGER" +
                ", " +
                "FOREIGN KEY (" + DEPARTMENT_ID + ") REFERENCES " + Department.TABLE_NAME + "(" + Department.DEPARTMENT_ID + ")" +
                ");";
    }

    public class SemesterSection {
        public static final String TABLE_NAME = "SEMESTER_SECTION";
        public static final String SEM_SEC_ID = "SEM_SEC_ID";
        public static final String SEMESTER = "SEMESTER";
        public static final String SECTION = "SECTION";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                SEM_SEC_ID + " TEXT PRIMARY KEY" +
                ", " +
                SEMESTER + " INTEGER" +
                ", " +
                SECTION + " TEXT" +
                ");";
    }

    public class Division {
        public static final String TABLE_NAME = "DIVISION";
        public static final String STUDENT_ID = "STUDENT_ID";
        public static final String SEM_SEC_ID = "SEM_SEC_ID";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                STUDENT_ID + " TEXT PRIMARY KEY" +
                ", " +
                SEM_SEC_ID + " TEXT" +
                ", " +
                "FOREIGN KEY (" + STUDENT_ID + ") REFERENCES " + Student.TABLE_NAME + "(" + Student.STUDENT_ID + ")" +
                ", " +
                "FOREIGN KEY (" + SEM_SEC_ID + ") REFERENCES " + SemesterSection.TABLE_NAME + "(" + SemesterSection.SEM_SEC_ID + ")" +
                ");";
    }

    public class Teaches {
        public static final String TABLE_NAME = "TEACHES";
        public static final String PROFESSOR_ID = "PROFESSOR_ID";
        public static final String SEM_SEC_ID = "SEM_SEC_ID";
        public static final String SUBJECT_ID = "SUBJECT_ID";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                PROFESSOR_ID + " TEXT" +
                ", " +
                SEM_SEC_ID + " TEXT" +
                ", " +
                SUBJECT_ID + " TEXT" +
                ", " +
                "FOREIGN KEY (" + PROFESSOR_ID + ") REFERENCES " + Professor.TABLE_NAME + "(" + Professor.PROFESSOR_ID + ")" +
                ", " +
                "FOREIGN KEY (" + SEM_SEC_ID + ") REFERENCES " + SemesterSection.TABLE_NAME + "(" + SemesterSection.SEM_SEC_ID + ")" +
                ", " +
                "FOREIGN KEY (" + SUBJECT_ID + ") REFERENCES " + Subject.TABLE_NAME + "(" + Subject.SUBJECT_ID + ")" +
                ", " +
                "PRIMARY KEY (" + PROFESSOR_ID + ", " + SEM_SEC_ID + ", " + SUBJECT_ID + ")" +
                ");";
    }

    public class IaMarks {
        public static final String TABLE_NAME = "IA_MARKS";
        public static final String STUDENT_ID = "STUDENT_ID";
        public static final String SEM_SEC_ID = "SEM_SEC_ID";
        public static final String SUBJECT_ID = "SUBJECT_ID";
        public static final String TEST1 = "TEST1";
        public static final String TEST2 = "TEST2";
        public static final String TEST3 = "TEST3";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                STUDENT_ID + " TEXT" +
                ", " +
                SEM_SEC_ID + " TEXT" +
                ", " +
                SUBJECT_ID + " TEXT" +
                ", " +
                TEST1 + " INTEGER" +
                ", " +
                TEST2 + " INTEGER" +
                ", " +
                TEST3 + " INTEGER" +
                ", " +
                "FOREIGN KEY (" + STUDENT_ID + ") REFERENCES " + Student.TABLE_NAME + "(" + Student.STUDENT_ID + ")" +
                ", " +
                "FOREIGN KEY (" + SEM_SEC_ID + ") REFERENCES " + SemesterSection.TABLE_NAME + "(" + SemesterSection.SEM_SEC_ID + ")" +
                ", " +
                "FOREIGN KEY (" + SUBJECT_ID + ") REFERENCES " + Subject.TABLE_NAME + "(" + Subject.SUBJECT_ID + ")" +
                ", " +
                "PRIMARY KEY (" + STUDENT_ID + ", " + SEM_SEC_ID + ", " + SUBJECT_ID + ")" +
                ");";
    }

}
