package com.auracity.model.buildings;

import java.util.*;
import com.auracity.model.agent.Citizen;

public class Granary extends building {
    // Granary-specific properties
    private int storageCapacity;

    // Constructor
    public Granary(int max_occupants, int storageCapacity, List<Citizen> occupants, int structuralIntegrity, int maintenanceCost) {
        super(max_occupants, structuralIntegrity, occupants);
        this.storageCapacity = storageCapacity;
        this.maintenanceCost = maintenanceCost;
    }

    public int getStorageCapacity() { return storageCapacity; }
    public int getMaintenanceCost() { return maintenanceCost; }
    public int loadingCapacity() {
        // how foodProduction from farms translates to updates in the granary
        return 0; // Placeholder implementation
    }
}