package com.koala.infinitum.android_project.httpApi.services;

import com.koala.infinitum.android_project.httpApi.Api;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.httpApi.models.PlaceBody;
import com.koala.infinitum.android_project.httpApi.models.ResponseOneObject;
import com.koala.infinitum.android_project.httpApi.models.Responses;
import com.koala.infinitum.android_project.httpApi.models.UserResponse;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceService {

    private UIThread<ResponseOneObject<UserResponse>> uiThread= new UIThread<>();

    private ExecutorService executorService= Executors.newSingleThreadExecutor();

   // @Inject
    public Api api;
    public PlaceService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://urbiscor-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public ClientCallback<Responses<Place>> getAll(final Integer limit, final Integer offset, final Boolean desc, final String category, final ClientCallback<Responses<Place>> clientCallback) {
        final UIThread<Responses<Place>> responsesUIThread= new UIThread<>();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        Response<Responses<Place>> response=api.getAllPLaces(limit, offset, desc, category).execute();
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
        return clientCallback;
    }

    public ClientCallback<Responses<UserResponse>> getSubscribesUsers(final Integer placeId, final Integer limit,final Integer offset, final ClientCallback<Responses<UserResponse>> clientCallback) {
        final UIThread<Responses<UserResponse>> responsesUIThread= new UIThread<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    Response<Responses<UserResponse>> response=api.getSubscribesUsers(placeId,limit, offset).execute();
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
                        uiThread.Fail(clientCallback,("Invalid"));
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

/*    public ClientCallback<Responses<Place>> createPlace(final Integer id, final Integer userId, final ClientCallback<Responses<Place>> clientCallback){
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
    }*/

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
