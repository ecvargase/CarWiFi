package com.espiot.cav.carwifi.eventbus;

/**
 * Created by camilovargas on 9/04/18.
 */

public class ItemListeners {

    private int position;
    private boolean isLongClick;

    public ItemListeners(int position, boolean isLongClick){
        this.position = position;
        this.isLongClick = isLongClick;
    }

    public int getPosition() {
        return position;
    }

    public boolean isLongClick() {
        return isLongClick;
    }
}
