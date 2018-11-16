package com.nagpal.shivam.dbms.navigation;

import com.nagpal.shivam.dbms.ui.UiScene;

public class Intent {
    UiScene mCurrentUiScene;
    Class<?> mNextSceneClass;
    Class<?> dataClass;
    Object data;
    private boolean hasData;

    public Intent(UiScene currentUiScene, Class<?> aNextSceneClass) {
        mCurrentUiScene = currentUiScene;
        mNextSceneClass = aNextSceneClass;
    }

    public void setData(Class<?> dataClass, Object data) {
        this.dataClass = dataClass;
        this.data = data;
        hasData = true;
    }

    public boolean hasData() {
        return hasData;
    }
}
