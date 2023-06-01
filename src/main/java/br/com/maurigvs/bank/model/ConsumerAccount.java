package br.com.maurigvs.bank.model;

import br.com.maurigvs.bank.model.abst.Account;
import br.com.maurigvs.bank.model.abst.AccountHolder;

public class ConsumerAccount extends Account {

    public ConsumerAccount(Long accountNumber, AccountHolder accountHolder, int pin, double initialDeposit) {
        super(accountNumber, accountHolder, pin, initialDeposit);
    }
}
