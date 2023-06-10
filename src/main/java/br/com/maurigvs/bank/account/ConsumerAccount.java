package br.com.maurigvs.bank.account;

import br.com.maurigvs.bank.accountholder.Person;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Account implementation for consumer (domestic) customers.
 * The account's holder is a {@link Person}.
 */
@Entity
@PrimaryKeyJoinColumn(name = "account_id")
@Table(name = "account_consumer")
public class ConsumerAccount extends Account {

    public ConsumerAccount(Long number, Person person, Integer pinCode, Double initialBalance) {
        super(number, person, pinCode, initialBalance);
    }
}