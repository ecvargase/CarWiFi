package com.espiot.cav.carwifi.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.espiot.cav.carwifi.R;
import com.espiot.cav.carwifi.common.models.ItemList;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import timber.log.Timber;

/**
 * Created by camilovargas on 3/03/18.
 */

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.InstructionViewHolder> {
    private List<ItemList> items;

    public static class InstructionViewHolder extends RecyclerView.ViewHolder {
        public ImageView instruction;
        public ImageView peripherial;
        public ImageView valid;

        public InstructionViewHolder(View v) {
            super(v);
            instruction = (ImageView) v.findViewById(R.id.instruction_card);
            peripherial = (ImageView) v.findViewById(R.id.peripheral_card);
            valid = (ImageView) v.findViewById(R.id.valid);
        }
    }

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
            case "up":
                Picasso.with(viewHolder.instruction.getContext())
                        .load(R.drawable.flecha_up)
                        .resize(80, 80)
                        .into(viewHolder.instruction);
                break;
            case "left":
                Picasso.with(viewHolder.instruction.getContext())
                        .load(R.drawable.flecha_left)
                        .resize(80, 80)
                        .into(viewHolder.instruction);
                break;
            case "right":
                Picasso.with(viewHolder.instruction.getContext())
                        .load(R.drawable.flecha_right)
                        .resize(80, 80)
                        .into(viewHolder.instruction);
                break;
            case "down":
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
                if (Objects.equals(stateMove, "up") || Objects.equals(stateMove, "down")
                        || Objects.equals(stateMove, "right") || Objects.equals(stateMove, "left")) {
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