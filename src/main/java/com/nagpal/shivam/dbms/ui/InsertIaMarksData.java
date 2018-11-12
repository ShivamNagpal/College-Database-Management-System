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

import java.util.List;

import static com.nagpal.shivam.dbms.Main.sStage;

public class InsertIaMarksData {

    private ComboBox<StudentData> mStudentDataComboBox;
    private ComboBox<SemesterSectionData> mSemesterSectionDataComboBox;
    private ComboBox<SubjectData> mSubjectDataComboBox;
    private TextField mTest1TextField;
    private TextField mTest2TextField;
    private TextField mTest3TextField;

    private InsertIaMarksData() {
    }

    public static void setScene() {
        Pane pane = new InsertIaMarksData().getLayout();
        pane.setPrefSize(800, 600);
        Scene scene = new Scene(pane);
        sStage.setTitle("Insert new student");
        sStage.setScene(scene);
    }

    private Pane getLayout() {
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

        Task<List<StudentData>> fetchStudentDetailsTask = new Task<List<StudentData>>() {
            @Override
            protected List<StudentData> call() {
                return DatabaseHelper.fetchStudentDetails();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                mStudentDataComboBox.getItems().addAll(this.getValue());
            }
        };
        Thread studentThread = new Thread(fetchStudentDetailsTask);
        studentThread.start();
        formGridPane.add(studentIdText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mStudentDataComboBox, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text semesterSectionIdText = new Text("Semester-Section");

        Callback<ListView<SemesterSectionData>, ListCell<SemesterSectionData>> semesterSectionComboBoxCallback = Utils.getSemesterSectionComboBoxCallback();

        mSemesterSectionDataComboBox = new ComboBox<>();
        mSemesterSectionDataComboBox.setCellFactory(semesterSectionComboBoxCallback);
        mSemesterSectionDataComboBox.setButtonCell(semesterSectionComboBoxCallback.call(null));
        mSemesterSectionDataComboBox.setPromptText("Choose a Semester-Section");

        Task<List<SemesterSectionData>> fetchSemesterSectionDetailsTask = new Task<List<SemesterSectionData>>() {
            @Override
            protected List<SemesterSectionData> call() {
                return DatabaseHelper.fetchSemesterSectionDetails();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                mSemesterSectionDataComboBox.getItems().addAll(this.getValue());
            }
        };
        Thread semesterSectionThread = new Thread(fetchSemesterSectionDetailsTask);
        semesterSectionThread.start();
        formGridPane.add(semesterSectionIdText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mSemesterSectionDataComboBox, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text subjectIdText = new Text("Subject");

        Callback<ListView<SubjectData>, ListCell<SubjectData>> subjectComboBoxCallback = Utils.getSubjectComboBoxCallback();

        mSubjectDataComboBox = new ComboBox<>();
        mSubjectDataComboBox.setCellFactory(subjectComboBoxCallback);
        mSubjectDataComboBox.setButtonCell(subjectComboBoxCallback.call(null));
        mSubjectDataComboBox.setPromptText("Choose Subject");

        Task<List<SubjectData>> fetchSubjectDetailsTask = new Task<List<SubjectData>>() {
            @Override
            protected List<SubjectData> call() {
                return DatabaseHelper.fetchSubjectDetails();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                mSubjectDataComboBox.getItems().addAll(this.getValue());
            }
        };
        Thread subjectThread = new Thread(fetchSubjectDetailsTask);
        subjectThread.start();
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

        containerGridPane.add(formGridPane, 1, 1);

        Button submitButton = new Button("Submit");
        containerGridPane.add(submitButton, 2, 2);
        submitButton.setOnAction(event -> submitData());

        return containerGridPane;
    }

    private void submitData() {
        IaMarksData iaMarksData = new IaMarksData();
        iaMarksData.studentId = mStudentDataComboBox.getValue().studentId;
        iaMarksData.semSecId = mSemesterSectionDataComboBox.getValue().semesterSectionId;
        iaMarksData.subjectId = mSubjectDataComboBox.getValue().subjectId;
        iaMarksData.test1 = Integer.parseInt(mTest1TextField.getText().trim());
        iaMarksData.test2 = Integer.parseInt(mTest2TextField.getText().trim());
        iaMarksData.test3 = Integer.parseInt(mTest3TextField.getText().trim());
    }
}
