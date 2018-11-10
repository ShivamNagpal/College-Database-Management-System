package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.DepartmentData;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.List;

import static com.nagpal.shivam.dbms.Main.sStage;
import static com.nagpal.shivam.dbms.ui.Utils.departmentChoiceBoxCallback;

public class InsertSubjectData {
    private InsertSubjectData() {
    }

    //Todo: Addition: Implement CBCS Scheme for the subject
    public static void setScene() {
        Pane pane = new InsertSubjectData().getLayout();
        pane.setPrefSize(800, 600);
        Scene scene = new Scene(pane);
        sStage.setTitle("Insert new subject");
        sStage.setScene(scene);
    }

    private Pane getLayout() {
        GridPane formGridPane = new GridPane();
        formGridPane.setPadding(new Insets(10));
        formGridPane.setVgap(10);
        formGridPane.setHgap(10);
        formGridPane.setAlignment(Pos.CENTER);

        int gridPaneStartingRowIndex = 0;

        Text nameText = new Text("Name*");
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Enter Name");
        formGridPane.add(nameText, 0, gridPaneStartingRowIndex);
        formGridPane.add(nameTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text idText = new Text("Id*");
        TextField idTextField = new TextField();
        idTextField.setPromptText("Enter Id");
        formGridPane.add(idText, 0, gridPaneStartingRowIndex);
        formGridPane.add(idTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text departmentIdText = new Text("Department*");
        ComboBox<DepartmentData> departmentDataComboBox = new ComboBox<>();
        departmentDataComboBox.setCellFactory(departmentChoiceBoxCallback);
        departmentDataComboBox.setButtonCell(departmentChoiceBoxCallback.call(null));

        departmentDataComboBox.setPromptText("Choose a department");
        Task<List<DepartmentData>> fetchDepartmentDetailsTask = new Task<List<DepartmentData>>() {
            @Override
            protected List<DepartmentData> call() {
                return DatabaseHelper.fetchDepartmentDetails();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                departmentDataComboBox.getItems().addAll(this.getValue());
            }
        };
        Thread thread = new Thread(fetchDepartmentDetailsTask);
        thread.start();
        Text linkToAddNewDepartment = new Text("Not Found!, Add new Department First");
        formGridPane.add(departmentIdText, 0, gridPaneStartingRowIndex);
        formGridPane.add(departmentDataComboBox, 1, gridPaneStartingRowIndex);
        formGridPane.add(linkToAddNewDepartment, 2, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        GridPane containerGridPane = new GridPane();

        Button backButton = new Button("Back");
        containerGridPane.add(backButton, 0, 0);

        containerGridPane.add(formGridPane, 1, 1);

        Button submitButton = new Button("Submit");
        containerGridPane.add(submitButton, 2, 2);

        return containerGridPane;
    }
}
