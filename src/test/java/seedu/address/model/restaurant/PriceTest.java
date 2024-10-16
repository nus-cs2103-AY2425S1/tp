package seedu.address.model.restaurant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        double invalidPrice = -1.23;
        assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
    }

    @Test
    public void isValidPrice() {
        // invalid prices
        assertFalse(Price.isValidPrice(-1.34));
        assertFalse(Price.isValidPrice(4566.5665));

        // valid prices
        assertTrue(Price.isValidPrice(342.00));
        assertTrue(Price.isValidPrice(233.22));
    }

    @Test
    public void equals() {
        Price price = new Price(9.99);

        // same values -> returns true
        assertTrue(price.equals(new Price(9.99)));

        // same object -> returns true
        assertTrue(price.equals(price));

        // null -> returns false
        assertFalse(price.equals(null));

        // different types -> returns false
        assertFalse(price.equals(5.0f));

        // different values -> returns false
        assertFalse(price.equals(new Price(9.95)));
    }

    @Test
    public void fromString() {
        // null string
        assertThrows(IllegalArgumentException.class, () -> Price.fromString(null));

        // empty string
        assertThrows(IllegalArgumentException.class, () -> Price.fromString(""));

        // invalid string
        assertThrows(IllegalArgumentException.class, () -> Price.fromString("abc"));

        // valid string
        Price price = Price.fromString("9.99");
        assertTrue(price.equals(new Price(9.99)));
    }
}
