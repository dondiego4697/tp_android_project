package com.koala.infinitum.android_project.httpApi.interfaces.place;

import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.httpApi.models.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGetAll {
    @GET("/api/place/get-all")
    Call<Response<Place>> getData(@Query("limit") Integer limit, @Query("offset") Integer offset, @Query("desc") Boolean desc);
}
