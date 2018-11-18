package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.SemesterSectionData;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import static com.nagpal.shivam.dbms.Main.sStage;

public class InsertOrEditSemesterSectionData extends UiScene {

    private final boolean isEditMode;
    private TextField mSemesterSectionIdTextField;
    private TextField mSemesterTextField;
    private TextField mSectionTextField;
    private SemesterSectionData mSemesterSectionData;
    private String mTitle;


    public InsertOrEditSemesterSectionData() {
        isEditMode = false;
        mTitle = "Insert new semester-section detail";
    }

    public InsertOrEditSemesterSectionData(SemesterSectionData semesterSectionData) {
        mSemesterSectionData = semesterSectionData;
        isEditMode = true;
        mTitle = "Edit semester-section detail";
    }

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.setPrefSize(800, 600);
        if (isEditMode) {
            fillDetails();
        }
        Scene scene = new Scene(pane);
        sStage.setTitle(mTitle);
        scene.getStylesheets().add("css/InsertOrEditScene.css");
        sStage.setScene(scene);
    }

    @Override
    protected Pane getLayout() {
        GridPane formGridPane = new GridPane();
        formGridPane.getStyleClass().add("formGridPane");

        int gridPaneStartingRowIndex = 0;

        Text semesterSectionIdText = new Text("Semester-Section Id*");
        mSemesterSectionIdTextField = new TextField();
        mSemesterSectionIdTextField.setPromptText("Enter Semester-Section Id");
        formGridPane.add(semesterSectionIdText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mSemesterSectionIdTextField, 1, gridPaneStartingRowIndex);
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
            mSemesterSectionData = new SemesterSectionData();
        }
        mSemesterSectionData.semesterSectionId = mSemesterSectionIdTextField.getText();
        mSemesterSectionData.semester = Integer.parseInt(mSemesterTextField.getText());
        //TODO: Check For NumberFormat Exception
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

    private void fillDetails() {
        mSemesterSectionIdTextField.setText(mSemesterSectionData.semesterSectionId);
        mSemesterTextField.setText(Integer.toString(mSemesterSectionData.semester));
        mSectionTextField.setText(mSemesterSectionData.section);
    }

}
