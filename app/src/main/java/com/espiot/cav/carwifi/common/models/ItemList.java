package com.espiot.cav.carwifi.common.models;

/**
 * Created by camilovargas on 3/03/18.
 */

public class ItemList {
    private String instruction;
    private String peripheral;
    private TYPE type;


    public enum TYPE {
        MOVE,
        LED
    }


    public ItemList(String instruction, String peripheral, TYPE type) {
        this.instruction = instruction;
        this.peripheral = peripheral;
        this.type = type;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getPeripheral() {
        return peripheral;
    }

    public void setPeripheral(String peripheral) {
        this.peripheral = peripheral;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }
}
