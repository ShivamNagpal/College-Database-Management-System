package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.SqlErrorCodes;
import com.nagpal.shivam.dbms.model.*;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

class Utils {

    static Callback<ListView<DepartmentData>, ListCell<DepartmentData>> getDepartmentComboBoxCallback() {
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

    static Callback<ListView<SemesterSectionData>, ListCell<SemesterSectionData>> getSemesterSectionComboBoxCallback() {
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

    static Callback<ListView<ProfessorData>, ListCell<ProfessorData>> getProfessorComboBoxCallback() {
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

    static Callback<ListView<StudentData>, ListCell<StudentData>> getStudentComboBoxCallback() {
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

    static Callback<ListView<SubjectData>, ListCell<SubjectData>> getSubjectComboBoxCallback() {
        return new Callback<ListView<SubjectData>, ListCell<SubjectData>>() {
            @Override
            public ListCell<SubjectData> call(ListView<SubjectData> param) {
                return new ListCell<SubjectData>() {
                    @Override
                    protected void updateItem(SubjectData item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.subjectId + " | " + item.name);
                        }
                    }
                };
            }
        };
    }

    static EventHandler<MouseEvent> getPreviewEventHandler(TableView mTableView) {
        return event -> {
            Node source = event.getPickResult().getIntersectedNode();

            while (source != null && !(source instanceof TableRow)) {
                source = source.getParent();
            }

            if (source != null && ((TableRow) source).isEmpty()) {
                mTableView.getSelectionModel().clearSelection();

                while (source.getParent() != null) {
                    source = source.getParent();
                }
                source.requestFocus();
            }
        };
    }

    static void onInsertOrUpdateResponse(UiScene uiScene, int response) {
        if (response == SqlErrorCodes.SQLITE_OK) {
            uiScene.onBackPressed();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Some Error Occurred", ButtonType.OK);
            alert.showAndWait();
        }
    }

    static void showErrorAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, content, ButtonType.OK);
        alert.showAndWait();
    }
}
