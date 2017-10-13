package com.koala.infinitum.android_project.httpApi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.koala.infinitum.android_project.support.DataParser;

public class Place {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("time_start")
    @Expose
    private String timeStart;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_login")
    @Expose
    private String userLogin;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("categorySlug")
    @Expose
    private String categorySlug;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("subscribers")
    @Expose
    private Integer subscribers;

    public Integer getId() {
        return id;
    }

    public String getCreated() {
        return DataParser.parse(created);
    }

    public String getDescription() {
        return description;
    }

    public String getPoint() {
        return point;
    }

    public String getTitle() {
        return title;
    }

    public Integer getSubscribers() {
        return subscribers;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getTimeStart() {
        return DataParser.parse(timeStart);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategorySlug(String categorySlug) {
        this.categorySlug = categorySlug;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSubscribers(Integer subscribers) {
        this.subscribers = subscribers;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
