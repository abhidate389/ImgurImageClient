package com.sample.imgurimageclient.repository.network.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.sample.imgurimageclient.repository.ImageApiRepository;

public class ImageDataSourceFactory extends DataSource.Factory {

    private ImageDataSource imageDataSource;
    MutableLiveData<ImageDataSource> mutableLiveData;
    private ImageApiRepository imageApiRepository;
    private String query;
    public ImageDataSourceFactory(String query, ImageApiRepository imageApiRepository) {
        this.imageApiRepository = imageApiRepository;
        mutableLiveData = new MutableLiveData<>();
        this.query = query;
    }

    @Override
    public DataSource create() {

        imageDataSource = new ImageDataSource(query, imageApiRepository);
        mutableLiveData.postValue(imageDataSource);
        return imageDataSource;
    }

    public MutableLiveData<ImageDataSource> getMutableLiveData() {
        return mutableLiveData;
    }

}