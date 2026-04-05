package engine;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Observer Interface
interface ResourceObserver {
    void onResourceChanged(String resourceName, double surplus);
}

public class ResourceManager {
    
    // We use a ConcurrentHashMap because the UI might read these stats while the tick thread is updating them.
    private final Map<String, Double> supplies = new ConcurrentHashMap<>();
    private final Map<String, Double> demands = new ConcurrentHashMap<>();
    
    private final List<ResourceObserver> observers = new ArrayList<>();

    public ResourceManager() {
        // Initialize the Quadrant Resource System
        supplies.put("Power", 0.0);
        supplies.put("Water", 0.0);
        supplies.put("Food", 0.0);
        supplies.put("Money", 1000.0); // Starting capital
        
        demands.put("Power", 0.0);
        demands.put("Water", 0.0);
        demands.put("Food", 0.0);
    }

    public synchronized void addObserver(ResourceObserver observer) {
        observers.add(observer);
    }

    // Called by buildings to register their output/needs
    public void updateSupply(String resource, double amount) {
        supplies.merge(resource, amount, Double::sum);
        notifyObservers(resource);
    }

    public void updateDemand(String resource, double amount) {
        demands.merge(resource, amount, Double::sum);
        notifyObservers(resource);
    }

    private void notifyObservers(String resource) {
        // Calculate Surplus/Deficit
        double surplus = supplies.getOrDefault(resource, 0.0) - demands.getOrDefault(resource, 0.0);
        
        for (ResourceObserver obs : observers) {
            obs.onResourceChanged(resource, surplus);
        }
    }

    public double getResourceSurplus(String resource) {
        return supplies.getOrDefault(resource, 0.0) - demands.getOrDefault(resource, 0.0);
    }
}