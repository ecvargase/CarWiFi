package com.carwifi.cav.carwifi.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by camilovargas on 3/10/17.
 */

public class Providers {

    private Network network = new Network();
    private ApiService service = network.apiServices();


    public void setupPWM(int progress) {

        Call<String> call = service.getPWM(String.valueOf(progress));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Timber.d(String.format("response successful message %s", response.message()));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Timber.e("response failed code , RETROFIT ERROR");
            }
        });
    }

    public void setupReverse(String state) {

        Call<String> call = service.getReverse(String.valueOf(state));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Timber.d(String.format("response successful message %s", response.message()));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Timber.e("response failed code , RETROFIT ERROR");
            }
        });
    }


    public void setupServo(int progress) {

        Call<String> call = service.getServo(String.valueOf(progress));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Timber.d(String.format("response successful message %s", response.message()));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Timber.e("response failed code , RETROFIT ERROR");
            }
        });
    }

}
