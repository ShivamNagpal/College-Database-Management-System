package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.model.DepartmentData;
import com.nagpal.shivam.dbms.model.SubjectData;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;

import static com.nagpal.shivam.dbms.Main.sStage;

public class InsertSubjectData extends UiScene {

    private final boolean isEditMode;
    private TextField mNameTextField;
    private TextField mIdTextField;
    private TextField mSchemeTextField;
    private TextField mSemesterTextField;
    private TextField mCreditsTextField;
    private ComboBox<DepartmentData> mDepartmentDataComboBox;
    private SubjectData mSubjectData;

    public InsertSubjectData() {
        isEditMode = false;
    }

    public InsertSubjectData(SubjectData subjectData) {
        mSubjectData = subjectData;
        isEditMode = true;
    }

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.setPrefSize(800, 600);
        Scene scene = new Scene(pane);
        sStage.setTitle("Insert new subject");
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

        Text schemeText = new Text("Scheme");
        mSchemeTextField = new TextField();
        mSchemeTextField.setPromptText("Enter Scheme");
        formGridPane.add(schemeText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mSchemeTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text semesterText = new Text("Semester");
        mSemesterTextField = new TextField();
        mSemesterTextField.setPromptText("Enter Semester");
        formGridPane.add(semesterText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mSemesterTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text creditsText = new Text("Credits");
        mCreditsTextField = new TextField();
        mCreditsTextField.setPromptText("Enter Credits");
        formGridPane.add(creditsText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mCreditsTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Text departmentIdText = new Text("Department*");
        Callback<ListView<DepartmentData>, ListCell<DepartmentData>> departmentComboBoxCallback = Utils.getDepartmentComboBoxCallback();


        mDepartmentDataComboBox = new ComboBox<>();
        mDepartmentDataComboBox.setCellFactory(departmentComboBoxCallback);
        mDepartmentDataComboBox.setButtonCell(departmentComboBoxCallback.call(null));

        mDepartmentDataComboBox.setPromptText("Choose a department");
        Task<List<DepartmentData>> fetchDepartmentDetailsTask = new Task<List<DepartmentData>>() {
            @Override
            protected List<DepartmentData> call() {
                return DatabaseHelper.fetchDepartmentDetails();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                mDepartmentDataComboBox.getItems().addAll(this.getValue());
            }
        };

        Thread thread = new Thread(fetchDepartmentDetailsTask);
        thread.start();
        Text linkToAddNewDepartment = new Text("Not Found!, Add new Department First");
        formGridPane.add(departmentIdText, 0, gridPaneStartingRowIndex);
        formGridPane.add(mDepartmentDataComboBox, 1, gridPaneStartingRowIndex);
        formGridPane.add(linkToAddNewDepartment, 2, gridPaneStartingRowIndex);
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
            mSubjectData = new SubjectData();
        }
        mSubjectData.name = mNameTextField.getText();
        mSubjectData.subjectId = mIdTextField.getText();
        mSubjectData.scheme = mSchemeTextField.getText();
        mSubjectData.semester = Integer.parseInt(mSemesterTextField.getText());
        mSubjectData.credits = Integer.parseInt(mCreditsTextField.getText());
        mSubjectData.departmentId = mDepartmentDataComboBox.getValue().departmentId;

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
        };
        Thread submitThread = new Thread(submitTask);
        submitThread.start();
    }
}
