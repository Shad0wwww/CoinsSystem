package dk.shadow.coins.account;

import java.util.UUID;

public class Account {
    UUID uuid;
    double amount;

    Account(UUID uuid, double amount) {
        this.uuid = uuid;
        this.amount = amount;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public double getAmount() {
        return this.amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void addAmount(double amount) {
        this.amount += amount;
    }
}
