package com.koala.infinitum.android_project.httpApi.services;

import android.os.Handler;
import android.os.Looper;

import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;

import retrofit2.Response;

/**
 * Created by andrey on 12.11.17.
 */

class UIThread<T>{

    private final Handler mainHandler = new Handler(Looper.getMainLooper());//singleton

    void Success(final ClientCallback<T> clientCallback, final Response<T> response) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                clientCallback.onSuccess(response);
            }
        });
    }

    void Fail(final ClientCallback<T> clientCallback, final String error){
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                clientCallback.onError(error);
            }
        });

    }
}
