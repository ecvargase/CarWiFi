package com.espiot.cav.carwifi;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.espiot.cav.carwifi.common.Config;
import com.espiot.cav.carwifi.common.models.ItemList;
import com.espiot.cav.carwifi.databinding.ActivityMainBinding;
import com.espiot.cav.carwifi.interfaces.CommonInterfaces;
import com.espiot.cav.carwifi.ui.ProgTan;

import java.util.Arrays;

import timber.log.Timber;

/**
 * Created by camilovargas on 11/09/17.
 */

public class MainActivity extends AppCompatActivity {

    private ProgTan progTan;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setMain(this);
        setConfig();
        initFragments();
    }

    public void setConfig() {
        activityMainBinding.textTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Config.getInstance().getItems() != null) {
                    String listView = "";
                    for (ItemList items : Config.getInstance().getItems()) {
                        String action = "";
                        String actuator = "";
                        if (items.getInstruction().equals(Config.DOWN))
                            action = " Hacia atrás";
                        if (items.getInstruction().equals(Config.LEFT))
                            action = " Hacia la izquierda";
                        if (items.getInstruction().equals(Config.RIGHT))
                            action = " Hacia la derecha";
                        if (items.getInstruction().equals(Config.UP))
                            action = " Hacia adelante";
                        if (items.getInstruction().equals(Config.OFF))
                            action = " Quitar la luz";
                        if (items.getInstruction().equals(Config.ON))
                            action = " Alumbrar";
                        if (items.getPeripheral().equals("led"))
                            actuator = "Bombillos ";
                        if (items.getPeripheral().equals("move"))
                            actuator = " Carrito ";
                        listView = listView + actuator + " : " + action + "\n\n";
                    }
                    Timber.i("Listado : %s", listView);
                    activityMainBinding.instructionText.setText(listView);
                }
            }
        });

    }

    public void initFragments() {

        if (progTan == null) {
            progTan = ProgTan.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_fragment_container, progTan);
            ft.commit();
        }
    }

}
