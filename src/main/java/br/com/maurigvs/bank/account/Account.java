package br.com.maurigvs.bank.account;

import br.com.maurigvs.bank.accountholder.AccountHolder;

import javax.persistence.*;

/**
 * Abstract bank account.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "account")
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long number;

    @ManyToOne
    @JoinColumn(name = "accountholder_id")
    private AccountHolder accountHolder;

    @Column(name = "pin_code")
    private Integer pinCode;

    @Column(name = "balance")
    private Double balance;

    protected Account(Long number, AccountHolder accountHolder, Integer pinCode, Double initialBalance) {
        this.number = number;
        this.accountHolder = accountHolder;
        this.pinCode = pinCode;
        this.balance = initialBalance;
    }

    public Long getNumber() {
        return number;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(AccountHolder accountHolder) {
        this.accountHolder = accountHolder;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}