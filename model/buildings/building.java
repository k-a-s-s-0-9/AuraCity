package model.buildings;

import java.util.UUID;
import java.util.*;
import model.agent.Citizen;

public class building {
    // Unique identifier for buildings and its properties
    private final String id;
    private int max_capacity;
    private int currentOccupants;

    //list of citizens currently residing in this housing unit
    private List<Citizen> occupants;

    // structural properties (e.g. durability, maintenance cost) can be added here as needed
    private int structuralIntegrity;
    protected int maintenanceCost;

    // Constructor
    public building(int max_capacity) {
        this.id = UUID.randomUUID().toString();
        this.max_capacity = max_capacity;
        this.currentOccupants = 0;
        this.occupants = new ArrayList<>();
        this.structuralIntegrity = 100;
        this.maintenanceCost = 0; // Default value, can be modified
    }

    public void structuralDegradation() {
        // Simple degradation logic (can be expanded with more complex behavior)
        structuralIntegrity -= 1; // Decrease integrity by 1 unit per time step
        if (structuralIntegrity < 0) {
            structuralIntegrity = 0; // Ensure it doesn't go negative
        }
    }

    // Expose current occupant count for subclasses and external systems
    public int getCurrentOccupants() {
        return currentOccupants;
    }

    // Expose the final building id so other objects (e.g. Citizen) can reference it
    public String getId() { return id; }

}
