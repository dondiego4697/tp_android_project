package com.koala.infinitum.android_project.httpApi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by andrey on 14.11.17.
 */

public class ExecutorSingletone {
    private static ExecutorService instance;

    public static synchronized ExecutorService getInstance() {
        if (instance == null) {
            instance = Executors.newSingleThreadExecutor();
        }
        return instance;
    }

}
