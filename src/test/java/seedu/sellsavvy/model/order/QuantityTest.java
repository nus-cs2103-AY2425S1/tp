package seedu.sellsavvy.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class QuantityTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Quantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "";
        assertThrows(IllegalArgumentException.class, () -> new Quantity(invalidQuantity));
    }

    @Test
    public void isValidQuantity() {
        // null quantity value
        assertThrows(NullPointerException.class, () -> Quantity.isValidQuantity(null));

        // invalid quantity values
        assertFalse(Quantity.isValidQuantity("")); // empty string
        assertFalse(Quantity.isValidQuantity(" ")); // spaces only
        assertFalse(Quantity.isValidQuantity("01")); // contains leading zeros
        assertFalse(Quantity.isValidQuantity("0")); // zero
        assertFalse(Quantity.isValidQuantity("-1")); // negative number
        assertFalse(Quantity.isValidQuantity("0.1")); // non-integer
        assertFalse(Quantity.isValidQuantity("quantity")); // non-numeric
        assertFalse(Quantity.isValidQuantity("1z1")); // contains non-numeric characters
        assertFalse(Quantity.isValidQuantity("9312 1534")); // spaces within digits

        // valid quantity values
        assertTrue(Quantity.isValidQuantity("1"));
        assertTrue(Quantity.isValidQuantity("124293842033123")); // long numbers
    }

    @Test
    public void equals() {
        Quantity quantity = new Quantity("999");

        // same values -> returns true
        assertTrue(quantity.equals(new Quantity("999")));

        // same object -> returns true
        assertTrue(quantity.equals(quantity));

        // null -> returns false
        assertFalse(quantity.equals(null));

        // different types -> returns false
        assertFalse(quantity.equals(5.0f));

        // different values -> returns false
        assertFalse(quantity.equals(new Quantity("995")));
    }
}
