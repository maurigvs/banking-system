package br.com.maurigvs.bank.accountholder;

import br.com.maurigvs.bank.account.Account;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Account Holder.
 */
@Entity
@Table(name = "accountholder")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AccountHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String holderName;

    @OneToMany(mappedBy = "accountHolder")
    private final List<Account> accounts = new ArrayList<>();

    protected AccountHolder() {}

    protected AccountHolder(Long id, String holderName) {
        this.id = id;
        this.holderName = holderName;
    }

    public Long getId() {
        return id;
    }

    public String getHolderName() {
        return holderName;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}