package com.espiot.cav.carwifi.ui;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.espiot.cav.carwifi.R;
import com.espiot.cav.carwifi.adapters.InstructionsAdapter;
import com.espiot.cav.carwifi.common.Config;
import com.espiot.cav.carwifi.common.models.InstructionsSet;
import com.espiot.cav.carwifi.common.models.ItemList;
import com.espiot.cav.carwifi.interfaces.CommonInterfaces;
import com.espiot.cav.carwifi.network.Providers;

import java.util.ArrayList;
import java.util.Objects;

import timber.log.Timber;

/**
 * Created by camilovargas on 3/03/18.
 */

public class ProgTan extends Fragment implements CommonInterfaces {


    private View view;
    private RecyclerView recycler;
    private RecyclerView.Adapter<InstructionsAdapter.InstructionViewHolder> adapter;
    private RecyclerView.LayoutManager lManager;
    private boolean hasInstrution = false;
    private boolean hasPeripheral = false;
    private String move = null;
    private String peripheral = null;
    private ArrayList<ItemList> items = new ArrayList<>();
    private Providers providers = new Providers();
    private ItemList.TYPE type;


    public static ProgTan newInstance() {

        Bundle args = new Bundle();
        ProgTan fragment = new ProgTan();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.prog_tan, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        setButtons();
        run();
        setRecyclerView();
        return view;
    }


    public boolean isCompleteInstruction() {
        return hasInstrution && hasPeripheral;
    }

    public void setRecyclerView() {

        items = new ArrayList<ItemList>();
        recycler = view.findViewById(R.id.list_instructions);
        recycler.setHasFixedSize(true);
        lManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recycler.setLayoutManager(lManager);
        adapter = new InstructionsAdapter(items);
        recycler.setAdapter(adapter);
    }


    public void setButtons() {
        view.findViewById(R.id.up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!hasInstrution) {
                    move = Config.UP;
                    hasInstrution = true;
                    validationData();
                }
            }
        });

        view.findViewById(R.id.down_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasInstrution) {
                    move = Config.DOWN;
                    hasInstrution = true;
                    validationData();
                }
            }
        });

        view.findViewById(R.id.right_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasInstrution) {
                    move = Config.RIGHT;
                    hasInstrution = true;
                    validationData();
                }
            }
        });

        view.findViewById(R.id.left_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasInstrution) {
                    move = Config.LEFT;
                    hasInstrution = true;
                    validationData();
                }
            }
        });

        view.findViewById(R.id.on_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasInstrution) {
                    move = Config.ON;
                    hasInstrution = true;
                    validationData();
                }
            }
        });
        view.findViewById(R.id.off_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasInstrution) {
                    move = Config.OFF;
                    hasInstrution = true;
                    validationData();
                }
            }
        });

        view.findViewById(R.id.led_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPeripheral) {
                    peripheral = "led";
                    type = ItemList.TYPE.LED;
                    hasPeripheral = true;
                    validationData();
                }
            }
        });

        view.findViewById(R.id.move_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPeripheral) {
                    peripheral = "move";
                    type = ItemList.TYPE.MOVE;
                    hasPeripheral = true;
                    validationData();
                }
            }
        });
    }

    private void validationData() {
        if (isCompleteInstruction()) {
            Timber.d("Datos a agregar" + move + peripheral);
            items.add(new ItemList(move, peripheral, type));

            adapter = new InstructionsAdapter(items);
            recycler.setAdapter(adapter);
            move = null;
            peripheral = null;
            hasInstrution = false;
            hasPeripheral = false;
        }
    }

    public void run() {
        view.findViewById(R.id.run).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (items.size() > 0) {
                    isVadidDataToSend();

                    items = new ArrayList<>();
                    adapter = new InstructionsAdapter(items);
                    recycler.setAdapter(adapter);
                } else {
                    openDialogSMS("No has agregado ninguna instrucción");
                }
            }
        });
    }


    public boolean isVadidDataToSend() {
        ArrayList<String> data = new ArrayList<String>();
        for (ItemList item : items) {
            switch (item.getType()) {
                case LED:
                    String stateLed = item.getInstruction();
                    if (Objects.equals(stateLed, Config.ON) || Objects.equals(stateLed, Config.OFF)) {
                        Timber.d("Valor válido %s", stateLed);
                        data.add(stateLed);
                    } else {
                        openDialogSMS("El Led no puede moverse hacía " + stateLed);
                        return false;
                    }
                    break;
                case MOVE:
                    String stateMove = item.getInstruction();
                    if (Objects.equals(stateMove, Config.UP) || Objects.equals(stateMove, Config.DOWN)
                            || Objects.equals(stateMove, Config.RIGHT) || Objects.equals(stateMove, Config.LEFT)) {
                        Timber.d("Valor válido %s", stateMove);
                        data.add(stateMove);
                    } else {
                        openDialogSMS("El carro no puede " + stateMove);
                        return false;

                    }
                    break;
            }
        }
        InstructionsSet instructionsSet = new InstructionsSet("", data);
        providers.setInstuctions(instructionsSet);
        return true;

    }


    void openDialogSMS(String value) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialog);
        TextView text = dialog.findViewById(R.id.text_dialog);
        text.setText(value);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public void onClick(View view, int position, boolean isLongClick) {
      Timber.d(String.valueOf(position));
    }
}
