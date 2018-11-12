package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.model.DepartmentData;
import com.nagpal.shivam.dbms.model.ProfessorData;
import com.nagpal.shivam.dbms.model.SemesterSectionData;
import com.nagpal.shivam.dbms.model.StudentData;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

class Utils {

    public static Callback<ListView<DepartmentData>, ListCell<DepartmentData>> getDepartmentComboBoxCallback() {
        return new Callback<ListView<DepartmentData>, ListCell<DepartmentData>>() {
            @Override
            public ListCell<DepartmentData> call(ListView<DepartmentData> param) {

                return new ListCell<DepartmentData>() {
                    @Override
                    protected void updateItem(DepartmentData item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.departmentId + " | " + item.name);
                        }
                    }
                };
            }
        };
    }

    public static Callback<ListView<SemesterSectionData>, ListCell<SemesterSectionData>> getSemesterSectionComboBoxCallback() {
        return new Callback<ListView<SemesterSectionData>, ListCell<SemesterSectionData>>() {
            @Override
            public ListCell<SemesterSectionData> call(ListView<SemesterSectionData> param) {
                return new ListCell<SemesterSectionData>() {
                    @Override
                    protected void updateItem(SemesterSectionData item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.semesterSectionId + " | " + item.semester + " | " + item.section);
                        }
                    }
                };
            }
        };
    }

    public static Callback<ListView<ProfessorData>, ListCell<ProfessorData>> getProfessorComboBoxCallback() {
        return new Callback<ListView<ProfessorData>, ListCell<ProfessorData>>() {
            @Override
            public ListCell<ProfessorData> call(ListView<ProfessorData> param) {
                return new ListCell<ProfessorData>() {
                    @Override
                    protected void updateItem(ProfessorData item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.professorId + " | " + item.name);
                        }
                    }
                };
            }
        };
    }

    public static Callback<ListView<StudentData>, ListCell<StudentData>> getStudentComboBoxCallback() {
        return new Callback<ListView<StudentData>, ListCell<StudentData>>() {
            @Override
            public ListCell<StudentData> call(ListView<StudentData> param) {
                return new ListCell<StudentData>() {
                    @Override
                    protected void updateItem(StudentData item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.studentId + " | " + item.name);
                        }
                    }
                };
            }
        };
    }
}
