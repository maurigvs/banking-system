package br.com.maurigvs.bank.model.abst;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.maurigvs.bank.model.StatementItem;
import br.com.maurigvs.bank.model.intf.AccountInterface;
import br.com.maurigvs.bank.model.intf.TransactionInterface;

public abstract class Account implements AccountInterface, TransactionInterface {

    private final Long accountNumber;

    private final AccountHolder accountHolder;

    private final int pin;

    private double balance;

    private final List<StatementItem> statement = new ArrayList<>();

    private final List<String> authenticationLog = new ArrayList<>();

    protected Account(Long accountNumber, AccountHolder accountHolder, int pin, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.pin = pin;
        credit("Depósito Inicial", initialDeposit);
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    @Override
    public boolean validatePin(int pin) {
        if(this.pin != pin)
            addtoAuthenticationLog("Failed authentication attempt");
        addtoAuthenticationLog("Pin authenticated successfully");
        return this.pin == pin;
    }

    @Override
    public void credit(String description, double amount) {
        balance += amount;
        addToStatement(description, amount);
    }

    @Override
    public boolean debit(String description, double amount) {
        if(balance < amount)
            return false;
        balance -= amount;
        addToStatement(description, amount * (-1));
        return true;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    @Override
    public List<StatementItem> getStatement() {
        return this.statement;
    }

    public List<String> getAuthenticationLog() {
        return this.authenticationLog;
    }

    private void addToStatement(String description, double amount){
        statement.add(new StatementItem(description, amount));
    }

    private void addtoAuthenticationLog(String description){
        authenticationLog.add(ZonedDateTime.now() + " - " + description + "\n");
    }
}
