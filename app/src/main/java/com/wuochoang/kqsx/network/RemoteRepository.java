package com.wuochoang.kqsx.network;

import com.wuochoang.kqsx.common.utils.LogUtils;

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by TuanJio on 8/11/2017.
 */

public class RemoteRepository implements RemoteSource {

    private final String UNDELIVERABLE_EXCEPTION_TAG = "Undeliverable exception received, not sure what to do";


    @Override
    public Single logIn() {
        RxJavaPlugins.setErrorHandler(throwable -> {
            LogUtils.i(UNDELIVERABLE_EXCEPTION_TAG + throwable.getMessage());
            return;
        });

//        Single<UserModel> newModelSingle = Single.create(singleOnSubscribe -> {
//
//        });
        return null;
    }
}
