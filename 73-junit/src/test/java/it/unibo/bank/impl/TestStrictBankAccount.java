package it.unibo.bank.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    private static final int DEF_USER_ID = 0;
    private static final int DEPO_OP_COUNT = 20;
    private static final double DEF_USER_BALANCE = 0;
    private static final double TEST_DEPO_AMOUNT = 100;
    private static final double TEST_NEG_WITHDROW_AMOUNT = 10;

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        mRossi = new AccountHolder("Mario", "Rossi", DEF_USER_ID);
        bankAccount = new StrictBankAccount(mRossi, DEF_USER_BALANCE);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(DEF_USER_BALANCE, bankAccount.getBalance());
        assertEquals(DEPO_OP_COUNT, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
        fail("Initialization test failed");
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        assertEquals(0, bankAccount.getTransactionsCount());
        for (int i = 1; i <= DEPO_OP_COUNT; i++) {
            bankAccount.deposit(mRossi.getUserID(), TEST_DEPO_AMOUNT);
            assertEquals(i, bankAccount.getTransactionsCount());
            assertEquals(TEST_DEPO_AMOUNT * i + DEF_USER_BALANCE, bankAccount.getBalance());
        }
        bankAccount.chargeManagementFees(mRossi.getUserID());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(
            TEST_DEPO_AMOUNT * DEPO_OP_COUNT + DEF_USER_BALANCE - MANAGEMENT_FEE 
            - TRANSACTION_FEE * DEPO_OP_COUNT, this.bankAccount.getBalance()
        );
        fail("Initialization test failed");
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        fail("To be implemented");
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        fail("To be implemented");
    }
}
