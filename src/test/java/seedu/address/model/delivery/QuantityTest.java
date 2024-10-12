package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class QuantityTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Quantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "100abc";
        assertThrows(IllegalArgumentException.class, () -> new Quantity(invalidQuantity));
    }

    @Test
    public void isValidQuantity() {
        // null quantity
        assertThrows(NullPointerException.class, () -> Quantity.isValidQuantity(null));

        // blank cost
        assertFalse(Quantity.isValidQuantity("")); // empty string
        assertFalse(Quantity.isValidQuantity(" ")); // spaces only

        // invalid quantities
        assertFalse(Quantity.isValidQuantity("100")); // missing unit
        assertFalse(Quantity.isValidQuantity("abc kg")); // non-numeric quantity
        assertFalse(Quantity.isValidQuantity("100abc")); // no space between number and unit
        assertFalse(Quantity.isValidQuantity("100 grams")); // invalid unit (grams instead of g)
        assertFalse(Quantity.isValidQuantity("100g")); // no space between number and unit
        assertFalse(Quantity.isValidQuantity("1 liters   ")); // space at the end
        assertFalse(Quantity.isValidQuantity("  1 liters   ")); // space at the start

        // valid quantities
        assertTrue(Quantity.isValidQuantity("100 kg")); // valid kilogram quantity
        assertTrue(Quantity.isValidQuantity("50 g")); // valid gram quantity
        assertTrue(Quantity.isValidQuantity("200       liters")); // valid liters quantity with spaces in middle
        assertTrue(Quantity.isValidQuantity("300   ml")); // valid milliliters quantity with random spaces
        assertTrue(Quantity.isValidQuantity("300 units")); // valid milliliters quantity with random spaces

    }

    @Test
    public void equals() {
        Quantity quantity = new Quantity("100 kg");

        // same values -> returns true
        assertTrue(quantity.equals(new Quantity("100 kg")));

        // same object -> returns true
        assertTrue(quantity.equals(quantity));

        // null -> returns false
        assertFalse(quantity.equals(null));

        // different types -> returns false
        assertFalse(quantity.equals(5));

        // different values -> returns false
        assertFalse(quantity.equals(new Quantity("200 liters")));
    }
}
