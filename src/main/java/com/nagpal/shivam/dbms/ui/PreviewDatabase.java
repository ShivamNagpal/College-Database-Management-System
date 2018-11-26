package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.navigation.Intent;
import com.nagpal.shivam.dbms.navigation.NavUtil;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.nagpal.shivam.dbms.Main.sStage;

public class PreviewDatabase extends UiScene {
    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.getStyleClass().add("parentPane");
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("css/PreviewDatabaseScene.css");
        sStage.setTitle("Database Preview");
        sStage.setScene(scene);
        pane.requestFocus();
    }

    @Override
    protected Pane getLayout() {
        Image logo = new Image("images/college-pic.jpg");
        ImageView logoImageView = new ImageView(logo);
        logoImageView.setFitWidth(400);
        logoImageView.setFitHeight(300);
        FlowPane logoImageViewFlowPane = new FlowPane(logoImageView);
        logoImageViewFlowPane.getStyleClass().add("logoImageViewFlowPane");

        LinkedHashMap<String, Class> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("Professor", PreviewProfessor.class);
        linkedHashMap.put("Student", PreviewStudent.class);
        linkedHashMap.put("Department", PreviewDepartment.class);
        linkedHashMap.put("Subject", PreviewSubject.class);
        linkedHashMap.put("Division", PreviewDivision.class);
        linkedHashMap.put("Teaches", PreviewTeaches.class);
        linkedHashMap.put("Semester-Section", PreviewSemesterSection.class);
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

        FlowPane tileFlowPane = new FlowPane(tilePane);
        tileFlowPane.getStyleClass().add("tileFlowPane");

        VBox vBox = new VBox(logoImageViewFlowPane, tileFlowPane);
        vBox.setSpacing(30);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox);
        return borderPane;
    }
}
