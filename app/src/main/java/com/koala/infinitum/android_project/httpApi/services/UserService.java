package com.koala.infinitum.android_project.httpApi.services;

import com.koala.infinitum.android_project.httpApi.Api;
import com.koala.infinitum.android_project.httpApi.AdiSingleton;
import com.koala.infinitum.android_project.httpApi.ExecutorSingletone;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.httpApi.models.Responses;
import com.koala.infinitum.android_project.httpApi.models.UserResponse;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import retrofit2.Response;

/**
 * Created by andrey on 12.11.17.
 */

public class UserService {




    private ExecutorService executorService= ExecutorSingletone.getInstance();

    public Api api= AdiSingleton.getInstance();


    public ClientCallback<Responses<UserResponse>> get(final Integer id, final ClientCallback<Responses<UserResponse>> clientCallback){
        final UIThread<Responses<UserResponse>> UIThread= new UIThread<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    Response<Responses<UserResponse>> response=api.getUser(id).execute();
                    if (response.body() == null) {
                        UIThread.Fail(clientCallback," некорректные данные");
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


    public ClientCallback<Responses<Place>> getUserPlaces(final Integer id, final Integer limit, final Integer offset, final Boolean desc, final ClientCallback<Responses<Place>> clientCallback,
                                                          final boolean flag){//false- те на которые подписан, true- им созданыне, потом покрасивее это сделаю.
        final UIThread<Responses<Place>> responsesUIThread= new UIThread<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    Response<Responses<Place>> response;
                    if(!flag) response =api.getUserPlaces(id,limit,offset,desc).execute();
                    else response =api.getUserCreatePLaces(id,limit,offset,desc).execute();
                    if (response.body() == null) {
                        responsesUIThread.Fail(clientCallback,"некорректные данные");
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
