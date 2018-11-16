package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.DepartmentData;
import com.nagpal.shivam.dbms.model.ProfessorData;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.nagpal.shivam.dbms.Main.sStage;

public class InsertProfessorData extends UiScene {

    private final boolean isEditMode;
    private TextField mNameTextField;
    private TextField mIdTextField;
    private DatePicker mDobDatePicker;
    private TextField mAddressTextField;
    private TextField mPhoneTextField;
    private TextField mEmailTextField;
    private TextField mDesignationTextField;
    private ComboBox<DepartmentData> mDepartmentDataComboBox;
    private ProfessorData mProfessorData;

    public InsertProfessorData() {
        isEditMode = false;
        sStage.setTitle("Insert new professor detail");
    }

    public InsertProfessorData(ProfessorData professorData) {
        mProfessorData = professorData;
        isEditMode = true;
        sStage.setTitle("Edit professor detail");
    }

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.setPrefSize(800, 600);
        fetchForeignKeys();
        if (isEditMode) {
            fillDetails();
        }
        Scene scene = new Scene(pane);
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

        Text dobText = new Text("Date of Birth");
        mDobDatePicker = new DatePicker();
        mDobDatePicker.setPromptText("Choose a date");
        formGridPane.add(dobText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mDobDatePicker, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text addressText = new Text("Address");
        mAddressTextField = new TextField();
        mAddressTextField.setPromptText("Enter Address");
        formGridPane.add(addressText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mAddressTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text phoneText = new Text("Phone");
        mPhoneTextField = new TextField();
        mPhoneTextField.setPromptText("Enter Phone Number");
        formGridPane.add(phoneText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mPhoneTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text emailText = new Text("Email");
        mEmailTextField = new TextField();
        mEmailTextField.setPromptText("Enter Email Address");
        formGridPane.add(emailText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mEmailTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text designationText = new Text("Designation");
        mDesignationTextField = new TextField();
        mDesignationTextField.setPromptText("Enter Designation");
        formGridPane.add(designationText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mDesignationTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text departmentIdText = new Text("Department*");

        Callback<ListView<DepartmentData>, ListCell<DepartmentData>> departmentComboBoxCallback = Utils.getDepartmentComboBoxCallback();


        mDepartmentDataComboBox = new ComboBox<>();
        mDepartmentDataComboBox.setCellFactory(departmentComboBoxCallback);
        mDepartmentDataComboBox.setButtonCell(departmentComboBoxCallback.call(null));

        mDepartmentDataComboBox.setPromptText("Choose a department");

        Text linkToAddNewDepartment = new Text("Not Found!, Add new Department First");
        formGridPane.add(departmentIdText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mDepartmentDataComboBox, 1, gridPaneStartingRowIndex);
        formGridPane.add(linkToAddNewDepartment, 2, gridPaneStartingRowIndex);
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
            mProfessorData = new ProfessorData();
        }
        mProfessorData.name = mNameTextField.getText();
        mProfessorData.professorId = mIdTextField.getText();
        mProfessorData.dateOfBirth = mDobDatePicker.getValue().format(DateTimeFormatter.ISO_DATE);
        mProfessorData.address = mAddressTextField.getText();
        mProfessorData.email = mEmailTextField.getText();
        mProfessorData.phone = mPhoneTextField.getText();
        mProfessorData.departmentId = mDepartmentDataComboBox.getValue().departmentId;
        mProfessorData.designation = mDesignationTextField.getText();

        Task<Integer> submitTask = new Task<Integer>() {
            @Override
            protected Integer call() {
                int i;
                if (!isEditMode) {
                    i = DatabaseHelper.insertIntoProfessor(mProfessorData);
                } else {
                    i = DatabaseHelper.updateProfessor(mProfessorData);
                }
                return i;
            }
        };
        Thread submitThread = new Thread(submitTask);
        submitThread.start();
    }

    private void fetchForeignKeys() {
        Task<List<DepartmentData>> fetchDepartmentDetailsTask = new Task<List<DepartmentData>>() {
            @Override
            protected List<DepartmentData> call() {
                List<DepartmentData> departmentData = null;
                if (isEditMode) {
                    departmentData = new ArrayList<>();
                    departmentData.addAll(DatabaseHelper.fetchParticularDepartment(mProfessorData.departmentId, true));
                    departmentData.addAll(DatabaseHelper.fetchParticularDepartment(mProfessorData.departmentId, false));
                } else {
                    departmentData = DatabaseHelper.fetchDepartmentDetails();
                }
                return departmentData;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                mDepartmentDataComboBox.getItems().addAll(this.getValue());
                if (isEditMode) {
                    mDepartmentDataComboBox.getSelectionModel().select(0);
                }
            }
        };
        Thread thread = new Thread(fetchDepartmentDetailsTask);
        thread.start();
    }

    private void fillDetails() {
        mNameTextField.setText(mProfessorData.name);
        mIdTextField.setText(mProfessorData.professorId);
        mDobDatePicker.setValue(LocalDate.parse(mProfessorData.dateOfBirth));
        mAddressTextField.setText(mProfessorData.address);
        mEmailTextField.setText(mProfessorData.email);
        mPhoneTextField.setText(mProfessorData.phone);
        mDesignationTextField.setText(mProfessorData.designation);
    }

}
