package com.wuochoang.kqsx.di.module;

import android.app.Application;

import com.wuochoang.kqsx.network.ApiService;
import com.wuochoang.kqsx.network.MyServiceInterceptor;
import com.wuochoang.kqsx.network.UserAgentInterceptor;
import com.wuochoang.kqsx.network.verifier.NullHostNameVerifier;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.wuochoang.kqsx.BuildConfig;

/**
 * Created by quyenlx on 8/9/2017.
 */

@Module
public class NetworkModule {

    //Network constants
    private final int TIMEOUT_CONNECT = 120;   //In seconds
    private final int TIMEOUT_READ = 120;   //In seconds
    private final int TIMEOUT_WRITE = 120;   //In seconds


    @Singleton
    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();
    }

    @Provides
    public Cache provideCache(Application app) {
        long cacheSize = 10 * 1024 * 1024;
        return new Cache(app.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        HttpLoggingInterceptor.Level level;
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY;
        } else {
            level = HttpLoggingInterceptor.Level.NONE;
        }
        return logging.setLevel(level);
    }

//    @Provides
//    @Singleton
//    public Interceptor provideInterceptor() {
//        return chain -> {
//            Request request = chain.request().newBuilder()
//                    .addHeader("Authorization", UserModel.TOKEN)
//                    .build();
//            return chain.proceed(request);
//        };
//    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor logging, MyServiceInterceptor interceptor) {
        //1----------------------------------------------------
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};

        //2----------------------------------------------------
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new UserAgentInterceptor(System.getProperty("http.agent")));
        builder.addInterceptor(logging);
        builder.addInterceptor(interceptor);
        builder.readTimeout(TIMEOUT_READ, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS);
        builder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS);

        //3----------------------------------------------------
        //Trust HTTPS
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();
            builder.hostnameVerifier(new NullHostNameVerifier());
            builder.sslSocketFactory(sslSocketFactory);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_DEV)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
