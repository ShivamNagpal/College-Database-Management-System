package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.data.PreviewIgnoredAttribute;
import com.nagpal.shivam.dbms.model.DivisionData;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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

public class PreviewDivision extends UiScene {
    private TableView<DivisionData> mTableView;

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.setMinSize(800, 600);
        Scene scene = new Scene(pane);
        sStage.setTitle("Division Preview");
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            Node source = evt.getPickResult().getIntersectedNode();

            while (source != null && !(source instanceof TableRow)) {
                source = source.getParent();
            }

            if (source == null || ((TableRow) source).isEmpty()) {
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

        Task<List<DivisionData>> DivisionDataTask = new Task<List<DivisionData>>() {
            @Override
            protected List<DivisionData> call() {
                return DatabaseHelper.fetchDivisionDetails();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                List<DivisionData> al = this.getValue();
                mTableView.setItems(FXCollections.observableList(al));
                mTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            }
        };

        Thread DivisionThread = new Thread(DivisionDataTask);
        DivisionThread.start();

        Button backButton = new Button("Back");
        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");

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
        Field[] fields = DivisionData.class.getFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(PreviewIgnoredAttribute.class)) {
                continue;
            }
            String columnName = f.getName();
            TableColumn<DivisionData, String> tableColumn = new TableColumn<>(columnName);
            tableColumn.setCellValueFactory(param -> {
                DivisionData data = param.getValue();

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
}
