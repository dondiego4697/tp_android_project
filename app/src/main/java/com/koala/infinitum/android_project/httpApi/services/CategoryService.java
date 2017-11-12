package com.koala.infinitum.android_project.httpApi.services;

import com.koala.infinitum.android_project.httpApi.Api;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.Category;
import com.koala.infinitum.android_project.httpApi.models.ResponseOneObject;
import com.koala.infinitum.android_project.httpApi.models.Responses;
import com.koala.infinitum.android_project.httpApi.models.UserResponse;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by andrey on 12.11.17.
 */

public class CategoryService {

    private UIThread<ResponseOneObject<UserResponse>> uiThread= new UIThread<>();

    private ExecutorService executorService= Executors.newSingleThreadExecutor();

    private Api api;


    public CategoryService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://urbiscor-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api= retrofit.create(Api.class);
    }

    public ClientCallback<Responses<Category>> getCategories(final ClientCallback<Responses<Category>> clientCallback){
        final UIThread<Responses<Category>> responsesUIThread= new UIThread<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    Response<Responses<Category>> response=api.getCategories().execute();
                    if (response.body() == null) {
                        responsesUIThread.Fail(clientCallback,"empty response");
                    } else if (response.body().getStatusCode() != 200) {
                        responsesUIThread.Fail(clientCallback,response.body().getErrorDescription());
                    } else {
                        responsesUIThread.Success(clientCallback,response);
                    }
                }catch (IOException e){
                    responsesUIThread.Fail(clientCallback,String.valueOf(e));
                }
            }
        });
        return  clientCallback;
    }

}
