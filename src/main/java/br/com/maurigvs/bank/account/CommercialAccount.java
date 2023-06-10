package br.com.maurigvs.bank.account;

import br.com.maurigvs.bank.accountholder.Company;
import br.com.maurigvs.bank.accountholder.Person;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Account implementation for commercial (business) customers.
 * The account's holder is a {@link Company}.
 */
@Entity
@PrimaryKeyJoinColumn(name = "account_id")
@Table(name = "account_commercial")
public class CommercialAccount extends Account {

    @ManyToMany
    @JoinTable(name = "account_commercial_person",
        joinColumns = @JoinColumn(name = "account_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id"))
    private final List<Person> authorizedUsers = new ArrayList<>();

    public CommercialAccount(Long number, Company company, Integer pinCode, Double initialBalance) {
        super(number, company, pinCode, initialBalance);
    }

    public List<Person> getAuthorizedUsers() {
        return authorizedUsers;
    }
}