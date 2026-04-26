package com.auracity.model.agent;

import com.auracity.engine.TimeListener;
import com.auracity.engine.TimeSnapshot;
import com.auracity.engine.ResourceManager;
import com.auracity.model.buildings.Building;

public class Citizen implements TimeListener {

    private final String name;
    private final String homeId;
    private final String workId;

    private int age;
    private int daysLived;

    private boolean alive;
    private LifeStage stage;

    private double stamina;
    private double happiness;
    private double wallet;

    private final ResourceManager resources;
    private CitizenState currentState;

    // Movement Variables
    private double x, y;
    private double targetX, targetY;
    private final double SPEED = 2.0;

    public Citizen(String name, String homeId, String workId, ResourceManager resources) {
        this.name = name;
        this.homeId = homeId;
        this.workId = workId;
        this.resources = resources;

        this.age = 18;
        this.daysLived = 0;
        this.alive = true;
        this.stage = LifeStage.ADULT;

        this.stamina = 1.0;
        this.happiness = 1.0;
        this.wallet = 0.0;
        this.currentState = new SleepingState();

        // Initialize Movement Location
        Building home = Building.REGISTRY.get(homeId);
        if (home != null) {
            this.x = (home.getGridX() * 64) + 32;
            this.y = (home.getGridY() * 64) + 32;
            this.targetX = this.x;
            this.targetY = this.y;
        }
    }

    @Override
    public void onTimeTick(TimeSnapshot t) {
        if (alive) {
            currentState.handle(this, t);
        }
    }

    // -------- Movement --------

    public void setTargetBuilding(String buildingId) {
        Building b = Building.REGISTRY.get(buildingId);
        if (b != null) {
            this.targetX = (b.getGridX() * 64) + 32;
            this.targetY = (b.getGridY() * 64) + 32;
        }
    }

    public void updateMovement() {
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > SPEED) {
            x += (dx / distance) * SPEED;
            y += (dy / distance) * SPEED;
        } else {
            x = targetX;
            y = targetY;
        }
    }

    public double getX() { return x; }
    public double getY() { return y; }

    // -------- Lifecycle --------

    public void incrementDaysLived() { daysLived++; }
    public void ageUp() {
        age++;
        if (age >= 65) stage = LifeStage.SENIOR;
    }
    public void die() { alive = false; }
    public boolean isAlive() { return alive; }
    public int getAge() { return age; }
    public int getDaysLived() { return daysLived; }
    public LifeStage getStage() { return stage; }

    // -------- State & Economy --------

    public void setState(CitizenState state) { this.currentState = state; }
    public CitizenState getState() { return currentState; }

    public void addMoney(double amount) { wallet += amount; }
    public void withdrawMoney(double amount) {
        wallet -= amount;
        if (wallet < 0) wallet = 0;
    }
    public double getWallet() { return wallet; }

    // -------- Happiness & Behavior --------

    public void setHappiness(double value) { happiness = value; }
    public double getHappiness() { return happiness; }

    public void recover() {
        stamina += 0.05;
        if (stamina > 1.0) stamina = 1.0;
    }
    public void work() {
        stamina -= 0.03;
        if (stamina < 0) stamina = 0;
    }
    public void relax() {
        happiness += 0.02;
        if (happiness > 1.0) happiness = 1.0;
    }

    // -------- Getters --------

    public String getName() { return name; }
    public String getHomeId() { return homeId; }
    public String getWorkId() { return workId; }
    public ResourceManager getResources() { return resources; }
}