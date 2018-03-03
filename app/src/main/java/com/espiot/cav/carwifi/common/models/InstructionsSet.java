package com.espiot.cav.carwifi.common.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by camilovargas on 3/03/18.
 */

public class InstructionsSet {

    @SerializedName("sessionID")
    private String sessionId;

    @SerializedName("instructions")
    private ArrayList<String> instructions;

    public InstructionsSet(String sessionId, ArrayList<String> instructions) {

        this.sessionId = sessionId;
        this.instructions = instructions;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }
}
