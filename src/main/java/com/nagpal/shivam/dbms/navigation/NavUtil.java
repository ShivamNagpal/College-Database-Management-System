package com.nagpal.shivam.dbms.navigation;

import com.nagpal.shivam.dbms.ui.UiScene;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

public class NavUtil {
    private static LinkedList<UiScene> sUiScenes;

    static {
        sUiScenes = new LinkedList<>();
    }

    public static void startScene(Intent intent) {
        try {
            UiScene uiScene;
            if (intent.hasData()) {
                Constructor<?> constructor = intent.mNextSceneClass.getConstructor(intent.dataClass);
                uiScene = (UiScene) constructor.newInstance(intent.dataClass.cast(intent.data));
            } else {
                Constructor<?> constructor = intent.mNextSceneClass.getConstructor();
                uiScene = (UiScene) constructor.newInstance();
            }
            uiScene.setScene();
            sUiScenes.addLast(intent.mCurrentUiScene);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static boolean canGoBack() {
        return sUiScenes.size() != 0;
    }

    public static void goBack() {
        UiScene uiScene = sUiScenes.removeLast();
        uiScene.setScene();
    }
}
