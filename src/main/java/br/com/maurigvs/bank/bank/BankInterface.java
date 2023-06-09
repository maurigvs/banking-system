package br.com.maurigvs.bank.bank;

import br.com.maurigvs.bank.accountholder.Company;
import br.com.maurigvs.bank.accountholder.Person;

import java.util.Map;

/**
 * Bank interface defining the contract to interact with such an entity.
 */
public interface BankInterface {

    /**
     * Creates a new commercial account.
     *
     * @param company           The company associated with this account.
     * @param pinCode           Account PIN.
     * @param initialBalance    Initial funds deposited in this account.
     * @return                  The account number for the newly created account.
     */
    Long openCommercialAccount(Company company, int pinCode, double initialBalance);

    /**
     * Creates a new consumer account.
     *
     * @param person            The person associated with this account.
     * @param pinCode           Account PIN.
     * @param initialBalance    Initial funds deposited in this account.
     * @return                  The account number for the newly created account.
     */
    Long openConsumerAccount(Person person, int pinCode, double initialBalance);

    /**
     * Check if PIN matches the account.
     *
     * @param accountNumber     The number of the account to be authenticated.
     * @param pinCode           Account PIN to be used.
     * @return                  <code>true</code> if authentication was successful;
     *                          <code>false</code> otherwise.
     */
    boolean authenticateUser(Long accountNumber, int pinCode);

    /**
     * Retrieve balance for designated account.
     *
     * @param accountNumber     The number of the account.
     * @return                  The balance of the account.
     */
    double getBalance(Long accountNumber);

    /**
     * Perform a credit in the designated account.
     *
     * @param accountNumber     The number of the account to be credited.
     * @param amount            The amount of money being deposited.
     */
    void credit(Long accountNumber, double amount);

    /**
     * Perform a debit in the given account, if possible.
     *
     * @param accountNumber     The number of the account to be debited.
     * @param amount            The desired amount of the debit.
     * @return                  <code>true</code> if amount could be withdrawn;
     *                          <code>false</code> otherwise.
     */
    boolean debit(Long accountNumber, double amount);

    /**
     * Add new authorized user to a (commercial) account.
     *
     * @param accountNumber     The number of the commercial account.
     * @param user              The person who is authorized to operate the account.
     */
    void addAuthorizedUser(Long accountNumber, Person user);

    /**
     * Check user is authorized for designated (commercial) account.
     *
     * @param accountNumber     The number of the commercial account.
     * @param user              The person who is wants to operate the account.
     * @return                  <code>true</code> if person is authorized.
     *                          <code>false</code> if otherwise.
     */
    boolean checkAuthorizedUser(Long accountNumber, Person user);

    /**
     * Calculates the average account balance, grouped by Account type.
     * The keys in this map correspond to a string representation of the Account types that exist
     * (CommercialAccount, ConsumerAccount), with the values being the result of the calculation
     * of the average balance of all accounts of that type that are registered in the Bank.
     *
     * @return A Map with keys representing the Account type (e.g. 'ConsumerAccount', 'CommercialAccount')
     * and the values with the calculated average balance for those account types.
     */
    Map<String, Double> getAverageBalanceReport();
}
