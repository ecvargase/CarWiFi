package com.espiot.cav.carwifi;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.espiot.cav.carwifi.interfaces.CommonInterfaces;
import com.espiot.cav.carwifi.ui.Controls;
import com.espiot.cav.carwifi.ui.ProgTan;

import timber.log.Timber;

/**
 * Created by camilovargas on 11/09/17.
 */

public class MainActivity extends AppCompatActivity implements CommonInterfaces {

    private Controls controls;
    private ProgTan progTan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        setContentView(R.layout.activity_main);
        initFragments();
    }

    public void initFragments() {

        if (progTan == null) {
            progTan = ProgTan.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_fragment_container, progTan);
            ft.commit();
        }
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        Timber.d("jhagsdjhasdha");
    }
}
