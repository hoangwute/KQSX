package com.wuochoang.kqsx.network.entities;

import com.wuochoang.kqsx.base.BaseView;
import com.olddog.common.ToastUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by TuanJio on 8/11/2017.
 */

public abstract class CallbackWrapper<T extends BaseResult> extends DisposableObserver<T> {

    // BaseView is just a reference of a View in MVP
    private WeakReference<BaseView> weakReference;

    public CallbackWrapper(BaseView view) {
        this.weakReference = new WeakReference<>(view);
    }

    protected abstract void onSuccess(T model);

    @Override
    public void onNext(T t) {
        //You can return StatusCodes of different cases from your API and handle it here. I usually include these cases on BaseResponse and iherit it from every Response
//        int code = t.getCode();
//        if(code == 0) onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        BaseView view = null;
        if(weakReference != null)
            view = weakReference.get();
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            view.onRequestFailure(getErrorMessage(responseBody));
        } else if (e instanceof SocketTimeoutException) {
            ToastUtils.show("Error timeout!");
        } else if (e instanceof IOException) {
            view.onNetworkError();
            ToastUtils.show("Error connect!");
        } else {
            view.onRequestFailure(e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }

    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
