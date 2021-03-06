package com.espiot.cav.carwifi.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.espiot.cav.carwifi.R;
import com.espiot.cav.carwifi.interfaces.CommonInterfaces;

/**
 * Created by camilovargas on 15/03/18.
 */

public class InstructionViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener, View.OnLongClickListener {
    public ImageView instruction;
    public ImageView peripherial;
    public ImageView valid;
    public TextView count;
    public RelativeLayout continer;
    private CommonInterfaces commonInterfaces;

    public InstructionViewHolder(View v) {
        super(v);
        continer = v.findViewById(R.id.general_container_instruction);
        instruction = v.findViewById(R.id.instruction_card);
        peripherial = v.findViewById(R.id.peripheral_card);
        valid = v.findViewById(R.id.valid);
        count = v.findViewById(R.id.counter);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
    }

    public void setItemClickListener(CommonInterfaces clickListener) {
        this.commonInterfaces = clickListener;
    }

    @Override
    public void onClick(View view) {
        commonInterfaces.onClick(view, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View view) {
        commonInterfaces.onClick(view, getAdapterPosition(), true);
        return true;
    }
}