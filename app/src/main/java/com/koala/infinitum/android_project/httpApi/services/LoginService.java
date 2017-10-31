package com.koala.infinitum.android_project.httpApi.services;


import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.interfaces.user.UserLogin;
import com.koala.infinitum.android_project.httpApi.models.Response;
import com.koala.infinitum.android_project.httpApi.models.ResponseOneObject;
import com.koala.infinitum.android_project.httpApi.models.UserBody;
import com.koala.infinitum.android_project.httpApi.models.UserValidation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginService {

    private UserLogin userLogin;

    public LoginService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://urbiscor-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userLogin = retrofit.create(UserLogin.class);
    }

    public void login(String login,
                      String password,
                      final ClientCallback<ResponseOneObject<UserValidation>> clientCallback) {
        try {
            userLogin.login(new UserBody(login, password)).enqueue(new Callback<ResponseOneObject<UserValidation>>() {
                @Override
                public void onResponse(Call<ResponseOneObject<UserValidation>> call, retrofit2.Response<ResponseOneObject<UserValidation>> response) {
                    if (response.body() == null) {
                        clientCallback.onError(("Неверный логин или пароль!"));
                    } else if (response.body().getStatusCode() != 200) {
                        clientCallback.onError(response.body().getErrorDescription());
                    } else {
                        clientCallback.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Call<ResponseOneObject<UserValidation>> call, Throwable t) {
                    clientCallback.onError(String.valueOf(t));
                }
            });
        } catch (Exception e) {
            clientCallback.onError(String.valueOf(e));
        }
    }

    public void register(String login,
                         String password,
                         final ClientCallback<ResponseOneObject<UserValidation>> clientCallback){
        try{
            userLogin.register(new UserBody(login, password)).enqueue(new Callback<ResponseOneObject<UserValidation>>() {
                @Override
                public void onResponse(Call<ResponseOneObject<UserValidation>> call, retrofit2.Response<ResponseOneObject<UserValidation>> response) {
                    if (response.body() == null) {
                        clientCallback.onError(("Ошибка при регистрации!"));
                    } else if (response.body().getStatusCode() != 200) {
                        clientCallback.onError(response.body().getErrorDescription());
                    } else {
                        clientCallback.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Call<ResponseOneObject<UserValidation>> call, Throwable t) {
                    clientCallback.onError(String.valueOf(t));
                }
            });
        } catch (Exception e){
            clientCallback.onError(String.valueOf(e));
        }
    }
}
