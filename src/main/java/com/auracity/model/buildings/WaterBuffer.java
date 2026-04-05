package model.buildings;

import java.util.UUID;
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
    public WaterBuffer(UUID id, int waterOutflow, int waterInflow) throws OverFlowException, UnderflowException {
        this.id = id;
        this.waterOutflow = waterOutflow;
        this.waterInflow = waterInflow;
    }

    // Getters
    public UUID getId() { return id; }
    public int getWaterOutflow() { return waterOutflow; }
    public int getWaterInflow() { return waterInflow; }
    public int getWaterCapacity() { return waterCapacity; }
    public int getFlowRate() { return flowRate; }
}

public class OverFlowException extends Exception {
    public OverFlowException() {
        super("Water output exceeds maximum capacity");
    }
}

public class UnderflowException extends Exception {
    public UnderflowException() {
        super("Water input is below minimum required");
    }
}