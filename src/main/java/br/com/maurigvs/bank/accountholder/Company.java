package br.com.maurigvs.bank.accountholder;

public class Company extends AccountHolder {

    private final String companyName;

    public Company(int cnpj, String companyName) {
        super(cnpj);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}