package br.com.maurigvs.bank.account;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
class AccountServiceImpl implements AccountService {

    protected final AccountRepository repository;

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

    Account getAccountByNumber(Long accountNumber) {
        return repository.findByNumber(accountNumber).orElseThrow();
    }
}