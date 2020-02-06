package com.sample.imgurimageclient.di;

import com.sample.imgurimageclient.view.ImageListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {UtilsModule.class})
@Singleton
public interface AppComponent  {

    void doInjection(ImageListFragment imageListFragment);
}
