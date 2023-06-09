package br.com.maurigvs.bank.account;

/**
 * The public contract for an Account.
 */
public interface AccountInterface {

    /**
     * Validate the PIN.
     *
     * @param pinCode   The attempted PIN.
     * @return          <code>true</code> if attemptedPin matches the account;
     *                  <code>false</code> otherwise.
     */
    boolean validatePin(int pinCode);

    /**
     * Add amount to account balance.
     *
     * @param amount    The amount to be deposited into the account.
     */
    void credit(double amount);

    /**
     * Withdraw amount from account (if possible).
     *
     * @param amount    The amount to be withdrawn from the account.
     * @return          <code>true</code>  if amount could be withdrawn;
     *                  <code>false</code> otherwise.
     */
    boolean debit(double amount);
}
