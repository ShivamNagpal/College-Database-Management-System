package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.IaMarksData;
import com.nagpal.shivam.dbms.model.SemesterSectionData;
import com.nagpal.shivam.dbms.model.StudentData;
import com.nagpal.shivam.dbms.model.SubjectData;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

import static com.nagpal.shivam.dbms.Main.sStage;

public class InsertOrEditIaMarksData extends UiScene {

    private final boolean isEditMode;
    private ComboBox<StudentData> mStudentDataComboBox;
    private ComboBox<SemesterSectionData> mSemesterSectionDataComboBox;
    private ComboBox<SubjectData> mSubjectDataComboBox;
    private TextField mTest1TextField;
    private TextField mTest2TextField;
    private TextField mTest3TextField;
    private IaMarksData mIaMarksData;
    private String mTitle;

    public InsertOrEditIaMarksData() {
        isEditMode = false;
        mTitle = "Insert new IA Marks detail";
    }

    public InsertOrEditIaMarksData(IaMarksData iaMarksData) {
        mIaMarksData = iaMarksData;
        isEditMode = true;
        mTitle = "Edit Ia Marks detail";
    }

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.getStyleClass().add("parentPane");
        fetchForeignKeys();
        if (isEditMode) {
            fillDetails();
        }
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

        Label subjectIdLabel = new Label("Subject");

        Callback<ListView<SubjectData>, ListCell<SubjectData>> subjectComboBoxCallback = Utils.getSubjectComboBoxCallback();

        mSubjectDataComboBox = new ComboBox<>();
        mSubjectDataComboBox.setCellFactory(subjectComboBoxCallback);
        mSubjectDataComboBox.setButtonCell(subjectComboBoxCallback.call(null));
        mSubjectDataComboBox.setPromptText("Choose Subject");

        formGridPane.add(subjectIdLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mSubjectDataComboBox, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Label test1Label = new Label("Test1");
        mTest1TextField = new TextField();
        mTest1TextField.setPromptText("Enter Test1 Marks");
        formGridPane.add(test1Label, 0, gridPaneStartingRowIndex);
        formGridPane.add(mTest1TextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Label test2Label = new Label("Test2");
        mTest2TextField = new TextField();
        mTest2TextField.setPromptText("Enter Test2 Marks");
        formGridPane.add(test2Label, 0, gridPaneStartingRowIndex);
        formGridPane.add(mTest2TextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Label test3Label = new Label("Test3");
        mTest3TextField = new TextField();
        mTest3TextField.setPromptText("Enter Test3 Scores");
        formGridPane.add(test3Label, 0, gridPaneStartingRowIndex);
        formGridPane.add(mTest3TextField, 1, gridPaneStartingRowIndex);
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
            mIaMarksData = new IaMarksData();
        }
        StudentData studentData = mStudentDataComboBox.getValue();
        SemesterSectionData semesterSectionData = mSemesterSectionDataComboBox.getValue();
        SubjectData subjectData = mSubjectDataComboBox.getValue();

        if (studentData == null || semesterSectionData == null || subjectData == null) {
            Utils.showErrorAlert("Values can't be empty");
            return;
        }
        mIaMarksData.studentId = studentData.studentId;
        mIaMarksData.semSecId = semesterSectionData.semesterSectionId;
        mIaMarksData.subjectId = subjectData.subjectId;
        try {
            String tm1 = mTest1TextField.getText().trim();
            if (!tm1.isEmpty()) {
                mIaMarksData.test1 = Integer.parseInt(tm1);
            }
            String tm2 = mTest2TextField.getText().trim();
            if (!tm2.isEmpty()) {
                mIaMarksData.test2 = Integer.parseInt(tm2);
            }
            String tm3 = mTest3TextField.getText().trim();
            if (!tm3.isEmpty()) {
                mIaMarksData.test3 = Integer.parseInt(tm3);
            }
        } catch (NumberFormatException e) {
            Utils.showErrorAlert("Enter Integral Value for Marks");
            return;
        }

        Task<Integer> submitTask = new Task<Integer>() {
            @Override
            protected Integer call() {
                int i;
                if (!isEditMode) {
                    i = DatabaseHelper.insertIntoIaMarks(mIaMarksData);
                } else {
                    i = DatabaseHelper.updateIaMarks(mIaMarksData);
                }
                return i;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                Utils.onInsertOrUpdateResponse(InsertOrEditIaMarksData.this, this.getValue());
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
                    studentData.addAll(DatabaseHelper.fetchParticularStudent(mIaMarksData.studentId, true));
                    studentData.addAll(DatabaseHelper.fetchParticularStudent(mIaMarksData.studentId, false));
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
                    semesterSectionData.addAll(DatabaseHelper.fetchParticularSemesterSection(mIaMarksData.semSecId, true));
                    semesterSectionData.addAll(DatabaseHelper.fetchParticularSemesterSection(mIaMarksData.semSecId, false));
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

        Task<List<SubjectData>> fetchSubjectDetailsTask = new Task<List<SubjectData>>() {
            @Override
            protected List<SubjectData> call() {
                List<SubjectData> subjectData = null;
                if (isEditMode) {
                    subjectData = new ArrayList<>();
                    subjectData.addAll(DatabaseHelper.fetchParticularSubject(mIaMarksData.subjectId, true));
                    subjectData.addAll(DatabaseHelper.fetchParticularSubject(mIaMarksData.subjectId, false));
                } else {
                    subjectData = DatabaseHelper.fetchSubjectDetails();
                }
                return subjectData;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                mSubjectDataComboBox.getItems().addAll(this.getValue());
                if (isEditMode) {
                    mSubjectDataComboBox.getSelectionModel().select(0);
                }
            }
        };
        Thread subjectThread = new Thread(fetchSubjectDetailsTask);
        subjectThread.start();
    }

    private void fillDetails() {
        mTest1TextField.setText(Integer.toString(mIaMarksData.test1));
        mTest2TextField.setText(Integer.toString(mIaMarksData.test2));
        mTest3TextField.setText(Integer.toString(mIaMarksData.test3));
    }

}
