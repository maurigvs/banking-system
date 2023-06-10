package br.com.maurigvs.bank.accountholder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "accountholder_company")
public class Company extends AccountHolder {

    @Column(name = "company_name")
    private final String companyName;

    public Company(Long cnpj, String companyName) {
        super(cnpj);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}