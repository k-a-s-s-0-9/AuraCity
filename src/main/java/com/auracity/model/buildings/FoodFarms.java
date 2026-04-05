package com.auracity.model.buildings;

import java.util.*;

public class FoodFarms extends building {
    // Farms-specific properties
    private int foodProduction;

    public FoodFarms(int max_occupants, int foodProduction) {
        super(max_occupants, 0, new ArrayList<>()); // Initialize with 0 occupants and an empty list
        this.foodProduction = foodProduction;
        maintenanceCost = 100; // default value
    }

    public int getFoodProduction() { 
        // Total production scales with the number of workers currently assigned to this building
        int workers = getCurrentOccupants();
        return foodProduction * Math.max(0, workers);
    }
    
}
