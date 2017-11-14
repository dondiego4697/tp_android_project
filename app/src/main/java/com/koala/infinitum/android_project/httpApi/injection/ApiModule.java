package com.koala.infinitum.android_project.httpApi.injection;

import com.koala.infinitum.android_project.httpApi.Api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by andrey on 12.11.17.
 */
/*@Module
public class ApiModule {

    @Provides
    @Singleton
    Api provideApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://urbiscor-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(Api.class);
    }
}*/

@Module
class ApiModule{
    private Api api;
    ApiModule(Api api){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://urbiscor-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api= retrofit.create(Api.class);
    }


    @Provides
    @Singleton
    Api provideContext(){return api;}
}
