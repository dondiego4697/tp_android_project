package com.koala.infinitum.android_project.httpApi.services;

import com.koala.infinitum.android_project.httpApi.Api;
import com.koala.infinitum.android_project.httpApi.AdiSingleton;
import com.koala.infinitum.android_project.httpApi.ExecutorSingletone;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.Responses;
import com.koala.infinitum.android_project.httpApi.models.Subscribes;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import retrofit2.Response;

/**
 * Created by andrey on 12.11.17.
 */

public class SubscribeService {


    private ExecutorService executorService= ExecutorSingletone.getInstance();

    private Api api= AdiSingleton.getInstance();

    public ClientCallback<Responses<Subscribes>> subscribe(final Integer userId, final Integer placeId, final ClientCallback<Responses<Subscribes>> clientCallback,
                                                           final boolean flag){ //true подписаться, false отписаться
        final UIThread<Responses<Subscribes>> UIThread= new UIThread<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    Response<Responses<Subscribes>> response;
                    if(flag) response=api.subscribe(userId,placeId).execute();
                    else response=api.unsubscribe(userId,placeId).execute();
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

}
