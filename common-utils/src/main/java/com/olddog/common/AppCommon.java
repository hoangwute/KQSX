package com.olddog.common;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import timber.log.Timber;

/**
 * Created by QuyenLx on 7/20/2017.
 */

public class AppCommon extends Application {
    private static AppCommon appCommon;
    private String accessToken;

    public static synchronized AppCommon get() {
        return appCommon;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appCommon = this;
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
