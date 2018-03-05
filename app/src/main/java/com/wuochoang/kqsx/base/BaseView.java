package com.wuochoang.kqsx.base;

/**
 * Created by quyenlx on 8/8/2017.
 */

public interface BaseView {
    void addFragment(BaseFragment fragment);

    void showLoading();

    void hideLoading();

    void hideKeyboard();

    void onRequestFailure(String message);

    void onNetworkError();
}
