package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.data.PreviewIgnoredAttribute;
import com.nagpal.shivam.dbms.model.ProfessorData;
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.lang.reflect.Field;
import java.util.List;

import static com.nagpal.shivam.dbms.Main.sStage;

public class PreviewProfessor extends UiScene {

    private TableView<ProfessorData> mTableView;

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.setMinSize(800, 600);
        Scene scene = new Scene(pane);
        sStage.setTitle("Professor Preview");
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            Node source = evt.getPickResult().getIntersectedNode();

            while (source != null && !(source instanceof TableRow)) {
                source = source.getParent();
            }

            if (source != null && ((TableRow) source).isEmpty()) {
                mTableView.getSelectionModel().clearSelection();
            }
        });
        sStage.setScene(scene);
    }

    @Override
    protected Pane getLayout() {

        mTableView = new TableView<>();
        addTableColumns();
        mTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Task<List<ProfessorData>> professorDataTask = new Task<List<ProfessorData>>() {
            @Override
            protected List<ProfessorData> call() {
                return DatabaseHelper.fetchProfessorDetails();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                List<ProfessorData> al = this.getValue();
                mTableView.setItems(FXCollections.observableList(al));
                mTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            }
        };

        Thread professorThread = new Thread(professorDataTask);
        professorThread.start();

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
        Field[] fields = ProfessorData.class.getFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(PreviewIgnoredAttribute.class)) {
                continue;
            }
            String columnName = f.getName();
            TableColumn<ProfessorData, String> tableColumn = new TableColumn<>(columnName);
            tableColumn.setCellValueFactory(param -> {
                ProfessorData data = param.getValue();

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
        Intent intent = new Intent(this, InsertProfessorData.class);
        NavUtil.startScene(intent);
    }

    private void editAction() {
        ObservableList<ProfessorData> professorDataArrayList = mTableView.getSelectionModel().getSelectedItems();
        if (professorDataArrayList.size() == 1) {
            Intent intent = new Intent(this, InsertProfessorData.class);
            intent.setData(ProfessorData.class, professorDataArrayList.get(0));
            NavUtil.startScene(intent);
        }

    }

    private void deleteAction() {
        ObservableList<ProfessorData> professorDataArrayList = mTableView.getSelectionModel().getSelectedItems();
        if (professorDataArrayList.size() > 0) {

        }
    }

}
