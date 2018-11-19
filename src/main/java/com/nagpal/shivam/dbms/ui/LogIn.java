package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.data.Database;
import com.nagpal.shivam.dbms.data.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import static com.nagpal.shivam.dbms.Main.sStage;

public class LogIn extends UiScene {
    private TextField mUsernameTextField;
    private TextField mServerUrlTextField;
    private PasswordField mPasswordField;

    @Override
    public void setScene() {
        Pane pane = getLayout();
        pane.getStyleClass().add("parentPane");
        Scene scene = new Scene(pane);
        sStage.setTitle("Login");
        scene.getStylesheets().add("css/Login.css");
        sStage.setScene(scene);
        pane.requestFocus();
    }

    @Override
    protected Pane getLayout() {
        GridPane formGridPane = new GridPane();
        formGridPane.getStyleClass().add("formGridPane");

        int gridPaneStartingRowIndex = 0;

        Label serverUrlLabel = new Label("Server Url");
        mServerUrlTextField = new TextField();
        mServerUrlTextField.setPromptText("Enter Server Url");
        mServerUrlTextField.setText("jdbc:mysql://localhost:3306");

        formGridPane.add(serverUrlLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mServerUrlTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Label usernameLabel = new Label("Username");
        mUsernameTextField = new TextField();
        mUsernameTextField.setPromptText("Enter Username");
        formGridPane.add(usernameLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mUsernameTextField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        Label passwordLabel = new Label("Password");
        mPasswordField = new PasswordField();
        mPasswordField.setPromptText("Enter Password");
        formGridPane.add(passwordLabel, 0, gridPaneStartingRowIndex);
        formGridPane.add(mPasswordField, 1, gridPaneStartingRowIndex);
        gridPaneStartingRowIndex += 1;

        BorderPane borderPane = new BorderPane();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> submitData());

        FlowPane submitButtonFlowPane = new FlowPane(submitButton);
        submitButtonFlowPane.getStyleClass().add("submitButtonFlowPane");

        VBox vBox = new VBox(formGridPane, submitButtonFlowPane);
        vBox.setSpacing(30);

        FlowPane vBoxFlowPane = new FlowPane(vBox);
        vBoxFlowPane.getStyleClass().add("vBoxFlowPane");

        borderPane.setCenter(vBoxFlowPane);

        return borderPane;
    }

    private void submitData() {
        String url = mServerUrlTextField.getText().trim();
        int len = url.length();
        if (url.charAt(len - 1) == '/') {
            url = url.substring(0, len - 1);
        }
        Database.putParameters(url, mUsernameTextField.getText().trim(), mPasswordField.getText());
        if (Database.getConnection() != null) {
            DatabaseHelper.createTables();
            new PreviewDatabase().setScene();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, Database.getLastError(), ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
        }
    }
}
