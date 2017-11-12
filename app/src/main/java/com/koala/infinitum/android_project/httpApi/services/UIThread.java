package com.koala.infinitum.android_project.httpApi.services;

import android.os.Handler;
import android.os.Looper;

import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.ResponseOneObject;
import com.koala.infinitum.android_project.httpApi.models.UserValidation;

import retrofit2.Response;

/**
 * Created by andrey on 12.11.17.
 */

public class UIThread<T>{
    //отписываться надо?

    private final Handler mainHandler = new Handler(Looper.getMainLooper());//singleton

    public void Success(final ClientCallback<T> clientCallback, final Response<T> response) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                clientCallback.onSuccess(response);
            }
        });
    }

    public void Fail(final ClientCallback<T> clientCallback, final String error){
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                clientCallback.onError(error);
            }
        });

    }
}
