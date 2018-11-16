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
        TilePane tilePane = new TilePane();
        Button[] buttons = new Button[]{
                new Button("Professor"),
                new Button("Student"),
                new Button("Department"),
                new Button("Subject"),
                new Button("Division"),
                new Button("Teaches"),
                new Button("SemesterSection"),
                new Button("IaMarks")
        };
        buttons[0].setOnAction(event -> {
            Intent intent = new Intent(PreviewDatabase.this, PreviewProfessor.class);
            NavUtil.startScene(intent);
        });
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
