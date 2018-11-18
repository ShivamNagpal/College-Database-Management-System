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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.nagpal.shivam.dbms.Main.sStage;

public class PreviewSubject extends UiScene {

    private TableView<SubjectData> mTableView;

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.setMinSize(800, 600);
        Scene scene = new Scene(pane);
        sStage.setTitle("Subject Preview");
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, Utils.getPreviewEventHandler(mTableView));
        sStage.setScene(scene);
    }

    @Override
    protected Pane getLayout() {

        mTableView = new TableView<>();
        addTableColumns();
        mTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Task<List<SubjectData>> subjectDataTask = new Task<List<SubjectData>>() {
            @Override
            protected List<SubjectData> call() {
                return DatabaseHelper.fetchSubjectDetails();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                List<SubjectData> al = this.getValue();
                mTableView.setItems(FXCollections.observableList(al));
                mTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            }
        };

        Thread subjectThread = new Thread(subjectDataTask);
        subjectThread.start();

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> backAction());
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> addAction());
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> editAction());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> deleteAction());

        BorderPane borderPane = new BorderPane();
        ToolBar toolBar = new ToolBar(backButton,
                new Separator(Orientation.VERTICAL),
                addButton,
                editButton,
                deleteButton
        );
        borderPane.setTop(toolBar);
        borderPane.setPadding(new Insets(15));
        borderPane.setCenter(mTableView);
        return borderPane;
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
