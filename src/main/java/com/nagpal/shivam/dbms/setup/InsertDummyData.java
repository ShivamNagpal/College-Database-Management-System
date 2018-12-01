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
        list.add(new DepartmentData("CSE", "RN01"));
        list.add(new DepartmentData("ECE", "RN02"));
        list.add(new DepartmentData("Math", "RN03"));
        list.add(new DepartmentData("ISE", "RN04"));

        for (DepartmentData data : list) {
            DatabaseHelper.insertIntoDepartment(data);
        }
    }

    private static void insertDummyDataInProfessor() {
        ArrayList<ProfessorData> list = new ArrayList<>();
        list.add(new ProfessorData("John Scott", "RNCSP1", "1979-05-25", "Electronics City, Bengaluru", "789465133", "abc@abc.com", "Professor", "RN01"));
        list.add(new ProfessorData("James Smith", "RNCSP2", "1987-07-05", "Raja Rajeshwari Nagar, Bengaluru", "465465133", "def@def.com", "Associate Professor", "RN01"));
        list.add(new ProfessorData("Hearn Baker", "RNECP1", "1985-01-15", "Yeshwanthpur, Bengaluru", "963465133", "ijk@ijk.com", "Assistant Professor", "RN02"));
        list.add(new ProfessorData("Edward Scott", "RNECP2", "1982-08-25", "Majestic, Bengaluru", "852465133", "xyz@xyz.com", "Assistant Professor", "RN02"));
        list.add(new ProfessorData("Pavan Hegde", "RNMATP1", "1981-10-15", "Peenya, Bengaluru", "1562348963", "xyz@xyz.com", "Associate Professor", "RN03"));
        list.add(new ProfessorData("Girish Malya", "RNISP1", "1983-07-05", "Yeshwanthpur, Bengaluru", "852465133", "xyz@xyz.com", "Assistant Professor", "RN04"));

        for (ProfessorData data : list) {
            DatabaseHelper.insertIntoProfessor(data);
        }
    }

    private static void insertDummyDataInStudent() {
        ArrayList<StudentData> list = new ArrayList<>();
        list.add(new StudentData("Neha SN", "RNCSS1", "1992-05-25", "Electronics City, Bengaluru", "963465133", "abc@abc.com", "RN01"));
        list.add(new StudentData("Ahana K", "RNCSS2", "1995-07-05", "Raja Rajeshwari Nagar, Bengaluru", "465465133", "def@def.com", "RN01"));
        list.add(new StudentData("Santosh Kumar", "RNECS1", "1996-01-15", "Yeshwanthpur, Bengaluru", "963465133", "ijk@ijk.com", "RN02"));
        list.add(new StudentData("Veena M", "RNECS2", "1997-12-25", "Majestic, Bengaluru", "852465133", "xyz@xyz.com", "RN02"));
        list.add(new StudentData("Nagesh HR", "RNISS1", "1997-12-25", "Yeshwanthpur, Bengaluru", "123491356", "xyz@xyz.com", "RN04"));

        for (StudentData data : list) {
            DatabaseHelper.insertIntoStudent(data);
        }
    }

    private static void insertDummyDataInSubject() {
        ArrayList<SubjectData> list = new ArrayList<>();
        list.add(new SubjectData("Mathematics - 1", "17MAT01", "17CS", 1, 4, "RN01"));
        list.add(new SubjectData("Mathematics - 2", "17MAT02", "17CS", 2, 4, "RN01"));
        list.add(new SubjectData("Analog and Digital Electronics", "15CS302", "15CS", 3, 4, "RN02"));
        list.add(new SubjectData("Software Engineering", "15CS402", "15CS", 4, 4, "RN02"));

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
        list.add(new TeachesData("RNCSP1", "4A", "17MAT01"));
        list.add(new TeachesData("RNCSP2", "4A", "17MAT02"));
        list.add(new TeachesData("RNCSP1", "5B", "17MAT01"));
        list.add(new TeachesData("RNCSP2", "5B", "17MAT02"));

        for (TeachesData data : list) {
            DatabaseHelper.insertIntoTeaches(data);
        }
    }

    private static void insertDummyDataInDivision() {
        ArrayList<DivisionData> list = new ArrayList<>();
        list.add(new DivisionData("RNCSS1", "4A"));
        list.add(new DivisionData("RNCSS2", "5B"));

        for (DivisionData data : list) {
            DatabaseHelper.insertIntoDivision(data);
        }
    }

    private static void insertDummyDataInIaMarks() {
        ArrayList<IaMarksData> list = new ArrayList<>();
        list.add(new IaMarksData("RNCSS1", "4A", "17MAT01", 40, 42, 43));
        list.add(new IaMarksData("RNCSS1", "5B", "17MAT02", 45, 44, 43));
        list.add(new IaMarksData("RNCSS2", "4A", "17MAT01", 35, 42, 43));
        list.add(new IaMarksData("RNCSS2", "5B", "17MAT02", 0, 42, 43));

        for (IaMarksData data : list) {
            DatabaseHelper.insertIntoIaMarks(data);
        }
    }

}
