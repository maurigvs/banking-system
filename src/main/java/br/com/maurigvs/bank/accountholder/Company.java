package br.com.maurigvs.bank.accountholder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "accountholder_company")
@PrimaryKeyJoinColumn(name = "company_id")
public class Company extends AccountHolder {

    @Column(name = "company_name")
    private String legalName;

    public Company() {
        super();
    }

    public Company(Long cnpj, String businessName, String legalName) {
        super(cnpj, businessName);
        this.legalName = legalName;
    }

    public String getLegalName() {
        return legalName;
    }
}