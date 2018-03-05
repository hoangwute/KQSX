package com.wuochoang.kqsx.common;

import android.os.Build;
import android.provider.Settings;

import com.olddog.common.AppCommon;

// BaseAndroid - Config
//
// Created by Vin on 4/18/17.
// Copyright (c) 2017 Ominext. All rights reserved.
//
public class Config {

    public static final boolean DEBUG = true;

    public static final int K_ENV_DEV = 0;
    public static final int K_ENV_TEST = 1;
    public static final int K_ENV_STAGING = 2;
    public static final int K_ENV_PRODUCTION = 3;

    //TODO change ENVIRONMENT
    public static final int K_BUILD = K_ENV_DEV;

    public static final String SERVER_DEV_URL = "http://api.hrm-dev.ominext.co/";
    public static final String SERVER_TEST_URL = "http://api.hrm-dev.ominext.co/";
    public static final String SERVER_STAGING_URL = "http://api.hrm-dev.ominext.co/";
    public static final String SERVER_PRODUCTION_URL = "http://api.hrm-dev.ominext.co/";
    public static final String BASIC_AUTHOR_USERNAME = "omihrm";
    public static final String BASIC_AUTHOR_PASSWORD = "ominext2017";

    public static String SERVER_URL = SERVER_DEV_URL;

    public static void configBuild() {
        switch (K_BUILD) {
            case K_ENV_DEV:
                SERVER_URL = SERVER_DEV_URL;
                break;
            case K_ENV_TEST:
                SERVER_URL = SERVER_TEST_URL;
                break;
            case K_ENV_STAGING:
                SERVER_URL = SERVER_STAGING_URL;
                break;
            case K_ENV_PRODUCTION:
                SERVER_URL = SERVER_PRODUCTION_URL;
                break;
        }
    }

    public static String getDeviceCode() {
        return Settings.Secure.getString(AppCommon.get().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    //-- Name device = Manufact + model
    public static String getNameDevice() {
        return Build.MANUFACTURER + Build.MODEL;
    }
}
