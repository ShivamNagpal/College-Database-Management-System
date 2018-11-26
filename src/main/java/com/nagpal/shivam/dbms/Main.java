package com.nagpal.shivam.dbms;

import com.nagpal.shivam.dbms.data.Database;
import com.nagpal.shivam.dbms.ui.LogIn;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    private static final String CLASS_NAME = Main.class.getSimpleName();
    public static Stage sStage;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        sStage = primaryStage;
        sStage.getIcons().add(new Image("images/icon.png"));
        sStage.show();
        new LogIn().setScene();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Database.closeConnection();
    }
}
