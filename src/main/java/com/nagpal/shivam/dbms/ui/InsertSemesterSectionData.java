package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.SemesterSectionData;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import static com.nagpal.shivam.dbms.Main.sStage;

public class InsertSemesterSectionData extends UiScene {

    private final boolean isEditMode;
    private TextField mSemesterSectionIdTextField;
    private TextField mSemesterTextField;
    private TextField mSectionTextField;
    private SemesterSectionData mSemesterSectionData;

    public InsertSemesterSectionData() {
        isEditMode = false;
    }

    public InsertSemesterSectionData(SemesterSectionData semesterSectionData) {
        mSemesterSectionData = semesterSectionData;
        isEditMode = true;
    }

    @Override
    public void setScene() {
        Pane pane = new InsertSemesterSectionData().getLayout();
        pane.setPrefSize(800, 600);
        Scene scene = new Scene(pane);
        sStage.setTitle("Insert new semester-section");
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

        Text semesterSectionIdText = new Text("Semester-Section Id*");
        mSemesterSectionIdTextField = new TextField();
        mSemesterSectionIdTextField.setPromptText("Enter Semester-Section Id");
        formGridPane.add(semesterSectionIdText, 0, gridPaneStartingRowIndex);
        formGridPane.add(semesterSectionIdText, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text semesterText = new Text("Semester");
        mSemesterTextField = new TextField();
        mSemesterTextField.setPromptText("Enter Semester");
        formGridPane.add(semesterText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mSemesterTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text sectionText = new Text("Section");
        mSectionTextField = new TextField();
        mSectionTextField.setPromptText("Enter Section");
        formGridPane.add(sectionText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mSectionTextField, 1, gridPaneStartingRowIndex);
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
            mSemesterSectionData = new SemesterSectionData();
        }
        mSemesterSectionData.semesterSectionId = mSemesterSectionIdTextField.getText();
        mSemesterSectionData.semester = Integer.parseInt(mSemesterTextField.getText());
        mSemesterSectionData.section = mSectionTextField.getText();

        Task<Integer> submitTask = new Task<Integer>() {
            @Override
            protected Integer call() {
                int i;
                if (!isEditMode) {
                    i = DatabaseHelper.insertIntoSemesterSection(mSemesterSectionData);
                } else {
                    i = DatabaseHelper.updateSemesterSection(mSemesterSectionData);
                }
                return i;
            }
        };
        Thread submitThread = new Thread(submitTask);
        submitThread.start();
    }
}
