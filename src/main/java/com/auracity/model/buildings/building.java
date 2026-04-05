package com.auracity.model.buildings;

import java.util.UUID;
import java.util.*;
import com.auracity.model.agent.Citizen;

public class building {
    // Unique identifier for buildings and its properties
    protected final String id;
    private int max_occupants;
    private int currentOccupants;

    //list of citizens currently residing in this housing unit
    private List<Citizen> occupants;

    // structural properties (e.g. durability, maintenance cost) can be added here as needed
    private int structuralIntegrity;
    protected int maintenanceCost;

    // Constructor
    public building(int max_occupants, int currentOccupants, List<Citizen> occupants) {
        this.id = UUID.randomUUID().toString();
        this.max_occupants = max_occupants;
        this.currentOccupants = currentOccupants;
        this.occupants = new ArrayList<>(occupants);
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

    // Manage occupants and keep the current occupant count in sync with the list
    public boolean addOccupant(Citizen occupant) {
        if (currentOccupants >= max_occupants) {
            return false;
        }
        else {
            occupants.add(occupant);
            currentOccupants = occupants.size();
            return true; // Successfully added occupant
        }
    }

    public boolean removeOccupant(Citizen occupant) {
        if (occupants.remove(occupant)) {
            currentOccupants = occupants.size();
            return true;
        }
        return false;
    }

    public List<Citizen> getOccupants() {
        return Collections.unmodifiableList(occupants);
    }

    // Expose current occupant count for subclasses and external systems
    public int getCurrentOccupants() {
        return currentOccupants;
    }

    // Expose the maximum capacity of the building
    public int getMaxCapacity() {
        return max_occupants;
    }

    // Expose the final building id so other objects (e.g. Citizen) can reference it
    public String getId() { return id; }

    public int getStructuralIntegrity() { return structuralIntegrity; }
    
    public int getMaintenanceCost() { return maintenanceCost; }

}
