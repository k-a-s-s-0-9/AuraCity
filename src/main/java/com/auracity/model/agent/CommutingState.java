package com.auracity.model.agent;

import com.auracity.engine.TimeSnapshot;

public class CommutingState implements CitizenState {

    @Override
    public void handle(
        Citizen citizen,
        TimeSnapshot t
    ) {
        if (t.hour() >= 9) {

            citizen.setState(
                new WorkingState()
            );
        }
        citizen.setTargetBuilding(citizen.getWorkId());
        if (t.hour() >= 9) {
            citizen.setState(new WorkingState());
        }
    }

    @Override
    public String getName() {
        return "COMMUTING";
    }
}
