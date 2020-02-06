package com.sample.imgurimageclient.repository.storage.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.sample.imgurimageclient.repository.models.ImageComment;
import com.sample.imgurimageclient.repository.storage.ImageCommentsDao;

import java.util.List;

/**
 * PageKeyedDataSource for getting comments from database
 */
public class ImageCommentDataSource extends PageKeyedDataSource<String, ImageComment> {

    public static final String TAG = ImageCommentDataSource.class.getSimpleName();
    private final ImageCommentsDao imageDao;
    private final String imageId;

    public ImageCommentDataSource(ImageCommentsDao dao, String imageId) {
        imageDao = dao;
        this.imageId = imageId;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, ImageComment> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);

        List<ImageComment> imageComments = imageDao.loadComments(imageId);
        if(imageComments.size() != 0) {
            callback.onResult(imageComments, "0", "1");
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, ImageComment> callback) {
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, ImageComment> callback) {
    }
}
