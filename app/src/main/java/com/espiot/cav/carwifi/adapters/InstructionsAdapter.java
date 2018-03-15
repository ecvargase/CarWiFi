package com.espiot.cav.carwifi.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.espiot.cav.carwifi.R;
import com.espiot.cav.carwifi.adapters.holders.InstructionViewHolder;
import com.espiot.cav.carwifi.common.models.ItemList;
import com.espiot.cav.carwifi.interfaces.CommonInterfaces;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import timber.log.Timber;

/**
 * Created by camilovargas on 3/03/18.
 */

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionViewHolder> {
    private List<ItemList> items;

    public InstructionsAdapter(List<ItemList> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public InstructionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.holder_instructions, viewGroup, false);
        return new InstructionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(InstructionViewHolder viewHolder, int i) {
        viewHolder.setItemClickListener(new CommonInterfaces() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    Toast.makeText(view.getContext(), "Instrucción: " + items.get(position).getInstruction()
                                    + ", periférico: " + items.get(position).getPeripheral(),
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(view.getContext(), "Eliminar Instrucción //TODO", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

        setViewHolder(viewHolder, i);
    }


    private void setViewHolder(InstructionViewHolder viewHolder, int i) {
        switch (items.get(i).getInstruction()) {
            case "ON":
                Picasso.with(viewHolder.instruction.getContext())
                        .load(R.drawable.led)
                        .resize(80, 80)
                        .into(viewHolder.instruction);
                break;
            case "OFF":
                Picasso.with(viewHolder.instruction.getContext())
                        .load(R.drawable.led_off)
                        .resize(80, 80)
                        .into(viewHolder.instruction);
                break;
            case "UP":
                Picasso.with(viewHolder.instruction.getContext())
                        .load(R.drawable.flecha_up)
                        .resize(80, 80)
                        .into(viewHolder.instruction);
                break;
            case "LEFT":
                Picasso.with(viewHolder.instruction.getContext())
                        .load(R.drawable.flecha_left)
                        .resize(80, 80)
                        .into(viewHolder.instruction);
                break;
            case "RIGHT":
                Picasso.with(viewHolder.instruction.getContext())
                        .load(R.drawable.flecha_right)
                        .resize(80, 80)
                        .into(viewHolder.instruction);
                break;
            case "DOWN":
                Picasso.with(viewHolder.instruction.getContext())
                        .load(R.drawable.flecha_down)
                        .resize(80, 80)
                        .into(viewHolder.instruction);
                break;
        }
        switch (items.get(i).getPeripheral()) {
            case "led":
                Picasso.with(viewHolder.peripherial.getContext())
                        .load(R.drawable.led)
                        .resize(80, 80)
                        .into(viewHolder.peripherial);
                break;
            case "move":
                Picasso.with(viewHolder.peripherial.getContext())
                        .load(R.drawable.motor)
                        .resize(120, 60)
                        .into(viewHolder.peripherial);
                break;
        }

        switch (items.get(i).getType()) {
            case LED:
                String stateLed = items.get(i).getInstruction();
                if (Objects.equals(stateLed, "ON") || Objects.equals(stateLed, "OFF")) {
                    Timber.d("Valor válido %s", stateLed);
                    Picasso.with(viewHolder.valid.getContext())
                            .load(R.drawable.ok)
                            .resize(20, 20)
                            .into(viewHolder.valid);
                } else {
                    Picasso.with(viewHolder.valid.getContext())
                            .load(R.drawable.wrong)
                            .resize(20, 20)
                            .into(viewHolder.valid);
                }
                break;
            case MOVE:
                String stateMove = items.get(i).getInstruction();
                if (Objects.equals(stateMove, "UP") || Objects.equals(stateMove, "DOWN")
                        || Objects.equals(stateMove, "RIGHT") || Objects.equals(stateMove, "LEFT")) {
                    Picasso.with(viewHolder.valid.getContext())
                            .load(R.drawable.ok)
                            .resize(20, 20)
                            .into(viewHolder.valid);
                } else {
                    Picasso.with(viewHolder.valid.getContext())
                            .load(R.drawable.wrong)
                            .resize(20, 20)
                            .into(viewHolder.valid);
                }
                break;
        }
    }
}