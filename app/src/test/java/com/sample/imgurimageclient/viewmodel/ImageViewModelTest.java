package com.sample.imgurimageclient.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.paging.PageKeyedDataSource;

import com.sample.imgurimageclient.repository.ImageApiRepository;
import com.sample.imgurimageclient.repository.models.Image;
import com.sample.imgurimageclient.repository.models.ImageDataList;
import com.sample.imgurimageclient.repository.network.paging.ImageDataSource;
import com.sample.imgurimageclient.repository.network.paging.ImageDataSourceFactory;
import com.sample.imgurimageclient.utilities.Constants;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
public class ImageViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    ImageApiRepository imageApiRepository;

    private ImageViewModel imageViewModel;

    @Mock
    Observer<ImageListViewState> observer;

    @Mock
    LifecycleOwner lifecycleOwner;
    @Mock
    ImageDataSourceFactory imageDataSourceFactory;
    @Mock
    ImageDataSource imageDataSource;

    Lifecycle lifecycle;

    @Mock
    PageKeyedDataSource.LoadInitialParams<Integer> params;
    @Mock
    PageKeyedDataSource.LoadInitialCallback<Integer, Image> callback;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        imageViewModel = new ImageViewModel(imageApiRepository);
        imageDataSourceFactory = new ImageDataSourceFactory(Constants.TESTING_QUERY, imageApiRepository);

        imageDataSource = new ImageDataSource(Constants.TESTING_QUERY, imageApiRepository);
        imageDataSource.getProgressLiveStatus().observeForever(observer);
    }

    @Test
    public void testNull() {
        when(imageApiRepository.getImages(Constants.TESTING_QUERY,1)).thenReturn(null);
        assertNotNull(imageViewModel.getProgressLoadStatus());
        assertTrue(imageDataSource.getProgressLiveStatus().hasObservers());
    }

    /**
     * test if API can fetch image data successfully
     */
    @Test
    public void testApiImageFetchDataSuccess() {
        // Mock API response
        when(imageApiRepository.getImages(Constants.TESTING_QUERY,1)).thenReturn(Observable.just(new ImageDataList()));
        imageDataSource.loadInitial(params,callback);
        verify(observer).onChanged(ImageListViewState.LOADING_STATE);
        verify(observer).onChanged(ImageListViewState.SUCCESS_STATE);
    }

    /**
     * test if API cannot fetch image data successfully
     */
    @Test
    public void testApiImageFetchDataError() {

        when(imageApiRepository.getImages(Constants.TESTING_QUERY,1)).thenReturn(Observable.error(new Throwable("Api error")));
        imageDataSource.loadInitial(params,callback);
        verify(observer).onChanged(ImageListViewState.LOADING_STATE);
        verify(observer).onChanged(ImageListViewState.ERROR_STATE);
    }

    @After
    public void tearDown() throws Exception {
        imageApiRepository = null;
        imageViewModel = null;
    }
}
