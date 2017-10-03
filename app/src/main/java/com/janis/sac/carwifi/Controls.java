package com.janis.sac.carwifi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by camilo on 11/09/17.
 */

public class Controls extends Fragment {

    private ApiService service;
    final String led1 = "on";
    final String led2 = "off";
    boolean curled = false;

    Network network = new Network();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        setupInitialsControls();
        service = network.ledService();
        return inflater.inflate(R.layout.controls, container, false);

    }

    public void setupInitialsControls() {
        getActivity().findViewById(R.id.switching_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        curled = !curled;
                        Log.i("Current Led", String.valueOf(curled));
                        String led;
                        if (curled) {
                            led = led1;
                        } else {
                            led = led2;
                        }
                        Call<String> call = service.getLED(led);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Log.d("LED", "response successful message " + response.message());
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.e("LED", "response failed code , RETROFIT ERROR");
                            }
                        });
                    }
                }
        );

        SeekBar seekBar = (SeekBar) getActivity().findViewById(R.id.seekBar);
        seekBar.setMax(1000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("ProgressChangedStart  ", String.valueOf(progress));
                Call<String> call = service.getPWM(String.valueOf(progress));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("LED", "response successful message " + response.message());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("LED", "response failed code , RETROFIT ERROR");
                    }
                });

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        SeekBar servo = (SeekBar) getActivity().findViewById(R.id.servo);
        servo.setMax(180);
        servo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("ProgressChangedStart  ", String.valueOf(progress));
                Call<String> call = service.getServo(String.valueOf(progress));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("LED", "response successful message " + response.message());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("LED", "response failed code , RETROFIT ERROR");
                    }
                });

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        getActivity().findViewById(R.id.test_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Call<String> call = service.getLED("off");
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Log.d("LED", "response successful message " + response.message());
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.e("LED", "response failed code , RETROFIT ERROR");
                            }
                        });
                    }
                }
        );
    }
}
