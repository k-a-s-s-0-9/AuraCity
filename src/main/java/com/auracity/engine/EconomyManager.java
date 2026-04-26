package com.auracity.engine;

import com.auracity.model.agent.Citizen;
import com.auracity.model.agent.WorkingState;
import com.auracity.model.buildings.*;;

public class EconomyManager
implements TimeListener {

    private final PopulationManager populationManager;

    private final Bank bank;

    private final ResourceManager resources;

    private double cityTreasury;

    private static final double SALARY = 100.0;

    private static final double TAX_RATE = 0.20;

    private static final double RENT = 20.0;

    private static final double FOOD_COST = 10.0;

    public EconomyManager(
        PopulationManager populationManager,
        Bank bank,
        ResourceManager resources
    ) {

        this.populationManager = populationManager;
        this.bank = bank;
        this.resources = resources;

        this.cityTreasury = 0;
    }

    @Override
    public void onTimeTick(
        TimeSnapshot t
    ) {

        if (t.newHour()) {
            processIncome();
        }

        if (t.newDay()) {
            processExpenses();
        }
    }

    // ---------------- Income (Step 5A) ----------------

    private void processIncome() {

        for (Citizen citizen : populationManager.getCitizens()) {

            if (citizen.getState() instanceof WorkingState) {

                double effectiveSalary =
                    SALARY * citizen.getHappiness();

                double tax =
                    effectiveSalary * TAX_RATE;

                double pay =
                    effectiveSalary - tax;

                citizen.addMoney(pay);

                cityTreasury += tax;

                // Bank deposit (Step 5B)
                if (citizen.getWallet() >= 200) {

                    bank.deposit(
                        citizen.getName(),
                        100
                    );

                    citizen.withdrawMoney(100);
                }
            }
        }
    }

    // ---------------- Expenses (Step 5C) ----------------

    private void processExpenses() {

        for (Citizen citizen : populationManager.getCitizens()) {

            double totalExpenses =
                RENT + FOOD_COST;

            citizen.withdrawMoney(totalExpenses);

            // Rent goes to treasury
            cityTreasury += RENT;

            // Increase food demand
            resources.updateDemand("Food", 1);
        }
    }


    // Add to EconomyManager.java
    public void deductFunds(double amount) {
        if (cityTreasury >= amount) {
            cityTreasury -= amount;
        }
    }

    public double getCityTreasury() {
        return cityTreasury;
    }
    
    public void addFunds(double amount) {
        this.cityTreasury += amount;
    }
}
