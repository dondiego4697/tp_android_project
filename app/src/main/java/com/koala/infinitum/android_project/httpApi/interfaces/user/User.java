package com.koala.infinitum.android_project.httpApi.interfaces.user;


import com.koala.infinitum.android_project.httpApi.models.Responses;
import com.koala.infinitum.android_project.httpApi.models.UserBody;
import com.koala.infinitum.android_project.httpApi.models.UserValidation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface User {
    @POST("/api/user/create")
    Call<Responses<UserValidation>> register(@Body UserBody userBody);

    @POST("/api/user/login")
    Call<Responses<UserValidation>> login(@Body UserBody userBody);


}
