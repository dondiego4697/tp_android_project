package com.koala.infinitum.android_project.httpApi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by andrey on 12.11.17.
 */

public class PlaceBody {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("creatorId")
    @Expose
    private Integer creatorId;
    @SerializedName("point")
    @Expose
    private List<Double> point;
    @SerializedName("timeStart")
    @Expose
    private String timeStart;

    public void setTitle(String title){this.title=title;}

    public void setDescription(String description){this.description=description;}

    public void setCategoryId(Integer id){this.categoryId=id;}

    public void setCreatorId(Integer id){this.creatorId=id;}

    public void setPoint(List<Double> point){this.point=point;}

    public void setTimeStart(String timeStart){this.timeStart=timeStart;}


    public String getTitle(){return title;}

    public String getDescription(){return description;}

    public String getTimeStart(){return timeStart;}

    public Integer getCategoryId(){return categoryId;}

    public Integer getCreatorId(){return creatorId;}

    public List<Double> getPoint(){return point;}


}
