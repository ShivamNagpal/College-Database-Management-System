package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.DepartmentData;
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

public class InsertDepartmentData extends UiScene {

    private final boolean isEditMode;
    private TextField mNameTextField;
    private TextField mIdTextField;
    private DepartmentData mDepartmentData;

    public InsertDepartmentData() {
        isEditMode = false;
    }

    public InsertDepartmentData(DepartmentData departmentData) {
        mDepartmentData = departmentData;
        isEditMode = true;
    }

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.setPrefSize(800, 600);
        Scene scene = new Scene(pane);
        sStage.setTitle("Insert new department");
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

        Text nameText = new Text("Name*");
        mNameTextField = new TextField();
        mNameTextField.setPromptText("Enter Name");
        formGridPane.add(nameText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mNameTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text idText = new Text("Id*");
        mIdTextField = new TextField();
        mIdTextField.setPromptText("Enter Id");
        formGridPane.add(idText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mIdTextField, 1, gridPaneStartingRowIndex);
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
        };
        Thread submitThread = new Thread(submitTask);
        submitThread.start();
    }
}
