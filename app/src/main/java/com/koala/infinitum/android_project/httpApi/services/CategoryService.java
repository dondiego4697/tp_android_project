package com.koala.infinitum.android_project.httpApi.services;

import com.koala.infinitum.android_project.httpApi.Api;
import com.koala.infinitum.android_project.httpApi.ApiSingletone;
import com.koala.infinitum.android_project.httpApi.ExecutorSingletone;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.Category;
import com.koala.infinitum.android_project.httpApi.models.Responses;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

/**
 * Created by andrey on 12.11.17.
 */

public class CategoryService {


    private ExecutorService executorService= ExecutorSingletone.getInstance();

    private Api api = ApiSingletone.getInstance();

    //мало ли надо будет отписаться
    public ClientCallback<Responses<Category>> getCategories(final ClientCallback<Responses<Category>> clientCallback) {
        final UIThread<Responses<Category>> UIThread = new UIThread<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<Responses<Category>> response = api.getCategories().execute();
                    if (response.body() == null) {
                        UIThread.Fail(clientCallback, "empty response");
                    } else if (response.body().getStatusCode() != 200) {
                        UIThread.Fail(clientCallback, response.body().getErrorDescription());
                    } else {
                        UIThread.Success(clientCallback, response);
                    }
                } catch (IOException e) {
                    UIThread.Fail(clientCallback, String.valueOf(e));
                }
            }
        });
        return clientCallback;
    }


}
