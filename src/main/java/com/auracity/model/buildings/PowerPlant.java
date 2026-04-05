package com.auracity.model.buildings;

import java.util.*;

public class PowerPlant extends building {
    // PowerPlant-specific properties
    private int powerOutput;

    public PowerPlant(int max_occupants, int powerOutput) {
        super(max_occupants, 100, new ArrayList<>());
        this.powerOutput = powerOutput;
        maintenanceCost = 100; // default value, can be modified as needed
    }

    public int getPowerOutput() { return powerOutput; }
    
}
