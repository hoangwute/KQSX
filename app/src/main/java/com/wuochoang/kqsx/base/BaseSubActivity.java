package com.wuochoang.kqsx.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.wuochoang.kqsx.BuildConfig;

/**
 * Created by quyenlx on 8/9/2017.
 */

public class BaseSubActivity extends BaseActivity {
    public static final String EXTRA_FRAGMENT_CLASS = BuildConfig.APPLICATION_ID + ".EXTRA_FRAGMENT_CLASS";
    public static final String EXTRA_FRAGMENT_ARGS = BuildConfig.APPLICATION_ID + ".EXTRA_FRAGMENT_ARGS";
    private BaseFragment fragment;

    public static Intent createIntent(Context context) {
        return new Intent(context, BaseSubActivity.class);
    }

    public static void start(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    public static void startForResult(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public BaseFragment initFragment() {
        Bundle extras = getIntent().getExtras();
        if (extras == null)
            return null;
        Class<? extends BaseFragment> clazz
                = (Class<? extends BaseFragment>) extras.getSerializable(EXTRA_FRAGMENT_CLASS);
        if (clazz == null)
            return null;

        try {
            Constructor c = clazz.getConstructor();
            try {
                fragment = (BaseFragment) c.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Bundle args = extras.getBundle(EXTRA_FRAGMENT_ARGS);
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

}
