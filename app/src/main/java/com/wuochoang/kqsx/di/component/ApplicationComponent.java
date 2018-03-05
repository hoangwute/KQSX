package com.wuochoang.kqsx.di.component;

import com.wuochoang.kqsx.di.module.ActivityModule;
import com.wuochoang.kqsx.di.module.ApplicationModule;
import com.wuochoang.kqsx.di.module.NetworkModule;
import com.wuochoang.kqsx.di.module.StorageModule;
import com.wuochoang.kqsx.network.MyServiceInterceptor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by quyenlx on 8/9/2017.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        StorageModule.class
})
public interface ApplicationComponent {
    ActivityComponent plus(ActivityModule module);

    void inject(MyServiceInterceptor myServiceInterceptor);

}
