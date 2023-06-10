package br.com.maurigvs.bank.transaction;

import br.com.maurigvs.bank.bank.BankService;
import br.com.maurigvs.bank.exception.AuthenticationException;
import br.com.maurigvs.bank.exception.MissingDataException;

/**
 * A bank transaction implementation.
 */
public class Transaction implements TransactionInterface {

    private final BankService bank;
    private final Long accountNumber;

    /**
     *
     * @param bank          The bank where the account is housed.
     * @param accountNumber The customer's account number.
     * @param pinCode       The PIN entered by the customer.
     * @throws AuthenticationException   Account validation failed.
     */
    public Transaction(BankService bank, Long accountNumber, int pinCode) {
        checkArguments(bank, accountNumber, pinCode);
        this.bank = bank;
        this.accountNumber = accountNumber;
        authenticateUser(pinCode);
    }

    @Override
    public double getBalance() {
        return bank.getBalance(accountNumber);
    }

    @Override
    public void credit(double amount) {
        bank.credit(accountNumber, amount);
    }

    @Override
    public boolean debit(double amount) {
        return bank.debit(accountNumber, amount);
    }

    private void checkArguments(BankService bank, Long accountNumber, int pinCode) {
        if(bank == null || accountNumber == null || pinCode == 0)
            throw new MissingDataException();
    }

    private void authenticateUser(int pinCode) {
        if(!this.bank.authenticateUser(this.accountNumber, pinCode))
            throw new AuthenticationException();
    }
}