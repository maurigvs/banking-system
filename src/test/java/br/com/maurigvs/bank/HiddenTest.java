package br.com.maurigvs.bank;

import br.com.maurigvs.bank.account.CommercialAccount;
import br.com.maurigvs.bank.account.ConsumerAccount;
import br.com.maurigvs.bank.accountholder.Company;
import br.com.maurigvs.bank.accountholder.Person;
import br.com.maurigvs.bank.bank.Bank;
import br.com.maurigvs.bank.bank.BankInterface;
import br.com.maurigvs.bank.exception.AuthenticationException;
import br.com.maurigvs.bank.exception.MissingDataException;
import br.com.maurigvs.bank.transaction.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HiddenTest {

    BankInterface bank;
    Long johnDoeAccount;
    Long juliaDoeAccount;
    Long danielSmithAccount1;
    Long danielSmithAccount2;
    Long bigCorp1Account;
    Long bigCorp2Account;
    Person johnDoe;
    Person juliaDoe;
    Person danielSmith;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        johnDoe = new Person(1,"John", "Doe");
        juliaDoe = new Person(2,"Julia", "Doe");
        danielSmith = new Person(3,"Daniel", "Smith");

        johnDoeAccount = bank.openConsumerAccount(johnDoe, 1111, 0.0);
        juliaDoeAccount = bank.openConsumerAccount(juliaDoe, 2222, 250.00);
        danielSmithAccount1 = bank.openConsumerAccount(danielSmith, 3333, 600.00);
        danielSmithAccount2 = bank.openConsumerAccount(danielSmith, 4444, 300.00);

        Company bigCorp1 = new Company(1,"BigCorp1");
        Company bigCorp2 = new Company(2, "BigCorp2");

        bigCorp1Account = bank.openCommercialAccount(bigCorp1, 1111, 0.0);
        bigCorp2Account = bank.openCommercialAccount(bigCorp2, 2222, 12345.00);

        bank.addAuthorizedUser(bigCorp1Account, johnDoe);
        bank.addAuthorizedUser(bigCorp1Account, juliaDoe);
        bank.addAuthorizedUser(bigCorp2Account, danielSmith);
    }

    @AfterEach
    void tearDown() {
        bank = null;
        johnDoeAccount = null;
        juliaDoeAccount = null;
        danielSmithAccount1 = null;
        bigCorp1Account = null;
        bigCorp2Account = null;
    }

    @Test
    void checkAuthorizedUserTest() {
        assertTrue(bank.checkAuthorizedUser(bigCorp1Account, new Person(1, "John", "Doe")));
        assertTrue(bank.checkAuthorizedUser(bigCorp1Account, johnDoe));
        assertTrue(bank.checkAuthorizedUser(bigCorp1Account, juliaDoe));
        assertTrue(bank.checkAuthorizedUser(bigCorp2Account, danielSmith));
    }

    @Test
    void checkUnauthorizedUserTest() {
        assertFalse(bank.checkAuthorizedUser(bigCorp1Account, new Person(2, "John", "Doe")));
        assertFalse(bank.checkAuthorizedUser(bigCorp1Account, null));
        assertFalse(bank.checkAuthorizedUser(bigCorp1Account, danielSmith));
        assertFalse(bank.checkAuthorizedUser(bigCorp2Account, johnDoe));
        assertFalse(bank.checkAuthorizedUser(johnDoeAccount, johnDoe));
        assertFalse(bank.checkAuthorizedUser(Long.MAX_VALUE, johnDoe));
        assertFalse(bank.checkAuthorizedUser(null, johnDoe));
    }

    @Test
    void sequentialAccountNumberTest() {
        assertEquals(johnDoeAccount + 1, (long) juliaDoeAccount);
        assertEquals(juliaDoeAccount + 1, (long) danielSmithAccount1);
        assertEquals(danielSmithAccount2 + 1, (long) bigCorp1Account);
        assertEquals(bigCorp1Account + 1, (long) bigCorp2Account);
    }

    @Test
    void validBalanceTest() {
        assertEquals(0.0, bank.getBalance(johnDoeAccount), 0);
        assertEquals(250.00, bank.getBalance(juliaDoeAccount), 0);
        assertEquals(600.00, bank.getBalance(danielSmithAccount1), 0);
        assertEquals(0.0, bank.getBalance(bigCorp1Account), 0);
        assertEquals(12345.00, bank.getBalance(bigCorp2Account), 0);
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
        assertTrue(bank.debit(juliaDoeAccount, amount), "Account " + juliaDoeAccount + " should have sufficient funds.");
        assertTrue(bank.debit(danielSmithAccount1, amount), "Account " + danielSmithAccount1 + " should have sufficient funds.");
        assertTrue(bank.debit(bigCorp2Account, amount), "Account " + bigCorp2Account + " should have sufficient funds.");
    }

    /**
     * Fail account debit due to insufficient balance.
     */
    @Test
    void invalidDebitTest() {
        double amount = 200.0;
        assertFalse(bank.debit(johnDoeAccount, amount), "Account " + johnDoeAccount + " should have insufficient funds.");
        assertFalse(bank.debit(bigCorp1Account, amount), "Account " + bigCorp1Account + " should have insufficient funds.");
    }

    /**
     * Test crediting accounts inside {@link Bank}.
     */
    @Test
    void creditAccountTest() {
        double amount = 500.00;
        double initialBalance1 = bank.getBalance(johnDoeAccount);
        double initialBalance2 = bank.getBalance(juliaDoeAccount);
        double initialBalance3 = bank.getBalance(danielSmithAccount1);
        double initialBalance4 = bank.getBalance(bigCorp1Account);
        double initialBalance5 = bank.getBalance(bigCorp2Account);

        bank.credit(johnDoeAccount, amount);
        bank.credit(juliaDoeAccount, amount);
        bank.credit(danielSmithAccount1, amount);
        bank.credit(bigCorp1Account, amount);
        bank.credit(bigCorp2Account, amount);

        assertEquals(initialBalance1 + amount, bank.getBalance(johnDoeAccount), 0);
        assertEquals(initialBalance2 + amount, bank.getBalance(juliaDoeAccount), 0);
        assertEquals(initialBalance3 + amount, bank.getBalance(danielSmithAccount1), 0);
        assertEquals(initialBalance4 + amount, bank.getBalance(bigCorp1Account), 0);
        assertEquals(initialBalance5 + amount, bank.getBalance(bigCorp2Account), 0);
    }

    /**
     * Tests {@link Transaction}: an attempt to access an account with an invalid PIN must throw an
     * Exception.
     */
    @Test
    void transactionWithInvalidPinTest() {
        assertThrows(AuthenticationException.class, () -> new Transaction(bank, johnDoeAccount, 9999));
    }

    @Test
    void transactionDebitTest() throws Exception {
        Transaction transaction1 = new Transaction(bank, danielSmithAccount1, 3333);
        double amount = 500.0;
        assertTrue(transaction1.debit(amount), "Debit was unsuccessful.");
        assertFalse(transaction1.debit(amount), "This transaction should have overdrawn the account.");
        assertTrue(transaction1.debit(transaction1.getBalance()), "Debit was unsuccessful.");
        assertEquals(0, transaction1.getBalance(), 0);
    }

    @Test
    void transactionCreditTest() throws Exception {
        Transaction transaction1 = new Transaction(bank, danielSmithAccount1, 3333);
        double beforeDeposit1 = transaction1.getBalance();
        double amount = 9999999.0;
        transaction1.credit(amount);
        assertEquals(beforeDeposit1 + amount, transaction1.getBalance(), 0);
    }

    @Test
    void invalidBankTransactionTest() {
        assertAll(
            () -> assertThrows(MissingDataException.class, () -> new Transaction(null, danielSmithAccount1, 3333)),
            () -> assertThrows(MissingDataException.class, () -> new Transaction(bank, null, 3333))
        );
    }

    @Test
    void invalidAccountNrTransactionTest()  {
        assertThrows(MissingDataException.class, () -> new Transaction(bank, null, 3333));
    }

    @Test
    void invalidPinTransactionTest() {
        assertThrows(AuthenticationException.class, () -> new Transaction(bank, danielSmithAccount1, Integer.MAX_VALUE));
    }

    @Test
    void getAverageBalanceReportTest() {
        assertEquals(287.5, bank.getAverageBalanceReport().getOrDefault(ConsumerAccount.class.getSimpleName(), 0.0), 0);
        assertEquals(6172.5, bank.getAverageBalanceReport().getOrDefault(CommercialAccount.class.getSimpleName(), 0.0), 0);
    }
}
