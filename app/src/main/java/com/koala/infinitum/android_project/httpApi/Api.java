package com.koala.infinitum.android_project.httpApi;

import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.httpApi.models.ResponseOneObject;
import com.koala.infinitum.android_project.httpApi.models.Responses;
import com.koala.infinitum.android_project.httpApi.models.Subcribes;
import com.koala.infinitum.android_project.httpApi.models.UserBody;
import com.koala.infinitum.android_project.httpApi.models.UserResponse;
import com.koala.infinitum.android_project.httpApi.models.UserValidation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by andrey on 12.11.17.
 */

public interface Api { // экземпляр ретрофита должен быть один, поидее, поэтому все api должно быть в одном классе, подключать его через provide singleton
    @GET("/api/place/get-all")
    Call<Responses<Place>> getData(@Query("limit") Integer limit, @Query("offset") Integer offset, @Query("desc") Boolean desc, @Query("category") String category);

    @POST("/api/user/create")
    Call<ResponseOneObject<UserValidation>> register(@Body UserBody userBody);

    @POST("/api/user/login")
    Call<ResponseOneObject<UserValidation>> login(@Body UserBody userBody);

    @GET("/api/user/get/{userId}")
    Call<Responses<UserResponse>> getUser(@Path("userId") Integer id);

    @GET("api/user/get-subscriptions/{userId}")
    Call<Responses<Place>> getUserPlaces(@Path("userId") Integer id, @Query("limit") Integer limit, @Query("offset") Integer offset, @Query("desc") Boolean desc);

    @GET("/get-places/{userId}")
    Call<Responses<Place>> getUserCreatePLaces(@Path("userId") Integer id, @Query("limit") Integer limit, @Query("offset") Integer offset, @Query("desc") Boolean desc);

    @GET("/api/place/subscribe")
    Call<Responses<Subcribes>> subscribe(@Query("userId") Integer userId, @Query("placeId") Integer placeId);

    @GET("/api/place/unsubscribe")
    Call<Responses<Subcribes>> unsubscribe(@Query("userId") Integer userId, @Query("placeId") Integer placeId);
}
