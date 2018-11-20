package com.nagpal.shivam.dbms.setup;

import com.nagpal.shivam.dbms.data.Database;
import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.*;

import java.util.ArrayList;

public class InsertDummyData {
    public static void main(String[] args) throws Exception {
        String[] credentials = Utils.getRootCredentials();
        Database.putParameters(credentials[0], credentials[1], credentials[2], credentials[3]);

        insertDummyDataInDepartment();
        insertDummyDataInProfessor();
        insertDummyDataInStudent();
        insertDummyDataInSubject();
        insertDummyDataInSemesterSection();
        insertDummyDataInTeaches();
        insertDummyDataInDivision();
        insertDummyDataInIaMarks();

        Database.closeConnection();
    }

    private static void insertDummyDataInDepartment() {
        ArrayList<DepartmentData> list = new ArrayList<>();
        list.add(new DepartmentData("CSE", "101"));
        list.add(new DepartmentData("ECE", "102"));
        list.add(new DepartmentData("Math", "103"));
        list.add(new DepartmentData("ISE", "104"));

        for (DepartmentData data : list) {
            DatabaseHelper.insertIntoDepartment(data);
        }
    }

    private static void insertDummyDataInProfessor() {
        ArrayList<ProfessorData> list = new ArrayList<>();
        list.add(new ProfessorData("Prof1", "P1", "1979-05-25", "Bengaluru", "789465133", "abc@abc.com", "Professor", "101"));
        list.add(new ProfessorData("Prof2", "P2", "1987-07-05", "Mumbai", "465465133", "def@def.com", "Associate Professor", "101"));
        list.add(new ProfessorData("Prof3", "P3", "1985-01-15", "Chennai", "963465133", "ijk@ijk.com", "Assistant Professor", "102"));
        list.add(new ProfessorData("Prof4", "P4", "1989-12-25", "Kolkata", "852465133", "xyz@xyz.com", "Assistant Professor", "102"));

        for (ProfessorData data : list) {
            DatabaseHelper.insertIntoProfessor(data);
        }
    }

    private static void insertDummyDataInStudent() {
        ArrayList<StudentData> list = new ArrayList<>();
        list.add(new StudentData("Stud1", "S1", "1992-05-25", "Bengaluru", "789465133", "abc@abc.com", "101"));
        list.add(new StudentData("Stud2", "S2", "1995-07-05", "Mumbai", "465465133", "def@def.com", "101"));
        list.add(new StudentData("Stud3", "S3", "1996-01-15", "Chennai", "963465133", "ijk@ijk.com", "102"));
        list.add(new StudentData("Stud4", "S4", "1997-12-25", "Kolkata", "852465133", "xyz@xyz.com", "102"));

        for (StudentData data : list) {
            DatabaseHelper.insertIntoStudent(data);
        }
    }

    private static void insertDummyDataInSubject() {
        ArrayList<SubjectData> list = new ArrayList<>();
        list.add(new SubjectData("Mathematics - 1", "17MAT01", "17CS", 1, 4, "101"));
        list.add(new SubjectData("Mathematics - 2", "17MAT02", "17CS", 2, 4, "101"));
        list.add(new SubjectData("Analog and Digital Electronics", "15CS302", "15CS", 3, 4, "102"));
        list.add(new SubjectData("Software Engineering", "15CS402", "15CS", 4, 4, "102"));

        for (SubjectData data : list) {
            DatabaseHelper.insertIntoSubject(data);
        }
    }

    private static void insertDummyDataInSemesterSection() {
        ArrayList<SemesterSectionData> list = new ArrayList<>();
        list.add(new SemesterSectionData("4A", 4, "A"));
        list.add(new SemesterSectionData("5B", 5, "B"));
        list.add(new SemesterSectionData("7A", 7, "A"));
        list.add(new SemesterSectionData("8B", 8, "B"));

        for (SemesterSectionData data : list) {
            DatabaseHelper.insertIntoSemesterSection(data);
        }
    }

    private static void insertDummyDataInTeaches() {
        ArrayList<TeachesData> list = new ArrayList<>();
        list.add(new TeachesData("P1", "4A", "17MAT01"));
        list.add(new TeachesData("P2", "4A", "17MAT02"));
        list.add(new TeachesData("P1", "5B", "17MAT01"));
        list.add(new TeachesData("P2", "5B", "17MAT02"));

        for (TeachesData data : list) {
            DatabaseHelper.insertIntoTeaches(data);
        }
    }

    private static void insertDummyDataInDivision() {
        ArrayList<DivisionData> list = new ArrayList<>();
        list.add(new DivisionData("S1", "4A"));
        list.add(new DivisionData("S2", "5B"));

        for (DivisionData data : list) {
            DatabaseHelper.insertIntoDivision(data);
        }
    }

    private static void insertDummyDataInIaMarks() {
        ArrayList<IaMarksData> list = new ArrayList<>();
        list.add(new IaMarksData("S1", "4A", "17MAT01", 40, 42, 43));
        list.add(new IaMarksData("S1", "5B", "17MAT02", 45, 44, 43));
        list.add(new IaMarksData("S2", "4A", "17MAT01", 35, 42, 43));
        list.add(new IaMarksData("S2", "5B", "17MAT02", 0, 42, 43));

        for (IaMarksData data : list) {
            DatabaseHelper.insertIntoIaMarks(data);
        }
    }

}
