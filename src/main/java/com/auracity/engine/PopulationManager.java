package com.auracity.engine;

import java.util.ArrayList;
import java.util.List;

import com.auracity.model.agent.Citizen;
import com.auracity.model.buildings.Housing; // CHANGED: Replaced ResidentialBuilding with Housing

public class PopulationManager implements TimeListener {

    private final List<Citizen> allCitizens;
    private final List<Housing> homes; // CHANGED: Now uses the Housing class
    private final ResourceManager resources;
    private int citizenCounter;

    public PopulationManager(ResourceManager resources) {
        this.resources = resources;
        this.allCitizens = new ArrayList<>();
        this.homes = new ArrayList<>();
        this.citizenCounter = 1;
    }

    public void addHome(Housing home) { // CHANGED: Parameter updated
        homes.add(home);
    }

    @Override
    public void onTimeTick(TimeSnapshot t) {
        // Spawn only once per day
        if (t.newDay()) {
            spawnCitizens();
        }
        for (Citizen c : allCitizens) {
            c.onTimeTick(t);
        }
    }

    private void spawnCitizens() {
        for (Housing home : homes) {
            
            // Using the standard methods inherited from Building.java
            if (home.hasSpace()) {
                
                Citizen citizen = new Citizen(
                    "Citizen-" + citizenCounter,
                    home.getId(), // CHANGED: Was getBuildingId(), now uses standard UUID String
                    "UNEMPLOYED", // CHANGED: Was an integer '1', must be a String now
                    resources
                );

                citizenCounter++;
                
                home.addOccupant(citizen); // CHANGED: Was addResident(), now uses standard method
                allCitizens.add(citizen);
            }
        }
    }

    public void removeCitizen(Citizen citizen) {
        allCitizens.remove(citizen);
    }

    public List<Citizen> getCitizens() {
        return allCitizens;
    }
}