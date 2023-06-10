package br.com.maurigvs.bank.accountholder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Abstract Account Holder.
 */
@MappedSuperclass
public abstract class AccountHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    /**
     * @param id The holder unique ID.
     */
    protected AccountHolder(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}