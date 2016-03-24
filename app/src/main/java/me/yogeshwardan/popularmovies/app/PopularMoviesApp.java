package me.yogeshwardan.popularmovies.app;

import android.app.Application;
import android.support.v4.BuildConfig;

import timber.log.Timber;

/**
 * Created by yogeshwardancharan on 24/3/16.
 */
public class PopularMoviesApp extends Application {

    @Override public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

}
