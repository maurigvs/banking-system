package br.com.maurigvs.bank.account;

import br.com.maurigvs.bank.accountholder.Person;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean validatePin(Long accountNumber, int pinCode) {
        Account account = getAccountByNumber(accountNumber);
        return account.getPinCode() == pinCode;
    }

    @Override
    public void credit(Long accountNumber, double amount) {
        Account account = getAccountByNumber(accountNumber);
        account.setBalance(account.getBalance() + amount);
    }

    @Override
    public boolean debit(Long accountNumber, double amount) {
        Account account = getAccountByNumber(accountNumber);
        if(account.getBalance() < amount)
            return false;
        account.setBalance(account.getBalance() + amount);
        return true;
    }

    @Override
    public void addAuthorizedUser(Long accountNumber, Person person) {
        CommercialAccount account = getCommercialAccountByNumber(accountNumber);
        account.getAuthorizedUsers().add(person);
    }

    @Override
    public boolean isAuthorizedUser(Long accountNumber, Person person) {
        CommercialAccount account = (CommercialAccount) getAccountByNumber(accountNumber);
        return account.getAuthorizedUsers().contains(person);
    }

    @Override
    public double getBalance(Long accountNumber) {
        return getAccountByNumber(accountNumber).getBalance();
    }

    @Override
    public Long openAccount(Account account) {
        return repository.save(account).getNumber();
    }

    @Override
    public boolean exists(Long accountNumber) {
        return repository.existsById(accountNumber);
    }

    @Override
    public Map<Long, Account> mapAllAccounts() {
        return repository.findAll().stream().collect(Collectors.toMap(Account::getNumber, Function.identity()));
    }

    private Account getAccountByNumber(Long accountNumber) {
        return repository.findByNumber(accountNumber).orElseThrow();
    }

    private CommercialAccount getCommercialAccountByNumber(Long accountNumber){
        Account account = getAccountByNumber(accountNumber);
        if(!(account instanceof CommercialAccount))
            throw new EntityNotFoundException("The account is not a commercial account");
        return (CommercialAccount) account;
    }
}