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

    /**
     * Add person to list of authorized users.
     *
     * @param person    The authorized user to be added to the account.
     */
    public void addAuthorizedUser(Person person) {
        authorizedUsers.add(person);
    }

    /**
     * Verify if the person is part of the list of authorized users for this account.
     *
     * @param person    The user who is supposed to be authorized.
     * @return          <code>true</code> if person matches an authorized user in {@link #authorizedUsers};
     *                  <code>false</code> otherwise.
     */
    public boolean isAuthorizedUser(Person person) {
        return authorizedUsers.contains(person);
    }
}