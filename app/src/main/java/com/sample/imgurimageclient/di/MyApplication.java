package com.sample.imgurimageclient.di;

import android.app.Application;

public class MyApplication extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate(){
        super.onCreate();
        appComponent = DaggerAppComponent.builder().utilsModule(new UtilsModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
