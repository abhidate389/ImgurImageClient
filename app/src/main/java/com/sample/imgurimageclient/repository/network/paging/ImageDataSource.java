package com.sample.imgurimageclient.repository.network.paging;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.sample.imgurimageclient.repository.models.Image;
import com.sample.imgurimageclient.repository.models.ImageDataList;
import com.sample.imgurimageclient.repository.models.Images;
import com.sample.imgurimageclient.utilities.Constants;
import com.sample.imgurimageclient.viewmodel.ImageListViewState;
import com.sample.imgurimageclient.repository.ImageApiRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * PageKeyedDataSource with Images
 */
public class ImageDataSource extends PageKeyedDataSource<Integer, Image> {

    public ImageApiRepository imageApiRepository;
    private final int page = 1;
    private String query;
    private MutableLiveData<ImageListViewState> progressLiveStatus;


    public ImageDataSource(String query, ImageApiRepository imageApiRepository) {

        this.imageApiRepository = imageApiRepository;
        this.query = query;
        progressLiveStatus = new MutableLiveData<>();
    }

    public MutableLiveData<ImageListViewState> getProgressLiveStatus() {
        return progressLiveStatus;
    }

    /**
     *
     * @param params
     * @param callback
     * It will load first page with given query
     */
    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Image> callback) {

        imageApiRepository.getImages(query,page)
                .doOnSubscribe( disposable -> {
                    progressLiveStatus.postValue(ImageListViewState.LOADING_STATE);
                })
                .subscribe(
                        (ImageDataList result) ->
                        {
                            List<Image> imageList = new ArrayList<>();
                            if(result.getImages() != null){

                                for(Images images : result.getImages()) {
                                    //if album then it has multiple images
                                    if(images.isAlbum() && images.getImageList() != null){
                                        for(Image image : images.getImageList()){
                                            /**
                                             * checking type of image and add only image/jpeg type
                                             */
                                            if(image.getType().equalsIgnoreCase(Constants.IMAGE_TYPE)){
                                                image.setTitle(images.getTitle());
                                                imageList.add(image);
                                            }
                                        }
                                    }
                                    else {
                                        /**
                                         *  //if no album then used id to make images after ward
                                         */
                                        Image image = new Image();
                                        image.setImageId(images.getId());
                                        image.setTitle(images.getTitle());
                                        imageList.add(image);
                                    }

                                }
                            }
                            progressLiveStatus.postValue(ImageListViewState.SUCCESS_STATE);
                            callback.onResult(imageList,null,page + 1);

                        },
                        throwable ->
                                progressLiveStatus.postValue(ImageListViewState.ERROR_STATE)
                );

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Image> callback) {

    }

    /**
     *
     * @param params
     * @param callback
     * It will load next pages with given query
     */
    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Image> callback) {

        imageApiRepository.getImages(query,params.key)
                .doOnSubscribe( disposable -> {
                    progressLiveStatus.postValue(ImageListViewState.LOADING_STATE);
                })
                .subscribe(
                        (ImageDataList result) ->
                        {

                            List<Image> imageList = new ArrayList<>();
                            if(result.getImages() != null){

                                for(Images images : result.getImages()) {
                                    //if album then it has multiple images
                                    if(images.isAlbum() && images.getImageList() != null){
                                        for(Image image : images.getImageList()){
                                            if(image.getType().equalsIgnoreCase(Constants.IMAGE_TYPE)){
                                                image.setTitle(images.getTitle());
                                                imageList.add(image);
                                            }
                                        }
                                    }
                                    else {
                                        Image image = new Image();
                                        image.setImageId(images.getId());
                                        image.setTitle(images.getTitle());
                                        imageList.add(image);
                                    }

                                }
                            }
                            progressLiveStatus.postValue(ImageListViewState.SUCCESS_STATE);
                            callback.onResult(imageList,params.key + 1);

                        },
                        throwable ->
                                progressLiveStatus.postValue(ImageListViewState.ERROR_STATE)
                );

    }
}
