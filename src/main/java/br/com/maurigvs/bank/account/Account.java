package br.com.maurigvs.bank.account;

import br.com.maurigvs.bank.accountholder.AccountHolder;

/**
 * Abstract bank account.
 */
public abstract class Account implements AccountInterface {

    private final Long number;
    private final AccountHolder accountHolder;
    private final int pinCode;
    private double balance;

    protected Account(Long number, AccountHolder accountHolder, int pinCode, double initialBalance) {
        this.number = number;
        this.accountHolder = accountHolder;
        this.pinCode = pinCode;
        credit(initialBalance);
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public Long getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public boolean validatePin(int pinCode) {
        return this.pinCode == pinCode;
    }

    @Override
    public void credit(double amount) {
        balance += amount;
    }

    @Override
    public boolean debit(double amount) {
        if(balance < amount)
            return false;
        balance -= amount;
        return true;
    }
}