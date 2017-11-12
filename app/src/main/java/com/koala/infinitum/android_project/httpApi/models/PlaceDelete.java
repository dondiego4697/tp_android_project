package com.koala.infinitum.android_project.httpApi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 12.11.17.
 */

public class PlaceDelete {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("userId")
    private Integer userId;

    public void setId(Integer id){this.id=id;}

    public void setUserId(Integer userId){this.userId=userId;}

    public Integer getId(){return id;}

    public Integer getUserId(){return userId;}

    public PlaceDelete(Integer id, Integer userId){
        this.id=id;
        this.userId=userId;
    }
}
