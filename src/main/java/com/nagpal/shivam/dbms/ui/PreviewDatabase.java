package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.navigation.Intent;
import com.nagpal.shivam.dbms.navigation.NavUtil;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.nagpal.shivam.dbms.Main.sStage;

public class PreviewDatabase extends UiScene {
    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.setPrefSize(800, 600);
        Scene scene = new Scene(pane);
        sStage.setTitle("Database Preview");
        sStage.setScene(scene);
    }

    @Override
    protected Pane getLayout() {
        LinkedHashMap<String, Class> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("Professor", PreviewProfessor.class);
        linkedHashMap.put("Student", PreviewStudent.class);
        linkedHashMap.put("Department", PreviewDepartment.class);
        linkedHashMap.put("Subject", PreviewSubject.class);
        linkedHashMap.put("Division", PreviewDivision.class);
        linkedHashMap.put("Teaches", PreviewTeaches.class);
        linkedHashMap.put("SemesterSection", PreviewSemesterSection.class);
        linkedHashMap.put("IaMarks", PreviewIaMarks.class);
        TilePane tilePane = new TilePane();

        ArrayList<Button> buttons = new ArrayList<>();
        for (Map.Entry<String, Class> entry : linkedHashMap.entrySet()) {
            Button button = new Button(entry.getKey());
            button.setOnAction(event -> NavUtil.startScene(new Intent(PreviewDatabase.this, entry.getValue())));
            buttons.add(button);
        }

        tilePane.setPrefColumns(3);
        tilePane.setHgap(20);
        tilePane.setVgap(20);
        tilePane.getChildren().addAll(buttons);

        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().add(tilePane);
        flowPane.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(flowPane);
        return borderPane;
    }
}
