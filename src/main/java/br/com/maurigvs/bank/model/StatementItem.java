package br.com.maurigvs.bank.model;

import java.time.ZonedDateTime;

public class StatementItem {

    private final ZonedDateTime dateTime = ZonedDateTime.now();
    private final String description;
    private final double amount;

    public StatementItem(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "\n[" + dateTime + " | " + description + " | " + amount + "]";
    }
}
