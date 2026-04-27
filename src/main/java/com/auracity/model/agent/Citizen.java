package com.auracity.models.agents;

import com.auracity.engine.TimeListener;
import com.auracity.engine.TimeSnapshot;
import com.auracity.engine.ResourceManager;

public class Citizen implements TimeListener {

    private final String name;
    private final int homeId;
    private final int workId;

    private int age;
    private int daysLived;

    private boolean alive;
    private LifeStage stage;

    private double stamina;
    private double happiness;
    private double wallet;

    private final ResourceManager resources;

    private CitizenState currentState;

    public Citizen(
        String name,
        int homeId,
        int workId,
        ResourceManager resources
    ) {

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
    }

    @Override
    public void onTimeTick(TimeSnapshot t) {

        if (alive) {
            currentState.handle(this, t);
        }
    }

    // -------- Lifecycle --------

    public void incrementDaysLived() {
        daysLived++;
    }

    public void ageUp() {
        age++;
        if (age >= 65) {
            stage = LifeStage.SENIOR;
        }
    }

    public void die() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getAge() {
        return age;
    }

    public int getDaysLived() {
        return daysLived;
    }

    public LifeStage getStage() {
        return stage;
    }

    // -------- State --------

    public void setState(CitizenState state) {
        this.currentState = state;
    }

    public CitizenState getState() {
        return currentState;
    }

    // -------- Economy --------

    public void addMoney(double amount) {
        wallet += amount;
    }

    public void withdrawMoney(double amount) {
        wallet -= amount;
        if (wallet < 0) {
            wallet = 0;
        }
    }

    public double getWallet() {
        return wallet;
    }

    // -------- Happiness --------

    public void setHappiness(double value) {
        happiness = value;
    }

    public double getHappiness() {
        return happiness;
    }

    // -------- Behavior --------

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

    public String getName() {
        return name;
    }

    public int getHomeId() {
        return homeId;
    }

    public int getWorkId() {
        return workId;
    }

    public ResourceManager getResources() {
        return resources;
    }
}
