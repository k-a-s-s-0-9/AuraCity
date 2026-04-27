package com.auracity.model.buildings;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import com.auracity.model.agent.Citizen;

public abstract class Building {
    
    public static final Map<String, Building> REGISTRY = new ConcurrentHashMap<>();
    private int gridX;
    private int gridY;

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

        Building.REGISTRY.put(this.id, this);
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
    
    public void setLocation(int x, int y) {
    this.gridX = x;
    this.gridY = y;
    }
    public int getGridX() { return gridX; }
    public int getGridY() { return gridY; }
}