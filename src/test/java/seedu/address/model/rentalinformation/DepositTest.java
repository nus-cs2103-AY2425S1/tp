package seedu.address.model.rentalinformation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DepositTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Deposit(null));
    }

    @Test
    public void constructor_invalidDeposit_throwsIllegalArgumentException() {
        String invalidDeposit = "";
        assertThrows(IllegalArgumentException.class, () -> new Deposit(invalidDeposit));
    }

    @Test
    public void isValidDeposit() {
        // null address
        assertThrows(NullPointerException.class, () -> Deposit.isValidDeposit(null));

        // invalid deposit
        assertFalse(Deposit.isValidDeposit("")); // empty string
        assertFalse(Deposit.isValidDeposit(" ")); // spaces only
        assertFalse(Deposit.isValidDeposit("123.")); // no decimal values
        assertFalse(Deposit.isValidDeposit("123.2")); // 1 decimal place only
        assertFalse(Deposit.isValidDeposit("123.234")); // more than 2 decimal places
        assertFalse(Deposit.isValidDeposit("123.123.")); // multiple decimal points
        assertFalse(Deposit.isValidDeposit("abc")); // non-numeric
        assertFalse(Deposit.isValidDeposit("-11")); // negative value

        // valid deposit
        assertTrue(Deposit.isValidDeposit("5")); // small value
        assertTrue(Deposit.isValidDeposit("10.50")); // decimal value
        assertTrue(Deposit.isValidDeposit("100")); // no decimal value
        assertTrue(Deposit.isValidDeposit("100.20")); // decimal value
        assertTrue(Deposit.isValidDeposit("99999")); // large value
    }

    @Test
    public void equals() {
        Deposit deposit = new Deposit("100");

        // same values -> returns true
        assertTrue(deposit.equals(new Deposit("100")));

        // same object -> returns true
        assertTrue(deposit.equals(deposit));

        // null -> returns false
        assertFalse(deposit.equals(null));

        // different types -> returns false
        assertFalse(deposit.equals(1));

        // different values -> returns false
        assertFalse(deposit.equals(new Deposit("10")));

        // value is null
        // one null
        assertFalse(deposit.equals(new Deposit()));

        deposit = new Deposit();
        assertFalse(deposit.equals(new Deposit("10")));

        // both null
        assertTrue(deposit.equals(new Deposit()));
    }

    @Test
    public void hashcode() {
        Deposit deposit = new Deposit("100");

        // same deposit
        assertEquals(deposit.hashCode(), new Deposit("100").hashCode());

        // different deposit
        assertNotEquals(deposit.hashCode(), new Deposit("200").hashCode());
    }

    @Test
    public void toStringMethod() {
        Deposit deposit = new Deposit("100");

        // same deposit
        assertEquals(deposit.toString(), new Deposit("100").toString());

        // different deposit
        assertNotEquals(deposit.toString(), new Deposit("200").toString());
        assertNotEquals(deposit.toString(), new Deposit("100.35").toString());

        // null value in deposit
        deposit = new Deposit();
        assertEquals("null", deposit.toString());
    }
}
