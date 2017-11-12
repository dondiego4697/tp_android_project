package com.koala.infinitum.android_project.httpApi.DAO;

import android.util.Log;

import com.koala.infinitum.android_project.httpApi.Api;
import com.koala.infinitum.android_project.httpApi.ListenerHandler;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.Responses;
import com.koala.infinitum.android_project.httpApi.models.UserBody;
import com.koala.infinitum.android_project.httpApi.models.UserValidation;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by andrey on 11.11.17.
 */

public class User {
    ExecutorService executor = Executors.newSingleThreadExecutor();


    private Api api;

    public User() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://urbiscor-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public ListenerHandler<ClientCallback<UserValidation>>  register(final String login,
                                                                               final String password,
                                                                               final ClientCallback<UserValidation> clientCallback) {
        final ListenerHandler<ClientCallback<UserValidation>> handler = new ListenerHandler<>(clientCallback);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final retrofit2.Response<Responses<UserValidation>> response;
                try {
                    response = api.register(new UserBody(login, password)).execute();
                    if (response.body() == null) {
                        clientCallback.onError(("Этот логин уже занят, придумайте другой"));
                    } else if (response.body().getStatusCode() != 200) {
                        clientCallback.onError(response.body().getErrorDescription());
                    } else {
                        clientCallback.onSuccess(response.body());
                    }
                } catch (IOException e) {
                    clientCallback.onError("Некорректные параметры");
                }
            }
        });
        return handler;
    }



    public ListenerHandler<ClientCallback<UserValidation>>  login(final String login,
                                                                     final String password,
                                                                     final ClientCallback<UserValidation> clientCallback) {
        final ListenerHandler<ClientCallback<UserValidation>> handler = new ListenerHandler<>(clientCallback);
        System.out.println(3);
/*        executor.execute(new Runnable() {
            @Override
            public void run() {*/
                final retrofit2.Response<Responses<UserValidation>> response;
                try {
                    response = api.login(new UserBody(login, password)).execute();
                    if (response.body() == null) {
                        clientCallback.onError(("Неправильные данные"));
                    } else if (response.body().getStatusCode() != 200) {
                        clientCallback.onError(response.body().getErrorDescription());
                    } else {
                        clientCallback.onSuccess(response.body());
                    }
                } catch (IOException e) {
                    clientCallback.onError("Некорректные параметры");
                }
          //  }
        //});
        return handler;
    }



}
