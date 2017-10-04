package com.espiot.cav.carwifi.network;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 *  * Created by camilovargas on 3/10/17.
 *  * Retrofit interface for requests.
 */

interface ApiService {

    @POST("reverse")
    Observable<String> getReverse(@Query("state") String value);

    @POST("pwm")
    Observable<String> getPWM(@Query("state") String value);

    @POST("servo")
    Observable<String> getServo(@Query("state") String value);
}