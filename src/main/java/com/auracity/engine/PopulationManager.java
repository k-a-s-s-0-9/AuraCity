package com.auracity.engine;

import java.util.ArrayList;
import java.util.List;

import com.auracity.model.agent.Citizen;
import com.auracity.model.buildings.*;;

public class PopulationManager
implements TimeListener {

    private final List<Citizen> allCitizens;

    private final List<ResidentialBuilding> homes;

    private final ResourceManager resources;

    private int citizenCounter;

    public PopulationManager(
        ResourceManager resources
    ) {

        this.resources = resources;

        this.allCitizens = new ArrayList<>();

        this.homes = new ArrayList<>();

        this.citizenCounter = 1;
    }

    public void addHome(
        ResidentialBuilding home
    ) {

        homes.add(home);
    }

    @Override
    public void onTimeTick(
        TimeSnapshot t
    ) {

        // Spawn only once per day
        if (t.newDay()) {

            spawnCitizens();
        }
    }

    private void spawnCitizens() {

        for (
            ResidentialBuilding home
            : homes
        ) {

            if (home.hasSpace()) {

                Citizen citizen =
                    new Citizen(

                        "Citizen-" + citizenCounter,

                        home.getBuildingId(),

                        1, // default workplace (can improve later)

                        resources
                    );

                citizenCounter++;

                home.addResident(citizen);

                allCitizens.add(citizen);
            }
        }
    }

    public void removeCitizen(
        Citizen citizen
    ) {

        allCitizens.remove(citizen);
    }

    public List<Citizen> getCitizens() {

        return allCitizens;
    }

    public int getPopulation() {

        return allCitizens.size();
    }
}
