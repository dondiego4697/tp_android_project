package com.koala.infinitum.android_project.Singletons;

import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.Category;
import com.koala.infinitum.android_project.httpApi.models.Responses;
import com.koala.infinitum.android_project.httpApi.services.CategoryService;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

public class CategorySingleton {

    private static CategorySingleton instance;
    private HashMap<String, String> categories;

    public static CategorySingleton getInstance() {
        if (instance == null) {
            instance = new CategorySingleton();
        }
        return instance;
    }

    public HashMap<String, String> getCategories() {
        return categories;
    }

    private CategorySingleton() {
        categories = new HashMap<>();
        new CategoryService().getCategories(new ClientCallback<Responses<Category>>() {
            @Override
            public void onSuccess(Response<Responses<Category>> response) {
                ArrayList<Category> arrCategories = (ArrayList<Category>) response.body().getData();
                for (int i = 0; i < arrCategories.size(); i++) {
                    categories.put(arrCategories.get(i).getName(), arrCategories.get(i).getSlug());
                }
            }

            @Override
            public void onError(String err) {

            }
        });
    }
}
