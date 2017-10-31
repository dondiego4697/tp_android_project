package com.koala.infinitum.android_project.httpApi.interfaces;

import retrofit2.Response;

public interface ClientCallback<T> {
    void onSuccess(Response<T> response);

    void onError(String err);
}
