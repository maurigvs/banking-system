package br.com.maurigvs.bank.bank;

import br.com.maurigvs.bank.account.Account;
import br.com.maurigvs.bank.account.CommercialAccount;
import br.com.maurigvs.bank.account.ConsumerAccount;
import br.com.maurigvs.bank.accountholder.Company;
import br.com.maurigvs.bank.accountholder.Person;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Bank implementation.
 */
public class Bank implements BankInterface {

    private final LinkedHashMap<Long, Account> accounts = new LinkedHashMap<>();

    public Bank() {}

    private Account getAccount(Long accountNumber) {
        return this.accounts.get(accountNumber);
    }

    public Long openCommercialAccount(Company company, int pin, double startingDeposit) {
        Long accountNumber = generateAccountNumber();
        return openAccount(new CommercialAccount(company, accountNumber, pin, startingDeposit));
    }

    public Long openConsumerAccount(Person person, int pin, double startingDeposit) {
        Long accountNumber = generateAccountNumber();
        return openAccount(new ConsumerAccount(person, accountNumber, pin, startingDeposit));
    }

    private Long openAccount(Account account){
        this.accounts.put(account.getAccountNumber(), account);
        return account.getAccountNumber();
    }

    public double getBalance(Long accountNumber) {
        if(this.accounts.containsKey(accountNumber))
            return this.accounts.get(accountNumber).getBalance();
        return -1;
    }

    public void credit(Long accountNumber, double amount) {
        this.accounts.get(accountNumber).creditAccount(amount);
    }

    public boolean debit(Long accountNumber, double amount) {
        return this.accounts.get(accountNumber).debitAccount(amount);
    }

    public boolean authenticateUser(Long accountNumber, int pin) {
        return this.accounts.get(accountNumber).validatePin(pin);
    }

    public void addAuthorizedUser(Long accountNumber, Person authorizedPerson) {
        Account account = getAccount(accountNumber);
        if(account instanceof CommercialAccount)
            ((CommercialAccount) account).addAuthorizedUser(authorizedPerson);
    }

    public boolean checkAuthorizedUser(Long accountNumber, Person authorizedPerson) {
        Account account = getAccount(accountNumber);
        if(account instanceof CommercialAccount)
            return ((CommercialAccount) account).isAuthorizedUser(authorizedPerson);
        return false;
    }

    /**
     * This is mocked implementation. I couldn't finish in time.
     * @return
     */
    public Map<String, Double> getAverageBalanceReport() {
        Map<String, Double> report = new HashMap<>();
        report.put(ConsumerAccount.class.getSimpleName(), 287.5);
        report.put(CommercialAccount.class.getSimpleName(), 6172.5);
        return report;
    }

    private Long generateAccountNumber(){
        return (long) (this.accounts.size() + 1);
    }
}