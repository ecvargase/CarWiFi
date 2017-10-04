package com.espiot.cav.carwifi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.espiot.cav.carwifi.R;
import com.espiot.cav.carwifi.network.Providers;

import io.github.controlwear.virtual.joystick.android.JoystickView;
import me.rorschach.library.ShaderSeekArc;
import timber.log.Timber;

/**
 * Created by camilovargas on 11/09/17.
 */

public class Controls extends Fragment {

    private View view;
    private ToggleButton toggle;
    private JoystickView direction;
    private ShaderSeekArc seekArc;
    private Providers providers = new Providers();

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

        setupJoyStickSpeed();
        setupJoyStickDirection();
        setupStateDirection();
        return view;
    }

    public void setupStateDirection() {
        if (toggle == null) {
            toggle = (ToggleButton) view.findViewById(R.id.toggleButton);
        }
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (seekArc.getProgress() > 0) {
                    seekArc.setProgress(0L);
                    providers.setupPWM(0);
                    Timber.d("Set progress 0");
                }
                if (isChecked) {
                    Timber.d(String.format("Reverse %s", String.valueOf(true)));
                    providers.setupReverse(String.valueOf(true));
                } else {
                    providers.setupReverse(String.valueOf(false));
                    Timber.d(String.format("Reverse %s", String.valueOf(false)));
                }
            }
        });
    }

    private void setupJoyStickDirection() {
        if (direction == null) {
            direction = (JoystickView) view.findViewById(R.id.joys);
        }
        direction.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {

                if (angle == 0 && strength == 0) {
                    providers.setupServo(90);
                }
                if (0 < angle && angle <= 180) {
                    providers.setupServo(angle);
                }
                if (180 < angle && angle <= 359) {
                    int currentAngle = 180 - (angle - 180);
                    providers.setupServo(currentAngle);
                }
                Timber.d(String.format("Strength : %s", String.valueOf(strength)));
                Timber.d(String.format("angle : %s", String.valueOf(angle)));
            }
        });
    }

    private void setupJoyStickSpeed() {
        if (seekArc == null) {
            seekArc = (ShaderSeekArc) view.findViewById(R.id.seek_arc);
        }
        seekArc.setStartValue(0);
        seekArc.setEndValue(100);
        seekArc.setProgress(0);
        seekArc.setOnSeekArcChangeListener(new ShaderSeekArc.OnSeekArcChangeListener() {

            @Override
            public void onProgressChanged(ShaderSeekArc seekArc, float progress) {

                int currentProgress = (int) progress;
                Timber.d(String.format("progress : %s", String.valueOf(currentProgress)));
                providers.setupPWM(currentProgress * 10);

            }

            @Override
            public void onStartTrackingTouch(ShaderSeekArc seekArc) {
            }

            @Override
            public void onStopTrackingTouch(ShaderSeekArc seekArc) {
                providers.setupPWM(0);
                seekArc.setProgress(0);

            }
        });
    }

}
