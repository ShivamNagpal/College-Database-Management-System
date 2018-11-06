package com.nagpal.shivam.dbms.data;

import com.nagpal.shivam.dbms.Log;
import com.nagpal.shivam.dbms.data.DatabaseContract.*;

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

    public static List<String> fetchDepartmentNames() {
        String sql = "SELECT " +
                Department.NAME +
                " FROM " +
                Department.TABLE_NAME;
        List<String> list = new ArrayList<>();
        Connection connection = Database.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()) {
                list.add(set.getString(Department.NAME));
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
}
