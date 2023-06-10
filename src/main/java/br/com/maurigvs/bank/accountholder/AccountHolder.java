package br.com.maurigvs.bank.accountholder;

import br.com.maurigvs.bank.account.Account;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Account Holder.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "accountholder")
public abstract class AccountHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    @OneToMany(mappedBy = "accountHolder")
    private final List<Account> accounts = new ArrayList<>();

    /**
     * @param id The holder unique ID.
     */
    protected AccountHolder(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}