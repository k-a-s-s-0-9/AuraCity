package model.buildings;

import java.util.UUID;
import java.util.*;

public class WaterBuffer extends building {
    // WaterBuffer-specific properties
    private UUID id;
    private int max_waterOutput = 100;
    private int max_waterInput = 100;
    private int waterOutput;
    private int waterInput;
    private int waterCapacity = 100; // Example capacity
    private int flowRate = 10; // Example flow rate

    // Constructor
    public WaterBuffer(UUID id, int waterOutput, int waterInput) {
        this.id = id;
        this.waterOutput = waterOutput;
        this.waterInput = waterInput;
    }

    // Getters
    public UUID getId() { return id; }
    public int getWaterOutput() { return waterOutput; }
    public int getWaterInput() { return waterInput; }
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