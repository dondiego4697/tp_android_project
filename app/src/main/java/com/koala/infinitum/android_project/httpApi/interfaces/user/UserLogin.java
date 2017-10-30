package com.koala.infinitum.android_project.httpApi.interfaces.user;


import com.koala.infinitum.android_project.httpApi.models.ResponseOneObject;
import com.koala.infinitum.android_project.httpApi.models.UserBody;
import com.koala.infinitum.android_project.httpApi.models.UserValidation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserLogin {
    @POST("/api/user/create")
    Call<ResponseOneObject<UserValidation>> register(@Body UserBody userBody);

    @POST("/api/user/login")
    Call<ResponseOneObject<UserValidation>> login(@Body UserBody userBody);
}
