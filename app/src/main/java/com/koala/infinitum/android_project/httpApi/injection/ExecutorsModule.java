package com.koala.infinitum.android_project.httpApi.injection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andrey on 12.11.17.
 */
@Module
class ExecutorsModule {

    @Provides
    @Singleton
    ExecutorService provideSingleExecutors() {
        return Executors.newSingleThreadExecutor();
    }
}

