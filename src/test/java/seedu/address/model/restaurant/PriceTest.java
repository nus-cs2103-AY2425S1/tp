package seedu.address.model.restaurant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
    }

    @Test
    public void isValidPrice() {
        // null price
        assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid price
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("91")); // non-$ characters
        assertFalse(Price.isValidPrice("$$$$$")); // out of range

        // valid price
        assertTrue(Price.isValidPrice("$"));
        assertTrue(Price.isValidPrice("$$"));
        assertTrue(Price.isValidPrice("$$$"));
        assertTrue(Price.isValidPrice("$$$$"));
    }

    @Test
    public void equals() {
        Price price = new Price("$$$");

        // same values -> returns true
        assertTrue(price.equals(new Price("$$$")));

        // same object -> returns true
        assertTrue(price.equals(price));

        // null -> returns false
        assertFalse(price.equals(null));

        // different types -> returns false
        assertFalse(price.equals(5.0f));

        // different values -> returns false
        assertFalse(price.equals(new Price("$$")));
    }

    @Test
    public void isRepresentedBy() {
        Price price = new Price("$$$");

        // same values -> returns true
        assertTrue(price.isRepresentedBy("$$$"));

        // different values -> returns false
        assertFalse(price.isRepresentedBy("$$"));
    }
}
