package com.koala.infinitum.android_project.httpApi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Responses<T> {
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("errorDescription")
    @Expose
    private String errorDescription;
    @SerializedName("data")
    @Expose
    private List<T> data;

    public Integer getStatusCode() {
        return statusCode;
    }

    public List<T> getData() {
        return data;
    }

    public String getResult() {
        return result;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
