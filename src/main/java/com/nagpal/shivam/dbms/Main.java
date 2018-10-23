package com.nagpal.shivam.dbms;

import com.nagpal.shivam.dbms.data.Database;
import com.nagpal.shivam.dbms.data.DatabaseHelper;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static final String CLASS_NAME = Main.class.getSimpleName();

    public static void main(String[] args) {
        DatabaseHelper.createTables();
        launch(args);
    }


    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Database.closeConnection();
    }
}
