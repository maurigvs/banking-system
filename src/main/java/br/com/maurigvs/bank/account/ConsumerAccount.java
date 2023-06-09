package br.com.maurigvs.bank.account;

import br.com.maurigvs.bank.accountholder.Person;

/**
 * Account implementation for consumer (domestic) customers.
 * The account's holder is a {@link Person}.
 */
public class ConsumerAccount extends Account {

    public ConsumerAccount(Long number, Person person, int pinCode, double initialBalance) {
        super(number, person, pinCode, initialBalance);
    }
}