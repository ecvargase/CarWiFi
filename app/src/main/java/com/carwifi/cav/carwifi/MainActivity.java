package com.carwifi.cav.carwifi;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.carwifi.cav.carwifi.ui.Controls;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {


    private Controls controls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        setContentView(R.layout.activity_main);
        initFragments();
    }


    public void initFragments() {
        if (controls == null) {
            controls = Controls.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_fragment_container, controls);
            ft.commit();
        }
    }

}
