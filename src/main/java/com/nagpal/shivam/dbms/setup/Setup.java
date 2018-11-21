package com.nagpal.shivam.dbms.setup;

import com.nagpal.shivam.dbms.Log;
import com.nagpal.shivam.dbms.data.Database;
import com.nagpal.shivam.dbms.data.DatabaseContract;
import com.nagpal.shivam.dbms.data.SqlErrorCodes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Setup {
    private static final String CLASS_NAME = Setup.class.getSimpleName();

    public static void main(String[] args) throws Exception {
        String[] credentials = Utils.getRootCredentials();
        Connection connection = DriverManager.getConnection(credentials[0], credentials[2], credentials[3]);
        Statement statement = connection.createStatement();
        statement.execute("CREATE SCHEMA " + credentials[1]);
        Database.closeSqlComponents(statement);
        Database.closeSqlComponents(connection);

        Database.putParameters(credentials[0], credentials[1], credentials[2], credentials[3]);
        setUpDatabase();

        Database.closeConnection();
    }

    private static int setUpDatabase() {
        try {
            createTables();
            createTriggers();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    private static void createTables() throws SQLException {
        Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(DatabaseContract.Department.SQL_CREATE_TABLE);
        statement.execute(DatabaseContract.Professor.SQL_CREATE_TABLE);
        statement.execute(DatabaseContract.Student.SQL_CREATE_TABLE);
        statement.execute(DatabaseContract.Subject.SQL_CREATE_TABLE);
        statement.execute(DatabaseContract.SemesterSection.SQL_CREATE_TABLE);
        statement.execute(DatabaseContract.Teaches.SQL_CREATE_TABLE);
        statement.execute(DatabaseContract.Division.SQL_CREATE_TABLE);
        statement.execute(DatabaseContract.IaMarks.SQL_CREATE_TABLE);
    }

    private static void createTriggers() throws SQLException {
        Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(DatabaseContract.Subject.SQL_CREATE_FULLTEXT_INDEX_TRIGGER_INSERT);
        statement.execute(DatabaseContract.Subject.SQL_CREATE_FULLTEXT_INDEX_TRIGGER_UPDATE);
        statement.execute(DatabaseContract.SemesterSection.SQL_CREATE_FULLTEXT_INDEX_TRIGGER_INSERT);
        statement.execute(DatabaseContract.SemesterSection.SQL_CREATE_FULLTEXT_INDEX_TRIGGER_UPDATE);
        statement.execute(DatabaseContract.IaMarks.SQL_CREATE_FULLTEXT_INDEX_TRIGGER_INSERT);
        statement.execute(DatabaseContract.IaMarks.SQL_CREATE_FULLTEXT_INDEX_TRIGGER_UPDATE);
    }
}
