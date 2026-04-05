package com.auracity.model.buildings;

import java.util.*;

public class Housing extends building {
    // Housing-specific properties
    private int rentCost;
    private String homeId;

    // Constructor
    public Housing(int max_occupants, int rentCost) {
        super(max_occupants, 0, new ArrayList<>());

        // Initialize buildingID as homeId for citizens to reference when they move in
        this.homeId = this.id;
        this.homeId = getId(); // Set homeId to the unique building ID

        this.rentCost = rentCost;
        maintenanceCost = 100; // default value, can be modified as needed
    }

    public int getRentCost() { return rentCost; }
    public String getHomeId() { return homeId; }
    
}