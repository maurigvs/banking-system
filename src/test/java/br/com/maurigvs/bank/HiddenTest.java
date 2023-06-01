package br.com.maurigvs.bank;

import br.com.maurigvs.bank.account.CommercialAccount;
import br.com.maurigvs.bank.account.ConsumerAccount;
import br.com.maurigvs.bank.accountholder.Company;
import br.com.maurigvs.bank.accountholder.Person;
import br.com.maurigvs.bank.bank.Bank;
import br.com.maurigvs.bank.bank.BankInterface;
import br.com.maurigvs.bank.transaction.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HiddenTest {

    BankInterface bank;
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
        bank = new Bank();
        person1 = new Person("John", "Doe", 1);
        person2 = new Person("Julia", "Doe", 2);
        person3 = new Person("Daniel", "Smith", 3);
        consumerAccountNr1 = bank.openConsumerAccount(person1, 1111, 0.0);
        consumerAccountNr2 = bank.openConsumerAccount(person2, 2222, 250.00);
        consumerAccountNr3 = bank.openConsumerAccount(person3, 3333, 600.00);
        consumerAccountNr4 = bank.openConsumerAccount(person3, 4444, 300.00);

        Company company1 = new Company("BigCorp1", 1);
        Company company2 = new Company("BigCorp2", 2);
        commercialAccountNr1 = bank.openCommercialAccount(company1, 1111, 0.0);
        commercialAccountNr2 = bank.openCommercialAccount(company2, 2222, 12345.00);

        bank.addAuthorizedUser(commercialAccountNr1, person1);
        bank.addAuthorizedUser(commercialAccountNr1, person2);
        bank.addAuthorizedUser(commercialAccountNr2, person3);
    }

    @AfterEach
    void tearDown() throws Exception {
        bank = null;
        consumerAccountNr1 = null;
        consumerAccountNr2 = null;
        consumerAccountNr3 = null;
        commercialAccountNr1 = null;
        commercialAccountNr2 = null;
    }

    @Test
    void checkAuthorizedUserTest() {
        assertTrue(bank.checkAuthorizedUser(commercialAccountNr1, new Person("John", "Doe", 1)));
        assertTrue(bank.checkAuthorizedUser(commercialAccountNr1, person1));
        assertTrue(bank.checkAuthorizedUser(commercialAccountNr1, person2));
        assertTrue(bank.checkAuthorizedUser(commercialAccountNr2, person3));
    }

    @Test
    void checkUnauthorizedUserTest() {
        assertFalse(bank.checkAuthorizedUser(commercialAccountNr1, new Person("John", "Doe", 2)));
        assertFalse(bank.checkAuthorizedUser(commercialAccountNr1, null));
        assertFalse(bank.checkAuthorizedUser(commercialAccountNr1, person3));
        assertFalse(bank.checkAuthorizedUser(commercialAccountNr2, person1));
        assertFalse(bank.checkAuthorizedUser(consumerAccountNr1, person1));
        assertFalse(bank.checkAuthorizedUser(Long.MAX_VALUE, person1));
        assertFalse(bank.checkAuthorizedUser(null, person1));
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

    @Test
    void invalidBalanceTest() {
        assertEquals(-1.0, bank.getBalance(Long.MAX_VALUE), 0);
    }

    /**
     * Valid account debit.
     */
    @Test
    void validDebitTest() {
        double amount = 200.0;
        assertTrue(bank.debit(consumerAccountNr2, amount), "Account " + consumerAccountNr2 + " should have sufficient funds.");
        assertTrue(bank.debit(consumerAccountNr3, amount), "Account " + consumerAccountNr3 + " should have sufficient funds.");
        assertTrue(bank.debit(commercialAccountNr2, amount), "Account " + commercialAccountNr2 + " should have sufficient funds.");
    }

    /**
     * Fail account debit due to insufficient balance.
     */
    @Test
    void invalidDebitTest() {
        double amount = 200.0;
        assertFalse(bank.debit(consumerAccountNr1, amount), "Account " + consumerAccountNr1 + " should have insufficient funds.");
        assertFalse(bank.debit(commercialAccountNr1, amount), "Account " + commercialAccountNr1 + " should have insufficient funds.");
    }

    /**
     * Test crediting accounts inside {@link Bank}.
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

    /**
     * Tests {@link Transaction}: an attempt to access an account with an invalid PIN must throw an
     * Exception.
     *
     * @throws Exception Account validation failed.
     */
    @Test
    void transactionWithInvalidPinTest() {
        assertThrows(Exception.class, () -> new Transaction(bank, consumerAccountNr1, 9999));
    }

    @Test
    void transactionDebitTest() throws Exception {
        Transaction transaction1 = new Transaction(bank, consumerAccountNr3, 3333);
        double beforeDeposit1 = transaction1.getBalance();
        double amount = 500.0;

        assertTrue(transaction1.debit(amount), "Debit was unsuccessful.");
        assertFalse(transaction1.debit(amount), "This transaction should have overdrawn the account.");
        assertTrue(transaction1.debit(transaction1.getBalance()), "Debit was unsuccessful.");
        assertEquals(0, transaction1.getBalance(), 0);
    }

    @Test
    void transactionCreditTest() throws Exception {
        Transaction transaction1 = new Transaction(bank, consumerAccountNr3, 3333);
        double beforeDeposit1 = transaction1.getBalance();
        double amount = 9999999.0;
        transaction1.credit(amount);
        assertEquals(beforeDeposit1 + amount, transaction1.getBalance(), 0);
    }

    @Test
    void invalidBankTransactionTest() {
        assertThrows(Exception.class, () -> {
            new Transaction(null, consumerAccountNr3, 3333);
            new Transaction(bank, null, 3333);
        });
    }

    @Test
    void invalidAccountNrTransactionTest()  {
        assertThrows(Exception.class, () -> new Transaction(bank, null, 3333));
    }

    @Test
    void invalidPinTransactionTest() {
        assertThrows(Exception.class, () -> new Transaction(bank, consumerAccountNr3, Integer.MAX_VALUE));
    }

    @Test
    void getAverageBalanceReportTest() throws Exception {
        assertEquals(287.5, bank.getAverageBalanceReport().getOrDefault(ConsumerAccount.class.getSimpleName(), 0.0), 0);
        assertEquals(6172.5, bank.getAverageBalanceReport().getOrDefault(CommercialAccount.class.getSimpleName(), 0.0), 0);
    }
}
