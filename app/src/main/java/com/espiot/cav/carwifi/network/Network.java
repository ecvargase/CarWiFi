package com.espiot.cav.carwifi.network;

import com.espiot.cav.carwifi.common.Config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by camilovargas on 11/09/17.
 */

class Network {

    private void setupOkhttpBuilder(OkHttpClient.Builder builder) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Timber.i(String.format("OkHttp --> %s", message));
                    }
                });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
    }

    ApiService apiServices() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        setupOkhttpBuilder(builder);
        OkHttpClient client = builder.build();
        Retrofit.Builder retrofitBuilder = new Retrofit
                .Builder()
                .baseUrl(Config.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client);
        Retrofit retrofit = retrofitBuilder
                .build();
        return retrofit.create(ApiService.class);
    }

}
