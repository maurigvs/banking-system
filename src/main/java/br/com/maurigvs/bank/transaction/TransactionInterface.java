package br.com.maurigvs.bank.transaction;

/**
 * The public methods for the {@link Transaction} class
 */
public interface TransactionInterface {

    /**
     *
     * @return          The account balance for account {@link Transaction#accountNumber}
     */
    double getBalance();

    /**
     *
     * @param amount    The account to credit into account {@link Transaction#accountNumber}
     */
    void credit(double amount);

    /**
     *
     * @param amount    The amount to debit/withdraw from account {@link Transaction#accountNumber}
     * @return          <code>true</code> if amount could be withdraw;
     *                  <code>false</code> otherwise.
     */
    boolean debit(double amount);
}
