package com.nagpal.shivam.dbms.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import static com.nagpal.shivam.dbms.Main.sStage;

public class InsertDepartmentData {
    private InsertDepartmentData() {
    }

    public static void setScene() {
        Pane pane = new InsertDepartmentData().getLayout();
        pane.setPrefSize(800, 600);
        Scene scene = new Scene(pane);
        sStage.setTitle("Insert new department");
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

        GridPane containerGridPane = new GridPane();

        Button backButton = new Button("Back");
        containerGridPane.add(backButton, 0, 0);

        containerGridPane.add(formGridPane, 1, 1);

        Button submitButton = new Button("Submit");
        containerGridPane.add(submitButton, 2, 2);

        return containerGridPane;
    }
}
