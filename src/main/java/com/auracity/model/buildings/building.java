package com.auracity.model.buildings;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import com.auracity.model.agent.Citizen; // Note the 'models' plural to match your bro's files

public abstract class Building {
    
    protected final String id;
    protected final BuildingType type; // The reference to the Blueprint
    
    protected int structuralIntegrity;
    protected int maintenanceCost;
    
    // Occupant tracking
    private int currentOccupants;
    private List<Citizen> occupants;

    // Constructor
    public Building(BuildingType type) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.structuralIntegrity = 100;
        
        // Base maintenance cost off the purchase cost (e.g., 5%)
        this.maintenanceCost = (int)(type.getCost() * 0.05); 
        
        this.currentOccupants = 0;
        this.occupants = new ArrayList<>();
    }

    // --- Core Logic ---

    public void structuralDegradation() {
        structuralIntegrity -= 1;
        if (structuralIntegrity < 0) {
            structuralIntegrity = 0;
        }
    }

    // Calculate actual power usage/generation based on building health
    public double getActualPowerContribution() {
        return type.getPowerRate() * (structuralIntegrity / 100.0);
    }

    // --- Occupant Logic (From your Bro's original code) ---

    public boolean addOccupant(Citizen occupant) {
        if (currentOccupants >= type.getMaxOccupants()) {
            return false;
        }
        occupants.add(occupant);
        currentOccupants = occupants.size();
        return true;
    }

    public boolean removeOccupant(Citizen occupant) {
        if (occupants.remove(occupant)) {
            currentOccupants = occupants.size();
            return true;
        }
        return false;
    }

    public boolean hasSpace() {
        return currentOccupants < type.getMaxOccupants();
    }

    // --- Getters ---

    public String getId() { return id; }
    public BuildingType getType() { return type; }
    public int getStructuralIntegrity() { return structuralIntegrity; }
    public int getMaintenanceCost() { return maintenanceCost; }
    public int getCurrentOccupants() { return currentOccupants; }
    public List<Citizen> getOccupants() { return Collections.unmodifiableList(occupants); }
}