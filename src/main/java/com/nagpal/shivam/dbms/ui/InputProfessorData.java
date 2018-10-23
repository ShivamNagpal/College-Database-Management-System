package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.Main;
import com.nagpal.shivam.dbms.data.DatabaseHelper;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.List;

public class InputProfessorData {

    private InputProfessorData() {
    }

    public static void setScene() {
        Pane pane = new InputProfessorData().getLayout();
        pane.setMinSize(800, 600);
        Scene scene = new Scene(pane);
        Main.sStage.setScene(scene);
    }

    private Pane getLayout() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        int gridPaneStartingRowIndex = 0;

        Text nameText = new Text("Name*");
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Enter Name");
        gridPane.add(nameText, 0, gridPaneStartingRowIndex);
        gridPane.add(nameTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text idText = new Text("Id*");
        TextField idTextField = new TextField();
        idTextField.setPromptText("Enter Id");
        gridPane.add(idText, 0, gridPaneStartingRowIndex);
        gridPane.add(idTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text dobText = new Text("Date of Birth");
        DatePicker datePicker = new DatePicker();
        gridPane.add(dobText, 0, gridPaneStartingRowIndex);
        gridPane.add(datePicker, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text addressText = new Text("Address");
        TextField addressTextField = new TextField();
        idTextField.setPromptText("Enter Address");
        gridPane.add(addressText, 0, gridPaneStartingRowIndex);
        gridPane.add(addressTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text phoneText = new Text("Phone");
        TextField phoneTextField = new TextField();
        idTextField.setPromptText("Enter Phone Number");
        gridPane.add(phoneText, 0, gridPaneStartingRowIndex);
        gridPane.add(phoneTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text emailText = new Text("Email");
        TextField emailTextField = new TextField();
        idTextField.setPromptText("Enter Email Address");
        gridPane.add(emailText, 0, gridPaneStartingRowIndex);
        gridPane.add(emailTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text designationText = new Text("Designation");
        TextField designationTextField = new TextField();
        idTextField.setPromptText("Enter Designation");
        gridPane.add(designationText, 0, gridPaneStartingRowIndex);
        gridPane.add(designationTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text departmentIdText = new Text("Department");
        ChoiceBox<String> departmentChoiceBox = new ChoiceBox<>();
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

        gridPane.add(departmentIdText, 0, gridPaneStartingRowIndex);
        gridPane.add(departmentChoiceBox, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        return gridPane;
    }
}
