package com.sample.imgurimageclient.repository.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Images {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("is_album")
    private boolean isAlbum;

    @SerializedName("images")
    private List<Image> imageList;

    public List<Image> getImageList() {
        return imageList;
    }

    public boolean isAlbum() {
        return isAlbum;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
