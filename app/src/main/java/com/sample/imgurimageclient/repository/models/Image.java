package com.sample.imgurimageclient.repository.models;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.SerializedName;

public class Image extends BaseObservable {

    @SerializedName("id")
    private String imageId;

    @SerializedName("link")
    private String imageUrl;

    @SerializedName("type")
    private String type;

    private String title;

    @Bindable
    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Call back to see difference between two image item in page list
     */
    public static final DiffUtil.ItemCallback<Image> CALLBACK = new DiffUtil.ItemCallback<Image>() {
        @Override
        public boolean areItemsTheSame(@NonNull Image image1, @NonNull Image image2) {
            return image1.imageId == image2.imageId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Image image1, @NonNull Image image2) {
            return true;
        }
    };
}
