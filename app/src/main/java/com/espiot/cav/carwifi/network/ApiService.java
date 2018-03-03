package com.espiot.cav.carwifi.network;

import com.espiot.cav.carwifi.common.models.InstructionsSet;
import com.espiot.cav.carwifi.common.models.ItemList;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * * Created by camilovargas on 3/10/17.
 * * Retrofit interface for requests.
 */

interface ApiService {

    @POST("reverse")
    Observable<String> getReverse(@Query("state") String value);

    @POST("pwm")
    Observable<String> getPWM(@Query("state") String value);

    @POST("servo")
    Observable<String> getServo(@Query("state") String value);

    @POST("api/v1/instructionsSet")
    Observable<String> getInstructions(@Body InstructionsSet instructionsSet);
}