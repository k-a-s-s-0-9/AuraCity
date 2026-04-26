package com.auracity.model.buildings;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Bank extends Building {
    
    // The Ledger: Tracks money for every citizen by their Name (or ID)
    private final Map<String, Double> accounts;
    private double totalVaultReserves;

    public Bank() {
        // Inherits health, ID, and base stats from the Enum!
        super(BuildingType.BANK);
        this.accounts = new ConcurrentHashMap<>();
        this.totalVaultReserves = 0.0;
    }

    // ---------------- Banking Logic ----------------

    public void deposit(String accountName, double amount) {
        if (amount <= 0) return;
        
        // Adds the amount to the existing balance, or creates a new account if missing
        accounts.merge(accountName, amount, Double::sum);
        totalVaultReserves += amount;
    }

    public boolean withdraw(String accountName, double amount) {
        double currentBalance = accounts.getOrDefault(accountName, 0.0);
        
        if (currentBalance >= amount) {
            accounts.put(accountName, currentBalance - amount);
            totalVaultReserves -= amount;
            return true; // Withdrawal successful
        }
        return false; // Insufficient funds
    }

    public double getAccountBalance(String accountName) {
        return accounts.getOrDefault(accountName, 0.0);
    }

    public double getTotalVaultReserves() {
        return totalVaultReserves;
    }
}