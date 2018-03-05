package com.wuochoang.kqsx.base;

import com.wuochoang.kqsx.network.ApiService;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by quyenlx on 8/8/2017.
 */

public abstract class BasePresenter<T extends BaseView> implements Presenter<T> {
    public BasePresenter(ApiService apiService) {
        this.apiService = apiService;
    }
    @Inject
    protected ApiService apiService;
    private T view;
    private CompositeDisposable disposable;

    @Override
    public void onAttachView(T view) {
        this.view = view;
        disposable = new CompositeDisposable();
    }

    @Override
    public void onDetach() {
        view = null;
        disposable.dispose();
    }

    public T getView() {
        return view;
    }

    public CompositeDisposable getDisposable() {
        if(disposable == null || disposable.isDisposed()){
            disposable = new CompositeDisposable();
        }
        return disposable;
    }

    protected void disposeCall(){
        if(disposable != null && disposable.size() > 0 && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    protected void showLoading(){
        if(getView() != null) getView().showLoading();
    }

    protected void hideLoading(){
        if(getView() != null) getView().hideLoading();
    }

    private void showNetworkError(){
        if(getView() != null) getView().onNetworkError();
    }
}
