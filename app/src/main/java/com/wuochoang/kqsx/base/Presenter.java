package com.wuochoang.kqsx.base;

/**
 * Created by quyenlx on 8/8/2017.
 */

public interface Presenter<T extends BaseView> {
    void onAttachView(T view);

    void onDetach();
}
