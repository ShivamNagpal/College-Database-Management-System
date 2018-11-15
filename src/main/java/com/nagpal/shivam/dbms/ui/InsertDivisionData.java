package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.DivisionData;
import com.nagpal.shivam.dbms.model.SemesterSectionData;
import com.nagpal.shivam.dbms.model.StudentData;
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

public class InsertDivisionData extends UiScene {

    private final boolean isEditMode;
    private ComboBox<StudentData> mStudentDataComboBox;
    private ComboBox<SemesterSectionData> mSemesterSectionDataComboBox;
    private DivisionData mDivisionData;

    public InsertDivisionData() {
        isEditMode = false;
    }

    public InsertDivisionData(DivisionData divisionData) {
        mDivisionData = divisionData;
        isEditMode = true;
    }

    @Override
    public void setScene() {
        Pane pane = new InsertDivisionData().getLayout();
        pane.setPrefSize(800, 600);
        Scene scene = new Scene(pane);
        sStage.setTitle("Insert new division entry");
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

}
