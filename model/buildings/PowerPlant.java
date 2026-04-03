package model.buildings;
import java.util.UUID;
import java.util.*;
import model.agent.Citizen;

public class PowerPlant extends building {
    // PowerPlant-specific properties
    private int powerOutput;

    public PowerPlant(int max_capacity, int powerOutput) {
        super(max_capacity);
        this.powerOutput = powerOutput;
        maintenanceCost = 100; // default value, can be modified as needed
    }

    public int getPowerOutput() { return powerOutput; }
    
}
