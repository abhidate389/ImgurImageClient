package com.sample.imgurimageclient.repository.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageDataList {

    @SerializedName("data")
    List<Images> images;

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @SerializedName("success")
    String success;


}


