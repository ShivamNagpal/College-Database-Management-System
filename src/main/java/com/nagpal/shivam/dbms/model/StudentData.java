package com.nagpal.shivam.dbms.model;

public class StudentData {
    public long rowId;
    public String name;
    public String studentId;
    public String dateOfBirth;
    public String address;
    public String phone;
    public String email;
    public String departmentId;

    public StudentData() {
    }

    public StudentData(String name, String studentId, String dateOfBirth, String address, String phone, String email, String departmentId) {
        this.name = name;
        this.studentId = studentId;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.departmentId = departmentId;
    }

    public StudentData(long rowId, String name, String studentId, String dateOfBirth, String address, String phone, String email, String departmentId) {
        this.rowId = rowId;
        this.name = name;
        this.studentId = studentId;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.departmentId = departmentId;
    }
}
