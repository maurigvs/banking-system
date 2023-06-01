package br.com.maurigvs.bank.model.abst;

public abstract class AccountHolder {

    private final String identificator;

    private final String holderName;

    protected AccountHolder(String identificator, String holderName) {
        this.identificator = identificator;
        this.holderName = holderName;
    }

    public String getIdentificator() {
        return identificator;
    }

    public String getHolderName() {
        return holderName;
    }
}
