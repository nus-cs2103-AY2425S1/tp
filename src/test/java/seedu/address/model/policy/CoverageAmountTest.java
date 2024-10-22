package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CoverageAmountTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CoverageAmount(null));
    }

    @Test
    public void constructor_invalidCoverageAmount_throwsIllegalArgumentException() {
        // negative values
        assertThrows(IllegalArgumentException.class, () -> new CoverageAmount("-200.0"));
        assertThrows(IllegalArgumentException.class, () -> new CoverageAmount(-200.0));

        // non numerals
        assertThrows(IllegalArgumentException.class, () -> new CoverageAmount("foo"));

        // more than two decimal places
        assertThrows(IllegalArgumentException.class, () -> new CoverageAmount("200.555"));
        assertThrows(IllegalArgumentException.class, () -> new CoverageAmount(200.555));
    }

    @Test
    public void isValidCoverageAmountStringMethod() {
        // invalid coverage amount
        assertFalse(CoverageAmount.isValidCoverageAmount("-200.0"));
        assertFalse(CoverageAmount.isValidCoverageAmount("200.555"));
        assertFalse(CoverageAmount.isValidCoverageAmount("foo"));
        assertFalse(CoverageAmount.isValidCoverageAmount(""));

        // valid coverage amount
        assertTrue(CoverageAmount.isValidCoverageAmount("200.5"));
        assertTrue(CoverageAmount.isValidCoverageAmount("200.55"));
        assertTrue(CoverageAmount.isValidCoverageAmount("200"));
        assertTrue(CoverageAmount.isValidCoverageAmount("0"));
    }

    @Test
    public void isValidCoverageAmountDoubleMethod() {
        // invalid coverage amount
        assertFalse(CoverageAmount.isValidCoverageAmount(-200.0));
        assertFalse(CoverageAmount.isValidCoverageAmount(200.555));

        // valid coverage amount
        assertTrue(CoverageAmount.isValidCoverageAmount(200.5));
        assertTrue(CoverageAmount.isValidCoverageAmount(200.55));
        assertTrue(CoverageAmount.isValidCoverageAmount(200));
        assertTrue(CoverageAmount.isValidCoverageAmount(0));
    }

    @Test
    public void toStringMethod() {
        String expected = "200.00";
        assertEquals(expected, new CoverageAmount(200.00).toString());
        assertEquals(expected, new CoverageAmount(200.0).toString());
        assertEquals(expected, new CoverageAmount(200).toString());
    }

    @Test
    public void equalsMethod() {
        CoverageAmount coverageAmount = new CoverageAmount(200.00);

        // same values -> returns true
        assertTrue(coverageAmount.equals(new CoverageAmount(200.00)));

        // same object -> returns true
        assertTrue(coverageAmount.equals(coverageAmount));

        // null -> returns false
        assertFalse(coverageAmount.equals(null));

        // different types -> return false
        assertFalse(coverageAmount.equals(new PremiumAmount(200.00)));

        // different values -> return false
        assertFalse(coverageAmount.equals(new CoverageAmount(300.00)));

        // different decimal points but same value -> return true
        assertTrue(coverageAmount.equals(new CoverageAmount(200.0)));
    }
}
