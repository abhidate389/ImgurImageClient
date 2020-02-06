package com.sample.imgurimageclient.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.sample.imgurimageclient.repository.ImagesRepository;
import com.sample.imgurimageclient.repository.models.Image;
import com.sample.imgurimageclient.repository.models.ImageComment;
import com.sample.imgurimageclient.utilities.AppExecutors;

public class ImageDetailViewModel extends AndroidViewModel {

    final private MutableLiveData image;
    private ImagesRepository imagesRepository;
    private Context context;

    public ImageDetailViewModel(Application application) {
        super(application);
        image = new MutableLiveData<Image>();
        imagesRepository = ImagesRepository.getInstance();
        this.context = application.getApplicationContext();

    }
    public MutableLiveData<Image> getImage() {
        return image;
    }

    /**
     *
     * @param imageComment
     * Add image comment to database by calling image repo
     */
    public void addImage(ImageComment imageComment){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                imagesRepository.insertComment(context,imageComment);
            }
        });
    }

    public LiveData<PagedList<ImageComment>> getComments(String imageId) {
        return imagesRepository.getComments(context,imageId);
    }

}
