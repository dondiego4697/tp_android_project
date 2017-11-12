package com.koala.infinitum.android_project.httpApi.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserValidation {

    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
