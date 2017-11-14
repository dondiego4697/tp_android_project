package com.koala.infinitum.android_project.Singletones;

import android.util.Log;

import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.Responses;
import com.koala.infinitum.android_project.httpApi.services.CategoryService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class SingletonCategory {
    private static SingletonCategory instance;
    private List<com.koala.infinitum.android_project.httpApi.models.Category> arrayList;

    private SingletonCategory() {
        CategoryService categoryService = new CategoryService();
        arrayList = new ArrayList<>();
        categoryService.getCategories(new ClientCallback<Responses<com.koala.infinitum.android_project.httpApi.models.Category>>() {
            @Override
            public void onSuccess(Response<Responses<com.koala.infinitum.android_project.httpApi.models.Category>> response) {
                arrayList = response.body().getData();
            }

            @Override
            public void onError(String err) {

            }
        });
    }

    public ArrayList<String> getCategories() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            list.add(arrayList.get(i).getName());
        }
        return list;
    }

    public static SingletonCategory getInstance() {
        instance = instance == null ? new SingletonCategory() : instance;
        return instance;
    }
}
