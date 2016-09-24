package com.example.arriveathomemedia.Speaker;

import android.app.Application;

import timber.log.Timber;

public class MediaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
