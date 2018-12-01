package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.DepartmentData;
import com.nagpal.shivam.dbms.model.ProfessorData;
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

public class InsertOrEditProfessorData extends UiScene {

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
    private String mTitle;

    public InsertOrEditProfessorData() {
        isEditMode = false;
        mTitle = "Insert new professor detail";
    }

    public InsertOrEditProfessorData(ProfessorData professorData) {
        mProfessorData = professorData;
        isEditMode = true;
        mTitle = "Edit professor detail";
    }

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.getStyleClass().add("parentPane");
        fetchForeignKeys();
        if (isEditMode) {
            fillDetails();
        }
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("css/InsertOrEditScene.css");
        sStage.setTitle(mTitle);
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

        Label designationLabel = new Label("Designation");
        mDesignationTextField = new TextField();
        mDesignationTextField.setPromptText("Enter Designation");
        formGridPane.add(designationLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mDesignationTextField, 1, gridPaneStartingRowIndex);
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
            mProfessorData = new ProfessorData();
        }
        mProfessorData.name = mNameTextField.getText();
        mProfessorData.professorId = mIdTextField.getText();
        LocalDate localDate = mDobDatePicker.getValue();
        if (localDate != null) {
            mProfessorData.dateOfBirth = localDate.format(DateTimeFormatter.ISO_DATE);
        }
        mProfessorData.address = mAddressTextField.getText();
        mProfessorData.email = mEmailTextField.getText();
        mProfessorData.phone = mPhoneTextField.getText();
        DepartmentData departmentData = mDepartmentDataComboBox.getValue();
        if (departmentData != null) {
            mProfessorData.departmentId = departmentData.departmentId;
        }
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

            @Override
            protected void succeeded() {
                super.succeeded();
                Utils.onInsertOrUpdateResponse(InsertOrEditProfessorData.this, this.getValue());
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
        if (mProfessorData.dateOfBirth != null) {
            mDobDatePicker.setValue(LocalDate.parse(mProfessorData.dateOfBirth));
        }
        mAddressTextField.setText(mProfessorData.address);
        mEmailTextField.setText(mProfessorData.email);
        mPhoneTextField.setText(mProfessorData.phone);
        mDesignationTextField.setText(mProfessorData.designation);
    }

}
