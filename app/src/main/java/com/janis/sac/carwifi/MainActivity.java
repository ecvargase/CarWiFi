package com.janis.sac.carwifi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import io.github.controlwear.virtual.joystick.android.JoystickView;
import me.rorschach.library.ShaderSeekArc;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    private ApiService service;
    Network network = new Network();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = network.ledService();
        setupJoyStickSpeed();
        setupJoyStickDirection();

    }

    private void setupJoyStickDirection() {
        JoystickView direction = (JoystickView) findViewById(R.id.joys);
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
        ShaderSeekArc seekArc = (ShaderSeekArc) findViewById(R.id.seek_arc);
        seekArc.setStartValue(0);
        seekArc.setEndValue(100);
        seekArc.setProgress(0);
        seekArc.setOnSeekArcChangeListener(new ShaderSeekArc.OnSeekArcChangeListener() {

            @Override
            public void onProgressChanged(ShaderSeekArc seekArc, float progress) {

                int currentProgress = (int) progress;
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
