package com.koala.infinitum.android_project.httpApi.services;


import android.content.Context;

import com.koala.infinitum.android_project.httpApi.Api;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.ResponseOneObject;
import com.koala.infinitum.android_project.httpApi.models.UserBody;
import com.koala.infinitum.android_project.httpApi.models.UserValidation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginService {

    private UIThread<ResponseOneObject<UserValidation>> uiThread = new UIThread<>();//?
    private ExecutorService executorService= Executors.newSingleThreadExecutor();

  // @Inject
    public Api api;

   /* public LoginService(){
        App.getComponent().inject(this);
    }*/
   public LoginService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://urbiscor-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public ClientCallback<ResponseOneObject<UserValidation>> login(final String login,
                                                                   final String password,
                                                                   final ClientCallback<ResponseOneObject<UserValidation>> clientCallback,
                                                                   Context context) {

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

  /*  private void Success(final ClientCallback<ResponseOneObject<UserValidation>> clientCallback,final Response<ResponseOneObject<UserValidation>> response) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                clientCallback.onSuccess(response);
            }
        });
    }

    private void Fail(final ClientCallback<ResponseOneObject<UserValidation>> clientCallback, final String error){
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                clientCallback.onError(error);
            }
        });

    }*/
}
