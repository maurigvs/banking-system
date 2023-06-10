package br.com.maurigvs.bank.account;

import br.com.maurigvs.bank.accountholder.AccountHolder;

/**
 * Abstract bank account.
 */
public abstract class Account {

    private final Long number;
    private final AccountHolder accountHolder;
    private final int pinCode;
    private double balance;

    protected Account(Long number, AccountHolder accountHolder, int pinCode, double initialBalance) {
        this.number = number;
        this.accountHolder = accountHolder;
        this.pinCode = pinCode;
        this.balance = initialBalance;
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
}