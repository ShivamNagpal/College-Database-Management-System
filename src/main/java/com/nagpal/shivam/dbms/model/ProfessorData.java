package com.nagpal.shivam.dbms.model;

public class ProfessorData {
    public long rowId;
    public String name;
    public String professorId;
    public String dateOfBirth;
    public String address;
    public String phone;
    public String email;
    public String designation;
    public String departmentId;

    public ProfessorData(long rowId, String name, String professorId, String dateOfBirth, String address, String phone, String email, String designation, String departmentId) {
        this.rowId = rowId;
        this.name = name;
        this.professorId = professorId;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.designation = designation;
        this.departmentId = departmentId;
    }
}
