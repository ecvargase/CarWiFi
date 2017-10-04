package com.janis.sac.carwifi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.controlwear.virtual.joystick.android.JoystickView;
import me.rorschach.library.ShaderSeekArc;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by camilo on 11/09/17.
 */

public class Controls extends Fragment {

    private ApiService service;


    Network network = new Network();
    private View view;

    public static Controls newInstance() {
        Bundle args = new Bundle();
        Controls fragment = new Controls();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.controls, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        service = network.ledService();
        setupJoyStickSpeed();
        setupJoyStickDirection();
        return view;

    }

    private void setupJoyStickDirection() {
        JoystickView direction = (JoystickView) view.findViewById(R.id.joys);
        direction.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {

                if (angle == 0 && strength == 0) {
                    setupServo(90);
                }
                if (0 < angle && angle <= 180) {
                    setupServo(angle);
                }
                if (180 < angle && angle <= 359) {
                    int currentAngle = 180 - (angle - 180);
                    setupServo(currentAngle);
                }
                Log.d("Strength : ", String.valueOf(strength));
                Log.d("angle : ", String.valueOf(angle));
            }
        });
    }

    private void setupJoyStickSpeed() {
        ShaderSeekArc seekArc = (ShaderSeekArc) view.findViewById(R.id.seek_arc);
        seekArc.setStartValue(0);
        seekArc.setEndValue(100);
        seekArc.setProgress(0);
        seekArc.setOnSeekArcChangeListener(new ShaderSeekArc.OnSeekArcChangeListener() {

            @Override
            public void onProgressChanged(ShaderSeekArc seekArc, float progress) {

                int currentProgress = (int) progress;
                Log.d("progress : ", String.valueOf(currentProgress));
                setupPWM(currentProgress * 10);

            }

            @Override
            public void onStartTrackingTouch(ShaderSeekArc seekArc) {
            }

            @Override
            public void onStopTrackingTouch(ShaderSeekArc seekArc) {
                setupPWM(0);
//                seekArc.setProgress(0);

            }
        });
    }


    public void setupServo(int progress) {

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


    public void setupPWM(int progress) {

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
}
