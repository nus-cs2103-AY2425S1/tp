package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CostTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Cost(null));
    }

    @Test
    public void constructor_invalidCost_throwsIllegalArgumentException() {
        String invalidCost = "abc";
        assertThrows(IllegalArgumentException.class, () -> new Cost(invalidCost));
    }

    @Test
    public void isValidCost() {
        // null cost
        assertThrows(NullPointerException.class, () -> Cost.isValidCost(null));

        // blank cost
        assertFalse(Cost.isValidCost("")); // empty string
        assertFalse(Cost.isValidCost(" ")); // spaces only

        // invalid cost
        assertFalse(Cost.isValidCost("abc")); // non-numeric
        assertFalse(Cost.isValidCost("100.")); // missing decimal places
        assertFalse(Cost.isValidCost("100.123")); // more than two decimal places
        assertFalse(Cost.isValidCost("-50.00")); // negative number

        // valid cost
        assertTrue(Cost.isValidCost("100"));
        assertTrue(Cost.isValidCost("100.00"));
        assertTrue(Cost.isValidCost("0.99"));
    }

    @Test
    public void equals() {
        Cost cost = new Cost("100.00");

        // same values -> returns true
        assertTrue(cost.equals(new Cost("100.00")));

        // same object -> returns true
        assertTrue(cost.equals(cost));

        // null -> returns false
        assertFalse(cost.equals(null));

        // different types -> returns false
        assertFalse(cost.equals(100.0f));

        // different values -> returns false
        assertFalse(cost.equals(new Cost("50.00")));
    }
}
