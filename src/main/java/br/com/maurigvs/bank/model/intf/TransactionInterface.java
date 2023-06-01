package br.com.maurigvs.bank.model.intf;

import java.util.List;

import br.com.maurigvs.bank.model.StatementItem;

public interface TransactionInterface {

    void credit(String description, double amount);

    boolean debit(String description, double amount);

    double getBalance();

    List<StatementItem> getStatement();
}
