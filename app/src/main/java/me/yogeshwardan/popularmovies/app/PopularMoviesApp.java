package me.yogeshwardan.popularmovies.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.BuildConfig;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;

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

        final Context context=this;
        Stetho.initialize(Stetho.newInitializerBuilder(context)
                .enableDumpapp(new DumperPluginsProvider() {
                    @Override
                    public Iterable<DumperPlugin> get() {
                        return new Stetho.DefaultDumperPluginsBuilder(context).finish();
                    }
                })
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

}
