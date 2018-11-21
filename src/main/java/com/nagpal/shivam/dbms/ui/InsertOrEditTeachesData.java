package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.ProfessorData;
import com.nagpal.shivam.dbms.model.SemesterSectionData;
import com.nagpal.shivam.dbms.model.SubjectData;
import com.nagpal.shivam.dbms.model.TeachesData;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

import static com.nagpal.shivam.dbms.Main.sStage;


public class InsertOrEditTeachesData extends UiScene {

    private final boolean isEditMode;
    private ComboBox<ProfessorData> mProfessorDataComboBox;
    private ComboBox<SemesterSectionData> mSemesterSectionDataComboBox;
    private ComboBox<SubjectData> mSubjectDataComboBox;
    private TeachesData mTeachesData;
    private String mTitle;

    public InsertOrEditTeachesData() {
        isEditMode = false;
        mTitle = "Insert new teaches detail";
    }

    public InsertOrEditTeachesData(TeachesData teachesData) {
        mTeachesData = teachesData;
        isEditMode = true;
        mTitle = "Edit teaches detail";
    }

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.getStyleClass().add("parentPane");
        fetchForeignKeys();
        Scene scene = new Scene(pane);
        sStage.setTitle(mTitle);
        scene.getStylesheets().add("css/InsertOrEditScene.css");
        sStage.setScene(scene);
        pane.requestFocus();
    }

    @Override
    protected Pane getLayout() {
        GridPane formGridPane = new GridPane();
        formGridPane.getStyleClass().add("formGridPane");

        int gridPaneStartingRowIndex = 0;

        Label professorIdLabel = new Label("Professor");

        Callback<ListView<ProfessorData>, ListCell<ProfessorData>> professorComboBoxCallback = Utils.getProfessorComboBoxCallback();

        mProfessorDataComboBox = new ComboBox<>();
        mProfessorDataComboBox.setCellFactory(professorComboBoxCallback);
        mProfessorDataComboBox.setButtonCell(professorComboBoxCallback.call(null));
        mProfessorDataComboBox.setPromptText("Choose a Professor");

        formGridPane.add(professorIdLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mProfessorDataComboBox, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Label semesterSectionIdLabel = new Label("Semester-Section");

        Callback<ListView<SemesterSectionData>, ListCell<SemesterSectionData>> semesterSectionComboBoxCallback = Utils.getSemesterSectionComboBoxCallback();

        mSemesterSectionDataComboBox = new ComboBox<>();
        mSemesterSectionDataComboBox.setCellFactory(semesterSectionComboBoxCallback);
        mSemesterSectionDataComboBox.setButtonCell(semesterSectionComboBoxCallback.call(null));
        mSemesterSectionDataComboBox.setPromptText("Choose Semester-Section");

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
