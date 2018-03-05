package com.wuochoang.kqsx.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by TuanJio on 8/11/2017.
 * Bằng cách này, ứng dụng Android của tôi cung cấp một số thông tin
 * về phiên bản hữu ích mà tôi có thể trích xuất từ các tệp nhật ký của máy chủ (Phiên bản Android, tên / loại thiết bị và phiên bản ứng dụng của tôi).
 * For example Dalvik/1.4.0 (Linux; U; Android 2.3.5; HTC Desire HD A9191 Build/GRJ90)
 */

public class UserAgentInterceptor implements Interceptor {

    public final String userAgent;

    public UserAgentInterceptor(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originRequest = chain.request();
        Request requestWithUserAgent = originRequest.newBuilder()
                .header("User-Agent", userAgent)
                .build();
        return chain.proceed(requestWithUserAgent);
    }
}
