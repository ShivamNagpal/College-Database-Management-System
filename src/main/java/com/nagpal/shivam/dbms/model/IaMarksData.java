package com.nagpal.shivam.dbms.model;

public class IaMarksData {
    public long rowId;
    public String studentId;
    public String semSecId;
    public String subjectId;
    public int test1;
    public int test2;
    public int test3;

    public IaMarksData() {
    }

    public IaMarksData(String studentId, String semSecId, String subjectId, int test1, int test2, int test3) {
        this.studentId = studentId;
        this.semSecId = semSecId;
        this.subjectId = subjectId;
        this.test1 = test1;
        this.test2 = test2;
        this.test3 = test3;
    }

    public IaMarksData(long rowId, String studentId, String semSecId, String subjectId, int test1, int test2, int test3) {
        this.rowId = rowId;
        this.studentId = studentId;
        this.semSecId = semSecId;
        this.subjectId = subjectId;
        this.test1 = test1;
        this.test2 = test2;
        this.test3 = test3;
    }
}