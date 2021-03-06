package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseContract;
import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.data.PreviewIgnoredAttribute;
import com.nagpal.shivam.dbms.data.SqlErrorCodes;
import com.nagpal.shivam.dbms.model.IaMarksData;
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

public class PreviewIaMarks extends UiScene {
    private TableView<IaMarksData> mTableView;
    private ComboBox<String> mSearchModeComboBox;
    private TextField mSearchTextField;
    private Task<List<IaMarksData>> mIaMarksDataTask;
    private Task<List<IaMarksData>> mSearchDataTask;

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.setMinSize(800, 600);
        Scene scene = new Scene(pane);
        sStage.setTitle("IaMarks Preview");
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, Utils.getPreviewEventHandler(mTableView));
        sStage.setScene(scene);
        pane.requestFocus();
    }

    @Override
    protected Pane getLayout() {

        mTableView = new TableView<>();
        addTableColumns();
        mTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        mIaMarksDataTask = getIaMarksDataTask();

        Thread iaMarksThread = new Thread(mIaMarksDataTask);
        iaMarksThread.start();

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> backAction());
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> addAction());
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> editAction());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> deleteAction());
        Button calculateAverageButton = new Button("Calculate Average");
        calculateAverageButton.setOnAction(event -> calculateAverage());

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
                calculateAverageButton,
                emptyPane,
                searchHBox
        );
        borderPane.setTop(toolBar);
        borderPane.setPadding(new Insets(15));
        borderPane.setCenter(mTableView);
        return borderPane;
    }

    private Task<List<IaMarksData>> getIaMarksDataTask() {
        return new Task<List<IaMarksData>>() {
            @Override
            protected List<IaMarksData> call() {
                return DatabaseHelper.fetchIaMarksDetails();
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
        if (mIaMarksDataTask != null) {
            mIaMarksDataTask.cancel(true);
        }
        if (mSearchDataTask != null) {
            mSearchDataTask.cancel(true);
        }

        String key = mSearchTextField.getText();
        if (key == null || key.isEmpty()) {
            mIaMarksDataTask = getIaMarksDataTask();
            Thread thread = new Thread(mIaMarksDataTask);
            thread.start();
        } else {
            String mode = mSearchModeComboBox.getSelectionModel().getSelectedItem();
            mSearchDataTask = new Task<List<IaMarksData>>() {
                @Override
                protected List<IaMarksData> call() {
                    return DatabaseHelper.searchIaMarksDetails(key, mode);
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

    private void calculateAverage() {
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() {
                return DatabaseHelper.executeIaMarksCalculateAverage();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                mIaMarksDataTask = getIaMarksDataTask();
                Thread thread = new Thread(mIaMarksDataTask);
                thread.start();
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    private void addTableColumns() {
        Field[] fields = IaMarksData.class.getFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(PreviewIgnoredAttribute.class)) {
                continue;
            }
            String columnName = f.getName();
            TableColumn<IaMarksData, String> tableColumn = new TableColumn<>(columnName);
            tableColumn.setCellValueFactory(param -> {
                IaMarksData data = param.getValue();

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
        Intent intent = new Intent(this, InsertOrEditIaMarksData.class);
        NavUtil.startScene(intent);
    }

    private void editAction() {
        ObservableList<IaMarksData> iaMarksDataObservableList = mTableView.getSelectionModel().getSelectedItems();
        if (iaMarksDataObservableList.size() == 1) {
            Intent intent = new Intent(this, InsertOrEditIaMarksData.class);
            intent.setData(IaMarksData.class, iaMarksDataObservableList.get(0));
            NavUtil.startScene(intent);
        }

    }

    private void deleteAction() {
        ObservableList<IaMarksData> iaMarksDataObservableList = mTableView.getSelectionModel().getSelectedItems();
        if (iaMarksDataObservableList.size() > 0) {
            Task<ArrayList<IaMarksData>> deleteTask = new Task<ArrayList<IaMarksData>>() {
                @Override
                protected ArrayList<IaMarksData> call() {
                    ArrayList<IaMarksData> al = new ArrayList<>();
                    for (IaMarksData data : iaMarksDataObservableList) {
                        int i = DatabaseHelper.deleteRow(DatabaseContract.IaMarks.TABLE_NAME, data.rowId);
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
