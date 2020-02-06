package com.sample.imgurimageclient.repository;

import com.sample.imgurimageclient.repository.models.ImageDataList;
import com.sample.imgurimageclient.repository.network.api.ImageSearchApi;
import com.sample.imgurimageclient.utilities.Constants;

import io.reactivex.Observable;

/**
 * Image repo to make API call
 */
public class ImageApiRepository {

    private ImageSearchApi imageSearchApi;

    public ImageApiRepository(ImageSearchApi imageSearchApi) {
        this.imageSearchApi = imageSearchApi;
    }

    /*
     * method to call image api
     * */
    public Observable<ImageDataList> getImages(String query, int page) {
        return imageSearchApi.getImages(query, Constants.CLIENT_ID,page);
    }
}
