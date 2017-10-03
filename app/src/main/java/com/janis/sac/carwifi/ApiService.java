package com.janis.sac.carwifi;



import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 *
 * Retrofit interface for requests.
 *
 */

public interface ApiService {

    @POST("led")
    Call<String> getLED(@Query("state") String value);

    @POST("pwm")
    Call<String> getPWM(@Query("state") String value);

    @POST("servo")
    Call<String> getServo(@Query("state") String value);
}
