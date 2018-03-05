package com.wuochoang.kqsx.network;

import android.util.Base64;

import com.wuochoang.kqsx.App;
import com.wuochoang.kqsx.common.Config;
import com.wuochoang.kqsx.common.Constant;
import com.wuochoang.kqsx.common.utils.LogUtils;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import com.wuochoang.kqsx.BuildConfig;

/**
 * Created by quyenlx on 8/9/2017.
 */

public class MyServiceInterceptor implements Interceptor {
    private static final String NO_AUTH_HEADER_KEY = "No-Authentication";
    private final String CONTENT_TYPE = "Content-Type";
    private final String CONTENT_TYPE_VALUE = "application/json";
    private final String API_KEY = "apikey";
    private final String API_KEY_VALUE = "0bac85d8945140b3bc8dde8aff16e329";

    /*@Headers(No-Authentication: true)*/
    private String sessionToken;

    @Inject
    public MyServiceInterceptor() {

    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        requestBuilder.method(request.method(), request.body());

        if (request.header(NO_AUTH_HEADER_KEY) == null) {
            // needs credentials
            // Nếu không cần login thì sẽ dùng basic addInterceptorAuthentication
            // Các Api cần Token thì sẽ cần Bearer Authentication
            sessionToken = App.get().getAccessToken();
            if (sessionToken != null) {
                requestBuilder.addHeader("Authorization", "Bearer " + sessionToken);
            } else {
                requestBuilder.addHeader("Authorization", encodeCredentialsForBasicAuthorization());
            }
            requestBuilder.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
            requestBuilder.addHeader("device_code", Config.getDeviceCode());
            requestBuilder.addHeader("name", Config.getNameDevice());
            requestBuilder.addHeader("platform", Constant.PLATFORM_DEVICE);
            requestBuilder.addHeader("version", BuildConfig.VERSION_NAME);
        }

        return chain.proceed(requestBuilder.build());
    }

    public static String encodeCredentialsForBasicAuthorization() {
        final String userAndPassword = Config.BASIC_AUTHOR_USERNAME + ":" + Config.BASIC_AUTHOR_PASSWORD;
        LogUtils.i("Basic " + Base64.encodeToString(userAndPassword.getBytes(), Base64.NO_WRAP));
        return "Basic " + Base64.encodeToString(userAndPassword.getBytes(), Base64.NO_WRAP);
    }
}