package com.nagpal.shivam.dbms.model;

import com.nagpal.shivam.dbms.data.PreviewIgnoredAttribute;

public class DepartmentData {
    @PreviewIgnoredAttribute
    public long rowId;
    public String name;
    public String departmentId;

    public DepartmentData() {
    }

    public DepartmentData(String name, String departmentId) {
        this.name = name;
        this.departmentId = departmentId;
    }

    public DepartmentData(long rowId, String name, String departmentId) {
        this.rowId = rowId;
        this.name = name;
        this.departmentId = departmentId;
    }

}
