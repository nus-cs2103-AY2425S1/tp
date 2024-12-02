package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;

public class OwedAmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OwedAmount(null));
    }

    @Test
    public void constructor_invalidOwed_throwsIllegalArgumentException() {
        String invalidOwedAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new OwedAmount(invalidOwedAmount));
    }

    @Test
    public void isValidOwedAmount() {
        // null owed
        assertThrows(NullPointerException.class, () -> OwedAmount.isValidOwedAmount(null));

        // invalid owedAmounts
        assertFalse(OwedAmount.isValidOwedAmount("")); // empty string
        assertFalse(OwedAmount.isValidOwedAmount(" ")); // spaces only
        assertFalse(OwedAmount.isValidOwedAmount("1.234")); // more than 2 decimal places
        assertFalse(OwedAmount.isValidOwedAmount("1.2.3")); // more than 1 decimal point
        assertFalse(OwedAmount.isValidOwedAmount("-1.23")); // negative number
        assertFalse(OwedAmount.isValidOwedAmount("10000000.00")); // more than max value

        // valid owedAmounts
        assertTrue(OwedAmount.isValidOwedAmount("1")); // 0 decimal places
        assertTrue(OwedAmount.isValidOwedAmount("1.2")); // 1 decimal place
        assertTrue(OwedAmount.isValidOwedAmount("123.23")); // 2 decimal places
        assertTrue(OwedAmount.isValidOwedAmount("0"));
        assertTrue(OwedAmount.isValidOwedAmount("9999999.99")); // boundary value
    }

    @Test
    public void isValidOwedAmount_double() {
        // null owed
        assertThrows(NullPointerException.class, () -> OwedAmount.isValidOwedAmount(null));

        // invalid owedAmounts
        assertFalse(OwedAmount.isValidOwedAmount(OwedAmount.MAX_VALUE + 1));
        assertFalse(OwedAmount.isValidOwedAmount(-1.5));

        // valid owedAmounts
        assertTrue(OwedAmount.isValidOwedAmount(OwedAmount.MAX_VALUE));
        assertTrue(OwedAmount.isValidOwedAmount(0.000));
    }

    @Test
    // EP: boundary value 0.01 after decreasing owed amount
    public void decreaseValue_boundary_value() throws CommandException {
        OwedAmount owedAmount = new OwedAmount("500.00");

        OwedAmount decreasedOwedAmount = owedAmount.decreaseValue(new SettleAmount("499.99"));
        assertEquals("0.01", decreasedOwedAmount.toString());
    }

    @Test
    public void decreaseValue_random_value() throws CommandException {
        // EP: random value that could cause off by 0.01 inaccuracy
        OwedAmount owedAmount = new OwedAmount("500.00");

        OwedAmount decreasedOwedAmount = owedAmount.decreaseValue(new SettleAmount("499.13"));
        assertEquals("0.87", decreasedOwedAmount.toString());
    }

    @Test
    // EP: Invalid value after decreasing owe amount
    public void decreaseValue_invalidAmount_throwCommandException() {
        OwedAmount owedAmount = new OwedAmount("10.00");

        assertThrows(CommandException.class, () -> owedAmount.decreaseValue(new SettleAmount("15.00")));
    }

    @Test
    public void isGreater() {
        OwedAmount owedAmount = new OwedAmount("10.00");

        assertFalse(owedAmount.isGreater(new SettleAmount("5.00")));

        assertTrue(owedAmount.isGreater(new SettleAmount("15.00")));
    }


    @Test
    public void toStringTest() {
        OwedAmount owedAmount = new OwedAmount("1.23");
        assertEquals("1.23", owedAmount.toString());

        OwedAmount owedAmount2 = new OwedAmount("1.0");
        assertEquals("1.00", owedAmount2.toString());

        OwedAmount owedAmount3 = new OwedAmount("0");
        assertEquals("0.00", owedAmount3.toString());
    }

    @Test
    public void equals() {
        OwedAmount owedAmount = new OwedAmount("1.23");
        OwedAmount sameOwedAmount = new OwedAmount("1.23");
        OwedAmount differentOwedAmount = new OwedAmount("1.24");

        // same values -> returns true
        assertTrue(owedAmount.equals(sameOwedAmount));

        // same object -> returns true
        assertTrue(owedAmount.equals(owedAmount));

        // null -> returns false
        assertFalse(owedAmount.equals(null));

        // different types -> returns false
        assertFalse(owedAmount.equals(5.0f));

        // different values -> returns false
        assertFalse(owedAmount.equals(differentOwedAmount));
    }

    @Test
    public void hashCodeTest() {
        OwedAmount owedAmount = new OwedAmount("1.23");
        OwedAmount sameOwedAmount = new OwedAmount("1.23");
        OwedAmount differentOwedAmount = new OwedAmount("1.24");

        assertEquals(owedAmount.hashCode(), sameOwedAmount.hashCode());
        assertNotEquals(owedAmount.hashCode(), differentOwedAmount.hashCode());

    }
}
