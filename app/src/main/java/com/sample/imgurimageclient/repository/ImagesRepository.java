package com.sample.imgurimageclient.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.sample.imgurimageclient.repository.models.ImageComment;
import com.sample.imgurimageclient.repository.storage.ImageCommentsDatabase;

/**
 * ImageApiRepository for inserting comment and getting comment
 */
public class ImagesRepository {

    private static final String TAG = ImagesRepository.class.getSimpleName();
    private static ImagesRepository instance;
    private ImageCommentsDatabase database;

    private ImagesRepository() {

    }

    public static ImagesRepository getInstance(){
        if(instance == null){
            instance = new ImagesRepository();
        }
        return instance;
    }

    public void insertComment(Context context,ImageComment imageComment){
        Log.d(TAG," Inserting data to DB");
        database = ImageCommentsDatabase.getInstance(context.getApplicationContext());
        database.imageCommentsDao().insertImageComments(imageComment);

    }

    public LiveData<PagedList<ImageComment>> getComments(Context context, String imageId){
        database = ImageCommentsDatabase.getInstance(context.getApplicationContext());
        return database.getComments(imageId);
    }
}
