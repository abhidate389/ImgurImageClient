package com.sample.imgurimageclient.repository.storage;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sample.imgurimageclient.repository.models.ImageComment;
import com.sample.imgurimageclient.repository.storage.paging.ImageCommentDataSourceFactory;
import com.sample.imgurimageclient.utilities.Constants;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ImageComment database store given comment with image id and comments
 */
@Database(entities = {ImageComment.class}, version = 1,exportSchema = false)
public abstract class ImageCommentsDatabase extends RoomDatabase {

    private static ImageCommentsDatabase instance;
    public abstract ImageCommentsDao imageCommentsDao();
    private static final Object sLock = new Object();
    private LiveData<PagedList<ImageComment>> comments;

    public static ImageCommentsDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        ImageCommentsDatabase.class, Constants.DATA_BASE_NAME)
                        .build();
                instance.init();

            }
            return instance;
        }
    }

    private void init() {

    }

    public LiveData<PagedList<ImageComment>> getComments(String imageId) {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Integer.MAX_VALUE).setPageSize(Integer.MAX_VALUE).build();
        Executor executor = Executors.newFixedThreadPool(Constants.NUMBERS_OF_THREADS);
        ImageCommentDataSourceFactory dataSourceFactory = new ImageCommentDataSourceFactory(imageCommentsDao(),imageId);
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        comments = livePagedListBuilder.setFetchExecutor(executor).build();
        return comments;
    }
}
