package model.buildings;

import java.util.UUID;
import java.util.*;
import model.agent.Citizen;

class housing extends building {
    // Housing-specific properties
    private int rentCost;

    public housing(int max_capacity, int rentCost) {
        super(max_capacity, currentOccupants);
        this.rentCost = rentCost;
        maintenanceCost = 100; // default value, can be modified as needed
    }

    public int getRentCost() { return rentCost; }
}