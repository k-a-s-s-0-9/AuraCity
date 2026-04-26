package com.auracity.engine;

import java.util.ArrayList;
import java.util.List;

import com.auracity.model.agent.Citizen;
import com.auracity.model.agent.LifeStage;

public class LifecycleManager
implements TimeListener {

    private final PopulationManager populationManager;

    public LifecycleManager(
        PopulationManager populationManager
    ) {

        this.populationManager =
            populationManager;
    }

    @Override
    public void onTimeTick(
        TimeSnapshot t
    ) {

        // Run lifecycle updates once per day
        if (t.newDay()) {

            processLifecycle();
        }
    }

    private void processLifecycle() {

        List<Citizen> dead =
            new ArrayList<>();

        for (
            Citizen citizen :
            populationManager.getCitizens()
        ) {

            // Skip already dead
            if (!citizen.isAlive()) {
                continue;
            }

            // Increase days lived
            citizen.incrementDaysLived();

            // Age every 30 days
            if (citizen.getDaysLived() % 30 == 0) {

                citizen.ageUp();
            }

            // Death logic (only seniors)
            if (
                citizen.getStage() == LifeStage.SENIOR &&
                citizen.getAge() > 75 &&
                Math.random() < 0.05
            ) {

                citizen.die();
                dead.add(citizen);
            }
        }

        // Remove dead citizens safely after loop
        for (Citizen citizen : dead) {

            populationManager.removeCitizen(citizen);
        }
    }
}
