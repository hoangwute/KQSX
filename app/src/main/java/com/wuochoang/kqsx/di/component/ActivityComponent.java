package com.wuochoang.kqsx.di.component;

import com.wuochoang.kqsx.di.module.ActivityModule;
import com.wuochoang.kqsx.ui.home.view.MainFragment;

import dagger.Subcomponent;

/**
 * Created by quyenlx on 8/9/2017.
 */

@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainFragment mainFragment);
}
