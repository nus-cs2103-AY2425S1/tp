package seedu.address.model.listing;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PriceTest {
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
