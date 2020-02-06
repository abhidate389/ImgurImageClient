package com.sample.imgurimageclient.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sample.imgurimageclient.repository.ImageApiRepository;

import javax.inject.Inject;

public class ImageViewModelFactory implements ViewModelProvider.Factory {
    private ImageApiRepository imageApiRepository;


    @Inject
    public ImageViewModelFactory(ImageApiRepository imageApiRepository) {
        this.imageApiRepository = imageApiRepository;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ImageViewModel.class)) {
            return (T) new ImageViewModel(imageApiRepository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
