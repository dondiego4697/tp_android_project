package com.koala.infinitum.android_project.httpApi.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserValidation {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("login")
    private String login;

    public String getToken() {
        return token;
    }

    public String getLogin(){return login;}

    public Integer getId(){return id;}

    public void setToken(String token) {
        this.token = token;
    }
}
