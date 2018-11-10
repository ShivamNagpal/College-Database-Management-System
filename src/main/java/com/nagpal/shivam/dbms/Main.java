package com.nagpal.shivam.dbms;

import com.nagpal.shivam.dbms.data.Database;
import com.nagpal.shivam.dbms.data.DatabaseHelper;
import com.nagpal.shivam.dbms.ui.InsertTeachesData;
import javafx.application.Application;
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
        DatabaseHelper.createTables();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        sStage = primaryStage;
        sStage.show();
        InsertTeachesData.setScene();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Database.closeConnection();
    }
}
