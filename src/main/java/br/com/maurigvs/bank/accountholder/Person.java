package br.com.maurigvs.bank.accountholder;

import br.com.maurigvs.bank.account.CommercialAccount;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The concrete Account holder of Person type.
 */
@Entity
@Table(name = "accountholder_person")
@PrimaryKeyJoinColumn(name = "person_id")
public class Person extends AccountHolder {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(mappedBy = "authorizedUsers")
    private final List<CommercialAccount> commercialAccounts = new ArrayList<>();

    public Person() {
        super();
    }

    public Person(Long cpf, String firstName, String lastName) {
        super(cpf, firstName + " " + lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<CommercialAccount> getCommercialAccounts() {
        return commercialAccounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(getId(), person.getId())
                && Objects.equals(getFirstName(), person.getFirstName())
                && Objects.equals(getLastName(), person.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName());
    }
}