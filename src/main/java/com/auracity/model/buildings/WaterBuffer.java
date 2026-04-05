package com.auracity.model.buildings;

import com.auracity.model.agent.Citizen;
import java.util.*;

public class WaterBuffer extends building {
    // WaterBuffer-specific properties
    private int max_waterOutflow = 100;
    private int max_waterInflow = 100;
    private int waterOutflow;
    private int waterInflow;
    private int waterCapacity = 100; // Example capacity
    private int flowRate = 10; // Example flow rate

    // Constructor
    public WaterBuffer(int max_occupants, int currentOccupants, List<Citizen> occupants) throws OverFlowException, UnderflowException {
        super(max_occupants, currentOccupants, occupants);
        this.waterOutflow = 0;
        this.waterInflow = 0;
    }

    // Getters
    public int getWaterOutflow() { return waterOutflow; }
    public int getWaterInflow() { return waterInflow; }
    public int getWaterCapacity() { return waterCapacity; }
    public int getFlowRate() { return flowRate; }
}

class OverFlowException extends Exception {
    public OverFlowException() {
        super("Water output exceeds maximum capacity");
    }
}

class UnderflowException extends Exception {
    public UnderflowException() {
        super("Water input is below minimum required");
    }
}