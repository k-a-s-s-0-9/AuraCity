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
    private int maintenanceCost;

    // Constructor
    public building(int max_capacity) {
        this.id = UUID.randomUUID().toString();
        this.max_capacity = max_capacity;
        this.currentOccupants = 0;
        this.occupants = new ArrayList<>();
        this.structuralIntegrity = 100;
        this.maintenanceCost = 0; // Default value, can be modified
    }

    // Expose the final building id so other objects (e.g. Citizen) can reference it
    public String getId() { return id; }

}