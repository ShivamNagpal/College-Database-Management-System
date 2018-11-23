package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.DepartmentData;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;

import static com.nagpal.shivam.dbms.Main.sStage;

public class InsertOrEditDepartmentData extends UiScene {

    private final boolean isEditMode;
    private TextField mNameTextField;
    private TextField mIdTextField;
    private DepartmentData mDepartmentData;
    private String mTitle;

    public InsertOrEditDepartmentData() {
        isEditMode = false;
        mTitle = "Insert new department detail";
    }

    public InsertOrEditDepartmentData(DepartmentData departmentData) {
        mDepartmentData = departmentData;
        isEditMode = true;
        mTitle = "Edit department detail";
    }

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.getStyleClass().add("parentPane");
        if (isEditMode) {
            fillDetails();
        }
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

        Label nameLabel = new Label("Name*");
        mNameTextField = new TextField();
        mNameTextField.setPromptText("Enter Name");
        formGridPane.add(nameLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mNameTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Label idLabel = new Label("Id*");
        mIdTextField = new TextField();
        mIdTextField.setPromptText("Enter Id");
        formGridPane.add(idLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mIdTextField, 1, gridPaneStartingRowIndex);
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
            mDepartmentData = new DepartmentData();
        }
        mDepartmentData.name = mNameTextField.getText();
        mDepartmentData.departmentId = mIdTextField.getText();

        Task<Integer> submitTask = new Task<Integer>() {
            @Override
            protected Integer call() {
                int i;
                if (!isEditMode) {
                    i = DatabaseHelper.insertIntoDepartment(mDepartmentData);
                } else {
                    i = DatabaseHelper.updateDepartment(mDepartmentData);
                }
                return i;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                Utils.onInsertOrUpdateResponse(InsertOrEditDepartmentData.this, this.getValue());
            }
        };
        Thread submitThread = new Thread(submitTask);
        submitThread.start();
    }

    private void fillDetails() {
        mNameTextField.setText(mDepartmentData.name);
        mIdTextField.setText(mDepartmentData.departmentId);
    }

}
