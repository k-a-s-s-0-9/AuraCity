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
        maintenanceCost = 100; // default value, can be modified as needed
    }

    public int getFoodProduction() { return foodProduction; }
    
}
