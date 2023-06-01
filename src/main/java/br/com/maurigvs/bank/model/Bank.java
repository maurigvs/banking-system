package br.com.maurigvs.bank.model;

import java.util.LinkedHashMap;
import java.util.List;

import br.com.maurigvs.bank.model.abst.Account;
import br.com.maurigvs.bank.model.intf.BankInterface;

public class Bank implements BankInterface {

    private final LinkedHashMap<Long, Account> accounts = new LinkedHashMap<>();

    @Override
    public Long openCommercialAccount(Company company, int pin, double initialAmount) {
        Long accountNumber = generateAccountNumber();
        openAccount(new CommercialAccount(accountNumber, company, pin, initialAmount));
        return accountNumber;
    }

    @Override
    public Long openConsumerAccount(Person person, int pin, double initialAmount) {
        Long accountNumber = generateAccountNumber();
        openAccount(new ConsumerAccount(accountNumber, person, pin, initialAmount));
        return accountNumber;
    }

    @Override
    public boolean authenticate(Long accountNumber, int pin) {
        if(this.accounts.containsKey(accountNumber))
            return this.accounts.get(accountNumber).validatePin(pin);
        return false;
    }

    @Override
    public void deposit(Long accountNumber, int pin, String description, double amount) {
        if(accounts.containsKey(accountNumber) && (authenticate(accountNumber, pin)))
            this.accounts.get(accountNumber).credit(description, amount);
    }

    @Override
    public boolean withdraw(Long accountNumber, int pin, String description, double valor) {
        if(accounts.containsKey(accountNumber) && (authenticate(accountNumber, pin)))
            return this.accounts.get(accountNumber).debit(description, valor);
        return false;
    }

    @Override
    public boolean transfer(Long originAccount, Long destinationAccount, int pin, String description, double amount) {
        if(accounts.containsKey(originAccount) &&
            accounts.containsKey(destinationAccount) &&
                authenticate(originAccount, pin) &&
                    (accounts.get(originAccount).debit(description,amount))){
            String accountHolderName = accounts.get(originAccount).getAccountHolder().getHolderName();
            accounts.get(destinationAccount).credit("Transferencia de " + accountHolderName, amount);
            return true;
        }
        return false;
    }

    @Override
    public double getBalance(Long accountNumber) {
        if(this.accounts.containsKey(accountNumber))
            return this.accounts.get(accountNumber).getBalance();
        return -1;
    }

    @Override
    public List<StatementItem> getStatement(Long accountNumber) {
        return accounts.get(accountNumber).getStatement();
    }

    public List<String> getAuthenticationLog(Long accountNumber){
        return accounts.get(accountNumber).getAuthenticationLog();
    }

    @Override
    public void authorizePerson(Long accountNumber, Person person) {
        if(this.accounts.containsKey(accountNumber)){
            Account account = this.accounts.get(accountNumber);
            if(account instanceof CommercialAccount)
                ((CommercialAccount) account).authorizePerson(person);
        }
    }

    @Override
    public boolean isPersonAuthorized(Long accountNumber, Person person) {
        if(this.accounts.containsKey(accountNumber)){
            Account account = this.accounts.get(accountNumber);
            if(account instanceof CommercialAccount)
                return ((CommercialAccount) account).isPersonAuthorized(person);
        }
        return false;
    }

    private void openAccount(Account account) {
        this.accounts.put(account.getAccountNumber(), account);
    }

    private Long generateAccountNumber(){
        return (long) (this.accounts.size() + 1);
    }
}
