package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.navigation.NavUtil;
import javafx.scene.layout.Pane;

public abstract class UiScene {
    public abstract void setScene();

    protected abstract Pane getLayout();

    protected void onBackPressed() {
        if (NavUtil.canGoBack()) {
            NavUtil.goBack();
        }
    }
}
