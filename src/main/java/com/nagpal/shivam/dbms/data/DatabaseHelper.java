package com.nagpal.shivam.dbms.data;

import com.nagpal.shivam.dbms.Log;
import com.nagpal.shivam.dbms.data.DatabaseContract.Department;
import com.nagpal.shivam.dbms.data.DatabaseContract.Student;
import com.nagpal.shivam.dbms.data.DatabaseContract.Professor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
    private static final String CLASS_NAME = DatabaseHelper.class.getSimpleName();

    public static boolean createTables() {
        Connection connection = Database.getConnection();
        try {
            Statement statement = connection.createStatement();

            statement.execute(Department.SQL_CREATE_TABLE);
            statement.execute(Professor.SQL_CREATE_TABLE);
            statement.execute(Student.SQL_CREATE_TABLE);
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return false;
        }
        return true;
    }
}
