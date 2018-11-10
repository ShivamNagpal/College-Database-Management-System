package com.nagpal.shivam.dbms.model;

public class DepartmentData {
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

    public void format() {
        name = name.toUpperCase();
        departmentId = departmentId.toUpperCase();
    }
}
