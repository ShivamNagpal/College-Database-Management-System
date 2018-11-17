package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.IaMarksData;
import com.nagpal.shivam.dbms.model.SemesterSectionData;
import com.nagpal.shivam.dbms.model.StudentData;
import com.nagpal.shivam.dbms.model.SubjectData;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

import static com.nagpal.shivam.dbms.Main.sStage;

public class InsertIaMarksData extends UiScene {

    private final boolean isEditMode;
    private ComboBox<StudentData> mStudentDataComboBox;
    private ComboBox<SemesterSectionData> mSemesterSectionDataComboBox;
    private ComboBox<SubjectData> mSubjectDataComboBox;
    private TextField mTest1TextField;
    private TextField mTest2TextField;
    private TextField mTest3TextField;
    private IaMarksData mIaMarksData;
    private String mTitle;

    public InsertIaMarksData() {
        isEditMode = false;
        mTitle = "Insert new IA Marks detail";
    }

    public InsertIaMarksData(IaMarksData iaMarksData) {
        mIaMarksData = iaMarksData;
        isEditMode = true;
        mTitle = "Edit Ia Marks detail";
    }

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.setPrefSize(800, 600);
        fetchForeignKeys();
        if (isEditMode) {
            fillDetails();
        }
        Scene scene = new Scene(pane);
        sStage.setTitle(mTitle);
        sStage.setScene(scene);
    }

    @Override
    protected Pane getLayout() {
        GridPane formGridPane = new GridPane();
        formGridPane.setPadding(new Insets(10));
        formGridPane.setVgap(10);
        formGridPane.setHgap(10);
        formGridPane.setAlignment(Pos.CENTER);

        int gridPaneStartingRowIndex = 0;

        Text studentIdText = new Text("Student");

        Callback<ListView<StudentData>, ListCell<StudentData>> studentComboBoxCallback = Utils.getStudentComboBoxCallback();

        mStudentDataComboBox = new ComboBox<>();
        mStudentDataComboBox.setCellFactory(studentComboBoxCallback);
        mStudentDataComboBox.setButtonCell(studentComboBoxCallback.call(null));
        mStudentDataComboBox.setPromptText("Choose a Student");

        formGridPane.add(studentIdText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mStudentDataComboBox, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text semesterSectionIdText = new Text("Semester-Section");

        Callback<ListView<SemesterSectionData>, ListCell<SemesterSectionData>> semesterSectionComboBoxCallback = Utils.getSemesterSectionComboBoxCallback();

        mSemesterSectionDataComboBox = new ComboBox<>();
        mSemesterSectionDataComboBox.setCellFactory(semesterSectionComboBoxCallback);
        mSemesterSectionDataComboBox.setButtonCell(semesterSectionComboBoxCallback.call(null));
        mSemesterSectionDataComboBox.setPromptText("Choose a Semester-Section");

        formGridPane.add(semesterSectionIdText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mSemesterSectionDataComboBox, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text subjectIdText = new Text("Subject");

        Callback<ListView<SubjectData>, ListCell<SubjectData>> subjectComboBoxCallback = Utils.getSubjectComboBoxCallback();

        mSubjectDataComboBox = new ComboBox<>();
        mSubjectDataComboBox.setCellFactory(subjectComboBoxCallback);
        mSubjectDataComboBox.setButtonCell(subjectComboBoxCallback.call(null));
        mSubjectDataComboBox.setPromptText("Choose Subject");

        formGridPane.add(subjectIdText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mSubjectDataComboBox, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text test1Text = new Text("Test1");
        mTest1TextField = new TextField();
        mTest1TextField.setPromptText("Enter Test1 Marks");
        formGridPane.add(test1Text, 0, gridPaneStartingRowIndex);
        formGridPane.add(mTest1TextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text test2Text = new Text("Test2");
        mTest2TextField = new TextField();
        mTest2TextField.setPromptText("Enter Test2 Marks");
        formGridPane.add(test2Text, 0, gridPaneStartingRowIndex);
        formGridPane.add(mTest2TextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text test3Text = new Text("Test3");
        mTest3TextField = new TextField();
        mTest3TextField.setPromptText("Enter Test3 Scores");
        formGridPane.add(test3Text, 0, gridPaneStartingRowIndex);
        formGridPane.add(mTest3TextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        GridPane containerGridPane = new GridPane();

        Button backButton = new Button("Back");
        containerGridPane.add(backButton, 0, 0);
        backButton.setOnAction(event -> super.onBackPressed());

        containerGridPane.add(formGridPane, 1, 1);

        Button submitButton = new Button("Submit");
        containerGridPane.add(submitButton, 2, 2);
        submitButton.setOnAction(event -> submitData());

        return containerGridPane;
    }

    private void submitData() {
        if (!isEditMode) {
            mIaMarksData = new IaMarksData();
        }
        mIaMarksData.studentId = mStudentDataComboBox.getValue().studentId;
        mIaMarksData.semSecId = mSemesterSectionDataComboBox.getValue().semesterSectionId;
        mIaMarksData.subjectId = mSubjectDataComboBox.getValue().subjectId;
        mIaMarksData.test1 = Integer.parseInt(mTest1TextField.getText().trim());
        mIaMarksData.test2 = Integer.parseInt(mTest2TextField.getText().trim());
        mIaMarksData.test3 = Integer.parseInt(mTest3TextField.getText().trim());
        // TODO: Check for Number Format Exception

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
