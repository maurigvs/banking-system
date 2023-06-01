package br.com.maurigvs.bank.model;

import java.util.Objects;

import br.com.maurigvs.bank.model.abst.AccountHolder;

public class Company extends AccountHolder {

    private final String nomeFantasia;

    public Company(String cnpj, String nomeFantasia) {
        super(cnpj, nomeFantasia);
        this.nomeFantasia = nomeFantasia;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company that = (Company) o;
        return Objects.equals(getIdentificator(), that.getIdentificator())
                && Objects.equals(getNomeFantasia(), that.getNomeFantasia());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentificator(), getNomeFantasia());
    }
}
