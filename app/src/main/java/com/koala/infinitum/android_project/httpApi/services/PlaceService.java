package com.koala.infinitum.android_project.httpApi.services;

import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.interfaces.place.IGetAll;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.httpApi.models.Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceService {

    private IGetAll getAll;
    public PlaceService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://urbiscor-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getAll = retrofit.create(IGetAll.class);
    }

    public void getAll(Integer limit, Integer offset, Boolean desc, String category, final ClientCallback<Response<Place>> clientCallback) {
        try {
            getAll.getData(limit, offset, desc, category).enqueue(new Callback<Response<Place>>() {
                @Override
                public void onResponse(Call<Response<Place>> call, retrofit2.Response<Response<Place>> response) {
                    if (response.body() == null) {
                        clientCallback.onError("empty response");
                    } else if (response.body().getStatusCode() != 200) {
                        clientCallback.onError(response.body().getErrorDescription());
                    } else {
                        clientCallback.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Call<Response<Place>> call, Throwable t) {
                    clientCallback.onError(String.valueOf(t));
                }
            });
        } catch (Exception e) {
            clientCallback.onError(String.valueOf(e));
        }
    }
}
