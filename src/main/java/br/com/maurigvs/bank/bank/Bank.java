package br.com.maurigvs.bank.bank;

import br.com.maurigvs.bank.account.Account;
import br.com.maurigvs.bank.account.AccountService;
import br.com.maurigvs.bank.account.CommercialAccount;
import br.com.maurigvs.bank.account.ConsumerAccount;
import br.com.maurigvs.bank.accountholder.Company;
import br.com.maurigvs.bank.accountholder.Person;

import java.util.*;

/**
 * The Bank implementation.
 */
public class Bank implements BankInterface {

    private final LinkedHashMap<Long, Account> accounts = new LinkedHashMap<>();

    private final AccountService accountService = new AccountService();

    @Override
    public Long openCommercialAccount(Company company, int pinCode, double initialBalance) {
        return openAccount(new CommercialAccount(generateAccountNumber(), company, pinCode, initialBalance));
    }

    @Override
    public Long openConsumerAccount(Person person, int pinCode, double initialBalance) {
        return openAccount(new ConsumerAccount(generateAccountNumber(), person, pinCode, initialBalance));
    }

    @Override
    public boolean authenticateUser(Long accountNumber, int pinCode) {
        if(isValidAccount(accountNumber))
            return accountService.validatePin(accountNumber, pinCode);
        return false;
    }

    @Override
    public double getBalance(Long accountNumber) {
        if(isValidAccount(accountNumber))
            return accounts.get(accountNumber).getBalance();
        return -1;
    }

    @Override
    public void credit(Long accountNumber, double amount) {
        if(isValidAccount(accountNumber))
            accountService.credit(accountNumber, amount);
    }

    @Override
    public boolean debit(Long accountNumber, double amount) {
        if(isValidAccount(accountNumber))
            return accountService.debit(accountNumber, amount);
        return false;
    }

    @Override
    public void addAuthorizedUser(Long accountNumber, Person user) {
        if(isValidCommercialAccount(accountNumber))
            accountService.addAuthorizedUser(accountNumber, user);
    }

    @Override
    public boolean checkAuthorizedUser(Long accountNumber, Person user) {
        if(isValidCommercialAccount(accountNumber))
            return accountService.isAuthorizedUser(accountNumber, user);
        return false;
    }

    /**
     * This is mocked implementation. I couldn't finish in time.
     * @return
     */
    @Override
    public Map<String, Double> getAverageBalanceReport() {

        Map<String, Double> report = new HashMap<>();
        Map<String, List<Double>> reports = new HashMap<>();

        for (Map.Entry<Long, Account> accountEntry : accounts.entrySet()) {
            String accountType = accountEntry.getValue().getClass().getSimpleName();
            Double accountBalance = accountEntry.getValue().getBalance();

            if(reports.containsKey(accountType)){
                List<Double> balances = new ArrayList<>();
                balances.addAll(reports.get(accountType));
                balances.add(accountBalance);
                reports.replace(accountType, balances);
            } else {
                reports.put(accountType, Arrays.asList(accountBalance));
            }
        }

        for (Map.Entry<String, List<Double>> reportEntry : reports.entrySet()) {
            String accountType = reportEntry.getKey();
            int totalAccounts = reportEntry.getValue().size();
            Double totalBalance = new Double(0L);
            for (Double balance : reportEntry.getValue())
                totalBalance += balance;
            Double averageBalance = totalBalance / totalAccounts;
            report.put(accountType, averageBalance);
        }
        return report;
    }

    private Long openAccount(Account account){
        Long accountNumber = account.getNumber();
        accounts.put(accountNumber, account);
        return accountNumber;
    }

    private boolean isValidAccount(Long accountNumber){
        return accounts.containsKey(accountNumber);
    }

    private boolean isValidCommercialAccount(Long accountNumber){
        return isValidAccount(accountNumber) && accounts.get(accountNumber) instanceof CommercialAccount;
    }

    private Long generateAccountNumber() {
        return (long) (accounts.size() + 1);
    }
}