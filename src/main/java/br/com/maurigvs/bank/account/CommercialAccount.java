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

    public CommercialAccount(Company company, Long accountNumber, int pin, double startingDeposit) {
        super(company, accountNumber, pin, startingDeposit);
    }

    /**
     * Add person to list of authorized users.
     *
     * @param person The authorized user to be added to the account.
     */
    public void addAuthorizedUser(Person person) {
        this.authorizedUsers.add(person);
    }

    /**
     * Verify if the person is part of the list of authorized users for this account.
     *
     * @param person
     * @return <code>true</code> if person matches an authorized user in {@link #authorizedUsers}; <code>false</code> otherwise.
     */
    public boolean isAuthorizedUser(Person person) {
        return this.authorizedUsers.contains(person);
    }
}
