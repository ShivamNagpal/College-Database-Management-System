package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.DepartmentData;
import com.nagpal.shivam.dbms.model.StudentData;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.nagpal.shivam.dbms.Main.sStage;

public class InsertOrEditStudentData extends UiScene {

    private final boolean isEditMode;
    private TextField mNameTextField;
    private TextField mIdTextField;
    private DatePicker mDobDatePicker;
    private TextField mAddressTextField;
    private TextField mPhoneTextField;
    private TextField mEmailTextField;
    private ComboBox<DepartmentData> mDepartmentDataComboBox;
    private StudentData mStudentData;
    private String mTitle;

    public InsertOrEditStudentData() {
        isEditMode = false;
        mTitle = "Insert new student detail";
    }

    public InsertOrEditStudentData(StudentData studentData) {
        mStudentData = studentData;
        isEditMode = true;
        mTitle = "Edit student detail";
    }

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.getStyleClass().add("parentPane");
        fetchForeignKeys();
        if (isEditMode) {
            fillDetails();
        }
        sStage.setTitle(mTitle);
        Scene scene = new Scene(pane);
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

        Label dobLabel = new Label("Date of Birth");
        mDobDatePicker = new DatePicker();
        mDobDatePicker.setPromptText("Choose a date");
        formGridPane.add(dobLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mDobDatePicker, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Label addressLabel = new Label("Address");
        mAddressTextField = new TextField();
        mAddressTextField.setPromptText("Enter Address");
        formGridPane.add(addressLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mAddressTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Label phoneLabel = new Label("Phone");
        mPhoneTextField = new TextField();
        mPhoneTextField.setPromptText("Enter Phone Number");
        formGridPane.add(phoneLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mPhoneTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Label emailLabel = new Label("Email");
        mEmailTextField = new TextField();
        mEmailTextField.setPromptText("Enter Email Address");
        formGridPane.add(emailLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mEmailTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Label departmentIdLabel = new Label("Department");

        Callback<ListView<DepartmentData>, ListCell<DepartmentData>> departmentComboBoxCallback = Utils.getDepartmentComboBoxCallback();

        mDepartmentDataComboBox = new ComboBox<>();
        mDepartmentDataComboBox.setCellFactory(departmentComboBoxCallback);
        mDepartmentDataComboBox.setButtonCell(departmentComboBoxCallback.call(null));

        mDepartmentDataComboBox.setPromptText("Choose a department");

        formGridPane.add(departmentIdLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mDepartmentDataComboBox, 1, gridPaneStartingRowIndex);
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
            mStudentData = new StudentData();
        }
        mStudentData.name = mNameTextField.getText();
        mStudentData.studentId = mIdTextField.getText();

        LocalDate value = mDobDatePicker.getValue();
        if (value != null) {
            mStudentData.dateOfBirth = value.format(DateTimeFormatter.ISO_DATE);
        }
        mStudentData.address = mAddressTextField.getText();
        mStudentData.email = mEmailTextField.getText();
        mStudentData.phone = mPhoneTextField.getText();
        DepartmentData departmentData = mDepartmentDataComboBox.getValue();
        if (departmentData != null) {
            mStudentData.departmentId = departmentData.departmentId;
        }

        Task<Integer> submitTask = new Task<Integer>() {
            @Override
            protected Integer call() {
                int i;
                if (!isEditMode) {
                    i = DatabaseHelper.insertIntoStudent(mStudentData);
                } else {
                    i = DatabaseHelper.updateStudent(mStudentData);
                }
                return i;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                Utils.onInsertOrUpdateResponse(InsertOrEditStudentData.this, this.getValue());
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
                    departmentData.addAll(DatabaseHelper.fetchParticularDepartment(mStudentData.departmentId, true));
                    departmentData.addAll(DatabaseHelper.fetchParticularDepartment(mStudentData.departmentId, false));
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
        mNameTextField.setText(mStudentData.name);
        mIdTextField.setText(mStudentData.studentId);
        if (mStudentData.dateOfBirth != null) {
            mDobDatePicker.setValue(LocalDate.parse(mStudentData.dateOfBirth));
        }
        mAddressTextField.setText(mStudentData.address);
        mEmailTextField.setText(mStudentData.email);
        mPhoneTextField.setText(mStudentData.phone);
    }

}
