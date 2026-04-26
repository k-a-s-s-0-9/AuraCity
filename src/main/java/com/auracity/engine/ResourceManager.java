package com.auracity.engine;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

// Observer Interface
interface ResourceObserver {

    void onResourceChanged(
        String resourceName,
        double surplus
    );
}

public class ResourceManager
implements TimeListener {

    private final Map<String, Double>
        supplies;

    private final Map<String, Double>
        demands;

    private final List<ResourceObserver>
        observers;

    public ResourceManager() {

        supplies =
            new ConcurrentHashMap<>();

        demands =
            new ConcurrentHashMap<>();

        observers =
            new CopyOnWriteArrayList<>();

        // Initial values
        supplies.put("Power", 0.0);
        supplies.put("Water", 0.0);
        supplies.put("Food", 0.0);
        supplies.put("Money", 1000.0);

        demands.put("Power", 0.0);
        demands.put("Water", 0.0);
        demands.put("Food", 0.0);
    }

    public void addObserver(
        ResourceObserver observer
    ) {
        observers.add(observer);
    }

    public void updateSupply(
        String resource,
        double amount
    ) {

        supplies.merge(
            resource,
            amount,
            Double::sum
        );

        notifyObservers(resource);
    }

    public void updateDemand(
        String resource,
        double amount
    ) {

        demands.merge(
            resource,
            amount,
            Double::sum
        );

        notifyObservers(resource);
    }

    private void notifyObservers(
        String resource
    ) {

        double surplus =

            supplies.getOrDefault(resource, 0.0)

            -

            demands.getOrDefault(resource, 0.0);

        for (ResourceObserver obs : observers) {

            obs.onResourceChanged(
                resource,
                surplus
            );
        }
    }

    public double getResourceSurplus(
        String resource
    ) {

        return

            supplies.getOrDefault(resource, 0.0)
            -
            demands.getOrDefault(resource, 0.0);
    }
    @Override
    public void onTimeTick(
        TimeSnapshot t
    ) {
        // Trigger updates once per hour
        if (t.newHour()) {

            notifyObservers("Power");
            notifyObservers("Water");
            notifyObservers("Food");
            notifyObservers("Money");
        }
    }
}
