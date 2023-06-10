package br.com.maurigvs.bank;

import br.com.maurigvs.bank.accountholder.Company;
import br.com.maurigvs.bank.accountholder.Person;
import br.com.maurigvs.bank.bank.BankService;
import br.com.maurigvs.bank.bank.BankServiceImpl;
import br.com.maurigvs.bank.transaction.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

class SampleTest {

    BankService bank;
    Long consumerAccountNr1;
    Long consumerAccountNr2;
    Long consumerAccountNr3;
    Long consumerAccountNr4;
    Long commercialAccountNr1;
    Long commercialAccountNr2;
    Person person1;
    Person person2;
    Person person3;

    @BeforeEach
    void setUp() {
        person1 = new Person(1L,"John", "Doe");
        person2 = new Person(2L,"Julia", "Doe");
        person3 = new Person(3L,"Daniel", "Smith");
        consumerAccountNr1 = bank.openConsumerAccount(person1, 1111, 0.0);
        consumerAccountNr2 = bank.openConsumerAccount(person2, 2222, 250.00);
        consumerAccountNr3 = bank.openConsumerAccount(person3, 3333, 600.00);
        consumerAccountNr4 = bank.openConsumerAccount(person3, 4444, 300.00);

        Company company1 = new Company(1L,"BigCorp1");
        Company company2 = new Company(2L,"BigCorp2");
        commercialAccountNr1 = bank.openCommercialAccount(company1, 1111, 0.0);
        commercialAccountNr2 = bank.openCommercialAccount(company2, 2222, 12345.00);

        bank.addAuthorizedUser(commercialAccountNr1, person1);
        bank.addAuthorizedUser(commercialAccountNr1, person2);
        bank.addAuthorizedUser(commercialAccountNr2, person3);
    }

    @AfterEach
    void tearDown() {
        bank = null;
        consumerAccountNr1 = null;
        consumerAccountNr2 = null;
        consumerAccountNr3 = null;
        commercialAccountNr1 = null;
        commercialAccountNr2 = null;
    }

    @Test
    void sequentialAccountNumberTest() {
        assertEquals(consumerAccountNr1 + 1, (long) consumerAccountNr2);
        assertEquals(consumerAccountNr2 + 1, (long) consumerAccountNr3);
        assertEquals(consumerAccountNr4 + 1, (long) commercialAccountNr1);
        assertEquals(commercialAccountNr1 + 1, (long) commercialAccountNr2);
    }

    @Test
    void validBalanceTest() {
        assertEquals(0.0, bank.getBalance(consumerAccountNr1), 0);
        assertEquals(250.00, bank.getBalance(consumerAccountNr2), 0);
        assertEquals(600.00, bank.getBalance(consumerAccountNr3), 0);
        assertEquals(0.0, bank.getBalance(commercialAccountNr1), 0);
        assertEquals(12345.00, bank.getBalance(commercialAccountNr2), 0);
    }

    /**
     * Valid account debit.
     */
    @Test
    void validDebitTest() {
        double amount = 200.0;
        assertTrue("Account " + consumerAccountNr2 + " should have sufficient funds.", bank.debit(consumerAccountNr2, amount));
        assertTrue("Account " + consumerAccountNr3 + " should have sufficient funds.", bank.debit(consumerAccountNr3, amount));
        assertTrue("Account " + commercialAccountNr2 + " should have sufficient funds.", bank.debit(commercialAccountNr2, amount));
    }

    /**
     * Test crediting accounts inside {@link BankServiceImpl}.
     */
    @Test
    void creditAccountTest() {

        double amount = 500.00;
        double initialBalance1 = bank.getBalance(consumerAccountNr1);
        double initialBalance2 = bank.getBalance(consumerAccountNr2);
        double initialBalance3 = bank.getBalance(consumerAccountNr3);
        double initialBalance4 = bank.getBalance(commercialAccountNr1);
        double initialBalance5 = bank.getBalance(commercialAccountNr2);

        bank.credit(consumerAccountNr1, amount);
        bank.credit(consumerAccountNr2, amount);
        bank.credit(consumerAccountNr3, amount);
        bank.credit(commercialAccountNr1, amount);
        bank.credit(commercialAccountNr2, amount);

        assertEquals(initialBalance1 + amount, bank.getBalance(consumerAccountNr1), 0);
        assertEquals(initialBalance2 + amount, bank.getBalance(consumerAccountNr2), 0);
        assertEquals(initialBalance3 + amount, bank.getBalance(consumerAccountNr3), 0);
        assertEquals(initialBalance4 + amount, bank.getBalance(commercialAccountNr1), 0);
        assertEquals(initialBalance5 + amount, bank.getBalance(commercialAccountNr2), 0);
    }

    @Test
    void transactionDebitTest() {
        Transaction transaction1 = new Transaction(bank, consumerAccountNr3, 3333);
        double amount = 500.0;

        assertTrue("Debit was unsuccessful.", transaction1.debit(amount));
        assertFalse("This transaction should have overdrawn the account.", transaction1.debit(amount));
        assertTrue("Debit was unsuccessful.", transaction1.debit(transaction1.getBalance()));
        assertEquals(0, transaction1.getBalance(), 0);
    }

    @Test
    void transactionCreditTest() {
        Transaction transaction1 = new Transaction(bank, consumerAccountNr3, 3333);
        double beforeDeposit1 = transaction1.getBalance();
        double amount = 9999999.0;
        transaction1.credit(amount);
        assertEquals(beforeDeposit1 + amount, transaction1.getBalance(), 0);
    }
}
