package com.koala.infinitum.android_project.httpApi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 12.11.17.
 */

public class UserResponse {
    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("created")
    @Expose
    private String created;

    public Integer getId() {return id;};

    public String getLogin(){return login;}

    public String getCreated(){return created;}


}
