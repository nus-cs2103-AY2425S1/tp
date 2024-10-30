package seedu.address.model.listing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    public void isValidArea() {
        // null area
        assertThrows(NullPointerException.class, () -> Area.isValidArea(null));

        // invalid area
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("911")); // less than 4 digits
        assertFalse(Price.isValidPrice("price")); // non-numeric
        assertFalse(Price.isValidPrice("9011p041")); // alphabets within digits
        assertFalse(Price.isValidPrice("9312 1534")); // spaces within digits

        // valid area
        assertTrue(Price.isValidPrice("9111")); // exactly 4 digits
        assertTrue(Price.isValidPrice("5000000"));
        assertTrue(Price.isValidPrice("124293842033123")); // large area
    }

    @Test
    public void equals() {
        Price price = new Price("3000", new BigDecimal(3000));

        // same values -> returns true
        assertTrue(price.equals(new Price("3000", new BigDecimal(3000))));

        // same object -> returns true
        assertTrue(price.equals(price));

        // null -> returns false
        assertFalse(price.equals(null));

        // different types -> returns false
        assertFalse(price.equals(5.0f));

        // different values -> returns false
        assertFalse(price.equals(new Price("2000", new BigDecimal(2000))));
    }
}
