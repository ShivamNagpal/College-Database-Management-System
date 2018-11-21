package com.nagpal.shivam.dbms.model;

import com.nagpal.shivam.dbms.data.PreviewIgnoredAttribute;

public class SemesterSectionData {
    @PreviewIgnoredAttribute
    public long rowId;
    public String semesterSectionId;
    public int semester;
    public String section;

    public SemesterSectionData() {
    }

    public SemesterSectionData(String semesterSectionId, int semester, String section) {
        this.semesterSectionId = semesterSectionId;
        this.semester = semester;
        this.section = section;
    }

    public SemesterSectionData(long rowId, String semesterSectionId, int semester, String section) {
        this.rowId = rowId;
        this.semesterSectionId = semesterSectionId;
        this.semester = semester;
        this.section = section;
    }
}
