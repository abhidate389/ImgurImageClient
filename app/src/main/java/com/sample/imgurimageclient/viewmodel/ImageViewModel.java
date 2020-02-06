package com.sample.imgurimageclient.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.sample.imgurimageclient.repository.ImageApiRepository;
import com.sample.imgurimageclient.repository.models.Image;
import com.sample.imgurimageclient.repository.network.paging.ImageDataSource;
import com.sample.imgurimageclient.repository.network.paging.ImageDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImageViewModel extends ViewModel {

    private ImageApiRepository imageApiRepository;
    private LiveData<PagedList<Image>> pagedListLiveData;
    private Executor executor;
    private LiveData<ImageListViewState> progressLoadStatus = new MutableLiveData<>();


    public ImageViewModel(ImageApiRepository imageApiRepository) {
        this.imageApiRepository = imageApiRepository;

    }

    /**
     *
     * @param query search query
     *              initiate network call with given query
     */
    public void initNetWorkCall(String query){
        executor = Executors.newFixedThreadPool(5);
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build();

        ImageDataSourceFactory dataSourceFactory = new ImageDataSourceFactory(query, imageApiRepository);
        pagedListLiveData = (new LivePagedListBuilder<Long,Image>(dataSourceFactory,config))
                .setFetchExecutor(executor)
                .build();
        progressLoadStatus = Transformations.switchMap(dataSourceFactory.getMutableLiveData(),ImageDataSource::getProgressLiveStatus);
    }

    /**
     *
     * @return keep track of progress of API call
     */
    public LiveData<ImageListViewState> getProgressLoadStatus() {
        return progressLoadStatus;
    }

    public LiveData<PagedList<Image>> getImages() {
        return pagedListLiveData;
    }


}
