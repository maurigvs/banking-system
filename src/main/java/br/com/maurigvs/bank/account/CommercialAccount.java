package br.com.maurigvs.bank.account;

import br.com.maurigvs.bank.accountholder.Company;
import br.com.maurigvs.bank.accountholder.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Account implementation for commercial (business) customers.
 * The account's holder is a {@link Company}.
 */
public class CommercialAccount extends Account {

    private final List<Person> authorizedUsers = new ArrayList<>();

    public CommercialAccount(Long number, Company company, int pinCode, double initialBalance) {
        super(number, company, pinCode, initialBalance);
    }
}