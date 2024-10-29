package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

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
    public void increaseValue() {
        OwedAmount owedAmount = new OwedAmount("10.00");

        OwedAmount increasedOwedAmount = owedAmount.increaseValue(5.00);
        assertEquals("15.00", increasedOwedAmount.toString());

        OwedAmount increasedOwedAmount2 = owedAmount.increaseValue(0.01);
        assertEquals("10.01", increasedOwedAmount2.toString());
    }

    @Test
    public void decreaseValue() {
        OwedAmount owedAmount = new OwedAmount("10.00");

        OwedAmount decreasedOwedAmount = owedAmount.decreaseValue(5.00);
        assertEquals("5.00", decreasedOwedAmount.toString());

        OwedAmount decreasedOwedAmount2 = owedAmount.decreaseValue(0.01);
        assertEquals("9.99", decreasedOwedAmount2.toString());

        OwedAmount decreasedOwedAmount3 = owedAmount.decreaseValue(10.00);
        assertEquals("0.00", decreasedOwedAmount3.toString());
    }

    @Test
    public void isGreater() {
        OwedAmount owedAmount = new OwedAmount("10.00");

        assertFalse(owedAmount.isGreater(5.00));

        assertTrue(owedAmount.isGreater(15.00));

        assertFalse(owedAmount.isGreater(10.00));
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
