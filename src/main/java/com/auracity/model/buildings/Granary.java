package model.buildings;

import java.util.UUID;
import java.util.*;
import model.agent.Citizen;

public class Granary extends building {
    // Granary-specific properties
    private int storageCapacity;

    public Granary(int max_occupants, int storageCapacity) {
        super(max_occupants, 0, new ArrayList<>());
        this.storageCapacity = storageCapacity;
        this.maintenanceCost = 100; // default value
        this.structuralIntegrity = 100; // default value
        this.occupants = new ArrayList<>(); // Initialize occupants list
    }

    public int getStorageCapacity() { return storageCapacity; }

    public int loadingCapacity() {
        // how foodProduction from farms translates to updates in the granary
        return 0; // Placeholder implementation
    }
