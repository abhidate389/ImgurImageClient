package com.sample.imgurimageclient.repository.network.api;

import com.sample.imgurimageclient.repository.models.ImageDataList;
import com.sample.imgurimageclient.utilities.Constants;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImageSearchApi {

    @GET(Constants.IMAGEURL)
    Observable<ImageDataList> getImages(@Query("q") String query, @Query("client_id") String clientId, @Query("page") int page);

}
