package com.auracity.models.agents;

import com.auracity.engine.TimeSnapshot;

public class RecreationState
implements CitizenState {

    @Override
    public void handle(
        Citizen citizen,
        TimeSnapshot t
    ) {

        citizen.relax();

        if (t.hour() >= 22) {

            citizen.setState(
                new SleepingState()
            );
        }
    }

    @Override
    public String getName() {
        return "RECREATION";
    }
}
