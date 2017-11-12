package com.koala.infinitum.android_project.httpApi.interfaces.place;

import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.httpApi.models.Responses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGetAll {
    @GET("/api/place/get-all")
    Call<Responses<Place>> getData(@Query("limit") Integer limit, @Query("offset") Integer offset, @Query("desc") Boolean desc, @Query("category") String category);
}
