package com.koala.infinitum.android_project.httpApi.services;


import com.koala.infinitum.android_project.httpApi.interfaces.user.UserLogin;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginService {

    private UserLogin userLogin;
    public LoginService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://urbiscor-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userLogin = retrofit.create(UserLogin.class);
    }

    public void login(){

    }
}
