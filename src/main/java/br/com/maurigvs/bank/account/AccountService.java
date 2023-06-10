package br.com.maurigvs.bank.account;

import br.com.maurigvs.bank.accountholder.Person;

public class AccountService implements AccountInterface {

    @Override
    public boolean validatePin(Long accountNumber, int pinCode) {
        return false;
    }

    @Override
    public void credit(Long accountNumber, double amount) {

    }

    @Override
    public boolean debit(Long accountNumber, double amount) {
        return false;
    }

    @Override
    public void addAuthorizedUser(Long commercialAccountNumber, Person person) {

    }

    @Override
    public boolean isAuthorizedUser(Long commercialAccountNumber, Person person) {
        return false;
    }
}
