package com.koala.infinitum.android_project.httpApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by andrey on 14.11.17.
 */

public class ApiSingletone {

    private static Api instance;

    public static synchronized Api getInstance() {
        if (instance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://urbiscor-server.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            instance = retrofit.create(Api.class);
        }
        return instance;
    }
}
