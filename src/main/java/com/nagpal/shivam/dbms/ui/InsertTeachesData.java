package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.ProfessorData;
import com.nagpal.shivam.dbms.model.SemesterSectionData;
import com.nagpal.shivam.dbms.model.SubjectData;
import com.nagpal.shivam.dbms.model.TeachesData;
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

import java.util.ArrayList;
import java.util.List;

import static com.nagpal.shivam.dbms.Main.sStage;


public class InsertTeachesData extends UiScene {

    private final boolean isEditMode;
    private ComboBox<ProfessorData> mProfessorDataComboBox;
    private ComboBox<SemesterSectionData> mSemesterSectionDataComboBox;
    private ComboBox<SubjectData> mSubjectDataComboBox;
    private TeachesData mTeachesData;
    private String mTitle;

    public InsertTeachesData() {
        isEditMode = false;
        mTitle = "Insert new teaches detail";
    }

    public InsertTeachesData(TeachesData teachesData) {
        mTeachesData = teachesData;
        isEditMode = true;
        mTitle = "Edit teaches detail";
    }

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.setPrefSize(800, 600);
        sStage.setMinHeight(600);
        fetchForeignKeys();
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

        Text professorIdText = new Text("Professor");

        Callback<ListView<ProfessorData>, ListCell<ProfessorData>> professorComboBoxCallback = Utils.getProfessorComboBoxCallback();

        mProfessorDataComboBox = new ComboBox<>();
        mProfessorDataComboBox.setCellFactory(professorComboBoxCallback);
        mProfessorDataComboBox.setButtonCell(professorComboBoxCallback.call(null));
        mProfessorDataComboBox.setPromptText("Choose a Professor");

        formGridPane.add(professorIdText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mProfessorDataComboBox, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text semesterSectionIdText = new Text("Semester-Section");

        Callback<ListView<SemesterSectionData>, ListCell<SemesterSectionData>> semesterSectionComboBoxCallback = Utils.getSemesterSectionComboBoxCallback();

        mSemesterSectionDataComboBox = new ComboBox<>();
        mSemesterSectionDataComboBox.setCellFactory(semesterSectionComboBoxCallback);
        mSemesterSectionDataComboBox.setButtonCell(semesterSectionComboBoxCallback.call(null));
        mSemesterSectionDataComboBox.setPromptText("Choose Semester-Section");

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
            mTeachesData = new TeachesData();
        }
        mTeachesData.professorId = mProfessorDataComboBox.getValue().professorId;
        mTeachesData.semesterSectionId = mSemesterSectionDataComboBox.getValue().semesterSectionId;
        mTeachesData.subjectId = mSubjectDataComboBox.getValue().subjectId;

        Task<Integer> submitTask = new Task<Integer>() {
            @Override
            protected Integer call() {
                int i;
                if (!isEditMode) {
                    i = DatabaseHelper.insertIntoTeaches(mTeachesData);
                } else {
                    i = DatabaseHelper.updateTeaches(mTeachesData);
                }
                return i;
            }
        };
        Thread submitThread = new Thread(submitTask);
        submitThread.start();
    }

    private void fetchForeignKeys() {
        Task<List<ProfessorData>> fetchProfessorDetailsTask = new Task<List<ProfessorData>>() {
            @Override
            protected List<ProfessorData> call() {
                List<ProfessorData> professorData = null;
                if (isEditMode) {
                    professorData = new ArrayList<>();
                    professorData.addAll(DatabaseHelper.fetchParticularProfessor(mTeachesData.professorId, true));
                    professorData.addAll(DatabaseHelper.fetchParticularProfessor(mTeachesData.professorId, false));
                } else {
                    professorData = DatabaseHelper.fetchProfessorDetails();
                }
                return professorData;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                mProfessorDataComboBox.getItems().addAll(this.getValue());
                if (isEditMode) {
                    mProfessorDataComboBox.getSelectionModel().select(0);
                }
            }
        };
        Thread professorThread = new Thread(fetchProfessorDetailsTask);
        professorThread.start();

        Task<List<SemesterSectionData>> fetchSemesterSectionDetailsTask = new Task<List<SemesterSectionData>>() {
            @Override
            protected List<SemesterSectionData> call() {
                List<SemesterSectionData> semesterSectionData = null;
                if (isEditMode) {
                    semesterSectionData = new ArrayList<>();
                    semesterSectionData.addAll(DatabaseHelper.fetchParticularSemesterSection(mTeachesData.semesterSectionId, true));
                    semesterSectionData.addAll(DatabaseHelper.fetchParticularSemesterSection(mTeachesData.semesterSectionId, false));
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
                    subjectData.addAll(DatabaseHelper.fetchParticularSubject(mTeachesData.subjectId, true));
                    subjectData.addAll(DatabaseHelper.fetchParticularSubject(mTeachesData.subjectId, false));
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

}
