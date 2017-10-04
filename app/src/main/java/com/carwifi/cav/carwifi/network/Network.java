package com.carwifi.cav.carwifi.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by camilo on 11/09/17.
 */

public class Network {

    public void setupOkhttpBuilder(OkHttpClient.Builder builder) {
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

    public ApiService apiServices() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        setupOkhttpBuilder(builder);
        OkHttpClient client = builder.build();
        Retrofit.Builder retrofitBuilder = new Retrofit
                .Builder()
                .baseUrl("http://192.168.4.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);
        Retrofit retrofit = retrofitBuilder
                .build();
        return retrofit.create(ApiService.class);
    }

}
