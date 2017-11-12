package com.koala.infinitum.android_project.httpApi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 12.11.17.
 */

public class Subscribes {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("user_id")
    @Expose
    private Integer userId;

    @SerializedName("place_id")
    @Expose
    private Integer placeId;

    public Integer getId(){return id;}

    public Integer getUserId(){return userId;}

    public Integer getPlaceId(){return placeId;}

}
