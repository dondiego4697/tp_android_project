package com.koala.infinitum.android_project;

import android.app.Application;

import com.koala.infinitum.android_project.httpApi.injection.DBComponent;
import com.koala.infinitum.android_project.httpApi.injection.DaggerDBComponent;

public class App extends Application {
    private static DBComponent component;

    public static DBComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerDBComponent.builder().build();
    }
}
