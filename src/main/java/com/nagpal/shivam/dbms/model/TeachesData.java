package com.nagpal.shivam.dbms.model;

import com.nagpal.shivam.dbms.data.PreviewIgnoredAttribute;

public class TeachesData {
    @PreviewIgnoredAttribute
    public long rowId;
    public String professorId;
    public String semesterSectionId;
    public String subjectId;

    public TeachesData() {
    }

    public TeachesData(String professorId, String semesterSectionId, String subjectId) {
        this.professorId = professorId;
        this.semesterSectionId = semesterSectionId;
        this.subjectId = subjectId;
    }

    public TeachesData(long rowId, String professorId, String semesterSectionId, String subjectId) {
        this.rowId = rowId;
        this.professorId = professorId;
        this.semesterSectionId = semesterSectionId;
        this.subjectId = subjectId;
    }
}
