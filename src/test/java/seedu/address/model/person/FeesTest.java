
package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class FeesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Fees(null));
    }

    @Test
    public void constructor_invalidFees_throwsIllegalArgumentException() {
        String invalidFees = "-1029";
        assertThrows(IllegalArgumentException.class, () -> new Fees(invalidFees));
    }

    @Test
    public void isValidFees() {
        // null address
        assertThrows(NullPointerException.class, () -> Fees.isValidFees(null));

        // invalid fees
        assertFalse(Fees.isValidFees("-200")); // negative number
        assertFalse(Fees.isValidFees(" ")); // spaces only

        // valid fees
        assertTrue(Fees.isValidFees("250"));
        assertTrue(Fees.isValidFees("1")); // one character
        assertTrue(Fees.isValidFees("2500"));
    }

    @Test
    public void equals() {
        Fees fees = new Fees("250");

        // same values -> returns true
        assertTrue(fees.equals(new Fees("250")));

        // same object -> returns true
        assertTrue(fees.equals(fees));

        // null -> returns false
        assertFalse(fees.equals(null));

        // different types -> returns false
        assertFalse(fees.equals(5.0f));

        // different values -> returns false
        assertFalse(fees.equals(new Fees("300")));
    }
}
