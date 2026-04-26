package com.auracity.model.agent;

import com.auracity.engine.TimeSnapshot;

public class SleepingState
implements CitizenState {

    @Override
    public void handle(
        Citizen citizen,
        TimeSnapshot t
    ) {

        citizen.recover();

        if (t.hour() == 8) {

            citizen.setState(
                new CommutingState()
            );
        }
    }

    @Override
    public String getName() {
        return "SLEEPING";
    }
}
