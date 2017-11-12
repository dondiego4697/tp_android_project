package com.koala.infinitum.android_project.httpApi.services;

import android.os.Handler;
import android.os.Looper;

import com.koala.infinitum.android_project.httpApi.Api;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.interfaces.place.IGetAll;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.httpApi.models.ResponseOneObject;
import com.koala.infinitum.android_project.httpApi.models.Responses;
import com.koala.infinitum.android_project.httpApi.models.UserResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceService {



    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private UIThread<ResponseOneObject<UserResponse>> uiThread= new UIThread<>();

    private ExecutorService executorService= Executors.newSingleThreadExecutor();

    private Api api;


    private IGetAll getAll;
    public PlaceService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://urbiscor-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getAll = retrofit.create(IGetAll.class);
    }

    public void getAll(Integer limit, Integer offset, Boolean desc, String category, final ClientCallback<Responses<Place>> clientCallback) {
        try {
            getAll.getData(limit, offset, desc, category).enqueue(new Callback<Responses<Place>>() { //потом на executor заменю
                @Override
                public void onResponse(Call<Responses<Place>> call, retrofit2.Response<Responses<Place>> response) {
                    if (response.body() == null) {
                        clientCallback.onError("empty response");
                    } else if (response.body().getStatusCode() != 200) {
                        clientCallback.onError(response.body().getErrorDescription());
                    } else {
                        clientCallback.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Call<Responses<Place>> call, Throwable t) {
                    clientCallback.onError(String.valueOf(t));
                }
            });
        } catch (Exception e) {
            clientCallback.onError(String.valueOf(e));
        }
    }


}
