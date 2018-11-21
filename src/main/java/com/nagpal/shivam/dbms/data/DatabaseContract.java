package com.nagpal.shivam.dbms.data;

public class DatabaseContract {
    public static final String ROW_ID = "_ROWID";
    public static final String ROW_ID_DATA_TYPE = "BIGINT(8) UNSIGNED NOT NULL UNIQUE AUTO_INCREMENT";

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
                ROW_ID + " " + ROW_ID_DATA_TYPE +
                ", " +
                NAME + " VARCHAR(64) NOT NULL" +
                ", " +
                PROFESSOR_ID + " VARCHAR(64) PRIMARY KEY" +
                ", " +
                DATE_OF_BIRTH + " DATE" +
                ", " +
                ADDRESS + " VARCHAR(256)" +
                ", " +
                PHONE + " VARCHAR(15)" +
                ", " +
                EMAIL + " VARCHAR(64)" +
                ", " +
                DEPARTMENT_ID + " VARCHAR(64) NOT NULL" +
                ", " +
                DESIGNATION + " VARCHAR(64)" +
                ", " +
                "FOREIGN KEY (" + DEPARTMENT_ID + ") REFERENCES " + Department.TABLE_NAME + "(" + Department.DEPARTMENT_ID + ")" +
                ", " +
                "FULLTEXT full_text_idx(" + NAME + "," + PROFESSOR_ID + "," + ADDRESS + "," + PHONE + "," + EMAIL + "," + DEPARTMENT_ID + "," + DESIGNATION + ")" +
                ");";
    }

    public class Department {
        public static final String TABLE_NAME = "DEPARTMENT";
        public static final String NAME = "NAME";
        public static final String DEPARTMENT_ID = "DEPARTMENT_ID";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                ROW_ID + " " + ROW_ID_DATA_TYPE +
                ", " +
                NAME + " VARCHAR(64) NOT NULL UNIQUE" +
                ", " +
                DEPARTMENT_ID + " VARCHAR(64) PRIMARY KEY" +
                ", " +
                "FULLTEXT full_text_idx(" + NAME + "," + DEPARTMENT_ID + ")" +
                ");";
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

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                ROW_ID + " " + ROW_ID_DATA_TYPE +
                ", " +
                NAME + " VARCHAR(64) NOT NULL" +
                ", " +
                STUDENT_ID + " VARCHAR(64) PRIMARY KEY" +
                ", " +
                DATE_OF_BIRTH + " DATE" +
                ", " +
                ADDRESS + " VARCHAR(256)" +
                ", " +
                PHONE + " VARCHAR(15)" +
                ", " +
                EMAIL + " VARCHAR(64)" +
                ", " +
                DEPARTMENT_ID + " VARCHAR(64) NOT NULL" +
                ", " +
                "FOREIGN KEY (" + DEPARTMENT_ID + ") REFERENCES " + Department.TABLE_NAME + "(" + Department.DEPARTMENT_ID + ")" +
                ", " +
                "FULLTEXT full_text_idx(" + NAME + "," + STUDENT_ID + "," + ADDRESS + "," + PHONE + "," + EMAIL + "," + DEPARTMENT_ID + ")" +
                ");";
    }

    public class Subject {
        public static final String TABLE_NAME = "SUBJECT";
        public static final String NAME = "NAME";
        public static final String SUBJECT_ID = "SUBJECT_ID";
        public static final String SCHEME = "SCHEME";
        public static final String SEMESTER = "SEMESTER";
        public static final String SEMESTER_IDX = "SEMESTER_IDX";
        public static final String CREDITS = "CREDITS";
        public static final String CREDITS_IDX = "CREDITS_IDX";
        public static final String DEPARTMENT_ID = "DEPARTMENT_ID";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                ROW_ID + " " + ROW_ID_DATA_TYPE +
                ", " +
                NAME + " VARCHAR(64) NOT NULL" +
                ", " +
                SUBJECT_ID + " VARCHAR(64) PRIMARY KEY" +
                ", " +
                DEPARTMENT_ID + " VARCHAR(64) NOT NULL" +
                ", " +
                SCHEME + " VARCHAR(64)" +
                ", " +
                SEMESTER + " INT" +
                ", " +
                SEMESTER_IDX + " TINYTEXT" +
                ", " +
                CREDITS + " INT" +
                ", " +
                CREDITS_IDX + " TINYTEXT" +
                ", " +
                "FOREIGN KEY (" + DEPARTMENT_ID + ") REFERENCES " + Department.TABLE_NAME + "(" + Department.DEPARTMENT_ID + ")" +
                ", " +
                "FULLTEXT full_text_idx(" + NAME + "," + SUBJECT_ID + "," + DEPARTMENT_ID + "," + SCHEME + "," + SEMESTER_IDX + "," + CREDITS_IDX + ")" +
                ");";

        public static final String SQL_CREATE_FULLTEXT_INDEX_TRIGGER_INSERT = "CREATE TRIGGER " + TABLE_NAME + "_FULL_TEXT_INDEX_TRIGGER_INSERT BEFORE INSERT ON " +
                TABLE_NAME + " FOR EACH ROW BEGIN " +
                "SET NEW." + SEMESTER_IDX + " = CAST(NEW." + SEMESTER + " AS CHAR); " +
                "SET NEW." + CREDITS_IDX + " = CAST(NEW." + CREDITS + " AS CHAR);" +
                "END;";

        public static final String SQL_CREATE_FULLTEXT_INDEX_TRIGGER_UPDATE = "CREATE TRIGGER " + TABLE_NAME + "_FULL_TEXT_INDEX_TRIGGER_UPDATE BEFORE UPDATE ON " +
                TABLE_NAME + " FOR EACH ROW BEGIN " +
                "SET NEW." + SEMESTER_IDX + " = CAST(NEW." + SEMESTER + " AS CHAR); " +
                "SET NEW." + CREDITS_IDX + " = CAST(NEW." + CREDITS + " AS CHAR);" +
                "END;";
    }

    public class SemesterSection {
        public static final String TABLE_NAME = "SEMESTER_SECTION";
        public static final String SEM_SEC_ID = "SEM_SEC_ID";
        public static final String SEMESTER = "SEMESTER";
        public static final String SEMESTER_IDX = "SEMESTER_IDX";
        public static final String SECTION = "SECTION";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                ROW_ID + " " + ROW_ID_DATA_TYPE +
                ", " +
                SEM_SEC_ID + " VARCHAR(64) PRIMARY KEY" +
                ", " +
                SEMESTER + " INT" +
                ", " +
                SEMESTER_IDX + " TINYTEXT" +
                ", " +
                SECTION + " VARCHAR(64)" +
                ", " +
                "FULLTEXT full_text_idx(" + SEM_SEC_ID + "," + SEMESTER_IDX + "," + SECTION + ")" +
                ");";

        public static final String SQL_CREATE_FULLTEXT_INDEX_TRIGGER_INSERT = "CREATE TRIGGER " + TABLE_NAME + "_FULL_TEXT_INDEX_TRIGGER_INSERT BEFORE INSERT ON " +
                TABLE_NAME + " FOR EACH ROW BEGIN " +
                "SET NEW." + SEMESTER_IDX + " = CAST(NEW." + SEMESTER + " AS CHAR); " +
                "END;";

        public static final String SQL_CREATE_FULLTEXT_INDEX_TRIGGER_UPDATE = "CREATE TRIGGER " + TABLE_NAME + "_FULL_TEXT_INDEX_TRIGGER_UPDATE BEFORE UPDATE ON " +
                TABLE_NAME + " FOR EACH ROW BEGIN " +
                "SET NEW." + SEMESTER_IDX + " = CAST(NEW." + SEMESTER + " AS CHAR); " +
                "END;";
    }

    public class Division {
        public static final String TABLE_NAME = "DIVISION";
        public static final String STUDENT_ID = "STUDENT_ID";
        public static final String SEM_SEC_ID = "SEM_SEC_ID";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                ROW_ID + " " + ROW_ID_DATA_TYPE +
                ", " +
                STUDENT_ID + " VARCHAR(64) PRIMARY KEY" +
                ", " +
                SEM_SEC_ID + " VARCHAR(64)" +
                ", " +
                "FOREIGN KEY (" + STUDENT_ID + ") REFERENCES " + Student.TABLE_NAME + "(" + Student.STUDENT_ID + ")" +
                ", " +
                "FOREIGN KEY (" + SEM_SEC_ID + ") REFERENCES " + SemesterSection.TABLE_NAME + "(" + SemesterSection.SEM_SEC_ID + ")" +
                ", " +
                "FULLTEXT full_text_idx(" + STUDENT_ID + "," + SEM_SEC_ID + ")" +
                ");";
    }

    public class Teaches {
        public static final String TABLE_NAME = "TEACHES";
        public static final String PROFESSOR_ID = "PROFESSOR_ID";
        public static final String SEM_SEC_ID = "SEM_SEC_ID";
        public static final String SUBJECT_ID = "SUBJECT_ID";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                ROW_ID + " " + ROW_ID_DATA_TYPE +
                ", " +
                PROFESSOR_ID + " VARCHAR(64)" +
                ", " +
                SEM_SEC_ID + " VARCHAR(64)" +
                ", " +
                SUBJECT_ID + " VARCHAR(64)" +
                ", " +
                "FOREIGN KEY (" + PROFESSOR_ID + ") REFERENCES " + Professor.TABLE_NAME + "(" + Professor.PROFESSOR_ID + ")" +
                ", " +
                "FOREIGN KEY (" + SEM_SEC_ID + ") REFERENCES " + SemesterSection.TABLE_NAME + "(" + SemesterSection.SEM_SEC_ID + ")" +
                ", " +
                "FOREIGN KEY (" + SUBJECT_ID + ") REFERENCES " + Subject.TABLE_NAME + "(" + Subject.SUBJECT_ID + ")" +
                ", " +
                "PRIMARY KEY (" + PROFESSOR_ID + ", " + SEM_SEC_ID + ", " + SUBJECT_ID + ")" +
                ", " +
                "FULLTEXT full_text_idx(" + PROFESSOR_ID + "," + SEM_SEC_ID + "," + SUBJECT_ID + ")" +
                ");";
    }

    public class IaMarks {
        public static final String TABLE_NAME = "IA_MARKS";
        public static final String STUDENT_ID = "STUDENT_ID";
        public static final String SEM_SEC_ID = "SEM_SEC_ID";
        public static final String SUBJECT_ID = "SUBJECT_ID";
        public static final String TEST1 = "TEST1";
        public static final String TEST1_IDX = "TEST1_IDX";
        public static final String TEST2 = "TEST2";
        public static final String TEST2_IDX = "TEST2_IDX";
        public static final String TEST3 = "TEST3";
        public static final String TEST3_IDX = "TEST3_IDX";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                ROW_ID + " " + ROW_ID_DATA_TYPE +
                ", " +
                STUDENT_ID + " VARCHAR(64)" +
                ", " +
                SEM_SEC_ID + " VARCHAR(64)" +
                ", " +
                SUBJECT_ID + " VARCHAR(64)" +
                ", " +
                TEST1 + " INT" +
                ", " +
                TEST1_IDX + " TINYTEXT" +
                ", " +
                TEST2 + " INT" +
                ", " +
                TEST2_IDX + " TINYTEXT" +
                ", " +
                TEST3 + " INT" +
                ", " +
                TEST3_IDX + " TINYTEXT" +
                ", " +
                "FOREIGN KEY (" + STUDENT_ID + ") REFERENCES " + Student.TABLE_NAME + "(" + Student.STUDENT_ID + ")" +
                ", " +
                "FOREIGN KEY (" + SEM_SEC_ID + ") REFERENCES " + SemesterSection.TABLE_NAME + "(" + SemesterSection.SEM_SEC_ID + ")" +
                ", " +
                "FOREIGN KEY (" + SUBJECT_ID + ") REFERENCES " + Subject.TABLE_NAME + "(" + Subject.SUBJECT_ID + ")" +
                ", " +
                "PRIMARY KEY (" + STUDENT_ID + ", " + SEM_SEC_ID + ", " + SUBJECT_ID + ")" +
                ", " +
                "FULLTEXT full_text_idx(" + STUDENT_ID + "," + SEM_SEC_ID + "," + SUBJECT_ID + "," + TEST1_IDX + "," + TEST2_IDX + "," + TEST3_IDX + ")" +
                ");";

        public static final String SQL_CREATE_FULLTEXT_INDEX_TRIGGER_INSERT = "CREATE TRIGGER " + TABLE_NAME + "_FULL_TEXT_INDEX_TRIGGER_INSERT BEFORE INSERT ON " +
                TABLE_NAME + " FOR EACH ROW BEGIN " +
                "SET NEW." + TEST1_IDX + " = CAST(NEW." + TEST1 + " AS CHAR); " +
                "SET NEW." + TEST2_IDX + " = CAST(NEW." + TEST2 + " AS CHAR); " +
                "SET NEW." + TEST3_IDX + " = CAST(NEW." + TEST3 + " AS CHAR); " +
                "END;";

        public static final String SQL_CREATE_FULLTEXT_INDEX_TRIGGER_UPDATE = "CREATE TRIGGER " + TABLE_NAME + "_FULL_TEXT_INDEX_TRIGGER_UPDATE BEFORE UPDATE ON " +
                TABLE_NAME + " FOR EACH ROW BEGIN " +
                "SET NEW." + TEST1_IDX + " = CAST(NEW." + TEST1 + " AS CHAR); " +
                "SET NEW." + TEST2_IDX + " = CAST(NEW." + TEST2 + " AS CHAR); " +
                "SET NEW." + TEST3_IDX + " = CAST(NEW." + TEST3 + " AS CHAR); " +
                "END;";
    }

}
