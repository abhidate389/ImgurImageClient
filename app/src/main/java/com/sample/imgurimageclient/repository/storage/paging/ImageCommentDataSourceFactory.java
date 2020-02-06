package com.sample.imgurimageclient.repository.storage.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.sample.imgurimageclient.repository.storage.ImageCommentsDao;

public class ImageCommentDataSourceFactory extends DataSource.Factory {

    private ImageCommentDataSource imageCommentDataSource;
    MutableLiveData<ImageCommentDataSource> mutableLiveData;
    private String imageId;
    private ImageCommentsDao imageDao;

    public ImageCommentDataSourceFactory(ImageCommentsDao imageDao, String imageId) {
        mutableLiveData = new MutableLiveData<>();
        this.imageId = imageId;
        this.imageDao = imageDao;
    }

    @Override
    public DataSource create() {
        imageCommentDataSource = new ImageCommentDataSource(imageDao,imageId);
        mutableLiveData.postValue(imageCommentDataSource);
        return imageCommentDataSource;
    }
}