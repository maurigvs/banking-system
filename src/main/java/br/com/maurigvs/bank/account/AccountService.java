package br.com.maurigvs.bank.account;

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

    double getBalance(Long accountNumber);

    Long openAccount(Account account);

    boolean exists(Long accountNumber);

    Map<Long, Account> mapAllAccounts();
}
