package br.com.maurigvs.bank.account;

import br.com.maurigvs.bank.accountholder.Person;

import java.util.Map;

/**
 * The public contract for an Account.
 */
public interface AccountService {

    /**
     * Validate the PIN.
     *
     * @param pinCode   The attempted PIN.
     * @return          <code>true</code> if attemptedPin matches the account;
     *                  <code>false</code> otherwise.
     */
    boolean validatePin(Long accountNumber, int pinCode);

    /**
     * Add amount to account balance.
     *
     * @param amount    The amount to be deposited into the account.
     */
    void credit(Long accountNumber, double amount);

    /**
     * Withdraw amount from account (if possible).
     *
     * @param amount    The amount to be withdrawn from the account.
     * @return          <code>true</code>  if amount could be withdrawn;
     *                  <code>false</code> otherwise.
     */
    boolean debit(Long accountNumber, double amount);

    /**
     * Add person to list of authorized users.
     *
     * @param person    The authorized user to be added to the account.
     */
    void addAuthorizedUser(Long commercialAccountNumber, Person person);

    /**
     * Verify if the person is part of the list of authorized users for this account.
     *
     * @param person    The user who is supposed to be authorized.
     * @return          <code>true</code> if person matches an authorized user in {@link #authorizedUsers};
     *                  <code>false</code> otherwise.
     */
    boolean isAuthorizedUser(Long commercialAccountNumber, Person person);

    double getBalance(Long accountNumber);

    Long openAccount(Account account);

    boolean exists(Long accountNumber);

    Map<Long, Account> mapAllAccounts();
}
