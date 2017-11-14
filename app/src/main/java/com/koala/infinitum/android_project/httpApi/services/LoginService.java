package com.koala.infinitum.android_project.httpApi.services;


import android.content.Context;

import com.koala.infinitum.android_project.httpApi.Api;
import com.koala.infinitum.android_project.httpApi.ApiSingletone;
import com.koala.infinitum.android_project.httpApi.ExecutorSingletone;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.ResponseOneObject;
import com.koala.infinitum.android_project.httpApi.models.UserBody;
import com.koala.infinitum.android_project.httpApi.models.UserValidation;

import java.util.concurrent.ExecutorService;

import retrofit2.Response;

public class LoginService {

    private ExecutorService executorService= ExecutorSingletone.getInstance();

    private Api api= ApiSingletone.getInstance();



    public ClientCallback<ResponseOneObject<UserValidation>> login(final String login,
                                                                   final String password,
                                                                   final ClientCallback<ResponseOneObject<UserValidation>> clientCallback,
                                                                   Context context) {

       final UIThread<ResponseOneObject<UserValidation>> uiThread = new UIThread<>();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                  Response<ResponseOneObject<UserValidation>> response =  api.login(new UserBody(login, password)).execute();
                            if (response.body() == null) {
                                uiThread.Fail(clientCallback,("Неверный логин или пароль!"));
                            } else if (response.body().getStatusCode() != 200) {
                                uiThread.Fail(clientCallback,response.body().getErrorDescription());
                            } else {
                                uiThread.Success(clientCallback,response);
                            }
                } catch (Exception e) {
                    uiThread.Fail(clientCallback,String.valueOf(e));
                }
            }
        });
       return clientCallback;
    }

    public ClientCallback<ResponseOneObject<UserValidation>> register(final String login,
                         final String password,
                         final ClientCallback<ResponseOneObject<UserValidation>> clientCallback){

        final UIThread<ResponseOneObject<UserValidation>> uiThread = new UIThread<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    Response<ResponseOneObject<UserValidation>> response=api.register(new UserBody(login, password)).execute();
                    if (response.body() == null) {
                                uiThread.Fail(clientCallback,"Этот логин уже занят, придумайте другой");
                            } else if (response.body().getStatusCode() != 200) {
                                uiThread.Fail(clientCallback,response.body().getErrorDescription());
                            } else {
                                uiThread.Success(clientCallback,response);
                            }
                }catch (Exception e){
                    uiThread.Fail(clientCallback,String.valueOf(e));
                }
            }
        });
        return clientCallback;
    }
}
