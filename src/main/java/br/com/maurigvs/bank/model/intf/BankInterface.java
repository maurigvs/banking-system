package br.com.maurigvs.bank.model.intf;

import java.util.List;

import br.com.maurigvs.bank.model.Company;
import br.com.maurigvs.bank.model.Person;
import br.com.maurigvs.bank.model.StatementItem;

public interface BankInterface {

    Long openCommercialAccount(Company company, int pin, double initialAmount);

    Long openConsumerAccount(Person person, int pin, double initialAmount);

    boolean authenticate(Long accountNumber, int pin);

    void deposit(Long accountNumber, int pin, String description, double amount);

    boolean withdraw(Long accountNumber, int pin, String description, double amount);

    boolean transfer(Long originAccount, Long destinationAccount, int pin, String description, double amount);

    double getBalance(Long accountNumber);

    List<StatementItem> getStatement(Long accountNumber);

    void authorizePerson(Long accountNumber, Person personAutorizada);

    boolean isPersonAuthorized(Long accountNumber, Person person);
}
