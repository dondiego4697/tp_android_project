package com.koala.infinitum.android_project.httpApi.interfaces.user;


import com.koala.infinitum.android_project.httpApi.models.Response;
import com.koala.infinitum.android_project.httpApi.models.User;

import retrofit2.Call;
import retrofit2.http.POST;

public interface UserLogin {
    @POST("/api/user/create")
    Call<Response<User>> register();

    @POST("/api/user/login")
    Call<Response<User>> login();
}
