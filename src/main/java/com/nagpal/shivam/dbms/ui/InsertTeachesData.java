package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.ProfessorData;
import com.nagpal.shivam.dbms.model.SemesterSectionData;
import com.nagpal.shivam.dbms.model.SubjectData;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;

import static com.nagpal.shivam.dbms.Main.sStage;


public class InsertTeachesData {
    private InsertTeachesData() {
    }

    public static void setScene() {
        Pane pane = new InsertTeachesData().getLayout();
        pane.setPrefSize(800, 600);
        Scene scene = new Scene(pane);
        sStage.setTitle("Insert new teaches");
        sStage.setScene(scene);
    }

    private Pane getLayout() {
        GridPane formGridPane = new GridPane();
        formGridPane.setPadding(new Insets(10));
        formGridPane.setVgap(10);
        formGridPane.setHgap(10);
        formGridPane.setAlignment(Pos.CENTER);

        int gridPaneStartingRowIndex = 0;

        Text professorIdText = new Text("Professor");

        Callback<ListView<ProfessorData>, ListCell<ProfessorData>> professorComboBoxCallback = new Callback<ListView<ProfessorData>, ListCell<ProfessorData>>() {
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

        ComboBox<ProfessorData> professorDataComboBox = new ComboBox<>();
        professorDataComboBox.setCellFactory(professorComboBoxCallback);
        professorDataComboBox.setButtonCell(professorComboBoxCallback.call(null));
        professorDataComboBox.setPromptText("Choose a Professor");

        Task<List<ProfessorData>> fetchProfessorDetailsTask = new Task<List<ProfessorData>>() {
            @Override
            protected List<ProfessorData> call() {
                return DatabaseHelper.fetchProfessorDetails();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                professorDataComboBox.getItems().addAll(this.getValue());
            }
        };
        Thread professorThread = new Thread(fetchProfessorDetailsTask);
        professorThread.start();
        formGridPane.add(professorIdText, 0, gridPaneStartingRowIndex);
        formGridPane.add(professorDataComboBox, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text semesterSectionIdText = new Text("Semester-Section");

        Callback<ListView<SemesterSectionData>, ListCell<SemesterSectionData>> semesterSectionComboBoxCallback = new Callback<ListView<SemesterSectionData>, ListCell<SemesterSectionData>>() {
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

        ComboBox<SemesterSectionData> semesterSectionDataComboBox = new ComboBox<>();
        semesterSectionDataComboBox.setCellFactory(semesterSectionComboBoxCallback);
        semesterSectionDataComboBox.setButtonCell(semesterSectionComboBoxCallback.call(null));
        semesterSectionDataComboBox.setPromptText("Choose Semester-Section");

        Task<List<SemesterSectionData>> fetchSemesterSectionDetailsTask = new Task<List<SemesterSectionData>>() {
            @Override
            protected List<SemesterSectionData> call() {
                return DatabaseHelper.fetchSemesterSectionDetails();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                semesterSectionDataComboBox.getItems().addAll(this.getValue());
            }
        };
        Thread semesterSectionThread = new Thread(fetchSemesterSectionDetailsTask);
        semesterSectionThread.start();
        formGridPane.add(semesterSectionIdText, 0, gridPaneStartingRowIndex);
        formGridPane.add(semesterSectionDataComboBox, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text subjectIdText = new Text("Subject");

        Callback<ListView<SubjectData>, ListCell<SubjectData>> subjectComboBoxCallback = new Callback<ListView<SubjectData>, ListCell<SubjectData>>() {
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

        ComboBox<SubjectData> subjectDataComboBox = new ComboBox<>();
        subjectDataComboBox.setCellFactory(subjectComboBoxCallback);
        subjectDataComboBox.setButtonCell(subjectComboBoxCallback.call(null));
        subjectDataComboBox.setPromptText("Choose Subject");

        Task<List<SubjectData>> fetchSubjectDetailsTask = new Task<List<SubjectData>>() {
            @Override
            protected List<SubjectData> call() {
                return DatabaseHelper.fetchSubjectDetails();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                subjectDataComboBox.getItems().addAll(this.getValue());
            }
        };
        Thread subjectThread = new Thread(fetchSubjectDetailsTask);
        subjectThread.start();
        formGridPane.add(subjectIdText, 0, gridPaneStartingRowIndex);
        formGridPane.add(subjectDataComboBox, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        GridPane containerGridPane = new GridPane();

        Button backButton = new Button("Back");
        containerGridPane.add(backButton, 0, 0);

        containerGridPane.add(formGridPane, 1, 1);

        Button submitButton = new Button("Submit");
        containerGridPane.add(submitButton, 2, 2);

        return containerGridPane;
    }
}
