package com.nagpal.shivam.dbms.model;

public class SubjectData {
    public long rowId;
    public String name;
    public String subjectId;
    public String scheme;
    public int semester;
    public int credits;
    public String departmentId;

    public SubjectData(long rowId, String name, String subjectId, String scheme, int semester, int credits, String departmentId) {
        this.rowId = rowId;
        this.name = name;
        this.subjectId = subjectId;
        this.scheme = scheme;
        this.semester = semester;
        this.credits = credits;
        this.departmentId = departmentId;
    }
}
