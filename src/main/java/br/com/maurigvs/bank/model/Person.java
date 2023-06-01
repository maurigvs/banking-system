package br.com.maurigvs.bank.model;

import java.util.Objects;

import br.com.maurigvs.bank.model.abst.AccountHolder;

public class Person extends AccountHolder {

    private final String nome;
    private final String sobrenome;

    public Person(String cpf, String nome, String sobrenome) {
        super(cpf, nome + " " + sobrenome);
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(getIdentificator(), person.getIdentificator())
                && Objects.equals(getNome(), person.getNome())
                && Objects.equals(getSobrenome(), person.getSobrenome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentificator(), getNome(), getSobrenome());
    }
}
