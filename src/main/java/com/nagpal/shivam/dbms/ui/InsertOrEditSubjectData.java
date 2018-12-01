package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.DepartmentData;
import com.nagpal.shivam.dbms.model.SubjectData;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

import static com.nagpal.shivam.dbms.Main.sStage;

public class InsertOrEditSubjectData extends UiScene {

    private final boolean isEditMode;
    private TextField mNameTextField;
    private TextField mIdTextField;
    private TextField mSchemeTextField;
    private TextField mSemesterTextField;
    private TextField mCreditsTextField;
    private ComboBox<DepartmentData> mDepartmentDataComboBox;
    private SubjectData mSubjectData;
    private String mTitle;

    public InsertOrEditSubjectData() {
        isEditMode = false;
        mTitle = "Insert new subject detail";
    }

    public InsertOrEditSubjectData(SubjectData subjectData) {
        mSubjectData = subjectData;
        isEditMode = true;
        mTitle = "Edit subject detail";
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

        Label schemeLabel = new Label("Scheme");
        mSchemeTextField = new TextField();
        mSchemeTextField.setPromptText("Enter Scheme");
        formGridPane.add(schemeLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mSchemeTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Label semesterLabel = new Label("Semester");
        mSemesterTextField = new TextField();
        mSemesterTextField.setPromptText("Enter Semester");
        formGridPane.add(semesterLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mSemesterTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Label creditsLabel = new Label("Credits");
        mCreditsTextField = new TextField();
        mCreditsTextField.setPromptText("Enter Credits");
        formGridPane.add(creditsLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mCreditsTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Label departmentIdLabel = new Label("Department*");
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
            mSubjectData = new SubjectData();
        }
        mSubjectData.name = mNameTextField.getText();
        mSubjectData.subjectId = mIdTextField.getText();
        mSubjectData.scheme = mSchemeTextField.getText();
        try {
            String semesterTextFieldText = mSemesterTextField.getText();
            if (!semesterTextFieldText.isEmpty()) {
                mSubjectData.semester = Integer.parseInt(semesterTextFieldText);
            }
            String creditsTextFieldText = mCreditsTextField.getText();
            if (!creditsTextFieldText.isEmpty()) {
                mSubjectData.credits = Integer.parseInt(creditsTextFieldText);
            }
        } catch (NumberFormatException e) {
            Utils.showErrorAlert("Enter Integral Value for Semester/Credits");
            return;
        }

        DepartmentData dataComboBoxValue = mDepartmentDataComboBox.getValue();
        if (dataComboBoxValue != null) {
            mSubjectData.departmentId = dataComboBoxValue.departmentId;
        }

        Task<Integer> submitTask = new Task<Integer>() {
            @Override
            protected Integer call() {
                int i;
                if (!isEditMode) {
                    i = DatabaseHelper.insertIntoSubject(mSubjectData);
                } else {
                    i = DatabaseHelper.updateSubject(mSubjectData);
                }
                return i;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                Utils.onInsertOrUpdateResponse(InsertOrEditSubjectData.this, this.getValue());
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
                    departmentData.addAll(DatabaseHelper.fetchParticularDepartment(mSubjectData.departmentId, true));
                    departmentData.addAll(DatabaseHelper.fetchParticularDepartment(mSubjectData.departmentId, false));
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
        mNameTextField.setText(mSubjectData.name);
        mIdTextField.setText(mSubjectData.subjectId);
        mSchemeTextField.setText(mSubjectData.scheme);
        mSemesterTextField.setText(Integer.toString(mSubjectData.semester));
        mCreditsTextField.setText(Integer.toString(mSubjectData.credits));
    }

}
