package seedu.address.model.person;

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
    public void isValidOwed() {
        // null owed
        assertThrows(NullPointerException.class, () -> OwedAmount.isValidOwedAmount(null));

        // invalid oweds
        assertFalse(OwedAmount.isValidOwedAmount("")); // empty string
        assertFalse(OwedAmount.isValidOwedAmount(" ")); // spaces only
        assertFalse(OwedAmount.isValidOwedAmount("1.234")); // more than 2 decimal places
        assertFalse(OwedAmount.isValidOwedAmount("1.2.3")); // more than 1 decimal point
        assertFalse(OwedAmount.isValidOwedAmount("-1.23")); // negative number

        // valid oweds
        assertTrue(OwedAmount.isValidOwedAmount("1")); // 0 decimal places
        assertTrue(OwedAmount.isValidOwedAmount("1.2")); // 1 decimal place
        assertTrue(OwedAmount.isValidOwedAmount("123.23")); // 2 decimal places
        assertTrue(OwedAmount.isValidOwedAmount("0")); // 3 digits
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
