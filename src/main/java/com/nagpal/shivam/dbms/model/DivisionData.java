package com.nagpal.shivam.dbms.model;

public class DivisionData {
    public long rowId;
    public String studentId;
    public String semesterSectionId;

    public DivisionData() {
    }

    public DivisionData(String studentId, String semesterSectionId) {
        this.studentId = studentId;
        this.semesterSectionId = semesterSectionId;
    }

    public DivisionData(long rowId, String studentId, String semesterSectionId) {
        this.rowId = rowId;
        this.studentId = studentId;
        this.semesterSectionId = semesterSectionId;
    }
}
