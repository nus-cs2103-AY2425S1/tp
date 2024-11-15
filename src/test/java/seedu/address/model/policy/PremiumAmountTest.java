package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PremiumAmountTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PremiumAmount(null));
    }

    @Test
    public void constructor_invalidPremiumAmount_throwsIllegalArgumentException() {
        // negative values
        assertThrows(IllegalArgumentException.class, () -> new PremiumAmount("-200.0"));
        assertThrows(IllegalArgumentException.class, () -> new PremiumAmount(-200.0));

        // non numerals
        assertThrows(IllegalArgumentException.class, () -> new PremiumAmount("foo"));

        // more than two decimal places
        assertThrows(IllegalArgumentException.class, () -> new PremiumAmount("200.555"));
        assertThrows(IllegalArgumentException.class, () -> new PremiumAmount(200.555));
    }

    @Test
    public void isValidPremiumAmountStringMethod() {
        // invalid premium amount
        assertFalse(PremiumAmount.isValidPremiumAmount("-200.0"));
        assertFalse(PremiumAmount.isValidPremiumAmount("200.555"));
        assertFalse(PremiumAmount.isValidPremiumAmount("foo"));
        assertFalse(PremiumAmount.isValidPremiumAmount(""));

        // valid premium amount
        assertTrue(PremiumAmount.isValidPremiumAmount("200.5"));
        assertTrue(PremiumAmount.isValidPremiumAmount("200.55"));
        assertTrue(PremiumAmount.isValidPremiumAmount("200"));
        assertTrue(PremiumAmount.isValidPremiumAmount("0"));
    }

    @Test
    public void isValidPremiumAmountDoubleMethod() {
        // invalid premium amount
        assertFalse(PremiumAmount.isValidPremiumAmount(-200.0));
        assertFalse(PremiumAmount.isValidPremiumAmount(200.555));

        // valid premium amount
        assertTrue(PremiumAmount.isValidPremiumAmount(200.5));
        assertTrue(PremiumAmount.isValidPremiumAmount(200.55));
        assertTrue(PremiumAmount.isValidPremiumAmount(200));
        assertTrue(PremiumAmount.isValidPremiumAmount(0));
    }

    @Test
    public void toStringMethod() {
        String expected = "200.00";
        assertEquals(expected, new PremiumAmount(200.00).toString());
        assertEquals(expected, new PremiumAmount(200.0).toString());
        assertEquals(expected, new PremiumAmount(200).toString());
    }

    @Test
    public void equalsMethod() {
        PremiumAmount premiumAmount = new PremiumAmount(200.00);

        // same values -> returns true
        assertTrue(premiumAmount.equals(new PremiumAmount(200.00)));

        // same object -> returns true
        assertTrue(premiumAmount.equals(premiumAmount));

        // null -> returns false
        assertFalse(premiumAmount.equals(null));

        // different types -> return false
        assertFalse(premiumAmount.equals(new CoverageAmount(200.00)));

        // different values -> return false
        assertFalse(premiumAmount.equals(new PremiumAmount(300.00)));

        // different decimal points but same value -> return true
        assertTrue(premiumAmount.equals(new PremiumAmount(200.0)));
    }
}
