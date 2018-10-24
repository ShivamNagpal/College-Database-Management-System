package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
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
        ComboBox<String> departmentChoiceBox = new ComboBox<>();
        departmentChoiceBox.setPromptText("Choose a department");
        Task<List<String>> fetchDepartmentNamesTask = new Task<List<String>>() {
            @Override
            protected List<String> call() throws Exception {
                return DatabaseHelper.fetchDepartmentNames();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                departmentChoiceBox.getItems().addAll(this.getValue());
            }
        };
        Thread thread = new Thread(fetchDepartmentNamesTask);
        thread.start();
        Text linkToAddNewDepartment = new Text("Not Found!, Add new Department First");
        formGridPane.add(departmentIdText, 0, gridPaneStartingRowIndex);
        formGridPane.add(departmentChoiceBox, 1, gridPaneStartingRowIndex);
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
