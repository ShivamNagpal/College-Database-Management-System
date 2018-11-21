package com.nagpal.shivam.dbms.model;

import com.nagpal.shivam.dbms.data.PreviewIgnoredAttribute;

public class SubjectData {
    @PreviewIgnoredAttribute
    public long rowId;
    public String name;
    public String subjectId;
    public String scheme;
    public int semester;
    public int credits;
    public String departmentId;

    public SubjectData() {
    }

    public SubjectData(String name, String subjectId, String scheme, int semester, int credits, String departmentId) {
        this.name = name;
        this.subjectId = subjectId;
        this.scheme = scheme;
        this.semester = semester;
        this.credits = credits;
        this.departmentId = departmentId;
    }

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
