package com.espiot.cav.carwifi.common;

import com.espiot.cav.carwifi.common.models.ItemList;

import java.util.ArrayList;

/**
 * Created by camilovargas on 10/4/17.
 */

public class Config {

    public static String baseURL = "http://192.168.4.1/";
    public static String DOWN = "DOWN";
    public static String UP = "UP";
    public static String LEFT = "LEFT";
    public static String RIGHT = "RIGHT";
    public static String ON = "ON";
    public static String OFF = "OFF";
    private static Config instance;

    private static ArrayList<ItemList> items = new ArrayList<>();

    public ArrayList<ItemList> getItems() {
        return items;
    }

    public static Config getInstance() {
        if (instance == null) {
            return new Config();
        } else {
            return instance;
        }
    }


    public void setItems(ArrayList<ItemList> items) {
        this.items = items;
    }
}
