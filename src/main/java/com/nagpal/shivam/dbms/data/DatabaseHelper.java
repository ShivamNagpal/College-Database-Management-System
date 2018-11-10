package com.nagpal.shivam.dbms.data;

import com.nagpal.shivam.dbms.Log;
import com.nagpal.shivam.dbms.data.DatabaseContract.*;
import com.nagpal.shivam.dbms.model.DepartmentData;

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
}
