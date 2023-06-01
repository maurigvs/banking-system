package br.com.maurigvs.bank.model;

import java.util.ArrayList;
import java.util.List;

import br.com.maurigvs.bank.model.abst.Account;
import br.com.maurigvs.bank.model.abst.AccountHolder;

public class CommercialAccount extends Account {

    private final List<Person> authorizedPersons = new ArrayList<>();

    public CommercialAccount(Long accountNumber, AccountHolder accountHolder, int pin, double initialDeposit) {
        super(accountNumber, accountHolder, pin, initialDeposit);
    }

    protected void authorizePerson(Person person){
        this.authorizedPersons.add(person);
    }

    public boolean isPersonAuthorized(Person person){
        return this.authorizedPersons.contains(person);
    }
}
