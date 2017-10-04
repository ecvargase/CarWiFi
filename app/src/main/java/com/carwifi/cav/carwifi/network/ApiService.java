package com.carwifi.cav.carwifi.network;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 *  * Created by camilovargas on 3/10/17.
 * Retrofit interface for requests.
 */

public interface ApiService {

    @POST("reverse")
    Call<String> getReverse(@Query("state") String value);

    @POST("pwm")
    Call<String> getPWM(@Query("state") String value);

    @POST("servo")
    Call<String> getServo(@Query("state") String value);
}