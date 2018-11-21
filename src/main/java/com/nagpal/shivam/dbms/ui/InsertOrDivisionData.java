package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.DivisionData;
import com.nagpal.shivam.dbms.model.SemesterSectionData;
import com.nagpal.shivam.dbms.model.StudentData;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

import static com.nagpal.shivam.dbms.Main.sStage;

public class InsertOrDivisionData extends UiScene {

    private final boolean isEditMode;
    private ComboBox<StudentData> mStudentDataComboBox;
    private ComboBox<SemesterSectionData> mSemesterSectionDataComboBox;
    private DivisionData mDivisionData;
    private String mTitle;

    public InsertOrDivisionData() {
        isEditMode = false;
        mTitle = "Insert new division detail";
    }

    public InsertOrDivisionData(DivisionData divisionData) {
        mDivisionData = divisionData;
        isEditMode = true;
        mTitle = "Edit division detail";
    }

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.getStyleClass().add("parentPane");
        fetchForeignKeys();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("css/InsertOrEditScene.css");
        sStage.setTitle(mTitle);
        sStage.setScene(scene);
        pane.requestFocus();
    }

    @Override
    protected Pane getLayout() {
        GridPane formGridPane = new GridPane();
        formGridPane.getStyleClass().add("formGridPane");

        int gridPaneStartingRowIndex = 0;

        Label studentIdLabel = new Label("Student");

        Callback<ListView<StudentData>, ListCell<StudentData>> studentComboBoxCallback = Utils.getStudentComboBoxCallback();

        mStudentDataComboBox = new ComboBox<>();
        mStudentDataComboBox.setCellFactory(studentComboBoxCallback);
        mStudentDataComboBox.setButtonCell(studentComboBoxCallback.call(null));
        mStudentDataComboBox.setPromptText("Choose a Student");

        formGridPane.add(studentIdLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mStudentDataComboBox, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Label semesterSectionIdLabel = new Label("Semester-Section");

        Callback<ListView<SemesterSectionData>, ListCell<SemesterSectionData>> semesterSectionComboBoxCallback = Utils.getSemesterSectionComboBoxCallback();

        mSemesterSectionDataComboBox = new ComboBox<>();
        mSemesterSectionDataComboBox.setCellFactory(semesterSectionComboBoxCallback);
        mSemesterSectionDataComboBox.setButtonCell(semesterSectionComboBoxCallback.call(null));
        mSemesterSectionDataComboBox.setPromptText("Choose a Semester-Section");

        formGridPane.add(semesterSectionIdLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mSemesterSectionDataComboBox, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        BorderPane borderPane = new BorderPane();

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> super.onBackPressed());

        ToolBar toolBar = new ToolBar(backButton);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> submitData());

        FlowPane submitButtonFlowPane = new FlowPane(submitButton);
        submitButtonFlowPane.getStyleClass().add("submitButtonFlowPane");

        VBox vBox = new VBox(formGridPane, submitButtonFlowPane);
        vBox.setSpacing(30);

        FlowPane vBoxFlowPane = new FlowPane(vBox);
        vBoxFlowPane.getStyleClass().add("vBoxFlowPane");

        borderPane.setTop(toolBar);
        borderPane.setCenter(vBoxFlowPane);

        return borderPane;
    }

    private void submitData() {
        if (!isEditMode) {
            mDivisionData = new DivisionData();
        }
        mDivisionData.studentId = mStudentDataComboBox.getValue().studentId;
        mDivisionData.semesterSectionId = mSemesterSectionDataComboBox.getValue().semesterSectionId;

        Task<Integer> submitTask = new Task<Integer>() {
            @Override
            protected Integer call() {
                int i;
                if (!isEditMode) {
                    i = DatabaseHelper.insertIntoDivision(mDivisionData);
                } else {
                    i = DatabaseHelper.updateDivision(mDivisionData);
                }
                return i;
            }
        };
        Thread submitThread = new Thread(submitTask);
        submitThread.start();
    }

    private void fetchForeignKeys() {
        Task<List<StudentData>> fetchStudentDetailsTask = new Task<List<StudentData>>() {
            @Override
            protected List<StudentData> call() {
                List<StudentData> studentData = null;
                if (isEditMode) {
                    studentData = new ArrayList<>();
                    studentData.addAll(DatabaseHelper.fetchParticularStudent(mDivisionData.studentId, true));
                    studentData.addAll(DatabaseHelper.fetchParticularStudent(mDivisionData.studentId, false));
                } else {
                    studentData = DatabaseHelper.fetchStudentDetails();
                }
                return studentData;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                mStudentDataComboBox.getItems().addAll(this.getValue());
                if (isEditMode) {
                    mStudentDataComboBox.getSelectionModel().select(0);
                }
            }
        };
        Thread studentThread = new Thread(fetchStudentDetailsTask);
        studentThread.start();

        Task<List<SemesterSectionData>> fetchSemesterSectionDetailsTask = new Task<List<SemesterSectionData>>() {
            @Override
            protected List<SemesterSectionData> call() {
                List<SemesterSectionData> semesterSectionData = null;
                if (isEditMode) {
                    semesterSectionData = new ArrayList<>();
                    semesterSectionData.addAll(DatabaseHelper.fetchParticularSemesterSection(mDivisionData.semesterSectionId, true));
                    semesterSectionData.addAll(DatabaseHelper.fetchParticularSemesterSection(mDivisionData.semesterSectionId, false));
                } else {
                    semesterSectionData = DatabaseHelper.fetchSemesterSectionDetails();
                }
                return semesterSectionData;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                mSemesterSectionDataComboBox.getItems().addAll(this.getValue());
                if (isEditMode) {
                    mSemesterSectionDataComboBox.getSelectionModel().select(0);
                }
            }
        };
        Thread semesterSectionThread = new Thread(fetchSemesterSectionDetailsTask);
        semesterSectionThread.start();
    }

}
