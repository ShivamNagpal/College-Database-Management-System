package com.nagpal.shivam.dbms.data;

import com.nagpal.shivam.dbms.Log;
import com.nagpal.shivam.dbms.data.DatabaseContract.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String CLASS_NAME = DatabaseHelper.class.getSimpleName();

    public static boolean createTables() {
        Connection connection = Database.getConnection();
        try {
            Statement statement = connection.createStatement();

            statement.execute(Department.SQL_CREATE_TABLE);
            statement.execute(Professor.SQL_CREATE_TABLE);
            statement.execute(Student.SQL_CREATE_TABLE);
            statement.execute(Subject.SQL_CREATE_TABLE);
            statement.execute(Teaches.SQL_CREATE_TABLE);
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return false;
        }
        return true;
    }

    public static List<String> fetchDepartmentNames() {
        // TODO: DUMMY DATA: Replace with actual query
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        return list;
    }
}
