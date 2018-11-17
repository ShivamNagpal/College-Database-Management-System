package com.nagpal.shivam.dbms.dummy;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.StudentData;

public class Temp {
    public static void main(String[] args) {
        DatabaseHelper.insertIntoStudent(new StudentData("Hello", "456789", null, null, null, null, "CSE"));
    }
}
