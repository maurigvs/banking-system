package br.com.maurigvs.bank.bank;

import br.com.maurigvs.bank.account.*;
import br.com.maurigvs.bank.accountholder.Company;
import br.com.maurigvs.bank.accountholder.Person;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The Bank implementation.
 */
@Service
public class BankServiceImpl implements BankService {

    private final CommercialAccountService accountService;

    public BankServiceImpl(CommercialAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Long openCommercialAccount(Company company, int pinCode, double initialBalance) {
        return openAccount(new CommercialAccount(null, company, pinCode, initialBalance));
    }

    @Override
    public Long openConsumerAccount(Person person, int pinCode, double initialBalance) {
        return openAccount(new ConsumerAccount(null, person, pinCode, initialBalance));
    }

    @Override
    public boolean authenticateUser(Long accountNumber, int pinCode) {
        if(accountService.exists(accountNumber))
            return accountService.validatePin(accountNumber, pinCode);
        return false;
    }

    @Override
    public double getBalance(Long accountNumber) {
        if(accountService.exists(accountNumber))
            return accountService.getBalance(accountNumber);
        return -1;
    }

    @Override
    public void credit(Long accountNumber, double amount) {
        if(accountService.exists(accountNumber))
            accountService.credit(accountNumber, amount);
    }

    @Override
    public boolean debit(Long accountNumber, double amount) {
        if(accountService.exists(accountNumber))
            return accountService.debit(accountNumber, amount);
        return false;
    }

    @Override
    public void addAuthorizedUser(Long accountNumber, Person user) {
        if(accountService.exists(accountNumber))
            accountService.addAuthorizedUser(accountNumber, user);
    }

    @Override
    public boolean checkAuthorizedUser(Long accountNumber, Person user) {
        if(accountService.exists(accountNumber))
            return accountService.isAuthorizedUser(accountNumber, user);
        return false;
    }

    @Override
    public Map<String, Double> getAverageBalanceReport() {

        Map<String, Double> report = new HashMap<>();
        Map<String, List<Double>> reports = new HashMap<>();
        Map<Long, Account> accounts = accountService.mapAllAccounts();

        for (Map.Entry<Long, Account> accountEntry : accounts.entrySet()) {
            String accountType = accountEntry.getValue().getClass().getSimpleName();
            Double accountBalance = accountEntry.getValue().getBalance();

            if(reports.containsKey(accountType)){
                List<Double> balances = new ArrayList<>(reports.get(accountType));
                balances.add(accountBalance);
                reports.replace(accountType, balances);
            } else {
                reports.put(accountType, Collections.singletonList(accountBalance));
            }
        }

        for (Map.Entry<String, List<Double>> reportEntry : reports.entrySet()) {
            String accountType = reportEntry.getKey();
            int totalAccounts = reportEntry.getValue().size();
            Double totalBalance = (double) 0L;
            for (Double balance : reportEntry.getValue())
                totalBalance += balance;
            Double averageBalance = totalBalance / totalAccounts;
            report.put(accountType, averageBalance);
        }
        return report;
    }

    private Long openAccount(Account account){
        return accountService.openAccount(account);
    }
}