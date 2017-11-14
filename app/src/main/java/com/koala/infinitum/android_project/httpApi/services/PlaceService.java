package com.koala.infinitum.android_project.httpApi.services;

import com.koala.infinitum.android_project.httpApi.Api;
import com.koala.infinitum.android_project.httpApi.ApiSingletone;
import com.koala.infinitum.android_project.httpApi.ExecutorSingletone;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.httpApi.models.PlaceBody;
import com.koala.infinitum.android_project.httpApi.models.PlaceDelete;
import com.koala.infinitum.android_project.httpApi.models.Responses;
import com.koala.infinitum.android_project.httpApi.models.UserResponse;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

public class PlaceService {



    private ExecutorService executorService= ExecutorSingletone.getInstance();

    private  Api api= ApiSingletone.getInstance();


    public ClientCallback<Responses<Place>> getAll(final Integer limit, final Integer offset, final Boolean desc, final String category, final ClientCallback<Responses<Place>> clientCallback) {
        final UIThread<Responses<Place>> UIThread= new UIThread<>();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        Response<Responses<Place>> response=api.getAllPLaces(limit, offset, desc, category).execute();
                        if (response.body() == null) {
                            UIThread.Fail(clientCallback,"empty response");
                        } else if (response.body().getStatusCode() != 200) {
                            UIThread.Fail(clientCallback,response.body().getErrorDescription());
                        } else {
                            UIThread.Success(clientCallback,response);
                        }
                    }catch (IOException e){
                        UIThread.Fail(clientCallback,String.valueOf(e));
                    }
                }
            });
        return clientCallback;
    }

    public ClientCallback<Responses<UserResponse>> getSubscribesUsers(final Integer placeId, final Integer limit,final Integer offset, final ClientCallback<Responses<UserResponse>> clientCallback) {
        final UIThread<Responses<UserResponse>> UIThread= new UIThread<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    Response<Responses<UserResponse>> response=api.getSubscribesUsers(placeId,limit, offset).execute();
                    if (response.body() == null) {
                        UIThread.Fail(clientCallback,"empty response");
                    } else if (response.body().getStatusCode() != 200) {
                        UIThread.Fail(clientCallback,response.body().getErrorDescription());
                    } else {
                        UIThread.Success(clientCallback,response);
                    }
                }catch (IOException e){
                    UIThread.Fail(clientCallback,String.valueOf(e));
                }
            }
        });
        return clientCallback;
    }

    public ClientCallback<Responses<Place>> createPlace(final PlaceBody placeBody, final ClientCallback<Responses<Place>> clientCallback){
        final UIThread<Responses<Place>> uiThread = new UIThread<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<Responses<Place>> response =  api.createPlace(placeBody).execute();
                    if (response.body() == null) {
                        uiThread.Fail(clientCallback,("Неккоректные данные"));
                    } else if (response.body().getStatusCode() != 200) {
                        uiThread.Fail(clientCallback,response.body().getErrorDescription());
                    } else {
                        uiThread.Success(clientCallback,response);
                    }
                } catch (Exception e) {
                    uiThread.Fail(clientCallback,String.valueOf(e));
                }
            }
        });
        return clientCallback;
    }

    public ClientCallback<Responses<Place>> deletePlace(final Integer id, final Integer userId, final ClientCallback<Responses<Place>> clientCallback){
        final UIThread<Responses<Place>> uiThread = new UIThread<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<Responses<Place>> response =  api.deletePlace(new PlaceDelete(id,userId)).execute();
                    if (response.body() == null) {
                        uiThread.Fail(clientCallback,("empty response"));
                    } else if (response.body().getStatusCode() != 200) {
                        uiThread.Fail(clientCallback,response.body().getErrorDescription());
                    } else {
                        uiThread.Success(clientCallback,response);
                    }
                } catch (Exception e) {
                    uiThread.Fail(clientCallback,String.valueOf(e));
                }
            }
        });
        return clientCallback;
    }

    public ClientCallback<Responses<Place>> getAroundPlace(final Double lat, final Double lng, final Integer limit, final Integer offset,
                                                             final Double step, final String category, final ClientCallback<Responses<Place>> clientCallback){
        final UIThread<Responses<Place>> uiThread = new UIThread<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<Responses<Place>> response =  api.getAroundPlace(lat,lng,limit,offset,step,category).execute();
                    if (response.body() == null){
                        uiThread.Fail(clientCallback,("empty response"));
                    } else if (response.body().getStatusCode() != 200) {
                        uiThread.Fail(clientCallback,response.body().getErrorDescription());
                    } else {
                        uiThread.Success(clientCallback,response);
                    }
                } catch (Exception e) {
                    uiThread.Fail(clientCallback,String.valueOf(e));
                }
            }
        });


        return clientCallback;
    }
}
