package br.com.maurigvs.bank.account;

import br.com.maurigvs.bank.accountholder.Person;

/**
 * The public contract for an Commercial Account.
 */
public interface CommercialAccountService {

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
}
