package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseContract;
import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.data.PreviewIgnoredAttribute;
import com.nagpal.shivam.dbms.data.SqlErrorCodes;
import com.nagpal.shivam.dbms.model.SubjectData;
import com.nagpal.shivam.dbms.navigation.Intent;
import com.nagpal.shivam.dbms.navigation.NavUtil;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.nagpal.shivam.dbms.Main.sStage;

public class PreviewSubject extends UiScene {

    private TableView<SubjectData> mTableView;
    private Task<List<SubjectData>> mSubjectDataTask;
    private ComboBox<String> mSearchModeComboBox;
    private TextField mSearchTextField;
    private Task<List<SubjectData>> mSearchDataTask;

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.setMinSize(800, 600);
        Scene scene = new Scene(pane);
        sStage.setTitle("Subject Preview");
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, Utils.getPreviewEventHandler(mTableView));
        sStage.setScene(scene);
        pane.requestFocus();
    }

    @Override
    protected Pane getLayout() {

        mTableView = new TableView<>();
        addTableColumns();
        mTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        mSubjectDataTask = getSubjectDataTask();

        Thread subjectThread = new Thread(mSubjectDataTask);
        subjectThread.start();

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> backAction());
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> addAction());
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> editAction());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> deleteAction());

        Pane emptyPane = new Pane();
        HBox.setHgrow(emptyPane, Priority.ALWAYS);

        mSearchModeComboBox = new ComboBox<>();
        mSearchModeComboBox.getItems().addAll(DatabaseHelper.SEARCH_MODE_NATURAL_LANGUAGE, DatabaseHelper.SEARCH_MODE_BOOLEAN);
        mSearchModeComboBox.getSelectionModel().select(0);
        mSearchModeComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> searchAction());

        mSearchTextField = new TextField();
        mSearchTextField.setPromptText(Constants.search);
        mSearchTextField.addEventHandler(KeyEvent.KEY_RELEASED, event -> searchAction());

        HBox searchHBox = new HBox(mSearchModeComboBox, mSearchTextField);


        BorderPane borderPane = new BorderPane();
        ToolBar toolBar = new ToolBar(backButton,
                new Separator(Orientation.VERTICAL),
                addButton,
                editButton,
                deleteButton,
                emptyPane,
                searchHBox
        );
        borderPane.setTop(toolBar);
        borderPane.setPadding(new Insets(15));
        borderPane.setCenter(mTableView);
        return borderPane;
    }

    private Task<List<SubjectData>> getSubjectDataTask() {
        return new Task<List<SubjectData>>() {
            @Override
            protected List<SubjectData> call() {
                return DatabaseHelper.fetchSubjectDetails();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                mTableView.getItems().clear();
                mTableView.getItems().addAll(this.getValue());
                mTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            }
        };
    }

    private void searchAction() {
        if (mSubjectDataTask != null) {
            mSubjectDataTask.cancel(true);
        }
        if (mSearchDataTask != null) {
            mSearchDataTask.cancel(true);
        }

        String key = mSearchTextField.getText();
        if (key == null || key.isEmpty()) {
            mSubjectDataTask = getSubjectDataTask();
            Thread thread = new Thread(mSubjectDataTask);
            thread.start();
        } else {
            String mode = mSearchModeComboBox.getSelectionModel().getSelectedItem();
            mSearchDataTask = new Task<List<SubjectData>>() {
                @Override
                protected List<SubjectData> call() {
                    return DatabaseHelper.searchSubjectDetails(key, mode);
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    mTableView.getItems().clear();
                    mTableView.getItems().addAll(this.getValue());
                }
            };
            Thread thread = new Thread(mSearchDataTask);
            thread.start();
        }

    }


    private void addTableColumns() {
        Field[] fields = SubjectData.class.getFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(PreviewIgnoredAttribute.class)) {
                continue;
            }
            String columnName = f.getName();
            TableColumn<SubjectData, String> tableColumn = new TableColumn<>(columnName);
            tableColumn.setCellValueFactory(param -> {
                SubjectData data = param.getValue();

                return new ObservableValue<String>() {
                    @Override
                    public void addListener(ChangeListener<? super String> listener) {

                    }

                    @Override
                    public void removeListener(ChangeListener<? super String> listener) {

                    }

                    @Override
                    public String getValue() {
                        try {
                            return String.valueOf(data.getClass().getField(columnName).get(data));
                        } catch (IllegalAccessException | NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void addListener(InvalidationListener listener) {

                    }

                    @Override
                    public void removeListener(InvalidationListener listener) {

                    }
                };
            });

            mTableView.getColumns().add(tableColumn);
        }
    }

    private void backAction() {
        super.onBackPressed();
    }

    private void addAction() {
        Intent intent = new Intent(this, InsertOrEditSubjectData.class);
        NavUtil.startScene(intent);
    }

    private void editAction() {
        ObservableList<SubjectData> subjectDataObservableList = mTableView.getSelectionModel().getSelectedItems();
        if (subjectDataObservableList.size() == 1) {
            Intent intent = new Intent(this, InsertOrEditSubjectData.class);
            intent.setData(SubjectData.class, subjectDataObservableList.get(0));
            NavUtil.startScene(intent);
        }

    }

    private void deleteAction() {
        ObservableList<SubjectData> subjectDataObservableList = mTableView.getSelectionModel().getSelectedItems();
        if (subjectDataObservableList.size() > 0) {
            Task<ArrayList<SubjectData>> deleteTask = new Task<ArrayList<SubjectData>>() {
                @Override
                protected ArrayList<SubjectData> call() {
                    ArrayList<SubjectData> al = new ArrayList<>();
                    for (SubjectData data : subjectDataObservableList) {
                        int i = DatabaseHelper.deleteRow(DatabaseContract.Subject.TABLE_NAME, data.rowId);
                        if (i == SqlErrorCodes.SQLITE_OK) {
                            al.add(data);
                        }
                    }
                    return al;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    mTableView.getItems().removeAll(this.getValue());
                }
            };
            Thread deleteThread = new Thread(deleteTask);
            deleteThread.start();
        }
    }

}
