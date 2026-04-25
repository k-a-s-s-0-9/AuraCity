package com.auracity.engine;

public class TestListener
implements TimeListener {

    @Override
    public void onTimeTick(
        TimeSnapshot t
    ) {

        if (t.newHour()) {

            System.out.println(
                "Time: Day " +
                t.day() +
                " " +
                String.format(
                    "%02d:%02d",
                    t.hour(),
                    t.minutes() % 60
                )
            );
        }

        if (t.newDay()) {

            System.out.println(
                "New Day Started: Day " +
                t.day()
            );
        }

        if (t.hour() == 8) {

            System.out.println(
                "Work begins"
            );
        }

        if (t.hour() == 20) {

            System.out.println(
                "Go home"
            );
        }
    }
}
