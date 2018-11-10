package com.nagpal.shivam.dbms.data;

import com.nagpal.shivam.dbms.Log;
import com.nagpal.shivam.dbms.data.DatabaseContract.*;
import com.nagpal.shivam.dbms.model.DepartmentData;
import com.nagpal.shivam.dbms.model.ProfessorData;
import com.nagpal.shivam.dbms.model.SemesterSectionData;
import com.nagpal.shivam.dbms.model.SubjectData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String CLASS_NAME = DatabaseHelper.class.getSimpleName();

    public static int createTables() {
        Connection connection = Database.getConnection();
        try {
            Statement statement = connection.createStatement();

            statement.execute(Department.SQL_CREATE_TABLE);
            statement.execute(Professor.SQL_CREATE_TABLE);
            statement.execute(Student.SQL_CREATE_TABLE);
            statement.execute(Subject.SQL_CREATE_TABLE);
            statement.execute(SemesterSection.SQL_CREATE_TABLE);
            statement.execute(Teaches.SQL_CREATE_TABLE);
            statement.execute(Division.SQL_CREATE_TABLE);
            statement.execute(IaMarks.SQL_CREATE_TABLE);
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static List<DepartmentData> fetchDepartmentDetails() {
        String sql = "SELECT " +
                DatabaseContract.ROW_ID + ", *" +
                " FROM " +
                Department.TABLE_NAME;
        Connection connection = Database.getConnection();
        List<DepartmentData> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            int rowIdIndex = set.findColumn(DatabaseContract.ROW_ID);
            int nameIndex = set.findColumn(Department.NAME);
            int departmentIdIndex = set.findColumn(Department.DEPARTMENT_ID);

            while (set.next()) {
                list.add(new DepartmentData(set.getLong(rowIdIndex), set.getString(nameIndex), set.getString(departmentIdIndex)));
            }
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
        }
        return list;
    }

    public static List<ProfessorData> fetchProfessorDetails() {
        String sql = "SELECT " +
                DatabaseContract.ROW_ID + ", *" +
                " FROM " +
                Professor.TABLE_NAME;
        Connection connection = Database.getConnection();
        List<ProfessorData> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            int rowIdIndex = set.findColumn(DatabaseContract.ROW_ID);
            int nameIndex = set.findColumn(Professor.NAME);
            int idIndex = set.findColumn(Professor.PROFESSOR_ID);
            int dobIndex = set.findColumn(Professor.DATE_OF_BIRTH);
            int addressIndex = set.findColumn(Professor.ADDRESS);
            int phoneIndex = set.findColumn(Professor.PHONE);
            int emailIndex = set.findColumn(Professor.EMAIL);
            int designationIndex = set.findColumn(Professor.DESIGNATION);
            int departmentIdIndex = set.findColumn(Professor.DEPARTMENT_ID);

            while (set.next()) {
                list.add(new ProfessorData(set.getLong(rowIdIndex), set.getString(nameIndex), set.getString(idIndex), set.getString(dobIndex), set.getString(addressIndex), set.getString(phoneIndex), set.getString(emailIndex), set.getString(designationIndex), set.getString(departmentIdIndex)));
            }
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
        }
        return list;
    }

    public static List<SemesterSectionData> fetchSemesterSectionDetails() {
        String sql = "SELECT " +
                DatabaseContract.ROW_ID + ", *" +
                " FROM " +
                SemesterSection.TABLE_NAME;
        Connection connection = Database.getConnection();
        List<SemesterSectionData> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            int rowIdIndex = set.findColumn(DatabaseContract.ROW_ID);
            int idIndex = set.findColumn(SemesterSection.SEM_SEC_ID);
            int semesterIndex = set.findColumn(SemesterSection.SEMESTER);
            int sectionIndex = set.findColumn(SemesterSection.SECTION);

            while (set.next()) {
                list.add(new SemesterSectionData(set.getLong(rowIdIndex), set.getString(idIndex), set.getInt(semesterIndex), set.getString(sectionIndex)));
            }
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
        }
        return list;
    }

    public static List<SubjectData> fetchSubjectDetails() {
        String sql = "SELECT " +
                DatabaseContract.ROW_ID + ", *" +
                " FROM " +
                Subject.TABLE_NAME;
        Connection connection = Database.getConnection();
        List<SubjectData> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            int rowIdIndex = set.findColumn(DatabaseContract.ROW_ID);
            int nameIndex = set.findColumn(Subject.NAME);
            int subjectIdIndex = set.findColumn(Subject.SUBJECT_ID);
            int schemeIndex = set.findColumn(Subject.SCHEME);
            int semesterIndex = set.findColumn(Subject.SEMESTER);
            int creditsIndex = set.findColumn(Subject.CREDITS);
            int departmentIdIndex = set.findColumn(Subject.DEPARTMENT_ID);


            while (set.next()) {
                list.add(new SubjectData(set.getLong(rowIdIndex), set.getString(nameIndex), set.getString(subjectIdIndex), set.getString(schemeIndex), set.getInt(semesterIndex), set.getInt(creditsIndex), set.getString(departmentIdIndex)));
            }
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
        }
        return list;
    }

    public static int insertIntoDepartment(String name, String id) {
        String sql = "INSERT INTO " +
                Department.TABLE_NAME +
                "(" +
                Department.NAME + ", " +
                Department.DEPARTMENT_ID +
                ") VALUES(?, ?)";
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int insertIntoStudent(String name, String id, String dob, String address, String email, String phone, String departmentId) {
        String sql = "INSERT INTO " +
                Student.TABLE_NAME +
                "(" +
                Student.NAME + ", " +
                Student.STUDENT_ID + ", " +
                Student.DATE_OF_BIRTH + ", " +
                Student.ADDRESS + ", " +
                Student.EMAIL + ", " +
                Student.PHONE + ", " +
                Student.DEPARTMENT_ID +
                ") VALUES(?, ?, ?, ?, ?, ?, ?)";
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, id);
            statement.setString(3, dob);
            statement.setString(4, address);
            statement.setString(5, email);
            statement.setString(6, phone);
            statement.setString(7, departmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int insertIntoProfessor(String name, String id, String dob, String address, String email, String phone, String departmentId, String designation) {
        String sql = "INSERT INTO " +
                Professor.TABLE_NAME +
                "(" +
                Professor.NAME + ", " +
                Professor.PROFESSOR_ID + ", " +
                Professor.DATE_OF_BIRTH + ", " +
                Professor.ADDRESS + ", " +
                Professor.EMAIL + ", " +
                Professor.PHONE + ", " +
                Professor.DEPARTMENT_ID + ", " +
                Professor.DESIGNATION +
                ") VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, id);
            statement.setString(3, dob);
            statement.setString(4, address);
            statement.setString(5, email);
            statement.setString(6, phone);
            statement.setString(7, departmentId);
            statement.setString(8, designation);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;

    }

    public static int insertIntoSubject(String name, String id, String scheme, int semester, int credits, String departmentId) {
        String sql = "INSERT INTO " +
                Subject.TABLE_NAME +
                "(" +
                Subject.NAME + ", " +
                Subject.SUBJECT_ID + ", " +
                Subject.SCHEME + ", " +
                Subject.SEMESTER + ", " +
                Subject.CREDITS + ", " +
                Subject.DEPARTMENT_ID +
                ") VALUES(?, ?, ?, ?, ?, ?)";
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, id);
            statement.setString(3, scheme);
            statement.setInt(4, semester);
            statement.setInt(5, credits);
            statement.setString(6, departmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }
}
