package br.com.maurigvs.bank.accountholder;

/**
 * Abstract Account Holder.
 */
public abstract class AccountHolder {

    private final int id;

    /**
     * @param id The holder unique ID.
     */
    protected AccountHolder(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}