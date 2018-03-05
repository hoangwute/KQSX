package com.wuochoang.kqsx.common;

import android.content.Intent;
import android.os.Bundle;

import com.wuochoang.kqsx.base.BaseActivity;
import com.wuochoang.kqsx.base.BaseSubActivity;

import javax.inject.Inject;

/**
 * Created by quyenlx on 8/9/2017.
 */

public class Router {
    private BaseActivity activity;

    @Inject
    public Router(BaseActivity activity) {
        this.activity = activity;
    }



    private void start(Class<?> classOf) {
        Intent intent = BaseSubActivity.createIntent(activity);
        intent.putExtra(BaseSubActivity.EXTRA_FRAGMENT_CLASS, classOf);
        BaseSubActivity.start(activity, intent);
    }

    private void start(Class<?> classOf, Bundle bundle) {
        Intent intent = BaseSubActivity.createIntent(activity);
        intent.putExtra(BaseSubActivity.EXTRA_FRAGMENT_CLASS, classOf);
        intent.putExtra(BaseSubActivity.EXTRA_FRAGMENT_ARGS, bundle);
        BaseSubActivity.start(activity, intent);
    }

    private void startFoResult(Class<?> classOf, int requestCode) {
        Intent intent = BaseSubActivity.createIntent(activity);
        intent.putExtra(BaseSubActivity.EXTRA_FRAGMENT_CLASS, classOf);
        BaseSubActivity.startForResult(activity, intent, requestCode);
    }

    private void startFoResult(Class<?> classOf, Bundle bundle, int requestCode) {
        Intent intent = BaseSubActivity.createIntent(activity);
        intent.putExtra(BaseSubActivity.EXTRA_FRAGMENT_CLASS, classOf);
        intent.putExtra(BaseSubActivity.EXTRA_FRAGMENT_ARGS, bundle);
        BaseSubActivity.startForResult(activity, intent, requestCode);
    }
}
