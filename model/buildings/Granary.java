package model.buildings;

import java.util.UUID;
import java.util.*;
import model.agent.Citizen;

public class Granary extends building {
    // Granary-specific properties
    private int storageCapacity;

    public Granary(int max_capacity, int storageCapacity) {
        super(max_capacity);
        this.storageCapacity = storageCapacity;
        maintenanceCost = 100; // default value, can be modified as needed
    }

    public int getStorageCapacity() { return storageCapacity; }
    
}
