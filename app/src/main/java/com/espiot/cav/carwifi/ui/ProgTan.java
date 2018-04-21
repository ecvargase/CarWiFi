package com.espiot.cav.carwifi.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.espiot.cav.carwifi.R;
import com.espiot.cav.carwifi.adapters.InstructionsAdapter;
import com.espiot.cav.carwifi.adapters.holders.InstructionViewHolder;
import com.espiot.cav.carwifi.common.Config;
import com.espiot.cav.carwifi.common.models.InstructionsSet;
import com.espiot.cav.carwifi.common.models.ItemList;
import com.espiot.cav.carwifi.databinding.ProgTanBinding;
import com.espiot.cav.carwifi.eventbus.ItemListeners;
import com.espiot.cav.carwifi.interfaces.CommonInterfaces;
import com.espiot.cav.carwifi.network.Providers;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import timber.log.Timber;

/**
 * Created by camilovargas on 3/03/18.
 */

public class ProgTan extends Fragment implements CommonInterfaces {


    private ProgTanBinding progTan;
    private RecyclerView.Adapter<InstructionViewHolder> adapter;
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
        progTan = DataBindingUtil.inflate(inflater, R.layout.prog_tan, container, false);
        setButtons();
        run();
        delete();
        setRecyclerView();


        return progTan.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onItemListener(ItemListeners itemListeners) {
        Timber.d("Position %s ", itemListeners.getPosition());
        Timber.d("LongClick %s ", itemListeners.isLongClick());

        if (itemListeners.isLongClick()) {
            deleteItem(itemListeners.getPosition());
        }
    }

    public boolean isCompleteInstruction() {
        return hasInstrution && hasPeripheral;
    }

    public void setRecyclerView() {

        items = new ArrayList<ItemList>();
        progTan.listInstructions.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        progTan.listInstructions.setLayoutManager(layoutManager);
        adapter = new InstructionsAdapter(items);
        progTan.listInstructions.setAdapter(adapter);
    }


    public void setButtons() {
        progTan.upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!hasInstrution) {
                    move = Config.UP;
                    hasInstrution = true;
                    validationData();
                    movePeripheral();
                }
            }
        });

        progTan.downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasInstrution) {
                    move = Config.DOWN;
                    hasInstrution = true;
                    validationData();
                    movePeripheral();

                }
            }
        });

        progTan.rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasInstrution) {
                    move = Config.RIGHT;
                    hasInstrution = true;
                    validationData();
                    movePeripheral();

                }
            }
        });

        progTan.leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasInstrution) {
                    move = Config.LEFT;
                    hasInstrution = true;
                    validationData();
                    movePeripheral();

                }
            }
        });

        progTan.onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasInstrution) {
                    move = Config.ON;
                    hasInstrution = true;
                    validationData();
                    movePeripheral();

                }
            }
        });
        progTan.offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasInstrution) {
                    move = Config.OFF;
                    hasInstrution = true;
                    validationData();
                    movePeripheral();

                }
            }
        });

        progTan.ledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPeripheral) {
                    peripheral = "led";
                    type = ItemList.TYPE.LED;
                    hasPeripheral = true;
                    validationData();
                    moveInstruction();
                }
            }
        });

        progTan.moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPeripheral) {
                    peripheral = "move";
                    type = ItemList.TYPE.MOVE;
                    hasPeripheral = true;
                    validationData();
                    moveInstruction();
                }
            }
        });
    }


    private void validationData() {
        if (isCompleteInstruction()) {
            Timber.d("Datos a agregar" + move + peripheral);
            items.add(new ItemList(move, peripheral, type));
            progTan.listInstructions.smoothScrollToPosition(items.size());
            Config.getInstance().setItems(items);
            adapter.notifyDataSetChanged();
            move = null;
            peripheral = null;
            hasInstrution = false;
            hasPeripheral = false;
            moveInstruction();
            movePeripheral();

        }
    }

    public void run() {
        progTan.run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (items.size() > 0) {
                    isVadidDataToSend();

                } else {
                    hasInstrution=false;
                    hasPeripheral=false;
                    moveInstruction();
                    movePeripheral();
                    openDialogSMS("No has agregado ninguna instrucción");
                }
            }
        });
    }

    Animation shakePeripheral;
    Animation shakeInstruction;

    private void movePeripheral() {
        shakePeripheral = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        if (hasInstrution) {
            progTan.embebidoContainer.startAnimation(shakePeripheral);
        } else {
            progTan.embebidoContainer.clearAnimation();

        }
    }

    private void moveInstruction() {
        shakeInstruction = AnimationUtils.loadAnimation(getContext(), R.anim.shake_ins);
        if (hasPeripheral) {
            progTan.lights.startAnimation(shakeInstruction);
            progTan.instructionsMove.startAnimation(shakeInstruction);
        } else {
            progTan.lights.clearAnimation();
            progTan.instructionsMove.clearAnimation();

        }
    }


    public void delete() {

        progTan.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasPeripheral = false;
                hasInstrution = false;
                movePeripheral();
                moveInstruction();
                if (items.size() > 0) {
                    items = new ArrayList<>();
                    adapter = new InstructionsAdapter(items);
                    Config.getInstance().setItems(items);
                    progTan.listInstructions.setAdapter(adapter);
                } else {
                    openDialogSMS("No has agregado ninguna instrucción");
                }
            }
        });
    }


    public boolean isVadidDataToSend() {
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> fail = new ArrayList<>();
        for (ItemList item : items) {
            switch (item.getType()) {
                case LED:
                    String stateLed = item.getInstruction();
                    if (Objects.equals(stateLed, Config.ON) || Objects.equals(stateLed, Config.OFF)) {
                        Timber.d("Valor válido %s", stateLed);
                        data.add(stateLed);
                    } else {
                        fail.add(String.valueOf(items.indexOf(item) + 1));
                    }
                    break;
                case MOVE:
                    String stateMove = item.getInstruction();
                    if (Objects.equals(stateMove, Config.UP) || Objects.equals(stateMove, Config.DOWN)
                            || Objects.equals(stateMove, Config.RIGHT) || Objects.equals(stateMove, Config.LEFT)) {
                        Timber.d("Valor válido %s", stateMove);
                        data.add(stateMove);
                    } else {
                        fail.add(String.valueOf(items.indexOf(item) + 1));
                    }
                    break;
            }
        }

        if (fail.size() > 0) {
            if (fail.size() == 1) {
                openDialogSMS("Oh Oh!\nHay un error en la casilla\n" + Arrays.toString(fail.toArray()));
            } else {
                openDialogSMS("Oh Oh!\nHay un error en las casillas\n" + Arrays.toString(fail.toArray()));
            }
        } else {
            InstructionsSet instructionsSet = new InstructionsSet("", data);
            providers.setInstuctions(instructionsSet);
            items = new ArrayList<>();
            adapter = new InstructionsAdapter(items);
            Config.getInstance().setItems(items);
            progTan.listInstructions.setAdapter(adapter);
        }

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

    @SuppressLint("SetTextI18n")
    void deleteItem(final int item) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialog);
        TextView text = dialog.findViewById(R.id.text_dialog);
        text.setTextSize(24);
        text.setTextColor(Color.RED);
        text.setTypeface(text.getTypeface(), Typeface.BOLD);
        text.setText("Quieres borrar la instrucción\n" + String.valueOf(item + 1) + " ?");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(item);
                adapter.notifyDataSetChanged();
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
