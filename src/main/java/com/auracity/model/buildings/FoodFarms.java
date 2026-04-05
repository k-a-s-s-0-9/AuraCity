package model.buildings;

import java.util.UUID;
import java.util.*;
import model.agent.Citizen;

public class FoodFarms extends building {
    // Farms-specific properties
    private int foodProduction;

    public FoodFarms(int max_capacity, int foodProduction) {
        super(max_capacity);
        this.foodProduction = foodProduction;
        maintenanceCost = 100; // default value
    }

    public int getFoodProduction() { 
        // Total production scales with the number of workers currently assigned to this building
        int workers = getCurrentOccupants();
        return foodProduction * Math.max(0, workers);
    }
    
}
