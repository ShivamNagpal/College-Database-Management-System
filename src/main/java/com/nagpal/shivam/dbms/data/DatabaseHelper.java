package com.nagpal.shivam.dbms.data;

import com.nagpal.shivam.dbms.Log;
import com.nagpal.shivam.dbms.data.DatabaseContract.*;
import com.nagpal.shivam.dbms.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {


    public static final String SEARCH_MODE_NATURAL_LANGUAGE = "Natural Language Mode";
    public static final String SEARCH_MODE_BOOLEAN = "Boolean Mode";
    private static final String CLASS_NAME = DatabaseHelper.class.getSimpleName();

    public static List<DepartmentData> fetchDepartmentDetails() {
        String sql = "SELECT * FROM " +
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

    public static List<DepartmentData> fetchParticularDepartment(String departmentId, boolean include) {
        String operator = include ? " = " : " != ";
        String sql = "SELECT * FROM " +
                Department.TABLE_NAME +
                " WHERE " +
                Department.DEPARTMENT_ID + operator + "'" + departmentId + "'";
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

    public static List<DepartmentData> searchDepartmentDetails(String searchString, String mode) {
        String sql = "SELECT * FROM " +
                Department.TABLE_NAME +
                " WHERE MATCH(" + Department.NAME + "," + Department.DEPARTMENT_ID + ") " +
                "AGAINST(? IN " + mode.toUpperCase() + ")";
        Connection connection = Database.getConnection();
        List<DepartmentData> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, searchString);
            ResultSet set = preparedStatement.executeQuery();
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

    public static List<StudentData> fetchStudentDetails() {
        String sql = "SELECT * FROM " +
                Student.TABLE_NAME;
        Connection connection = Database.getConnection();
        List<StudentData> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            int rowIdIndex = set.findColumn(DatabaseContract.ROW_ID);
            int nameIndex = set.findColumn(Student.NAME);
            int idIndex = set.findColumn(Student.STUDENT_ID);
            int dobIndex = set.findColumn(Student.DATE_OF_BIRTH);
            int addressIndex = set.findColumn(Student.ADDRESS);
            int phoneIndex = set.findColumn(Student.PHONE);
            int emailIndex = set.findColumn(Student.EMAIL);
            int departmentIdIndex = set.findColumn(Student.DEPARTMENT_ID);

            while (set.next()) {
                list.add(new StudentData(set.getLong(rowIdIndex), set.getString(nameIndex), set.getString(idIndex), set.getString(dobIndex), set.getString(addressIndex), set.getString(phoneIndex), set.getString(emailIndex), set.getString(departmentIdIndex)));
            }
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
        }
        return list;
    }

    public static List<StudentData> fetchParticularStudent(String studentId, boolean include) {
        String operator = include ? " = " : " != ";
        String sql = "SELECT * FROM " +
                Student.TABLE_NAME +
                " WHERE " +
                Student.STUDENT_ID + operator + "'" + studentId + "'";
        Connection connection = Database.getConnection();
        List<StudentData> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            int rowIdIndex = set.findColumn(DatabaseContract.ROW_ID);
            int nameIndex = set.findColumn(Student.NAME);
            int idIndex = set.findColumn(Student.STUDENT_ID);
            int dobIndex = set.findColumn(Student.DATE_OF_BIRTH);
            int addressIndex = set.findColumn(Student.ADDRESS);
            int phoneIndex = set.findColumn(Student.PHONE);
            int emailIndex = set.findColumn(Student.EMAIL);
            int departmentIdIndex = set.findColumn(Student.DEPARTMENT_ID);

            while (set.next()) {
                list.add(new StudentData(set.getLong(rowIdIndex), set.getString(nameIndex), set.getString(idIndex), set.getString(dobIndex), set.getString(addressIndex), set.getString(phoneIndex), set.getString(emailIndex), set.getString(departmentIdIndex)));
            }
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
        }
        return list;
    }

    public static List<ProfessorData> fetchProfessorDetails() {
        String sql = "SELECT * FROM " +
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

    public static List<ProfessorData> fetchParticularProfessor(String professorId, boolean include) {
        String operator = include ? " = " : " != ";
        String sql = "SELECT * FROM " +
                Professor.TABLE_NAME +
                " WHERE " +
                Professor.PROFESSOR_ID + operator + "'" + professorId + "'";
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
        String sql = "SELECT * FROM " +
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

    public static List<SemesterSectionData> fetchParticularSemesterSection(String semesterSectionId, boolean include) {
        String operator = include ? " = " : " != ";
        String sql = "SELECT * FROM " +
                SemesterSection.TABLE_NAME +
                " WHERE " +
                SemesterSection.SEM_SEC_ID + operator + "'" + semesterSectionId + "'";
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
        String sql = "SELECT * FROM " +
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

    public static List<SubjectData> fetchParticularSubject(String subjectId, boolean include) {
        String operator = include ? " = " : " != ";
        String sql = "SELECT * FROM " +
                Subject.TABLE_NAME +
                " WHERE " +
                Subject.SUBJECT_ID + operator + "'" + subjectId + "'";
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

    public static List<TeachesData> fetchTeachesDetails() {
        String sql = "SELECT * FROM " +
                Teaches.TABLE_NAME;
        Connection connection = Database.getConnection();
        List<TeachesData> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            int rowIdIndex = set.findColumn(DatabaseContract.ROW_ID);
            int professorIdIndex = set.findColumn(Teaches.PROFESSOR_ID);
            int semSecIdIndex = set.findColumn(Teaches.SEM_SEC_ID);
            int subjectIdIndex = set.findColumn(Teaches.SUBJECT_ID);

            while (set.next()) {
                list.add(new TeachesData(set.getLong(rowIdIndex), set.getString(professorIdIndex), set.getString(semSecIdIndex), set.getString(subjectIdIndex)));
            }
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
        }
        return list;
    }

    public static List<DivisionData> fetchDivisionDetails() {
        String sql = "SELECT * FROM " +
                Division.TABLE_NAME;
        Connection connection = Database.getConnection();
        List<DivisionData> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            int rowIdIndex = set.findColumn(DatabaseContract.ROW_ID);
            int studentIdIndex = set.findColumn(Division.STUDENT_ID);
            int semSecIdIndex = set.findColumn(Division.SEM_SEC_ID);

            while (set.next()) {
                list.add(new DivisionData(set.getLong(rowIdIndex), set.getString(studentIdIndex), set.getString(semSecIdIndex)));
            }
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
        }
        return list;
    }

    public static List<IaMarksData> fetchIaMarksDetails() {
        String sql = "SELECT * FROM " +
                IaMarks.TABLE_NAME;
        Connection connection = Database.getConnection();
        List<IaMarksData> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            int rowIdIndex = set.findColumn(DatabaseContract.ROW_ID);
            int studentIdIndex = set.findColumn(IaMarks.STUDENT_ID);
            int semSecIdIndex = set.findColumn(IaMarks.SEM_SEC_ID);
            int subjectIdIndex = set.findColumn(IaMarks.SUBJECT_ID);
            int test1Index = set.findColumn(IaMarks.TEST1);
            int test2Index = set.findColumn(IaMarks.TEST2);
            int test3Index = set.findColumn(IaMarks.TEST3);

            while (set.next()) {
                list.add(new IaMarksData(set.getLong(rowIdIndex), set.getString(studentIdIndex), set.getString(semSecIdIndex), set.getString(subjectIdIndex), set.getInt(test1Index), set.getInt(test2Index), set.getInt(test3Index)));
            }
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
        }
        return list;
    }

    public static int insertIntoDepartment(DepartmentData departmentData) {
        String sql = "INSERT INTO " +
                Department.TABLE_NAME +
                "(" +
                Department.NAME + ", " +
                Department.DEPARTMENT_ID +
                ") VALUES(?, ?)";
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, departmentData.name);
            statement.setString(2, departmentData.departmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int insertIntoStudent(StudentData studentData) {
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
            statement.setString(1, studentData.name);
            statement.setString(2, studentData.studentId);
            statement.setString(3, studentData.dateOfBirth);
            statement.setString(4, studentData.address);
            statement.setString(5, studentData.email);
            statement.setString(6, studentData.phone);
            statement.setString(7, studentData.departmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int insertIntoProfessor(ProfessorData professorData) {
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
            statement.setString(1, professorData.name);
            statement.setString(2, professorData.professorId);
            statement.setString(3, professorData.dateOfBirth);
            statement.setString(4, professorData.address);
            statement.setString(5, professorData.email);
            statement.setString(6, professorData.phone);
            statement.setString(7, professorData.departmentId);
            statement.setString(8, professorData.designation);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;

    }

    public static int insertIntoSubject(SubjectData subjectData) {
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
            statement.setString(1, subjectData.name);
            statement.setString(2, subjectData.subjectId);
            statement.setString(3, subjectData.scheme);
            statement.setInt(4, subjectData.semester);
            statement.setInt(5, subjectData.credits);
            statement.setString(6, subjectData.departmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int insertIntoSemesterSection(SemesterSectionData semesterSectionData) {
        String sql = "INSERT INTO " +
                SemesterSection.TABLE_NAME +
                "(" +
                SemesterSection.SEM_SEC_ID + ", " +
                SemesterSection.SEMESTER + ", " +
                SemesterSection.SECTION +
                ") VALUES(?, ?, ?)";
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, semesterSectionData.semesterSectionId);
            statement.setInt(2, semesterSectionData.semester);
            statement.setString(3, semesterSectionData.section);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int insertIntoTeaches(TeachesData teachesData) {
        String sql = "INSERT INTO " +
                Teaches.TABLE_NAME +
                "(" +
                Teaches.PROFESSOR_ID + ", " +
                Teaches.SEM_SEC_ID + ", " +
                Teaches.SUBJECT_ID +
                ") VALUES(?, ?, ?)";
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, teachesData.professorId);
            statement.setString(2, teachesData.semesterSectionId);
            statement.setString(3, teachesData.subjectId);
            statement.executeUpdate();
            Database.closeSqlComponents(statement);
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int insertIntoDivision(DivisionData divisionData) {
        String sql = "INSERT INTO " +
                Division.TABLE_NAME +
                "(" +
                Division.STUDENT_ID + ", " +
                Division.SEM_SEC_ID +
                ") VALUES(?, ?)";
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, divisionData.studentId);
            statement.setString(2, divisionData.semesterSectionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int insertIntoIaMarks(IaMarksData iaMarksData) {
        String sql = "INSERT INTO " +
                IaMarks.TABLE_NAME +
                "(" +
                IaMarks.STUDENT_ID + ", " +
                IaMarks.SEM_SEC_ID + ", " +
                IaMarks.SUBJECT_ID + ", " +
                IaMarks.TEST1 + ", " +
                IaMarks.TEST2 + ", " +
                IaMarks.TEST3 +
                ") VALUES(?, ?, ?, ?, ?, ?)";
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, iaMarksData.studentId);
            statement.setString(2, iaMarksData.semSecId);
            statement.setString(3, iaMarksData.subjectId);
            statement.setInt(4, iaMarksData.test1);
            statement.setInt(5, iaMarksData.test2);
            statement.setInt(6, iaMarksData.test3);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int updateDepartment(DepartmentData departmentData) {
        String sql = "UPDATE " +
                Department.TABLE_NAME +
                " SET " +
                Department.NAME + "=?, " +
                Department.DEPARTMENT_ID + "=? " +
                "WHERE " + DatabaseContract.ROW_ID + " =" + departmentData.rowId;
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, departmentData.name);
            statement.setString(2, departmentData.departmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int updateStudent(StudentData studentData) {
        String sql = "UPDATE " +
                Student.TABLE_NAME +
                " SET " +
                Student.NAME + "=?, " +
                Student.STUDENT_ID + "=?, " +
                Student.DATE_OF_BIRTH + "=?, " +
                Student.ADDRESS + "=?, " +
                Student.EMAIL + "=?, " +
                Student.PHONE + "=?, " +
                Student.DEPARTMENT_ID + "=? " +
                "WHERE " + DatabaseContract.ROW_ID + " =" + studentData.rowId;
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentData.name);
            statement.setString(2, studentData.studentId);
            statement.setString(3, studentData.dateOfBirth);
            statement.setString(4, studentData.address);
            statement.setString(5, studentData.email);
            statement.setString(6, studentData.phone);
            statement.setString(7, studentData.departmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int updateProfessor(ProfessorData professorData) {
        String sql = "UPDATE " +
                Professor.TABLE_NAME +
                " SET " +
                Professor.NAME + "=?, " +
                Professor.PROFESSOR_ID + "=?, " +
                Professor.DATE_OF_BIRTH + "=?, " +
                Professor.ADDRESS + "=?, " +
                Professor.EMAIL + "=?, " +
                Professor.PHONE + "=?, " +
                Professor.DEPARTMENT_ID + "=?, " +
                Professor.DESIGNATION + "=? " +
                "WHERE " + DatabaseContract.ROW_ID + " =" + professorData.rowId;
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, professorData.name);
            statement.setString(2, professorData.professorId);
            statement.setString(3, professorData.dateOfBirth);
            statement.setString(4, professorData.address);
            statement.setString(5, professorData.email);
            statement.setString(6, professorData.phone);
            statement.setString(7, professorData.departmentId);
            statement.setString(8, professorData.designation);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int updateSubject(SubjectData subjectData) {
        String sql = "UPDATE " +
                Subject.TABLE_NAME +
                " SET " +
                Subject.NAME + "=?, " +
                Subject.SUBJECT_ID + "=?, " +
                Subject.SCHEME + "=?, " +
                Subject.SEMESTER + "=?, " +
                Subject.CREDITS + "=?, " +
                Subject.DEPARTMENT_ID + "=? " +
                "WHERE " + DatabaseContract.ROW_ID + " =" + subjectData.rowId;
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, subjectData.name);
            statement.setString(2, subjectData.subjectId);
            statement.setString(3, subjectData.scheme);
            statement.setInt(4, subjectData.semester);
            statement.setInt(5, subjectData.credits);
            statement.setString(6, subjectData.departmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int updateSemesterSection(SemesterSectionData semesterSectionData) {
        String sql = "UPDATE " +
                SemesterSection.TABLE_NAME +
                " SET " +
                SemesterSection.SEM_SEC_ID + "=?, " +
                SemesterSection.SEMESTER + "=?, " +
                SemesterSection.SECTION + "=? " +
                "WHERE " + DatabaseContract.ROW_ID + " =" + semesterSectionData.rowId;
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, semesterSectionData.semesterSectionId);
            statement.setInt(2, semesterSectionData.semester);
            statement.setString(3, semesterSectionData.section);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int updateTeaches(TeachesData teachesData) {
        String sql = "UPDATE " +
                Teaches.TABLE_NAME +
                " SET " +
                Teaches.PROFESSOR_ID + "=?, " +
                Teaches.SEM_SEC_ID + "=?, " +
                Teaches.SUBJECT_ID + "=? " +
                "WHERE " + DatabaseContract.ROW_ID + " =" + teachesData.rowId;
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, teachesData.professorId);
            statement.setString(2, teachesData.semesterSectionId);
            statement.setString(3, teachesData.subjectId);
            statement.executeUpdate();
            Database.closeSqlComponents(statement);
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int updateDivision(DivisionData divisionData) {
        String sql = "UPDATE " +
                Division.TABLE_NAME +
                " SET " +
                Division.STUDENT_ID + "=?, " +
                Division.SEM_SEC_ID + "=? " +
                "WHERE " + DatabaseContract.ROW_ID + " =" + divisionData.rowId;
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, divisionData.studentId);
            statement.setString(2, divisionData.semesterSectionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int updateIaMarks(IaMarksData iaMarksData) {
        String sql = "UPDATE " +
                IaMarks.TABLE_NAME +
                " SET " +
                IaMarks.STUDENT_ID + "=?, " +
                IaMarks.SEM_SEC_ID + "=?, " +
                IaMarks.SUBJECT_ID + "=?, " +
                IaMarks.TEST1 + "=?, " +
                IaMarks.TEST2 + "=?, " +
                IaMarks.TEST3 + "=? " +
                "WHERE " + DatabaseContract.ROW_ID + " =" + iaMarksData.rowId;
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, iaMarksData.studentId);
            statement.setString(2, iaMarksData.semSecId);
            statement.setString(3, iaMarksData.subjectId);
            statement.setInt(4, iaMarksData.test1);
            statement.setInt(5, iaMarksData.test2);
            statement.setInt(6, iaMarksData.test3);
            statement.executeUpdate();
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

    public static int deleteRow(String tableName, long rowId) {
        String sql = "DELETE FROM " +
                tableName +
                " WHERE " + DatabaseContract.ROW_ID + " =" + rowId;
        Connection connection = Database.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return e.getErrorCode();
        }
        return SqlErrorCodes.SQLITE_OK;
    }

}
